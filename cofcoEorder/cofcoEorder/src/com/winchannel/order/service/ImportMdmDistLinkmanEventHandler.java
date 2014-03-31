package com.winchannel.order.service;

import java.util.Map;

import com.winchannel.core.importer.ImpEventHandler;

/**
 * @author shijingru
 * @客户联系人信息导入扩展类
 */
public class ImportMdmDistLinkmanEventHandler extends ImpEventHandler{

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void endRow(Map<String, String> row, T bean) {
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
	}
	@Override
	public void startRow(Map<String, String> row) {
		String isSms=row.get("6");
		if("".equals(isSms)||null==isSms){
			row.put("6", "0");
		}
		
	}


	
}
