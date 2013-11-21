package com.jinfang.golf.utils;

import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

/**
 * @Date 2012 -06 -20
 * @author job
 * @description deal with date by this.
 * 
 */
public class DateUtil {
	private static final Logger logger = Logger.getLogger(DateUtil.class);

	public final static long ONE_DAY_SECONDS = 86400;

	/*
	 * private static DateFormat dateFormat = null; private static DateFormat
	 * longDateFormat = null; private static DateFormat dateWebFormat = null;
	 */
	public final static String shortFormat = "yyyyMMdd";
	public final static String longFormat = "yyyyMMddHHmmss";
	public final static String webFormat = "yyyy-MM-dd";
	public final static String timeFormat = "HHmmss";
	public final static String monthFormat = "yyyyMM";
	public final static String chineseDtFormat = "yyyy年MM月dd日";
	public final static String newFormat = "yyyy-MM-dd HH:mm:ss";
	public final static String noSecondFormat = "yyyy-MM-dd HH:mm";
	public final static long ONE_DAY_MILL_SECONDS = 86400000;

	public static DateFormat getNewDateFormat(String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);

		df.setLenient(false);
		return df;
	}

	public static String format(Date date, String format) {
		if (date == null) {
			return null;
		}

		return new SimpleDateFormat(format).format(date);
	}

	public static Date parseDateNoTime(String sDate) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(shortFormat);

		if ((sDate == null) || (sDate.length() < shortFormat.length())) {
			throw new ParseException("length too little", 0);
		}

		if (!StringUtils.isNumeric(sDate)) {
			throw new ParseException("not all digit", 0);
		}

		return dateFormat.parse(sDate);
	}

	public static Date parseDateNoTime(String sDate, String format) throws ParseException {
		if (StringUtils.isBlank(format)) {
			throw new ParseException("Null format. ", 0);
		}

		DateFormat dateFormat = new SimpleDateFormat(format);

		if ((sDate == null) || (sDate.length() < format.length())) {
			throw new ParseException("length too little", 0);
		}

		return dateFormat.parse(sDate);
	}

	public static Date parseDateNoTimeWithDelimit(String sDate, String delimit) throws ParseException {
		sDate = sDate.replaceAll(delimit, "");

		DateFormat dateFormat = new SimpleDateFormat(shortFormat);

		if ((sDate == null) || (sDate.length() != shortFormat.length())) {
			throw new ParseException("length not match", 0);
		}

		return dateFormat.parse(sDate);
	}

	public static Date parseDateLongFormat(String sDate) {
		DateFormat dateFormat = new SimpleDateFormat(longFormat);
		Date d = null;

		if ((sDate != null) && (sDate.length() == longFormat.length())) {
			try {
				d = dateFormat.parse(sDate);
			} catch (ParseException ex) {
				return null;
			}
		}

		return d;
	}

	public static Date parseDateNewFormat(String sDate) {
		DateFormat dateFormat = new SimpleDateFormat(newFormat);
		Date d = null;
		if ((sDate != null) && (sDate.length() == newFormat.length())) {
			try {
				d = dateFormat.parse(sDate);
			} catch (ParseException ex) {
				return null;
			}
		}
		return d;
	}

	/**
	 * 计算当前时间几小时之后的时间
	 * 
	 * @param date
	 * @param hours
	 * 
	 * @return
	 */
	public static Date addHours(Date date, long hours) {
		return addMinutes(date, hours * 60);
	}

	/**
	 * 计算当前时间几分钟之后的时间
	 * 
	 * @param date
	 * @param minutes
	 * 
	 * @return
	 */
	public static Date addMinutes(Date date, long minutes) {
		return addSeconds(date, minutes * 60);
	}

	/**
	 * @param date1
	 * @param secs
	 * 
	 * @return
	 */

	public static Date addSeconds(Date date1, long secs) {
		return new Date(date1.getTime() + (secs * 1000));
	}

	/**
	 * 取得新的日期
	 * 
	 * @param date1
	 *            日期
	 * @param days
	 *            天数
	 * 
	 * @return 新的日期
	 */
	public static Date addDays(Date date1, long days) {
		return addSeconds(date1, days * ONE_DAY_SECONDS);
	}

	public static String getTomorrowDateString(String sDate) throws ParseException {
		Date aDate = parseDateNoTime(sDate);

		aDate = addSeconds(aDate, ONE_DAY_SECONDS);

		return getDateString(aDate);
	}

	public static String getLongDateString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(longFormat);

		return getDateString(date, dateFormat);
	}

	public static String getNewFormatDateString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(newFormat);
		return getDateString(date, dateFormat);
	}

	public static String getDateString(Date date, DateFormat dateFormat) {
		if (date == null || dateFormat == null) {
			return null;
		}

		return dateFormat.format(date);
	}

	public static String getYesterDayDateString(String sDate) throws ParseException {
		Date aDate = parseDateNoTime(sDate);

		aDate = addSeconds(aDate, -ONE_DAY_SECONDS);

		return getDateString(aDate);
	}

	/**
	 * 当天的时间格式化为"yyyyMMdd"
	 * 
	 * @return
	 */
	public static String getDateString(Date date) {
		DateFormat df = getNewDateFormat(shortFormat);

		return df.format(date);
	}

	public static String getWebDateString(Date date) {
		DateFormat dateFormat = getNewDateFormat(webFormat);

		return getDateString(date, dateFormat);
	}

	/**
	 * 取得“X年X月X日”的日期格式
	 * 
	 * @param date
	 * 
	 * @return
	 */
	public static String getChineseDateString(Date date) {
		DateFormat dateFormat = getNewDateFormat(chineseDtFormat);

		return getDateString(date, dateFormat);
	}

	public static String getTodayString() {
		DateFormat dateFormat = getNewDateFormat(shortFormat);

		return getDateString(new Date(), dateFormat);
	}

	public static String getTimeString(Date date) {
		DateFormat dateFormat = getNewDateFormat(timeFormat);

		return getDateString(date, dateFormat);
	}

	public static String getBeforeDayString(int days) {
		Date date = new Date(System.currentTimeMillis() - (ONE_DAY_MILL_SECONDS * days));
		DateFormat dateFormat = getNewDateFormat(shortFormat);

		return getDateString(date, dateFormat);
	}

	/**
	 * 取得两个日期间隔秒数（日期1 -日期2）
	 * 
	 * @param one
	 *            日期1
	 * @param two
	 *            日期2
	 * 
	 * @return 间隔秒数
	 */
	public static long getDiffSeconds(Date one, Date two) {
		Calendar sysDate = new GregorianCalendar();

		sysDate.setTime(one);

		Calendar failDate = new GregorianCalendar();

		failDate.setTime(two);
		return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / 1000;
	}

	public static long getDiffMinutes(Date one, Date two) {
		Calendar sysDate = new GregorianCalendar();

		sysDate.setTime(one);

		Calendar failDate = new GregorianCalendar();

		failDate.setTime(two);
		return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / (60 * 1000);
	}

	/**
	 * 取得两个日期的间隔天数
	 * 
	 * @param one
	 * @param two
	 * 
	 * @return 间隔天数
	 */
	public static long getDiffDays(Date one, Date two) {
		Calendar sysDate = new GregorianCalendar();

		sysDate.setTime(one);

		Calendar failDate = new GregorianCalendar();

		failDate.setTime(two);
		return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / (24 * 60 * 60 * 1000);
	}
	
	/**
	 * 取得两个日期的间隔天数（第二个时间要大于第一个时间，否则得到的是负数）
	 * @param t1
	 * @param t2
	 * @return
	 */
	public static long getDiffDaysByStr(String t1, String t2) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		long betweenDays = 0;
		Date d1 = new Date();
		Date d2 = new Date();
		try {
			d1 = format.parse(t1);
			d2 = format.parse(t2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		// 保证第二个时间一定大于第一个时间
		if (c1.after(c2)) {
			c1 = c2;
			c2.setTime(d1);
		}
		int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		betweenDays = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
		for (int i = 0; i < betweenYears; i++) {
			c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
			betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
		}
		return betweenDays;
	}

	public static String getBeforeDayString(String dateString, int days) {
		Date date;
		DateFormat df = getNewDateFormat(shortFormat);

		try {
			date = df.parse(dateString);
		} catch (ParseException e) {
			date = new Date();
		}

		date = new Date(date.getTime() - (ONE_DAY_MILL_SECONDS * days));

		return df.format(date);
	}

	public static boolean isValidShortDateFormat(String strDate) {
		if (strDate.length() != shortFormat.length()) {
			return false;
		}

		try {
			Integer.parseInt(strDate);
		} catch (Exception NumberFormatException) {
			return false;
		}

		DateFormat df = getNewDateFormat(shortFormat);

		try {
			df.parse(strDate);
		} catch (ParseException e) {
			return false;
		}

		return true;
	}

	public static boolean isValidShortDateFormat(String strDate, String delimiter) {
		String temp = strDate.replaceAll(delimiter, "");

		return isValidShortDateFormat(temp);
	}

	/**
	 * 判断表示时间的字符是否为符合yyyyMMddHHmmss格式
	 * 
	 * @param strDate
	 * @return
	 */
	public static boolean isValidLongDateFormat(String strDate) {
		if (strDate.length() != longFormat.length()) {
			return false;
		}

		try {
			Long.parseLong(strDate); // ---- 避免日期中输入非数字 ----
		} catch (Exception NumberFormatException) {
			return false;
		}

		DateFormat df = getNewDateFormat(longFormat);

		try {
			df.parse(strDate);
		} catch (ParseException e) {
			return false;
		}

		return true;
	}

	/**
	 * 判断表示时间的字符是否为符合yyyyMMddHHmmss格式
	 * 
	 * @param strDate
	 * @param delimiter
	 * @return
	 */

	public static boolean isValidLongDateFormat(String strDate, String delimiter) {
		String temp = strDate.replaceAll(delimiter, "");

		return isValidLongDateFormat(temp);
	}

	public static String getShortFirstDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		Date dt = new Date();

		cal.setTime(dt);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		DateFormat df = getNewDateFormat(shortFormat);

		return df.format(cal.getTime());
	}

	public static String getWebTodayString() {
		DateFormat df = getNewDateFormat(webFormat);

		return df.format(new Date());
	}

	public static String getWebFirstDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		Date dt = new Date();

		cal.setTime(dt);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		DateFormat df = getNewDateFormat(webFormat);

		return df.format(cal.getTime());
	}

	public static String convert(String dateString, DateFormat formatIn, DateFormat formatOut) {
		try {
			Date date = formatIn.parse(dateString);

			return formatOut.format(date);
		} catch (ParseException e) {
			logger.warn("convert() --- orign date error: " + dateString);
			return "";
		}
	}

	public static String convert2WebFormat(String dateString) {
		DateFormat df1 = getNewDateFormat(shortFormat);
		DateFormat df2 = getNewDateFormat(webFormat);

		return convert(dateString, df1, df2);
	}

	public static String convert2ChineseDtFormat(String dateString) {
		DateFormat df1 = getNewDateFormat(shortFormat);
		DateFormat df2 = getNewDateFormat(chineseDtFormat);

		return convert(dateString, df1, df2);
	}

	public static String convertFromWebFormat(String dateString) {
		DateFormat df1 = getNewDateFormat(shortFormat);
		DateFormat df2 = getNewDateFormat(webFormat);

		return convert(dateString, df2, df1);
	}

	public static boolean webDateNotLessThan(String date1, String date2) {
		DateFormat df = getNewDateFormat(webFormat);

		return dateNotLessThan(date1, date2, df);
	}

	/**
	 * @param date1
	 * @param date2
	 * @param dateWebFormat2
	 * 
	 * @return
	 */
	public static boolean dateNotLessThan(String date1, String date2, DateFormat format) {
		try {
			Date d1 = format.parse(date1);
			Date d2 = format.parse(date2);

			if (d1.before(d2)) {
				return false;
			} else {
				return true;
			}
		} catch (ParseException e) {
			logger.debug("dateNotLessThan() --- ParseException(" + date1 + ", " + date2 + ")");
			return false;
		}
	}

	public static String getEmailDate(Date today) {
		String todayStr;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");

		todayStr = sdf.format(today);
		return todayStr;
	}

	public static String getSmsDate(Date today) {
		String todayStr;
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日HH:mm");

		todayStr = sdf.format(today);
		return todayStr;
	}

	public static String formatTimeRange(Date startDate, Date endDate, String format) {
		if ((endDate == null) || (startDate == null)) {
			return null;
		}

		String rt = null;
		long range = endDate.getTime() - startDate.getTime();
		long day = range / DateUtils.MILLIS_PER_DAY;
		long hour = (range % DateUtils.MILLIS_PER_DAY) / DateUtils.MILLIS_PER_HOUR;
		long minute = (range % DateUtils.MILLIS_PER_HOUR) / DateUtils.MILLIS_PER_MINUTE;

		if (range < 0) {
			day = 0;
			hour = 0;
			minute = 0;
		}

		rt = format.replaceAll("dd", String.valueOf(day));
		rt = rt.replaceAll("hh", String.valueOf(hour));
		rt = rt.replaceAll("mm", String.valueOf(minute));

		return rt;
	}

	public static String formatMonth(Date date) {
		if (date == null) {
			return null;
		}

		return new SimpleDateFormat(monthFormat).format(date);
	}

	/**
	 * 获取系统日期的前一天日期，返回Date
	 * 
	 * @return
	 */
	public static Date getBeforeDate() {
		Date date = new Date();

		return new Date(date.getTime() - (ONE_DAY_MILL_SECONDS));
	}

	/**
	 * 获得指定时间当天起点时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayBegin(Date date) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		df.setLenient(false);

		String dateString = df.format(date);

		try {
			return df.parse(dateString);
		} catch (ParseException e) {
			return date;
		}
	}

	/**
	 * 判断参date加上 min分钟后，是否小于当前时间
	 * 
	 * @param date
	 * @param min
	 * @return
	 */
	public static boolean dateLessThanNowAddMin(Date date, long min) {
		return addMinutes(date, min).before(new Date());

	}

	public static boolean isBeforeNow(Date date) {
		if (date == null)
			return false;
		return date.compareTo(new Date()) < 0;
	}

	public static void main(String[] args) {
		System.out.println(isBeforeNow(new Date()));
	}

	public static Date parseNoSecondFormat(String sDate) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(noSecondFormat);

		if ((sDate == null) || (sDate.length() < noSecondFormat.length())) {
			throw new ParseException("length too little", 0);
		}

		if (!StringUtils.isNumeric(sDate)) {
			throw new ParseException("not all digit", 0);
		}

		return dateFormat.parse(sDate);
	}
	
    public static long timeIgnoreHMS(long time){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }
    
}
