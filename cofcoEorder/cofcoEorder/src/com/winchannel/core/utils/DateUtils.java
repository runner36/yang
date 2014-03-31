package com.winchannel.core.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.sf.ehcache.CacheManager;

public class DateUtils extends org.apache.commons.lang.time.DateUtils {
	
	public static final int MINIMAL_DAYS_IN_FIRSTWEEK = 4;
	public static final int FIRST_DAY_OF_WEEK = 2;
	
	public static String format(Date date, String pattern) {
		if (date != null) {
			try {
				return new SimpleDateFormat(pattern).format(date);
			}
			catch (Exception ex) {
			}
		}
		return "";
	}

	public static Date parse(String date, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			sdf.setLenient(false);
			return sdf.parse(date);
		}
		catch (Exception ex) {
		}
		return null;
	}
	
	/**
	 * @描述: 得到date的昨天
	 * 
	 */
	public static Date getYesterdayByDate(String date, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			sdf.setLenient(false);
			Date temp = sdf.parse(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(temp);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			return cal.getTime();
		}
		catch (Exception ex) {
		}
		return null;
	}

	/**
	 * @描述: 得到某一月的天数
	 * 
	 */
	public static int getDaysOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, 1);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @描述: 得到某一月的天数
	 * 
	 */
	public static int getDaysOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @描述: 得到某一季的天数
	 * 
	 */
	public static int getDaysOfQuarter(int year, int quarter) {
		Calendar cal = Calendar.getInstance();
		int days = 0;
		int firstMonth = (quarter - 1) * 3;
		for (int i = firstMonth; i < firstMonth + 3; i++) {
			cal.set(year, i, 1);
			days += cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		return days;
	}

	/**
	 * @描述: 得到某一年的天数
	 * 
	 */
	public static int getDaysOfYear(int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, 0, 1);
		return cal.getActualMaximum(Calendar.DAY_OF_YEAR);
	}

	/**
	 * @描述: 得到某一年的天数
	 * 
	 */
	public static int getDaysOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getActualMaximum(Calendar.DAY_OF_YEAR);
	}

	/**
	 * @描述: 得到某一周的星期一的时间
	 * 
	 */
	public static Date getFirstDayOfWeek(int year, int week) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(FIRST_DAY_OF_WEEK);
		cal.setMinimalDaysInFirstWeek(MINIMAL_DAYS_IN_FIRSTWEEK);
		cal.set(year, Calendar.JANUARY, 1);
		int addDays = week * 7;
		if (cal.get(Calendar.WEEK_OF_YEAR) == 1) {
			addDays = (week - 1) * 7;
		}
		cal.add(Calendar.DATE, addDays);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		return cal.getTime();

	}

	/**
	 * @描述: 得到某一周的第一天
	 * 
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(FIRST_DAY_OF_WEEK);
		cal.setMinimalDaysInFirstWeek(MINIMAL_DAYS_IN_FIRSTWEEK);
		cal.setTime(date);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 1) {
			dayOfWeek = 8;
		}
		
		cal.setTimeInMillis((cal.getTimeInMillis() + (FIRST_DAY_OF_WEEK - dayOfWeek) * 86400000L));
		return cal.getTime();
	}

	/**
	 * @描述: 得到某一月的第一天
	 * 
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}
	
	/**
	 * @描述: 得到某一月的最后一天
	 * 
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	/**
	 * @描述: 得到上月的第一天
	 * 
	 */
	public static Date getFirstDayOfLastMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(cal.getTimeInMillis() - cal.get(Calendar.DAY_OF_MONTH) * 86400000L);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * @描述: 得到上月的最后一天
	 * 
	 */
	public static Date getLastDayOfLastMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(cal.getTimeInMillis() - cal.get(Calendar.DAY_OF_MONTH) * 86400000L);
		return cal.getTime();
	}

	/**
	 * @描述: 得到下月的第一天
	 * 
	 */
	public static Date getFirstDayOfNextMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.setTimeInMillis(cal.getTimeInMillis() + cal.getActualMaximum(Calendar.DAY_OF_MONTH) * 86400000L);
		return cal.getTime();
	}

	/**
	 * @描述: 得到下月的最后一天
	 * 
	 */
	public static Date getLastDayOfNextMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(cal.getTimeInMillis() + cal.getActualMaximum(Calendar.DAY_OF_MONTH) * 86400000L);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.setTimeInMillis(cal.getTimeInMillis() + (cal.getActualMaximum(Calendar.DAY_OF_MONTH)-1) * 86400000L);
		return cal.getTime();
	}


	/**
	 * @描述: 得到某一日期的周数
	 * 
	 */
	public static int getWeekOfDate(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(FIRST_DAY_OF_WEEK);
		cal.setMinimalDaysInFirstWeek(MINIMAL_DAYS_IN_FIRSTWEEK);
		cal.set(year, month - 1, day, 0, 0, 0);
		return cal.get(Calendar.WEEK_OF_YEAR);

	}

	/**
	 * @描述: 得到某一日期的天数
	 * 
	 */
	public static int getDayOfDate(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day, 0, 0, 0);
		return cal.get(Calendar.DAY_OF_YEAR);
	}
	
	public static Date getDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	public static Date getDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	public static Date yesterday() {
		return new Date(System.currentTimeMillis() - 86400000L);
	}
	
	public static Date getEndDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2099);
		cal.set(Calendar.MONTH, 12 - 1);
		cal.set(Calendar.DATE, 31);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	private static CacheManager cacheManager = CacheManager.create();
	
	public static void main(String[] args) throws InterruptedException {
//		System.out.println(DateUtils.getEndDate());
//		System.out.println(DateUtils.yesterday().toLocaleString());
//		System.out.println(DateUtils.format(DateUtils.getFirstDayOfMonth(DateUtils.yesterday()), "yyyy-MM-dd"));
		
//		Calendar cal = Calendar.getInstance();
//		for (int i = 2010; i < 2050; i++) {
//			cal.set(Calendar.YEAR, i);
//			System.out.println(i + ": " + (cal.getTimeInMillis() - 1267680982015L));
//		}
//		cal.setTimeInMillis(1);
//		Calendar firstDay = Calendar.getInstance();
//		firstDay.setTimeInMillis(DateUtils.getFirstDayOfLastMonth().getTime());
//		long lastDay = DateUtils.getLastDayOfNextMonth().getTime();
//		
//		while (firstDay.getTimeInMillis() <= lastDay) {
//			
//			
//			System.out.println(firstDay.getTime().toLocaleString());
//			firstDay.setTimeInMillis(firstDay.getTimeInMillis() + 86400000L);
//		}
//		String prodCode = "123456-123-12-; ;";
//		System.out.println(prodCode.substring(0, prodCode.length() - 1));
//		System.out.println(IdGeneratorFactory.getUpperStringTimeIdGenerator("d").generate());
//		
//		String v = "er,12,34,r3,32";
//		System.out.println("'" + StringUtils.replace(v, ",", "','") + "'");
		
		
		
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(DateUtils.parse("2008-01-08", "yyyy-MM-dd"));
//		int day = cal.get(Calendar.DAY_OF_WEEK);
//		cal.setTimeInMillis(cal.getTimeInMillis() - (7 + day - 1) * 86400000L);
//		
//		long firstDay = cal.getTimeInMillis();
//		
//		for (int i = 0; i < 115; i++) {
//			System.out.println(new Date(firstDay).toLocaleString());
//			firstDay += (86400000L * 7);
//		}
		

		/*Cache reportCache1 = new Cache("reportCache1", 500, false, false, 8, 2);
		cacheManager.addCache(reportCache1);
		Cache c = cacheManager.getCache("reportCache1");
		c.put(new Element("key1", "value1"));
		System.out.println(c.get("key1").getValue());
		
		c.put(new Element("key2", "value2"));
		
		
		Thread.sleep(10000);
		System.out.println(c.get("key1").getValue());*/
		
//		Integer ii = 1;
//		Integer i = ii;
//		System.out.println(--i);
//		System.out.println(i);
//		System.out.println(ii);
//		
//		Pattern PATTERN_SIZE = Pattern.compile("[0-9]{3}");
//		System.out.println(PATTERN_SIZE.matcher("121").matches());
//		
//		System.out.println((new DecimalFormat("0.00")).format(0.6250000000000000000000));
//		System.out.println(DateUtils.class.getResource("/com/winchannel/dms/conf"));
		
//		URL u = DateUtils.class.getResource("/slog4j.properties");
//		if (u == null) {
//			u = DateUtils.class.getResource("/dlog4j.properties");
//		}
//		System.out.println(StringUtils.replace("2010-07-31", "-", "") + "-库存.rar");
		
//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.YEAR, 2099);
//		cal.set(Calendar.MONTH, 12 - 1);
//		cal.set(Calendar.DATE, 31);
//		System.out.println(cal.getTime());
		
//		System.out.println(DateUtils.getYesterdayByDate("2008-3-01", "yyyy-MM-dd"));
//		new File("d:/tt.xls").renameTo(new File("d:/tt.xls.zip"));
		
		URL url = DateUtils.class.getResource("/config/jdbc.properties");
		File d = new File(url.getFile()).getParentFile();
		if (d.isDirectory()) {
			FilenameFilter filter = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					if (name.indexOf("m") == 0) {
						return true;
					}
					return false;
				}
			
			};
			String[] list = d.list(filter);
			for (String f : list) {
				System.out.println(f);
			}
		}
	}
}
