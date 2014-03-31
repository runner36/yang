package com.winchannel.mdm.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winchannel.base.model.BaseDictItem;
import com.winchannel.core.importer.ImpEventHandler;
import com.winchannel.mdm.conversion.model.MdmUnitConversion;

/**
 * @author 作者:chenjie
 * @version 创建时间：2011-3-6 下午02:01:12 类说明:
 */

public class ImportProdEventHandler extends ImpEventHandler {
	BaseDictItem pcObj = null;
	Map<String, String> conversionMap = null;
	@Override
	public void start() {
		pcObj = this.getUnitByCode("PC".intern());
		conversionMap = getMdmUnitConversionToMap();
	}
	@Override
	public void end() {
		conversionMap=null;
		pcObj=null;
	}

	@Override
	public void startRow(Map<String, String> row) {

	}
	@Override
	public <T> void endRow(Map<String, String> row, T bean) {
		// 针对NIKE只维护产品PC 到PC 的单位关系
		String prodCode = row.get("4").intern() + "-".intern() + row.get("5").intern() + "-".intern()
				+ row.get("31").intern();
		if (conversionMap.get(prodCode) == null) {
			MdmUnitConversion unitConObj = new MdmUnitConversion();
			unitConObj.setProdCode(prodCode);
			unitConObj.setConvUnit1Id(pcObj);
			unitConObj.setConvUnit2Id(pcObj);
			unitConObj.setConvUnit1Val(Long.valueOf(1));
			unitConObj.setConvUnit2Val(Long.valueOf(1));
			super.getPersister().save(unitConObj);
			conversionMap.put(prodCode, prodCode);
		}
	}

	public Map<String, String> getMdmUnitConversionToMap() {
		String hsql = "from MdmUnitConversion".intern();
		Map<String, String> map = new HashMap<String, String>();
		List<MdmUnitConversion> list = super.getPersister().findEntity(MdmUnitConversion.class, hsql);
		for (MdmUnitConversion obj : list) {
			map.put(obj.getProdCode(), obj.getProdCode());
		}
		return map;
	}
	public BaseDictItem getUnitByCode(String unitCode) {
		String hsql = "from BaseDictItem where itemCode=? and baseDict.dictId='prodUnit'".intern();
		return super.getPersister().findUniqueEntity(BaseDictItem.class, hsql,
				unitCode.intern());
	}
}
