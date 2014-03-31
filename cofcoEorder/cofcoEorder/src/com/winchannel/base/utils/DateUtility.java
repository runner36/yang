package com.winchannel.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import com.winchannel.core.utils.DateUtils;
import org.apache.commons.lang.time.*;
public class DateUtility {

	// private static Log log = LogUtil.getLoger(DateUtility.class);
	 private static final long ONE_DAY = 86400000l;
	/**
	 * 获取当前日期字符�? 格式为YYYY-MM-DD
	 *
	 * @return java.lang.String
	 */
	public static String getCurrentDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String s = df.format(new Date());
		return s;
	}
	
	public static String getCurrentYear(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		String s = df.format(new Date());
		return s;
	}
	
	public static String getCurrentMonth(){
		SimpleDateFormat df = new SimpleDateFormat("MM");
		String s = df.format(new Date());
		return s;
	}
	
	public static String getDayInWeek(String sDate){
		Date date = strToDate(sDate);
		SimpleDateFormat df = new SimpleDateFormat("EEE");
       String s = df.format(date);
       return s;		
	}
	
	public static Date strToDate(String str) { 
		Date date = null;
		if (str != null) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				date = df.parse(str);
			} catch (ParseException e) {
				//log.error("DateParse Error!");
			}
		}
		return date;
	}
	public static Date strToDateTime(String str) {
		Date date = null;
		if (str != null) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				date = df.parse(str);
			} catch (ParseException e) {
				//log.error("DateParse Error!");
			}
		}
		return date;
	}
	public static String dateTimeToStr(Date date) {
		String str = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (date != null) {
			str = df.format(date);
		}
		return str;
	}
	
	public static String dateToStr(Date date) {
		String str = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (date != null) {
			str = df.format(date);
		}
		return str;
	}
	public static String longDateToStr(long date) {
		String str = null;
		Date dateTemp=new Date();
		dateTemp.setTime(date);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (dateTemp != null) {
			str = df.format(dateTemp);
		}
		return str;
	}
	/**
	 * 转化成中文类型的日期
	 * @param date
	 * @return
	 */
	public static String dateToStrCh(Date date) {
		String str = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日?");
		if (date != null) {
			str = df.format(date);
		}
		return str;
	}
	
	/**
	 * 在原有的日期上面加i�?
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date add(Date date, int i){
	    date = new Date(date.getTime() + i * ONE_DAY);	    
	    return date;
	}

	/**
	 * �?1�?
	 * @param date
	 * @return
	 */
	public static Date add(Date date){
	    return add(date,1);
	}
	
	/**
	 * �?1�?
	 * @param date
	 * @return
	 */
	public static Date sub(Date date){
	    return add(date,-1);
	}
	public static String getBeforeDate(){
		Date date= DateUtility.sub(new Date());
		return DateUtility.dateToStr(date);
		
	}
	public static String getAfterDate(){
		Date date= DateUtility.add(new Date());
		return DateUtility.dateToStr(date);
		
	}	
   public static String getCurrentDateTime() {
       SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       String s = df.format(new Date());
       return s;
   }
   
   public static String getCurrentDateWeek(){
       SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd�? EEE");
       String s = df.format(new Date());
       return s;
       
   }
   public static String getCurrentDateWeekEn(){
       SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy ",new Locale("en"));
       String s = df.format(new Date());
       return s;
       
   }
  /**
   * 返回月份之间的差�?
   * @param startYear
   * @param startMonth
   * @param endYear
   * @param endMonth
   * @return
   */
public static int compareMonth(String startYear,String startMonth,String endYear,String endMonth){
    return (Integer.parseInt(endYear) - Integer.parseInt(startYear))*12 + (Integer.parseInt(endMonth) - Integer.parseInt(startMonth));
    
}
/**
 * 
 * @param sDate
 * @return
 */
public static String getYearMonth(String sDate){
	 Date date1 = null;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String s = null;
       try {
           date1 = df.parse(sDate);
           df.applyPattern("yyyy-MM");
           s=df.format(date1);
       } catch (ParseException e) {
          return s;
       }
       return s;
}
/**
* 
* @param date
* @return
*/
public static String getYearMonth(Date date){
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
    String s = null;
      
           s=df.format(date);
       
          return s;
       
	
}
public static String getMonthDay(Date date){
   SimpleDateFormat df = new SimpleDateFormat("MM月dd�?");
   String s = null;
     
          s=df.format(date);
      
         return s;
      
	
}
public static String getYearMonthDay(String sDate){
	 Date date1 = null;
   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
   String s = null;
      try {
          date1 = df.parse(sDate);
          df.applyPattern("yyMMdd");
          s=df.format(date1);
      } catch (ParseException e) {
         return s;
      }
      return s;
}
public static String getStartQueryTime(String date){
	return DateUtility.dateToStr(DateUtility.strToDate(date)) + " 00:00:00";
}
public static String getEndQueryTime(String date){
	return DateUtility.dateToStr(DateUtility.strToDate(date)) + " 23:59:59";
}
/**
 *  
 * @param sDate
 * @return
 */
public static String getMonth(String sDate){
    Date date1 = null;
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    String s = null;
       try {
           date1 = df.parse(sDate);
           df.applyPattern("MM");
           s=df.format(date1);
       } catch (ParseException e) {
          return s;
       }
       return s;
    
}

public static String formatDate(String sDate){
	 Date date1 = null;
  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  String s = null;
     try {
         date1 = df.parse(sDate);
         df.applyPattern("yyyy-MM-dd");
         s=df.format(date1);
     } catch (ParseException e) {
        return s;
     }
     return s;
}
  /**
   * 
   * @param sDate1
   * @param sDate2
   * @return
   */ 
public static int compareDate(String sDate1, String sDate2) {
       
       Date date1 = null;
       Date date2 = null;
       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      
           try {
               date1 = dateFormat.parse(sDate1);
               date2 = dateFormat.parse(sDate2);
           } catch (ParseException e) {
               
           }
       
       long dif = 0;
       if (date2.after(date1))
           dif = (date2.getTime() - date1.getTime()) / 1000 / 60 / 60 / 24;
       else
           dif = (date1.getTime() - date2.getTime()) / 1000 / 60 / 60 / 24;

       return (int)dif;
 }
public static String dateDiff(String sDate1, String sDate2) {
    
    Date date1 = null;
    Date date2 = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   
    try {
        date1 = dateFormat.parse(sDate1);
        date2 = dateFormat.parse(sDate2);
    } catch (ParseException e) {
        e.printStackTrace();
    }
    
    long dif = 0;
    
    if (date2.after(date1))
        dif = date2.getTime() - date1.getTime();
    else
        dif = date1.getTime() - date2.getTime();
    
    return DurationFormatUtils.formatDurationHMS(dif);
}

public static boolean compareToDate(String callTime, String currTime) {
   
   Date date1 = null;
   Date date2 = null;
   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  
       try {
           date1 = dateFormat.parse(callTime);
           date2 = dateFormat.parse(currTime);
       } catch (ParseException e) {
           e.printStackTrace();
       }
       boolean ret;
       if((date1.getTime()-date2.getTime())>=0){
       	ret=true;
       }else{
       	ret=false;	
       }
       return ret;
}


public static int getDate(String sDate, String sTag)
{
   int iSecondMinusPos = sDate.lastIndexOf('-');
   if (sTag.equalsIgnoreCase("y"))
   {
       return Integer.parseInt(sDate.substring(0, 4));
   } else
   if (sTag.equalsIgnoreCase("m"))
   {
       return Integer.parseInt(sDate.substring(5, iSecondMinusPos));
   }  else
       return Integer.parseInt(sDate.substring(iSecondMinusPos + 1));
}
public static int getDayOfWeek(){

	Calendar toDay = Calendar.getInstance();

	toDay.setFirstDayOfWeek(Calendar.MONDAY);

	int ret = toDay.get(Calendar.DAY_OF_WEEK) - 1;

	if(ret == 0){
		ret = 7;
	}

	return ret;
}
public static String getFirstDayOfMonth(){
	Calendar   ca   =   Calendar.getInstance();   
	  ca.setTime(new Date());   //   someDate   为你要获取的那个月的时间   
	  ca.set(Calendar.DAY_OF_MONTH,   1);   
	  Date   firstDate   =   ca.getTime();   
	  ca.add(Calendar.MONTH,   1);   
	  ca.add(Calendar.DAY_OF_MONTH,   -1);   
	  Date   lastDate   =   ca.getTime();  
	  return dateToStr(firstDate);
	
}
public static String getLastDayOfMonth(){
	Calendar   ca   =   Calendar.getInstance();   
	  ca.setTime(new Date());   //   someDate   为你要获取的那个月的时间   
	  ca.set(Calendar.DAY_OF_MONTH,   1);   
	  Date   firstDate   =   ca.getTime();   
	  ca.add(Calendar.MONTH,   1);   
	  ca.add(Calendar.DAY_OF_MONTH,   -1);   
	  Date   lastDate   =   ca.getTime();  
	  return dateToStr(lastDate);
}

/** 取上个月的年�?
* @return
*/
public static String getBeforeMonth(){
	Calendar ca = Calendar.getInstance();
	ca.setTime(new Date()); // someDate 为你要获取的那个月的时间
	ca.set(Calendar.DAY_OF_MONTH, 1);
	ca.add(Calendar.MONTH, -1);
	SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM");
   return sim.format(ca.getTime()).toString();
	
	
}	

/**
* 取下个月的年�?
* @return
*/
public static String getAfterMonth(){
	Calendar ca = Calendar.getInstance();
	ca.setTime(new Date()); // someDate 为你要获取的那个月的时间
	ca.set(Calendar.DAY_OF_MONTH, 1);
	ca.add(Calendar.MONTH, 1);
	SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM");
   return sim.format(ca.getTime()).toString();
	
	
}

/**
 * 获取当前日期到月末的剩余天数
 * @return
 */

public static int getDay2MonthEnd(){
	Calendar   ca   =   Calendar.getInstance();   
	  ca.setTime(new Date()); 
	
	
	return getDaysInMonth() - ca.get(Calendar.DATE);
}
/**
 * 获取当前月份的天�?
 * @return
 */
public static int getDaysInMonth(){
	
	Calendar   ca   =   Calendar.getInstance();   
	  ca.setTime(new Date());   //   someDate   为你要获取的那个月的时间   
	  ca.set(Calendar.DAY_OF_MONTH,   1);   
	  ca.add(Calendar.MONTH,   1);   
	  ca.add(Calendar.DAY_OF_MONTH,   -1);   
	  Date   lastDate   =   ca.getTime();  
	  ca.setTime(lastDate);
	  return ca.get(Calendar.DATE);
		
	
	
}

public static void main(String[] args) throws ParseException{
//   	//System.out.println(getDaysInMonth());
//   	//System.out.println(DateUtility.compareToDate("2009-08-15",DateUtility.getAfterDate()));
//   	String inputStr="2009-09-23";
//   	String aa="1";
//   	String bb="1,2";
//   	//System.out.println(aa.indexOf(bb)+"-------"+bb.indexOf(aa)+bb.indexOf("2"));
	//System.out.println(dateDiff("2010-01-11 23:05:00","2010-01-10 22:00:00"));
	//System.out.println(DurationFormatUtils.formatDuration(1000*5l, "H小时m分钟s秒"));
	//System.out.println(DurationFormatUtils.formatDurationHMS(1000l));
	//System.out.println(DurationFormatUtils.formatDurationISO(1000l));
	//System.out.println(DurationFormatUtils.formatDuration(1000000000*5l, "H'小时'm'分钟's'秒'"));
}
   
public static boolean  getBizFiveDaysBefore(String inputDateStr) 
{
	boolean flag=true;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	//输入日期
	Date inputDate=null;
	try {
		inputDate = dateFormat.parse(inputDateStr);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//当前日期
	Date currentDate=new Date();
	String currentDateStr=DateUtils.format(currentDate, "yyyy-MM-dd");
	try {
		currentDate=dateFormat.parse(currentDateStr);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//计算时间间隔
	if(currentDate.getTime()<inputDate.getTime())
	{
		flag=false;
	}
	else
	{
		long time = 1000*3600*24; 
		long dateRange = currentDate.getTime() - inputDate.getTime(); 
		
	    long days=dateRange/time;
	   if(days>4)
	    {
	    	flag=false;
	    }
	}
	return flag;
}


//inputDateStr输入日期;dayRange 天数间隔
public static boolean  getBizDayRest(String inputDateStr,long dayRange) throws ParseException
{
	boolean flag=true;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	//输入日期
	Date inputDate=dateFormat.parse(inputDateStr);
	//当前日期
	Date currentDate=new Date();
	String currentDateStr=DateUtils.format(currentDate, "yyyy-MM-dd");
	currentDate=dateFormat.parse(currentDateStr);
	//计算时间间隔
	if(currentDate.getTime()<inputDate.getTime())
	{
		flag=false;
	}
	else
	{
		long dateRange = currentDate.getTime() - inputDate.getTime(); 
		long time = 1000*3600*24; 
	    long days=dateRange/time;
		if(days>dayRange)
	    {
	    	flag=false;
	    }
	}
	
    
	return flag;
}
public static Map getBizModifyTime(Date currDate){		//
		Map hashMap=new HashMap();
//		currDate=new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currDate);
		int ret = calendar.get(Calendar.DAY_OF_WEEK)-1;
		Date tempDate=null;
		if(ret>4){
			tempDate=calendar.getTime();
			int t=ret==5?2:3;
			for(int j=0;j<=t;j++){
				calendar.setTime(tempDate);
				calendar.add(Calendar.DATE, -j);
				//System.out.println(j+"=="+DateUtils.format(calendar.getTime(), "yyyy-MM-dd"));
				hashMap.put(DateUtils.format(calendar.getTime(), "yyyy-MM-dd"),"true");
			}
		}else{
			int temp=4-ret;
			calendar.add(Calendar.DATE, temp);
			tempDate=calendar.getTime();
			for(int j=0;j<=8;j++){
				calendar.setTime(tempDate);
				calendar.add(Calendar.DATE, -j);
				hashMap.put(DateUtils.format(calendar.getTime(), "yyyy-MM-dd"), "true");
			}
		}
		return hashMap;		
	}
	
	
	public static boolean canModiByYBWeek(Date currDate){
		boolean flag = false;
		int [] week = {4,5,6,7,1,2,3};
		Calendar calendar = Calendar.getInstance();
		
		Calendar curCalendar = Calendar.getInstance();
		if(currDate.getTime() > curCalendar.getTimeInMillis())
			return false;		
		Map hashMap=new HashMap();	
		hashMap.put(DateUtils.format(curCalendar.getTime(), "yyyy-MM-dd"),"true");
		int currentWeek = calendar.get(Calendar.DAY_OF_WEEK);
		//加上周三和周四
		if(currentWeek == 5 || currentWeek == 4){
			if(currentWeek == 5){
				Calendar c3 = Calendar.getInstance();
				c3.add(Calendar.DATE, -1);
				hashMap.put(DateUtils.format(c3.getTime(), "yyyy-MM-dd"),"true");
				Calendar c4 = Calendar.getInstance();
				hashMap.put(DateUtils.format(c4.getTime(), "yyyy-MM-dd"),"true");
				
				
			}
			
			if(currentWeek == 4){
				Calendar c3 = Calendar.getInstance();
				c3.add(Calendar.DATE, -1);
				hashMap.put(DateUtils.format(c3.getTime(), "yyyy-MM-dd"),"true");

			}
			
			Calendar c3 = Calendar.getInstance();
			
			for(int i = 0 ;i <= currentWeek + 2 ;i++){
				c3.add(Calendar.DATE, -1);
				hashMap.put(DateUtils.format(c3.getTime(), "yyyy-MM-dd"),"true");	

			}
			
		}
		
		int pos = 0;
		if(currentWeek != 5 && currentWeek != 4){
			for(int i = 0 ; i <= 6 ; i++){
				if( currentWeek == week[i] ){
					pos = i;
					break;
				}				
			}
			//向前取
			for(int i = pos ;i > 0 ; i--){
				calendar.add(Calendar.DATE, -1);
				hashMap.put(DateUtils.format(calendar.getTime(), "yyyy-MM-dd"),"true");
			}		
			//本日
			hashMap.put(DateUtils.format(curCalendar.getTime(), "yyyy-MM-dd"),"true");
			//向后取
			calendar = curCalendar;	
			for(int i = pos ;i < 6 ; i++){		
				calendar.add(Calendar.DATE, 1);
				hashMap.put(DateUtils.format(calendar.getTime(), "yyyy-MM-dd"),"true");		
			}
		}
		
		//System.out.println(hashMap);
		String key = DateUtils.format(currDate, "yyyy-MM-dd");
		if(hashMap.containsKey(key)){
			return true;
		}else{		
			return false;		
		}
	}
	
	public static Map getDayAndWeek(){
		
		Map hashMap=new HashMap();
		Calendar lastDate = Calendar.getInstance();   
	    lastDate.add(Calendar.MONTH,1);//加一个月   
	    lastDate.set(Calendar.DATE, 1);//把日期设置为当月第
	    int days =lastDate.getActualMaximum(Calendar.DAY_OF_MONTH);
	    Date startTime=lastDate.getTime();
	    lastDate.set(Calendar.DATE, days);
	    Date endTime=lastDate.getTime();
	    for(long i=startTime.getTime();i<=endTime.getTime();i = i + 86400000){
	    	 Calendar calendar = Calendar.getInstance();   
			 calendar.setTime(new Date(i));
			 int week=calendar.get(Calendar.DAY_OF_WEEK)-1;
	    	 hashMap.put(DateUtils.format(new Date(i), "yyyy-MM-dd"), week);
	    	
		}
		return hashMap;
	}
	
	
	
	public static Map getNowDayAndWeek(){
		
		Map hashMap=new HashMap();
		Calendar lastDate = Calendar.getInstance();   
		lastDate.set(Calendar.DATE, 1);//把日期设置为当月第
//		lastDate.set(Calendar.DATE, 0);
//		lastDate.set(Calendar.HOUR, 12);
//		lastDate.set(Calendar.MINUTE, 0);
//		lastDate.set(Calendar.SECOND, 0);
	    int days =lastDate.getActualMaximum(Calendar.DAY_OF_MONTH);
	    Date startTime=lastDate.getTime();
	    lastDate.set(Calendar.DATE, days);
	    Date endTime=lastDate.getTime();
	    for(long i=startTime.getTime();i<=endTime.getTime();i = i + 86400000){
	    	 Calendar calendar = Calendar.getInstance();   
			 calendar.setTime(new Date(i));
			 int week=calendar.get(Calendar.DAY_OF_WEEK)-1;
	    	 hashMap.put(DateUtils.format(new Date(i), "yyyy-MM-dd"), week);
	    	
		}
		return hashMap;
	}
	
	
	
	/**
	  * 获得所在月份的最后一天
	  * @param d　月份所在的时间 
	  * @return 月份的最后一天
	  * @author 混混 
	  */
	 public static Date getLastDateByMonth(Date d){
	  Calendar now =Calendar.getInstance();
	  now.setTime(d);
	  now.set(Calendar.MONTH, now.get(Calendar.MONTH)+1);
	  now.set(Calendar.DATE, 1);
	  now.set(Calendar.DATE, now.get(Calendar.DATE)-1);
	  now.set(Calendar.HOUR, 11);
	  now.set(Calendar.MINUTE, 59);
	  now.set(Calendar.SECOND, 59);
	  return now.getTime();
	 } 
	 
	 /**
	  * 获得所在月份的第一天
	  * @param d　月份所在的时间 
	  * @return 月份的最后一天
	  * @author 混混 
	  */
	 public static Date getFirstDateByMonth(Date d){
	  Calendar now =Calendar.getInstance();
	  now.setTime(d);
	  now.set(Calendar.DATE, 0);
	  now.set(Calendar.HOUR, 12);
	  now.set(Calendar.MINUTE, 0);
	  now.set(Calendar.SECOND, 0);
	  return now.getTime();
	 }
	 
	 
	  /**   
	    *   获得某年某季度的最后一天的日期   
	    *   @param   year   
	    *   @param   quarter   
	    *   @return   Date   
	    */   
	  public   static   Date   getLastDayOfQuarter(int   year,int   quarter){   
	  int   month=0;   
	  if(quarter>4){   
	  return   null;   
	  }else{   
	  month=quarter*3;   
	  }   
	  return   getLastDayOfMonth(year,month);   
	    
	  }   
	    
	  /**   
	    *   获得某年某季度的第一天的日期   
	    *   @param   year   
	    *   @param   quarter   
	    *   @return   Date   
	    */   
	  public   static   Date   getFirstDayOfQuarter(int   year,int   quarter){   
	  int   month=0;   
	  if(quarter>4){   
	  return   null;   
	  }else{   
	  month=(quarter-1)*3+1;   
	  }   
	  return   getFirstDayOfMonth(year,month);   
	  }   
	  
	  
	  /**   
	    *   获得某年某月第一天的日期   
	    *   @param   year   
	    *   @param   month   
	    *   @return   Date   
	    */   
	  public   static   Date   getFirstDayOfMonth(int   year,int   month){   
	  Calendar   calendar=Calendar.getInstance();   
	  calendar.set(Calendar.YEAR,year);   
	  calendar.set(Calendar.MONTH,month-1);   
	  calendar.set(Calendar.DATE,1);   
	  return   getSqlDate(calendar.getTime());   
	  }   
	    
	  /**   
	    *   获得某年某月最后一天的日期   
	    *   @param   year   
	    *   @param   month   
	    *   @return   Date   
	    */   
	  public   static   Date   getLastDayOfMonth(int   year,int   month){   
	  Calendar   calendar=Calendar.getInstance();   
	  calendar.set(Calendar.YEAR,year);   
	  calendar.set(Calendar.MONTH,month);   
	  calendar.set(Calendar.DATE,1);   
	  return   getPreviousDate(getSqlDate(calendar.getTime()));   
	  } 
 
	  public   static   Date   getSqlDate(java.util.Date   date){   
		  return   new   Date(date.getTime());   
		  }   
	  /**   
	    *   获得某一日期的后一天   
	    *   @param   date   
	    *   @return   Date   
	    */   
	  public   static   Date   getNextDate(Date   date){   
	  Calendar   calendar=Calendar.getInstance();   
	  calendar.setTime(date);   
	  int   day=calendar.get(Calendar.DATE);   
	  calendar.set(Calendar.DATE,day+1);   
	  return   getSqlDate(calendar.getTime());   
	  }   
	    
	  /**   
	    *   获得某一日期的前一天   
	    *   @param   date   
	    *   @return   Date   
	    */   
	  public   static   Date   getPreviousDate(Date   date){   
	  Calendar   calendar=Calendar.getInstance();   
	  calendar.setTime(date);   
	  int   day=calendar.get(Calendar.DATE);   
	  calendar.set(Calendar.DATE,day-1);   
	  return   getSqlDate(calendar.getTime());   
	  }   

	  
	  /** *//**
	   * 取得指定日期的下一个月的第一天
	   * 
	   * @param date
	   *            指定日期。
	   * @return 指定日期的下一个月的第一天
	   */
	  public static synchronized java.util.Date getFirstDayOfNextMonth(
	   java.util.Date date )
	  {
	   /** *//**
	    * 详细设计： 
	    * 1.调用getNextMonth设置当前时间 
	    * 2.以1为基础，调用getFirstDayOfMonth
	    */
	   GregorianCalendar gc = ( GregorianCalendar ) Calendar.getInstance();
	   gc.setTime( date );
	   gc.setTime( getNextMonth( gc.getTime() ) );
	   gc.setTime( getFirstDayOfMonth( gc.getTime() ) );
	   return gc.getTime();
	  }
	  /** *//**
	   * 取得指定日期的下一个月
	   * 
	   * @param date
	   *            指定日期。
	   * @return 指定日期的下一个月
	   */
	  public static synchronized java.util.Date getNextMonth( java.util.Date date )
	  {
	   /** *//**
	    * 详细设计： 
	    * 1.指定日期的月份加1
	    */
	   GregorianCalendar gc = ( GregorianCalendar ) Calendar.getInstance();
	   gc.setTime( date );
	   gc.add( Calendar.MONTH, 1 );
	   return gc.getTime();
	  }
	  
	  
	  /** *//**
	   * 取得指定日期的所处月份的第一天
	   * 
	   * @param date
	   *            指定日期。
	   * @return 指定日期的所处月份的第一天
	   */
	  public static synchronized java.util.Date getFirstDayOfMonth( java.util.Date date )
	  {
	   /** *//**
	    * 详细设计： 1.设置为1号
	    */
	   GregorianCalendar gc = ( GregorianCalendar ) Calendar.getInstance();
	   gc.setTime( date );
	   gc.set( Calendar.DAY_OF_MONTH, 1 );
	   return gc.getTime();
	  }
	 
	  
	  /**
	  * 取得当前日期所在周的第一天
	  *
	  * @param date
	  * @return
	  */
	  public static Date getFirstDayOfWeek(Date date) {
	  Calendar c = new GregorianCalendar();
	  c.setFirstDayOfWeek(Calendar.MONDAY);
	  c.setTime(date);
	  c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
	  return c.getTime ();
	  }

	  /**
	  * 取得当前日期所在周的最后一天
	  *
	  * @param date
	  * @return
	  */
	  public static Date getLastDayOfWeek(Date date) {
	  Calendar c = new GregorianCalendar();
	  c.setFirstDayOfWeek(Calendar.MONDAY);
	  c.setTime(date);
	  c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
	  return c.getTime();
	  }

	  /**
		  * 上周一到五的日期
		  *
		  * @param date
		  * @return
		  */
      public   static   void  calcLastWeek(String begin,String end,String now,GregorianCalendar calendar){
          int  minus = calendar.get(GregorianCalendar.DAY_OF_WEEK) + 1 ;
         calendar.add(GregorianCalendar.DATE, - minus);
         end = new  java.sql.Date(calendar.getTime().getTime()).toString();
         calendar.add(GregorianCalendar.DATE, - 4 );
         begin = new  java.sql.Date(calendar.getTime().getTime()).toString();
          System.out.println( " begin: " + begin);
         //System.out.println( " end: " + end);
         //System.out.println( " ---------------------- " );
     } 
      public static String dateToStringyyyymmdd(Date date){
    	  SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    	    String s = null;
    	      
    	           s=df.format(date);
    	       
    	          return s;
    	    
    	}
      
      
      
      
      
      /**
       * 当前日期前一个月的最后一天
       * **/
      public static String getBefore1Month(){
    		Calendar   cal=Calendar.getInstance();//当前日期
    		 // cal.add(Calendar.MONTH, -1);
    		  cal.set(Calendar.DATE,1);//设为当前月的1号   
    		  cal.add(Calendar.DATE,-1);//减一天，变为上月最后一天   
    		  SimpleDateFormat   simpleDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd 23:59:59");   
    		 return simpleDateFormat.format(cal.getTime());
      }
      
      
      /**
       * 当前日期前一个月
       * **/
      public static String getFirstMonth(){
    		Calendar   cal=Calendar.getInstance();//当前日期
  		  cal.add(Calendar.MONTH, -1);
  		  cal.set(Calendar.DATE,1);//设为当前月的1号     
  		  SimpleDateFormat   simpleDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd");   
  		 return simpleDateFormat.format(cal.getTime());  
      }
      
      /**
       * 当前日期前三个月的第一天
       * **/
      public static String getBefore2Month(){
  		Calendar   cal=Calendar.getInstance();//当前日期
		  cal.add(Calendar.MONTH, -3);
		  cal.set(Calendar.DATE,1);//设为当前月的1号     
		  SimpleDateFormat   simpleDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd");   
		 return simpleDateFormat.format(cal.getTime());  
      }
      
      /**
       * 当前日期前二个月的第一天
       * **/
      public static String getB2Month(){
  		Calendar   cal=Calendar.getInstance();//当前日期
		  cal.add(Calendar.MONTH, -2);
		  cal.set(Calendar.DATE,1);//设为当前月的1号     
		  SimpleDateFormat   simpleDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd");   
		 return simpleDateFormat.format(cal.getTime());  
      }
      
}
