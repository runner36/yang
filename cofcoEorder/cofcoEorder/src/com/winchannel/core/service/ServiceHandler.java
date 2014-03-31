package com.winchannel.core.service;

import java.util.Map;

public class ServiceHandler {
	
	public static void ftpService(String serviceCode) {
		ftpService(serviceCode, null);
	}
	
	public static void ftpService(String serviceCode, Map<String, String> params) {
		try {
			Service service = new FtpService(serviceCode, params);
			service.service();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void returnDataService(String serviceCode, Map<String, String> params){
		try {
			Service service = new ReturnDataService(serviceCode, params);
			service.service();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
