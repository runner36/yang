package com.winchannel.mdm.conversion.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.beanutils.PropertyUtils;

import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.service.BaseDictManager;
import com.winchannel.core.dao.HibernateEntityDao;
import com.winchannel.core.exception.BusinessException;
import com.winchannel.core.utils.Page;
import com.winchannel.mdm.conversion.model.MdmUnitConversion;
import com.winchannel.mdm.product.model.MdmProduct;
import com.winchannel.mdm.product.service.MdmProductManager;
import com.winchannel.mdm.util.string.StringUtils;

public class MdmConversionManager extends HibernateEntityDao<MdmUnitConversion> {

	private BaseDictManager baseDictManager;
	private MdmProductManager mdmProductManager;

	public MdmProductManager getMdmProductManager() {
		return mdmProductManager;
	}

	public void setMdmProductManager(MdmProductManager mdmProductManager) {
		this.mdmProductManager = mdmProductManager;
	}

	public BaseDictManager getBaseDictManager() {
		return baseDictManager;
	}

	public void setBaseDictManager(BaseDictManager baseDictManager) {
		this.baseDictManager = baseDictManager;
	}

	public int saveConversion(MdmUnitConversion conversionObj) {
		if (getCountConv(conversionObj) == 0) {
			super.save(conversionObj);
			return 1;
		} else {
			return -1;
		}
	}

	public void delConvById(Long convId) {
		super.deleteById(convId);
	}

	public void delConvByProdCode(String prodCode) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from MDM_UNIT_CONVERSION  where PROD_CODE='").append(prodCode).append("'");
		super.executeSqlUpdate(sql.toString());
	}

	public void addConv(String prodCode, String unit1[], String convVal[],
			String unit2[]) {
		if(super.getSession()!=null)
			super.getSession().clear();
		if (prodCode != null && unit1 != null) {
			for (int i = 0; i < unit1.length; i++) {
				MdmUnitConversion obj = new MdmUnitConversion();
				obj.setConvUnit1Val(Long.valueOf(1));
				obj.setConvUnit2Val(Double.valueOf(convVal[i]));
				obj.setProdCode(prodCode);
				obj.setConvUnit1Id((baseDictManager.get(Long.valueOf(unit1[i]))));
				obj.setConvUnit2Id(baseDictManager.get(Long.valueOf(unit2[i])));
				obj.setRemark(obj.getConvUnit1Id().getItemName() + " (1"
						+ obj.getConvUnit1Id().getItemName() + "="
						+ obj.getConvUnit2Val()
						+ obj.getConvUnit2Id().getItemName() + ")");
				super.save(obj);
			}
		}
	}

	public MdmUnitConversion getConvById(Long ConvId) {
		return super.findUniqueEntity("from MdmUnitConversion where convId=?",
				ConvId);
	}

	public List<MdmUnitConversion> getUnitConversionListByProdCode(
			String prodCode) {
		return super.find("from MdmUnitConversion where prodCode=?", prodCode);
	}

	public List<?> getConversionUnit(String prodCode) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT  bdi.DICT_ITEM_ID,bdi.ITEM_NAME FROM MDM_UNIT_CONVERSION muc LEFT JOIN BASE_DICT_ITEM bdi ON bdi.DICT_ITEM_ID=muc.CONV_UNIT1_ID WHERE muc.PROD_CODE='")
				.append(prodCode)
				.append("'GROUP BY bdi.DICT_ITEM_ID,bdi.ITEM_NAME");
		return super.executeSqlQuery(sql.toString());
	}

	public int getCountConv(MdmUnitConversion conversionObj) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT count(conv_id) FROM MDM_UNIT_CONVERSION d WHERE d.CONV_UNIT1_ID=")
				.append(conversionObj.getConvUnit1Id().getDictItemId())
				.append(" AND d.CONV_UNIT2_ID=")
				.append(conversionObj.getConvUnit2Id().getDictItemId())
				.append(" AND d.PROD_CODE='")
				.append(conversionObj.getProdCode()).append("'");
		if (conversionObj.getConvId() != null && conversionObj.getConvId() != 0) {
			sql.append(" and conv_id!=").append(conversionObj.getConvId());
		}
		Object[] obj = executeUniqueSqlQuery(sql.toString());
		return Integer.valueOf(String.valueOf(obj[0]));
	}

	public MdmUnitConversion getConvObj(MdmUnitConversion conversionObj) {
		return super
				.findUniqueEntity(
						"from MdmUnitConversion where prodCode=? and convUnit1Id.dictItemId=? and convUnit2Id.dictItemId=?",
						conversionObj.getProdCode(), conversionObj
								.getConvUnit1Id().getDictItemId(),
						conversionObj.getConvUnit2Id().getDictItemId());
	}

	public int getCountConv(String prodCode, long CONV_UNIT1_ID) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select count(conv_id)  from  MDM_UNIT_CONVERSION where  PROD_CODE='")
				.append(prodCode).append("'").append("  and (CONV_UNIT1_ID=")
				.append(CONV_UNIT1_ID).append(" or CONV_UNIT2_ID=")
				.append(CONV_UNIT1_ID).append(")");
		Object[] obj = executeUniqueSqlQuery(sql.toString());
		return Integer.valueOf(String.valueOf(obj[0]));
	}

	public int getCountConv(String prodCode, long conv_unit1_id,
			long conv_unit2_id) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select count(conv_id)  from  MDM_UNIT_CONVERSION where  PROD_CODE='")
				.append(prodCode).append("'").append("  and CONV_UNIT1_ID=")
				.append(conv_unit1_id).append(" and  CONV_UNIT2_ID=")
				.append(conv_unit2_id);
		Object[] obj = executeUniqueSqlQuery(sql.toString());
		return Integer.valueOf(String.valueOf(obj[0]));
	}

	public List getConvertList(Page page) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT muc.PROD_CODE,mp.prod_name,muc.CONV_UNIT1_VAL,bdi.ITEM_NAME,muc.CONV_UNIT2_VAL,bdi1.ITEM_NAME FROM MDM_UNIT_CONVERSION muc ");
		sql.append(" INNER JOIN MDM_PRODUCT mp ON mp.prod_code=muc.PROD_CODE ");
		sql.append(" LEFT JOIN BASE_DICT_ITEM bdi ON bdi.DICT_ITEM_ID=muc.CONV_UNIT1_ID  ");
		sql.append(" LEFT JOIN BASE_DICT_ITEM bdi1 ON bdi1.DICT_ITEM_ID=muc.CONV_UNIT2_ID ");
		sql.append(" where 1=1 " + this.makeSqlAuthExpByOrg(page, "org"));

		if (!StringUtils.isNull(page.get("prodCode_"))) {
			sql.append(" and muc.PROD_CODE like '" + page.get("prodCode_"))
					.append("%'");
		}

		if (!StringUtils.isNull(page.get(Page.SORT))) {
			sql.append(" order by " + page.get(Page.SORT)
			// .replace("5", "bdi1.item_name")
			// .replace("4", "muc.conv_unit2_val")
			// .replace("3", "bdi.item_name")
			// .replace("2", "muc.conv_unit1_val")
					.replace("1", "mp.prod_name").replace("0", "muc.PROD_CODE"));
		}
		return this.executeSqlQuery(sql.toString(), page);
	}

	/**
	 * 导入Excel
	 * 
	 * @param f
	 * @param optUser
	 * @param baseDictManager
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<?> saveExcel(InputStream f, String optUser) throws Exception {
		HashMap hashMap = new HashMap();
		ArrayList errMsgList = new ArrayList();
		ArrayList beanList = new ArrayList();
		try {
			Workbook wb = Workbook.getWorkbook(f);
			Sheet sheet = wb.getSheet(0);
			int rows = sheet.getRows();
			for (int i = 1; i < rows; i++) {
				MdmUnitConversion mdmUnitConversion = new MdmUnitConversion();
				String errMsgStr = "";
				MdmProduct mdmProduct = null;
				// 产品编码
				if (!StringUtils.isNull(sheet.getCell(0, i).getContents()
						.trim())) {
					mdmProduct = mdmProductManager
							.getProductByProdCode(StringUtils.trim(sheet
									.getCell(0, i).getContents().trim()));
					if (mdmProduct != null) {
						mdmUnitConversion.setProdCode(mdmProduct.getProdCode());
					} else {
						errMsgStr += "(" + i
								+ ",0)：<font color='#FF0000'>产品编码（"
								+ sheet.getCell(0, i).getContents()
								+ "）未找到&nbsp;&nbsp;&nbsp;&nbsp;</font>";
					}
				} else {
					errMsgStr += "("
							+ i
							+ ",0)：<font color='#FF0000'>产品编码不能为空&nbsp;&nbsp;&nbsp;&nbsp;</font>";
				}
				// 单位1
				if (!StringUtils.isNull(sheet.getCell(1, i).getContents()
						.trim())) {
					BaseDictItem obj = super
							.findUniqueEntity(
									BaseDictItem.class,
									" from BaseDictItem where dict_id='prodUnit' and  itemName=?",
									sheet.getCell(1, i).getContents().trim());
					if (obj != null && obj.getDictItemId().longValue() > 0)
						mdmUnitConversion.setConvUnit1Id(obj);
					else {
						errMsgStr += "(" + i
								+ ",1)：<font color='#FF0000'>单位1未找到("
								+ sheet.getCell(1, i).getContents()
								+ ")&nbsp;&nbsp;&nbsp;&nbsp;</font>";
					}
				} else {
					errMsgStr += "("
							+ i
							+ ",1)：<font color='#FF0000'>单位1不能为空&nbsp;&nbsp;&nbsp;&nbsp;</font>";
				}
				// 值
				if (!StringUtils.isNull(sheet.getCell(2, i).getContents()
						.trim())) {
					if (StringUtils.isNumericByPattern(sheet.getCell(2, i)
							.getContents().trim())) {
						mdmUnitConversion.setConvUnit2Val(new Double(sheet
								.getCell(2, i).getContents().trim()));
					} else {
						errMsgStr += "(" + i + ",2)：<font color='#FF0000'>值（"
								+ sheet.getCell(2, i).getContents()
								+ "）必须为数字&nbsp;&nbsp;&nbsp;&nbsp;</font>";
					}
				} else {
					errMsgStr += "("
							+ i
							+ ",2)：<font color='#FF0000'>值不能为空&nbsp;&nbsp;&nbsp;&nbsp;</font>";
				}
				// 单位2
				if (!StringUtils.isNull(sheet.getCell(3, i).getContents()
						.trim())) {
					BaseDictItem obj = super
							.findUniqueEntity(
									BaseDictItem.class,
									" from BaseDictItem where dict_id='prodUnit' and  itemName=?",
									sheet.getCell(3, i).getContents().trim());
					if (obj != null && obj.getDictItemId().longValue() > 0)
						mdmUnitConversion.setConvUnit2Id(obj);
					else
						errMsgStr += "(" + i
								+ ",3)：<font color='#FF0000'>单位2未找到("
								+ sheet.getCell(3, i).getContents()
								+ ")&nbsp;&nbsp;&nbsp;&nbsp;</font>";
				} else {
					errMsgStr += "("
							+ i
							+ ",3)：<font color='#FF0000'>单位2不能为空&nbsp;&nbsp;&nbsp;&nbsp;</font>";
				}
				if (mdmProduct != null
						&& mdmUnitConversion.getConvUnit1Id() != null
						&& mdmUnitConversion.getConvUnit2Id() != null) {
					if (mdmProduct.getProdCountUnit() != null
							&& mdmProduct.getProdBaseUnit() != null) {
						if (mdmUnitConversion.getConvUnit1Id().getDictItemId() == mdmProduct
								.getProdBaseUnit().getDictItemId()
								|| mdmUnitConversion.getConvUnit2Id()
										.getDictItemId() == mdmProduct
										.getProdBaseUnit().getDictItemId()) {
							mdmUnitConversion.setConvUnit1Val(Long.valueOf(1));
							if ((mdmUnitConversion.getConvUnit1Id()
									.getDictItemId() == mdmProduct
									.getProdBaseUnit().getDictItemId() && mdmUnitConversion
									.getConvUnit2Id().getDictItemId() == mdmProduct
									.getProdCountUnit().getDictItemId())
									|| (mdmUnitConversion.getConvUnit2Id()
											.getDictItemId() == mdmProduct
											.getProdBaseUnit().getDictItemId() && mdmUnitConversion
											.getConvUnit1Id().getDictItemId() == mdmProduct
											.getProdCountUnit().getDictItemId())) {
								if (hashMap.get(mdmProduct.getProdCode()) == null) {
									hashMap.put(mdmProduct.getProdCode(), "1");
								} else {
									hashMap.remove(mdmProduct.getProdCode());
									hashMap.put(mdmProduct.getProdCode(), "1");
								}
							} else {
								if (hashMap.get(mdmProduct.getProdCode()) == null) {
									hashMap.put(mdmProduct.getProdCode(), "0");
								}
							}
						} else {
							errMsgStr += "("
									+ i
									+ ",行)：<font color='#FF0000'>产品："
									+ mdmProduct.getProdCode()
									+ "单位1"
									+ sheet.getCell(1, i).getContents()
									+ " 或单位2"
									+ sheet.getCell(3, i).getContents()
									+ " 未包含基本单位 ("
									+ mdmProduct.getProdBaseUnit()
											.getItemName()
									+ ")&nbsp;&nbsp;&nbsp;&nbsp;</font>";
						}
					} else {
						errMsgStr += "("
								+ i
								+ ",行)：<font color='#FF0000'>产品:"
								+ mdmProduct.getProdCode()
								+ "基本单位或产品统计单位为空&nbsp;&nbsp;&nbsp;&nbsp;</font>";
					}
				}
				// 如果没有那么导入
				if (!StringUtils.isNull(errMsgStr))
					errMsgList.add(errMsgStr + "<br>");
				else {
					beanList.add(mdmUnitConversion);
				}
			}
			if (errMsgList == null || errMsgList.size() <= 0) {
				if (beanList != null && beanList.size() > 0) {
					for (int k = 0; k < beanList.size(); k++) {
						StringBuffer errMsgStr = new StringBuffer();
						MdmUnitConversion mdmUnitConversion = (MdmUnitConversion) beanList
								.get(k);
						String prodCode = mdmUnitConversion.getProdCode();
						MdmProduct mdmProduct = mdmProductManager
								.getProductByProdCode(prodCode);
						if (prodCode != null && prodCode.length() > 0) {
							if (hashMap.get(prodCode) != null) {
								if ("0".equals(hashMap.get(prodCode).toString())) {
									if (this.getCountConv(prodCode, mdmProduct
											.getProdBaseUnit().getDictItemId(),
											mdmProduct.getProdCountUnit()
													.getDictItemId()) > 0
											|| this.getCountConv(prodCode,
													mdmProduct
															.getProdCountUnit()
															.getDictItemId(),
													mdmProduct
															.getProdBaseUnit()
															.getDictItemId()) > 0) {
										continue;

									} else {
										errMsgStr
												.append("<font color='#FF0000'>产品编码：")
												.append(prodCode)
												.append(" 缺少统计单位（"
														+ mdmProduct
																.getProdCountUnit()
																.getItemName()
														+ "）到基本单位("
														+ mdmProduct
																.getProdBaseUnit()
																.getItemName()
														+ ")的转换关系！</font>");
										errMsgList.add(errMsgStr + "<br>");
									}
								}
							}
						}
					}
				}
			}
			hashMap = null;
			// 如果没有错误，那么再写入数据库
			if (errMsgList == null || errMsgList.size() <= 0) {
				for (int j = 0; j < beanList.size(); j++) {
					MdmUnitConversion mdmUnitConversion = (MdmUnitConversion) beanList.get(j);
					MdmUnitConversion mdmUnitConversionDB = this.getConvObj(mdmUnitConversion);
					if (mdmUnitConversionDB != null) {
						Long convId = mdmUnitConversionDB.getConvId();
						//增加如果指示转换值发生变化则更新MAPPING表的更新时间
						if(mdmUnitConversionDB.getConvUnit2Val()!=mdmUnitConversion.getConvUnit2Val()){
							impCompareUnitConvert(mdmUnitConversionDB);
						}
						PropertyUtils.copyProperties(mdmUnitConversionDB,mdmUnitConversion);
						mdmUnitConversionDB.setConvId(convId);
						this.save(mdmUnitConversionDB);
					} else {
						this.save(mdmUnitConversion);
					}

				}
				errMsgList.add("<font color='#FF0000'>导入成功 <br></font>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			errMsgList.add("<font color='#FF0000'>打开的模板不对 <br></font>");
		}
		return errMsgList;
	}
	/**
	 * @param prodCode
	 * 导入时否更新 产品MAPPING表
	 */
	public void impCompareUnitConvert(MdmUnitConversion mdmUnitConversionDB){
		StringBuffer sql=new StringBuffer();
		sql.append("UPDATE DMS_PROD_MAPPING SET UPDATED=getdate() WHERE TARGET_PROD_CODE='")
		.append(mdmUnitConversionDB.getProdCode()).append("' and ( TARGET_UNIT_ID=").append(mdmUnitConversionDB.getConvUnit1Id().getDictItemId())
		.append(" or TARGET_UNIT_ID=").append(mdmUnitConversionDB.getConvUnit2Id().getDictItemId()).append(")");
		super.executeSqlUpdate(sql.toString());
	}
	
	public void compareUnitConvert(String prodCode, String unit1[],
			String convVal[], String unit2[]) {
		int count=0;
		String message = null;
		//如果相同传过来的和已存在的单位转换关系相同则不需要处理
		List<MdmUnitConversion> unitConvertList = getUnitConversionListByProdCode(prodCode);
		List<MdmUnitConversion> changUnitConerList = new ArrayList<MdmUnitConversion>();
		//如果以前不存在直接返回 
		if(unitConvertList!=null && unitConvertList.size()>0){
			//传入的和要修改的是否都相同
			count = compareNewAndOldUnitConvert(unit1, convVal, unit2, count,unitConvertList,changUnitConerList);
		}
		if(count>0){
			// 先找到此产品在mapping 表里被 MAPPING 的单位，然后看新传入的关系是不是都包含这些单位，如果不包含则抛出异常
			List<Object[]> mappUnitList = getMappingHaveUint(prodCode);
			HashMap<String, String> mappUnitMap = mappingUnitChangeToMap(mappUnitList);
			// 判断此次传入的转换关系单位 是否都包含MAPPING过的单位
			if (mappUnitList != null && mappUnitList.size() > 0) {
				message = valDateMappinUint(unit1, unit2, mappUnitList);
			}
			mappUnitList=null;
			if (message == null || message.length() == 0) {
				updateMapping(prodCode, mappUnitMap, changUnitConerList);
			}else
			{
				throw new BusinessException(message);
			}
		}
		unitConvertList=null;
		changUnitConerList=null;
	}

	private int compareNewAndOldUnitConvert(String[] unit1, String[] convVal,
			String[] unit2, int count, List<MdmUnitConversion> unitConvertList,List<MdmUnitConversion> changUnitConerList) {
		HashMap<String,String> hmap=new HashMap<String,String>();
		if(unit1!=null && unit1.length>0){
			for(int i=0;i<unit1.length;i++){
				hmap.put(unit1[i]+convVal[i]+unit2[i], unit1[i]+convVal[i]+unit2[i]);
			}
		}
		for(MdmUnitConversion obj:unitConvertList){
			String varStr=obj.getConvUnit1Id().getDictItemId()+""+obj.getConvUnit2Val()+""+obj.getConvUnit2Id().getDictItemId();
			if (hmap == null || hmap.size() == 0) {
				count = unitConvertList.size();
				break;
			} else {
				if (hmap.get(varStr) == null) {
					changUnitConerList.add(obj);
					count++;
				}
			}
		}
		return count;
	}

	private void updateMapping(String prodCode,
			HashMap<String, String> mappUnitMap,
			List<MdmUnitConversion> changUnitConerList) {
		for (MdmUnitConversion obj : changUnitConerList) {
			updateMappingByObj(prodCode, mappUnitMap, obj);
		}
	}

	private void updateMappingByObj(String prodCode,
			HashMap<String, String> mappUnitMap, MdmUnitConversion obj) {
		// 更新MAPpING 表里 Mapping 到此产品-此单位的更新时间
		if (mappUnitMap != null && mappUnitMap.size() > 0) {
			if (mappUnitMap.get(String.valueOf(obj.getConvUnit1Id().getDictItemId())) != null
					|| mappUnitMap.get(String.valueOf(obj.getConvUnit2Id()
							.getDictItemId())) != null) {
				StringBuffer sql = new StringBuffer();
				sql.append(
						"UPDATE DMS_PROD_MAPPING SET UPDATED=getdate() WHERE TARGET_PROD_CODE='")
						.append(prodCode).append("' ");
				if (mappUnitMap.get(String.valueOf(obj.getConvUnit1Id().getDictItemId())) != null && mappUnitMap.get(String.valueOf(obj.getConvUnit2Id().getDictItemId())) != null) {
					sql.append(" and (TARGET_UNIT_ID=")
							.append(obj.getConvUnit1Id().getDictItemId())
							.append(" or TARGET_UNIT_ID=")
							.append(obj.getConvUnit2Id().getDictItemId()).append(")");
				} else if (mappUnitMap.get(String.valueOf(obj.getConvUnit1Id().getDictItemId())) != null) {
					sql.append(" and TARGET_UNIT_ID=").append(
							obj.getConvUnit1Id().getDictItemId());
				} else if (String.valueOf(mappUnitMap.get(obj.getConvUnit2Id()
						.getDictItemId())) != null) {
					sql.append(" and TARGET_UNIT_ID=").append(
							obj.getConvUnit2Id().getDictItemId());
				}
				super.executeSqlUpdate(sql.toString());
			}
		}
	}

	private String valDateMappinUint(String[] unit1, String[] unit2,
			List<Object[]> mappUnitList) {
		StringBuffer message = new StringBuffer();
		for (Object[] objs : mappUnitList) {
			
			String mapUnitId =String.valueOf(objs[0]);
			String mapUnitName=String.valueOf(objs[1]);
			if (unit1 != null && unit1.length > 0) {
				int count=0;
				for (int i = 0; i < unit1.length; i++) {
					if (mapUnitId.equals(unit1[i]) || mapUnitId.equals(unit2[i])){
						count++;
						break;
					}
				}
				if(count==0)
				{
					message.append(mapUnitName).append(",");
				}
			} else {
				message.append(mapUnitName).append(",");
			}
		}
		if (message != null && message.length() > 0) {
			message.append(" 被MAPPING过请先删除MAPPING关系");
		}
		return message.toString();
	}

	/**
	 * @param prodCode
	 * @return 查找Mapping 表中此产品 几个单位被Mapping
	 */
	private List<Object[]> getMappingHaveUint(String prodCode) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TARGET_UNIT_ID,item.ITEM_NAME  FROM DMS_PROD_MAPPING d")
			.append(" LEFT JOIN BASE_DICT_ITEM item ON item.DICT_ITEM_ID=d.TARGET_UNIT_ID ")
				.append(" WHERE d.TARGET_PROD_CODE='").append(prodCode)
				.append("' AND d.TARGET_UNIT_ID IS NOT NULL  ")
				.append(" GROUP BY d.TARGET_UNIT_ID,item.ITEM_NAME ");
		return super.executeSqlQuery(sql.toString());
	}
	
	private HashMap<String, String> mappingUnitChangeToMap(
			List<Object[]> mappUnitList) {
		HashMap<String, String> hmap = new HashMap<String, String>();
		for (Object[] objs : mappUnitList) {
			hmap.put(objs[0].toString(), objs[1].toString());
		}
		return hmap;
	}

}
