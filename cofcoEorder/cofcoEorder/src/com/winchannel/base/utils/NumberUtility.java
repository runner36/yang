package com.winchannel.base.utils;
/*
 * 
 * 文件名：NumberUtility.java
 * 功  能：
 * 版  本：1.0
 * 日  期：Jun 20, 2009 9:11:36 PM
 * 作  者：Eric
 * 版  权：
 * 修改历史
 * ---------------------------------------------------------
 * 修改日期         修改人           修改目的
 * 
 */

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
public class NumberUtility {
public static String formatCurrency(double money){
	NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
	return format.format(money);
	
}
public static String formatNumber(int number) {
    DecimalFormat df = new DecimalFormat("00000000");
    return df.format(number);
}

public static String formatNumber(Long number) {
    DecimalFormat df = new DecimalFormat("00000000");
    return df.format(number);
}

public static String formatNumber(long number) {
    DecimalFormat df = new DecimalFormat("00000000");
    return df.format(number);
}

public static String formatDouble(double number) {
    DecimalFormat df = new DecimalFormat("###########0.00");
    return df.format(number);
}

public static String formatDouble(Double number) {
    DecimalFormat df = new DecimalFormat("##########0.00");
    return df.format(number);
}
public static String formatDouble0(Double number) {
    DecimalFormat df = new DecimalFormat("##########");
    return df.format(number);
}
public static String formatDouble1(Double number) {
    DecimalFormat df = new DecimalFormat("##########0.0");
    return df.format(number);
}
public static String formatDouble1(String number) {
	number = nvl(number,"0");
    DecimalFormat df = new DecimalFormat("##########0.0");
    return df.format(Double.parseDouble(number));
}
public static String formatDouble0(String number) {
		number = nvl(number,"0");
	
		DecimalFormat df = new DecimalFormat("###########");
	    return df.format(Double.parseDouble(number));
	
    
}
public static String formatDouble(String number) {
		number = nvl(number,"0");
		DecimalFormat df = new DecimalFormat("##########0.00");
	    return df.format(java.lang.Double.parseDouble(number));
	
    
}
public static String formatDoubleScale(String number) {
	number = nvl(number,"0");
	DecimalFormat df = new DecimalFormat("###,###,###,###,###,###");
    return df.format(java.lang.Double.parseDouble(number));


}
public static String formatDouble4(Double number) {
    DecimalFormat df = new DecimalFormat("##########0.0000");
    return df.format(number);
}
public static String formatDouble4(double number) {
    DecimalFormat df = new DecimalFormat("##0.0000");
    return df.format(number);
}
public static String formatDouble0(double number) {
    DecimalFormat df = new DecimalFormat("########");
    return df.format(number);
}
public static void main(String[] args){
	 

	System.out.println(multiply("99999999999.999","99999999999.999",3));
	System.out.println(NumberUtility.formatDouble1("84.50"));
	
}
	public static String nvl(String str, String re) {
		if (str != null && !str.equals("")) {
			return str;
		} else
			return re;
	}
	public static String multiply(String v1, String v2,int scale) {
		BigDecimal money=new BigDecimal(v1);
		money=money.multiply(new BigDecimal(v2));
		money= money.setScale(scale, BigDecimal.ROUND_UP);
		return money.toString();
	}
	
	 public static String add(String v1, String v2) {

		  BigDecimal b1 = new BigDecimal(v1);

		  BigDecimal b2 = new BigDecimal(v2);

		  return b1.add(b2).toString();

		 }

}


