package com.winchannel.base.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.model.BaseOrg;
import com.winchannel.base.model.BaseOrgGeo;
import com.winchannel.core.exception.BusinessException;
import com.winchannel.core.importer.ImpEventHandler;
import com.winchannel.core.utils.StringUtils;

public class ImportDictItemEventHandler extends ImpEventHandler{

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void endRow(Map<String, String> row, T bean) {
		BaseDictItem curItem=(BaseDictItem) bean;
		if(curItem!=null){
			BaseDictItem pItem = curItem.getBaseDictItem();
				while(pItem!=null){
					switch (pItem.getLevelCode().intValue()) {
						case 4:
							curItem.setPn4(pItem.getItemName());
							curItem.setPi4(pItem.getDictItemId());
							break;
						case 3:
							curItem.setPn3(pItem.getItemName());
							curItem.setPi3(pItem.getDictItemId());
							break;
						case 2:
							curItem.setPn2(pItem.getItemName());
							curItem.setPi2(pItem.getDictItemId());
							break;
						case 1:
							curItem.setPn1(pItem.getItemName());
							curItem.setPi1(pItem.getDictItemId());
							break;
						default:
							break;
					}
					pItem = pItem.getBaseDictItem();
				}
				
				switch (curItem.getLevelCode().intValue()) {
					case 5:
						curItem.setPn5(curItem.getItemName());
						curItem.setPi5(curItem.getDictItemId());
						curItem.setPnall(curItem.getPn1()+"-"+curItem.getPn2()+"-"+curItem.getPn3()+"-"+curItem.getPn4()+"-"+curItem.getPn5());
						break;
					case 4:
						curItem.setPn4(curItem.getItemName());
						curItem.setPi4(curItem.getDictItemId());
						curItem.setPnall(curItem.getPn1()+"-"+curItem.getPn2()+"-"+curItem.getPn3()+"-"+curItem.getPn4());
						break;
					case 3:
						curItem.setPn3(curItem.getItemName());
						curItem.setPi3(curItem.getDictItemId());
						curItem.setPnall(curItem.getPn1()+"-"+curItem.getPn2()+"-"+curItem.getPn3());
						break;
					case 2:
						curItem.setPn2(curItem.getItemName());
						curItem.setPi2(curItem.getDictItemId());
						curItem.setPnall(curItem.getPn1()+"-"+curItem.getPn2());
						break;
					case 1:
						curItem.setPn1(curItem.getItemName());
						curItem.setPi1(curItem.getDictItemId());
						curItem.setPnall(curItem.getPn1());
						break;
					default:
						break;
				}
		}
		this.getPersister().save(curItem);
		
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
	}
	@Override
	public void startRow(Map<String, String> row) {
	}
	/**
	 * @param code
	 * @return
	 * 根据地理信息编码查找地理信息对象
	 */
	public BaseDictItem getGeoByCode(String code){
		return (BaseDictItem) this.getPersister().findUniqueEntity(BaseDictItem.class, "from BaseDictItem where itemCode=?", code);
	}

}
