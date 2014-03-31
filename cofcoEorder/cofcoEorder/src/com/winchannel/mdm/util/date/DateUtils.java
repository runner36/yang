package com.winchannel.mdm.util.date;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	/**
	 * 判断是否为合法的日期时间字符串
	 * 
	 * @param str_input
	 * @return boolean;符合为true,不符合为false
	 */
	public static boolean isValidDate(String str_input, String rDateFormat)
	{
		if (!isNull(str_input)) 
		{
			int pos=str_input.indexOf("-");
			if(pos<0)
				return false;
			String year=str_input.substring(0,pos);
			if(year.length()>4)
			  return false;
			SimpleDateFormat formatter = new SimpleDateFormat(rDateFormat);
			formatter.setLenient(false);
			try {
				formatter.format(formatter.parse(str_input));
			} catch (Exception e) {
				return false;
			}
			return true;
		}
		return false;
	}

	private static boolean isNull(String str) {
		if (str == null)
			return true;
		else
			return false;
	}
	/**
	 * 字符串转换为日期型
	 * 
	 * @param strDate
	 * @param DataFormat:
	 *            yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date strToDate(String strDate, String DataFormat) {
		SimpleDateFormat formatter = new SimpleDateFormat(DataFormat);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out
				.println(isValidDate("事实上", "yyyy-MM-dd"));
		
			
	}
	

	public static String addDays(String date, int addDays){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date calDateFrom = null;
		try {
			calDateFrom = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(calDateFrom);
		c.set(Calendar.DATE, c.get(Calendar.DATE)+addDays);
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}
	
	public static Calendar addDaysCalendar(String date, int addDays){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date calDateFrom = null;
		try {
			calDateFrom = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(calDateFrom);
//		c.set(Calendar.DATE, c.get(Calendar.DATE)+addDays);
		c.set(Calendar.DATE, c.get(Calendar.DATE)+addDays);
		return c;
	}
	
	/**    
	 * 得到本月的第一天    
	 * @return    
	 */     
	public static String getMonthFirstDay(Calendar calendar) {      
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    calendar.set(Calendar.DAY_OF_MONTH, calendar      
	            .getActualMinimum(Calendar.DAY_OF_MONTH));      
	     
	    return sdf.format(calendar.getTime());      
	}      
	
	public static String getMonthFirstDay(String time) {      
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Calendar calendar = Calendar.getInstance();      
	    calendar.setTime(date);
	    calendar.set(Calendar.DAY_OF_MONTH, calendar      
	            .getActualMinimum(Calendar.DAY_OF_MONTH));      
	     
	    return sdf.format(calendar.getTime());      
	}     
	
	public static Date getMonthFirstDayDate(Date date) {      
	    Calendar calendar = Calendar.getInstance();      
	    calendar.setTime(date);
	    calendar.set(Calendar.DAY_OF_MONTH, calendar      
	            .getActualMinimum(Calendar.DAY_OF_MONTH));      
	     
	    return calendar.getTime();      
	}     
	     
	/**    
	 * 得到本月的最后一天    
	 *     
	 * @return    
	 */     
	public static String getMonthLastDay(Calendar calendar) {      
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//	    Calendar calendar = Calendar.getInstance();      
	    calendar.set(Calendar.DAY_OF_MONTH, calendar      
	            .getActualMaximum(Calendar.DAY_OF_MONTH));      
	    return sdf.format(calendar.getTime());      
	}     
	
	/**    
	 * 得到下个月的时间 
	 *     
	 * @return    
	 */   
	public static String getNextMonth(String time) {      
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Calendar calendar = Calendar.getInstance();      
	    calendar.setTime(date);
	    calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);      
	    return sdf.format(calendar.getTime());      
	}      
	
	/**    
	 * 得到本月最后一天的时间 
	 *     
	 * @return    
	 */   
	public static String getLastDayOfMonth(String time) {      
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Calendar calendar = Calendar.getInstance();      
	    calendar.setTime(date);
	    return getMonthLastDay(calendar);      
	}      
	
	public static long getDayNumBetweenTwoDates(String date1, String date2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long l = 0;
		try {
			Date dt1= sdf.parse(date1); 
            Date dt2= sdf.parse(date2); 
            l = dt2.getTime() - dt1.getTime(); 
        }catch(Exception e){ 
            System.out.println("exception"+e.toString()); 
        }
		return l/60/60/1000/24;
	}

	public static Date getFirstDayOfLastMonth (Date date) {
		Calendar calendar = Calendar.getInstance();      
	    calendar.setTime(date);
	    calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);      
		return DateUtils.getMonthFirstDayDate(calendar.getTime());
	}
	
	public static Date getFirstDayOfLastMonth (String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();      
	    calendar.setTime(date);
	    calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);      
		return DateUtils.getMonthFirstDayDate(calendar.getTime());
	}
	
	public static String getFirstDayOfLastMonthString (String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();      
	    calendar.setTime(date);
	    calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);      
		return sdf.format(DateUtils.getMonthFirstDayDate(calendar.getTime()));
	}
	
//	public static void main (String[] s) {
		String date = "2010-5-14";
//		System.out.println(new TimeUtils().getMonthFirstDay());
//		System.out.println(new TimeUtils().getMonthLastDay());
//		System.out.println(new TimeUtils().getLastDayOfMonth("2010-06-01"));
		
//		String date1 = "2010-06-01"; 
//        String date2 = "2010-06-24"; 
//        System.out.println(getDayNumBetweenTwoDates(date1, date2)); 
//		Calendar c = Calendar.getInstance();
//		String nowStr = TimeUtils.getMonthFirstDay(c);
//		Calendar endTag = TimeUtils.addDaysCalendar(nowStr, 0);
//		System.out.println(endTag.getTime()); 
//		Calendar c = Calendar.getInstance();
//		System.out.println(TimeUtils.getDayNumBetweenTwoDates("2010-05-01", "2010-06-30"));
//		Map<String, String[][]> map = new HashMap<String, String[][]>();
//	}


}
