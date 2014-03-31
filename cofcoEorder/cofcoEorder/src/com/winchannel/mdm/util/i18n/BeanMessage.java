package com.winchannel.mdm.util.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.util.RequestUtils;

public class BeanMessage {
  
	/**
	 * 此方法通过给定的国际化资源文件和键，来得到键对应的值
	 * @param buddleResource:为国际化资源文件存放的包名
	 * @param key:为要查找的键
	 * @param request
	 * @return
	 */
	public static String getLocaleMessage(String buddleResource,String key,HttpServletRequest request){
	     String message="";
	     Locale locale = RequestUtils.getUserLocale(request,null);//获得Locale实例
	     ResourceBundle bundle=ResourceBundle.getBundle(buddleResource,locale);//获取当前资源包
	     message=bundle.getString(key);//查找资源包中的键所对应的值
	  return message;
	 }
}
