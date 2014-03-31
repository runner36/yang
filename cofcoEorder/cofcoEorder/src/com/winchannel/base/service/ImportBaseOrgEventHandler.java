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

public class ImportBaseOrgEventHandler extends ImpEventHandler{

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void endRow(Map<String, String> row, T bean) {
		BaseOrg baseOrg=(BaseOrg) bean;
		String geoIds=row.get("22");
//		String state=row.get("17");
//		if(StringUtils.isNotBlank(state)){
//			if("有效".equals(state)){
//				baseOrg.setState("1");
//			}else if("无效".equals(state)){
//				baseOrg.setState("0");
//			}else{
//				throw new BusinessException("状态为(有效)或(无效)：不能填写"+state);
//			}
//		}else{
//			baseOrg.setState("1");
//		}
	
		if(geoIds!=null && geoIds.length()>0 && baseOrg!=null)
		{
			//删除之前保存的记录
			this.getPersister().deleteAll("from BaseOrgGeo where baseOrg.orgId=?", baseOrg.getOrgId());
			// 保存新记录
			if (StringUtils.isNotBlank(geoIds)) {
				StringBuffer geoNameAll=new StringBuffer();
				String[] arr = geoIds.split(",");
				List<BaseOrgGeo> saveList = new ArrayList<BaseOrgGeo>(arr.length);
				for (String str : arr) {
					if(str!=null&& str.length()>0)
					{
						BaseOrgGeo bog = new BaseOrgGeo();
						bog.setBaseOrg(baseOrg);
						BaseDictItem baseDictItem = this.getGeoByCode(str);
						if(baseDictItem==null)
						{
							throw new BusinessException("地理区域编码未找到："+str);
						}
						bog.setBaseDictItem(baseDictItem);
						saveList.add(bog);
						this.getPersister().save(bog);
						geoNameAll.append(bog.getBaseDictItem().getItemCode()).append(",");
					}
				}
				baseOrg.setGeoAll(geoNameAll.toString());
			}else
			{
				baseOrg.setGeoAll(null);
			}
		}
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
	}
	@Override
	public void startRow(Map<String, String> row) {
		String state=row.get("17");
		if(StringUtils.isNotBlank(state)){
			if("有效".equals(state)){
				row.put("17", "1");
			}else if("无效".equals(state)){
				row.put("17", "0");
			}else{
				throw new BusinessException("状态为(有效)或(无效)：不能填写"+state);
			}
		}else{
			row.put("17", "1");
		}
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
