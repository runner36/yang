package com.winchannel.core.importer;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.winchannel.core.bean.DataField;
import com.winchannel.core.utils.StringUtils;

/**
 * @author xianghui
 *
 */
public class ImpConfigurator {
	
	public static final Log log = LogFactory.getLog("ImpConfigurator");
	public static final URL CONF_FILE_PATH = ImpConfigurator.class.getResource("/config/import");
	
	private Class entityClass;
	private String[] key;
	private DataField[] keyField;
	private int startRow;
	private Class eventHandler;
	private String templateName;
	private List<DataField> fields = new ArrayList<DataField>();
	private DataField mlKey;
	private DataField mlParentKey;
	
	/**自定义导入器*/
	private Class customDataImporter;
	
	private ImpConfigurator(String fileName) throws Exception {
		init(fileName);
	}
	
	private void init(String fileName) throws Exception {
		String path = CONF_FILE_PATH.getFile() + fileName;
		SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read(path);
		
		Element tabEl = (Element) doc.getRootElement();
		this.entityClass = Class.forName(tabEl.attributeValue("class"));
		
		String keyval = tabEl.attributeValue("key");
		if (StringUtils.isNotBlank(keyval)) {
			this.key = keyval.split("\\,");
			this.keyField = new DataField[this.key.length];
		}

		this.startRow = StringUtils.isNotBlank(tabEl.attributeValue("startRow")) ? Integer.parseInt(tabEl.attributeValue("startRow")) : 0;
		if (StringUtils.isNotBlank(tabEl.attributeValue("start-row"))) {
			this.startRow = Integer.parseInt(tabEl.attributeValue("start-row"));
		}
		this.templateName = tabEl.attributeValue("template-name");
		if (StringUtils.isNotBlank(tabEl.attributeValue("eventHandler"))) {
			this.eventHandler = Class.forName(tabEl.attributeValue("eventHandler"));
		}
		
		//自定义导入器
		if (StringUtils.isNotBlank(tabEl.attributeValue("customDataImporter"))) {
			this.customDataImporter = Class.forName(tabEl.attributeValue("customDataImporter"));
		}
		
		List list = tabEl.element("columns").elements();
		String mlKeyCode = null;
		for (int i = 0; i < list.size(); i++) {
			Element columnEl = (Element) list.get(i);
			DataField field = new DataField();
			field.setName(columnEl.attributeValue("name"));
			field.setCode(columnEl.attributeValue("code"));
			field.setTitle(columnEl.attributeValue("title"));
			field.setType(columnEl.attributeValue("type"));
			field.setLength(StringUtils.isNotBlank(columnEl.attributeValue("length")) ? Integer.parseInt(columnEl.attributeValue("length")) : 0);
			field.setFormat(columnEl.attributeValue("format"));
			field.setRequired(Boolean.parseBoolean(columnEl.attributeValue("required")));
			field.setMask(columnEl.attributeValue("mask"));
			field.setFilter(columnEl.attributeValue("filter"));
			field.setValue(columnEl.attributeValue("value"));
			field.setAllowSkip(Boolean.parseBoolean(columnEl.attributeValue("allow-skip")));
			fields.add(field);
			
			if (key != null) {
				for (int j = 0; j < this.key.length; j++) {
					if (key[j].equals(field.getCode())) {
						keyField[j] = field;
					}
				}
			}
			
			if (StringUtils.isNotBlank(field.getCode())) {
				String[] c = field.getCode().split("\\.");
				if (this.entityClass.getDeclaredField(c[0]).getType().equals(this.entityClass)) {
					this.mlParentKey = field;
					mlKeyCode = c[1];
				}
			}
			
		}
		for (DataField field : fields) {
			if (field.getCode().equals(mlKeyCode)) {
				this.mlKey = field;
				break;
			}
		}
		
	}
	
	public static ImpConfigurator getConfigurator(String fileName) throws Exception {
		return new ImpConfigurator(fileName);
	}

	public Class getEntityClass() {
		return entityClass;
	}

	public List<DataField> getFields() {
		return fields;
	}

	public int getStartRow() {
		return startRow;
	}

	public String[] getKey() {
		return key;
	}

	public DataField[] getKeyField() {
		return keyField;
	}

	public DataField getMlKey() {
		return mlKey;
	}

	public DataField getMlParentKey() {
		return mlParentKey;
	}

	public String getTemplateName() {
		return templateName;
	}

	public Class getEventHandler() {
		return eventHandler;
	}

	public Class getCustomDataImporter() {
		return customDataImporter;
	}

}
