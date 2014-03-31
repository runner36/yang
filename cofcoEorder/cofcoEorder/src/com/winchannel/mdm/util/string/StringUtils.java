package com.winchannel.mdm.util.string;

import java.util.regex.Pattern;

public class StringUtils {

	/**
	 * 用JAVA自带的函数,判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 用正则表达式,判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumericByPattern(String str) {
		System.out.println("=====:="+str);
		if(str==null || "".equals(str.trim()))
		   return false;
		int index = str.trim().indexOf(".");   
        if(index<0){   
            return StringUtils.isNumeric(str.trim());   
         }else{   
            String num1 = str.trim().substring(0,index);   
            String num2 = str.trim().substring(index+1);               
               
         return StringUtils.isNumeric(num1) && StringUtils.isNumeric(num2);   
        }   

	}

	/**
	 * 用ascii码,判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumericByAscii(String str) {
		for (int i = str.length(); --i >= 0;) {
			int chr = str.charAt(i);
			if (chr < 48 || chr > 57)
				return false;
		}
		return true;
	}
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		if (str == null || trimSpace(str).length()==0)
			return true;
		else
			return false;
	}
	/**
	 * 去掉字符串的空格（中间空格，左右空格）
	 * @param str
	 * @return
	 */
	public static String trimSpace(String str) {
		return str=str.replaceAll(" ","");
	}
	/**
	 * 去掉字符串的空格（左右空格）
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		return str=str.trim();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(isNumericByPattern("3.29 "));
	}

}
