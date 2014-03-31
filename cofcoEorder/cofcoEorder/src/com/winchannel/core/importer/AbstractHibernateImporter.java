package com.winchannel.core.importer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.hibernate.CacheMode;

import com.winchannel.core.bean.DataField;
import com.winchannel.core.dao.HibernatePersister;
import com.winchannel.core.importer.iterator.DataIterator;
import com.winchannel.core.validator.DataValidator;
import com.winchannel.core.validator.ValidatorFactory;

/**
 * @author xianghui
 * 
 */
public abstract class AbstractHibernateImporter implements Importer {

	public static final Pattern PATTERN = Pattern
			.compile("(?<=\\$\\{)[\\w|\\.]*(?=\\})");
	public static final int MAX_ERROR_NUM = 200;

	protected ImpConfigurator conf;
	protected Map<String, Object> params;
	protected HibernatePersister persister;
	protected Map<String, DataValidator> validator;
	protected DataIterator iterator;

	protected Map<String, Object> objcache = new HashMap<String, Object>();

	protected StringBuilder errors;
	protected int errNum = 0;
	protected int succNum = 0;
	protected int skipNum = 0;
	protected ImpEventHandler event;
	protected boolean isTest;

	public AbstractHibernateImporter(ImpConfigurator conf,
			Map<String, Object> params) throws Exception {
		this.conf = conf;
		this.params = params;
		List<DataField> cols = conf.getFields();
		validator = new HashMap<String, DataValidator>();
		for (DataField f : cols) {
			if (StringUtils.isNotBlank(f.getCode())) {
				validator.put(f.getCode(), ValidatorFactory.createValidator(f));
			}
		}
	}

	public void init() {
		this.errors = new StringBuilder();
		this.errNum = 0;
		this.succNum = 0;
		this.skipNum = 0;
	}

	protected void setIterator(DataIterator iterator) throws Exception {
		this.iterator = iterator;
	}

	protected void setPersister() {
		this.persister = new HibernatePersister();
	}

	protected void setEventHandler() throws InstantiationException,
			IllegalAccessException {
		if (conf.getEventHandler() != null) {
			event = (ImpEventHandler) conf.getEventHandler().newInstance();
			event.init(conf, persister, params);
		}
	}

	public ImpInfo imp(DataIterator iterator) throws Exception {
		isTest = false;
		try {
			this.setIterator(iterator);
		} catch (Exception e) {
			throw e;
		}
		this.setPersister();
		this.setEventHandler();
		this.init();
		try {
			log.info("imp start ...");
			persister.beginTransaction();
			if (errNum == 0 && event != null)
				event.start();
			ImpInfo info = impData();
			if (errNum == 0 && event != null)
				event.end();
			persister.commit();
			log.info("imp end ...");
			return info;
		} catch (Exception e) {
			persister.rollback();
			throw e;
		} finally {
			persister.close();
			persister = null;
		}
	}

	public ImpInfo test(DataIterator iterator) throws Exception {
		isTest = true;
		this.setIterator(iterator);
		this.setPersister();
		this.setEventHandler();
		this.init();
		try {
			persister.beginTransaction();
			log.info("test imp start ...");
			if (errNum == 0 && event != null)
				event.start();
			return impData();
		} catch (Exception e) {
			throw e;
		} finally {
			log.info("test imp end ...");
			persister.close();
			persister = null;
		}
	}

	protected ImpInfo impData() throws Exception {
		int count = 1;
		this.persister.getSession().setCacheMode(CacheMode.GET);
		while (iterator.hasNext()) {
			Map<String, String> row = iterator.next();
			if ((iterator.getIndex()) >= conf.getStartRow()) {
				if (event != null) {
					try {
						event.startRow(row);
					} catch (Exception e) {
						errNum++;
						errors.append("行start：(" + iterator.getIndex() + ")"
								+ e.getMessage() + "<br>");
					}
				}
				Object bean = this.saveRow(row);
				if (event != null && bean != null) {
					try {
						event.endRow(row, bean);
					} catch (Exception e) {
						errNum++;
						errors.append("行end：(" + iterator.getIndex() + ")"
								+ e.getMessage() + "<br>");
					}
				}
				if (errNum > MAX_ERROR_NUM) {
					throw new Exception(errors.toString());
				}
				count++;
			}

			int b = 0;
			if (count > 10000) {
				b = 10000;
			} else {
				b = 1000;
			}
			if (count % 20 == 0) {
				persister.flush();
				persister.clear();
			}
			if (count % b == 0) {
				log.info("count : " + count + " ...");
			}
		}

		if (errNum > 0) {
			throw new Exception(errors.toString());
		}
		ImpInfo info = new ImpInfo();
		info.setSuccNum(succNum);
		info.setSkipNum(skipNum);
		return info;

	}

	protected Object saveRow(Map<String, String> row) throws Exception {
		DataField[] keyField = conf.getKeyField();
		Object bean = null;
		if (keyField != null) {
			bean = this.getKeyObject(keyField, bean, row);
		}

		if (bean == null) {
			bean = conf.getEntityClass().newInstance();
		}

		for (DataField field : conf.getFields()) {
			if (StringUtils.isBlank(field.getCode())) {
				continue;
			}

			String prop = field.getCode();
			try {
				Object v = this.getValue(field, bean, row);

				if (prop.indexOf(".") != -1) {
					String[] props = prop.split("\\.");
					if (v != null) {
						v = this.getRefObject(bean, props, v, field.getFilter());
					}
					prop = props[0];
				}
				PropertyUtils.setSimpleProperty(bean, prop, v);
			} catch (Exception e) {
				if (field.isAllowSkip()) {
					skipNum++;
					return null;
				} else {
					errors.append(e.getMessage() + " (行:"
							+ (iterator.getIndex()) + ", 列:" + field.getName()
							+ ", 值:" + row.get(field.getName()) + ")<br>");
					errNum++;
				}

			}
		}
		if (errNum == 0) {
			succNum++;
			save(bean);
		}
		return bean;
	}

	protected void save(Object object) {
		if (isTest) {
			return;
		}
		persister.save(object);
		persister.flush();
		persister.evict(object);

	}

	protected Object getBean(Class clazz, String[] key, Object[] value,
			String[] filter) throws Exception {
		StringBuilder sb = new StringBuilder("from ").append(
				clazz.getSimpleName()).append(" where 1=1");
		for (String k : key) {
			sb.append(" and ").append(k).append("=?");
		}
		for (String f : filter) {
			if (StringUtils.isNotBlank(f)) {
				sb.append(" and ").append(f);
			}
		}
		return persister.findUnique(sb.toString(), value);
	}
	/**
	 * @author shijingru
	 * @如果key中参数对应的code有filter时，执行sql不应该有filter条件，因为路径不存在
	 * （例如，key中一参数为"baseDictItem.itemCode"，
	 * code中filter为"baseDict.dictId='prodSTRU'",在MdmDistributorAddress.class中没有baseDict.dictId路径）
	 * @param clazz,key,value,filter
	 * @return
	 * @throws Exception
	 */
	protected Object getKeyBean(Class clazz, String[] key, Object[] value,
			String[] filter) throws Exception {
		StringBuilder sb = new StringBuilder("from ").append(
				clazz.getSimpleName()).append(" where 1=1");
		for (String k : key) {
			sb.append(" and ").append(k).append("=?");
		}
		return persister.findUnique(sb.toString(), value);
	}
	
	protected Object getKeyObject(DataField[] keyField, Object bean,
			Map<String, String> row) throws Exception {
		if (isTest) {
			return null;
		}
		try {
			String[] c = new String[keyField.length];
			Object[] v = new Object[keyField.length];
			String[] f = new String[keyField.length];
			for (int j = 0; j < keyField.length; j++) {
				c[j] = keyField[j].getCode();
				v[j] = this.getValue(keyField[j], bean, row);
				f[j] = keyField[j].getFilter();
			}
			bean = this.getKeyBean(conf.getEntityClass(), c, v, f);
		} catch (Exception e) {
		}
		return bean;
	}

	protected Object getRefObject(Object bean, String[] prop, Object value,
			String filter) throws Exception {
		String objKey = prop[0] + "." + prop[1] + "." + value;
		Object obj = objcache.get(objKey);
		if (obj == null) {
			Class propClass = PropertyUtils.getPropertyType(bean, prop[0]);
			obj = this.getBean(propClass, new String[] { prop[1] },
					new Object[] { value }, new String[] { filter });
			if (obj != null) {
				objcache.put(objKey, obj);
			}
		}
		if (obj == null) {
			throw new Exception("关联的对象不存在！");
		}
		return obj;
	}

	protected Object getValue(DataField field, Object bean,
			Map<String, String> row) throws Exception {
		String value = row.get(field.getName());
		if (StringUtils.isNotBlank(field.getValue())) {
			value = this.eval(new Object[] { row, bean, params },
					field.getValue());
		}

		DataValidator dv = validator.get(field.getCode());
		Object v = null;
		if (dv != null) {
			v = dv.validate(value);
		}
		if ("".equals(v)) {
			v = null;
		}
		return v;
	}

	protected String eval(Object[] objects, String exp) {
		if (StringUtils.isBlank(exp)) {
			return "";
		}

		Matcher matcher = PATTERN.matcher(exp);
		while (matcher.find()) {
			String name = matcher.group();

			Object result = null;
			for (Object obj : objects) {
				try {
					result = PropertyUtils.getProperty(obj, name);
				} catch (Exception e) {
				}
				if (result != null) {
					break;
				}
			}
			exp = StringUtils.replace(exp, "${" + name + "}",
					(result == null ? "" : result.toString()));
		}
		return exp == null ? "" : exp;

	}

	public void template(HttpServletResponse response) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFRow row = sheet.createRow(0);

		HSSFFont font = wb.createFont();
		// 自定义字体颜色为灰色 RGB
		int[] rgb = new int[] { 127, 127, 127 };
		HSSFPalette palette = wb.getCustomPalette();
		palette.setColorAtIndex(HSSFColor.BLACK.index, (byte) rgb[0],
				(byte) rgb[1], (byte) rgb[2]);
		setFont(font, HSSFColor.BLACK.index);

		HSSFCellStyle headerStyle = wb.createCellStyle();
		setCellStyle(font, headerStyle);

		HSSFFont reqFont = wb.createFont();
		setFont(reqFont, HSSFColor.RED.index);

		HSSFCellStyle reqHeaderStyle = wb.createCellStyle();
		setCellStyle(reqFont, reqHeaderStyle);

		HSSFFont blueFont = wb.createFont();
		setFont(blueFont, HSSFColor.BLUE.index);

		HSSFCellStyle blueHeaderStyle = wb.createCellStyle();
		setCellStyle(blueFont, blueHeaderStyle);

		List<DataField> fields = conf.getFields();
		for (short i = 0; i < fields.size(); i++) {
			DataField field = fields.get(i);
			if (StringUtils.isNotBlank(field.getName())) {
				HSSFCell cell = row
						.createCell(Integer.parseInt(field.getName()) - 1);
				if (field.isRequired()) {
					cell.setCellStyle(reqHeaderStyle);
				} else if (StringUtils.isNotBlank(field.getCode())) {
					cell.setCellStyle(blueHeaderStyle);
				} else {
					cell.setCellStyle(headerStyle);
				}
				cell.setCellValue(fields.get(i).getTitle());
				sheet.setColumnWidth(i,
						field.getTitle().getBytes().length * 280);
			}
		}

		String fileName = conf.getTemplateName();
		if (StringUtils.isBlank(fileName)) {
			fileName = "template.xls";
		}
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes("GBK"), "ISO-8859-1") + ".xls");
		response.setHeader("Cache-Control",
				"must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));
		wb.write(response.getOutputStream());
	}

	private void setFont(HSSFFont reqFont, short color) {
		reqFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		reqFont.setColor(color);
		reqFont.setFontName(HSSFFont.FONT_ARIAL);
		reqFont.setFontHeightInPoints((short) 8);
	}

	private void setCellStyle(HSSFFont reqFont, HSSFCellStyle reqHeaderStyle) {
		reqHeaderStyle.setFont(reqFont);
		reqHeaderStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		reqHeaderStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		reqHeaderStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		reqHeaderStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		reqHeaderStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		reqHeaderStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		reqHeaderStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		reqHeaderStyle.setRightBorderColor(HSSFColor.BLACK.index);
		reqHeaderStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		reqHeaderStyle.setTopBorderColor(HSSFColor.BLACK.index);
		reqHeaderStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		reqHeaderStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	}

}
