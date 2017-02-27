/**
 */
package testTimestamp;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

public class DateUtils {

	/**
	 * 将日期时间型转成字符串 如:" 2002-07-01 11:40:02"
	 * 
	 * @param inDate
	 *            日期时间 " 2002-07-01 11:40:02"
	 * @return String 转换后日期时间字符串
	 */
	public static String DateTimetoStr(Date inDate) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return fmt.format(inDate);
	}

	/**
	 * 将日期型转成字符串 如:"2002-07-01"
	 * 
	 * @param inDate
	 *            日期 "2002-07-01"
	 * @return String 转换后日期字符串
	 */
	public static String DatetoStr(Date inDate) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		if (inDate != null)
			return fmt.format(inDate);
		else
			return "";
	}

	/**
	 * 将字符串型(英文格式)转成日期型 如: "Tue Dec 26 14:45:20 CST 2000"
	 * 
	 * @param DateFormatStr
	 *            字符串 "Tue Dec 26 14:45:20 CST 2000"
	 * @return Date 日期
	 */

	public static Date StrToDateEN(String shorDateStr) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"EEE MMM dd hh:mm:ss 'CST' yyyy", java.util.Locale.US);
			return formatter.parse(shorDateStr);
		} catch (Exception e) {
			return new Date();
		}
	}

	/**
	 * 将字符串型(中文格式)转成日期型 如:"2002-07-01 22:09:55"
	 * 
	 * @param datestr
	 *            字符串 "2002-07-01 22:09:55"
	 * @return Date 日期
	 */

	public static Date StrToDateCN(String datestr) {
		Date date = null;
		try {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = fmt.parse(datestr);
		} catch (Exception e) {
			return date;
		}

		return date;
	}

	/**
	 * 将字符串型(中文格式)转成日期型 如:"2002-07-01 22:09:55"
	 * 
	 * @param datestr
	 *            字符串 "2002-07-01 22:09:55"
	 * @return Date 日期
	 */

	public static Date Str2ToDateCN(String datestr) {
		Date date = null;
		try {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
			date = fmt.parse(datestr);
		} catch (Exception e) {
			return date;
		}

		return date;
	}

	/**
	 * 转换util.date-->sql.date
	 * 
	 * @param inDate
	 * @return
	 */

	public static java.sql.Date UtilDateToSqlDate(Date inDate) {
		return new java.sql.Date(getDateTime(inDate));
	}

	private static long getDateTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DATE);
		cal.set(year, month, day, 0, 0, 0);
		long result = cal.getTimeInMillis();
		result = result / 1000 * 1000;
		return result;
	}

	public static Date FormatstrToDate(String DateFormatStr) {

		try {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			return fmt.parse(DateFormatStr);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 得到二个日期间的间隔天数
	 */
	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	/**
	 * 根据一个日期，返回是星期几的字符串
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getWeek(String sdate) {
		// 再转换为时间
		Date date = strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 两个时间之间的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.util.Date mydate = null;
		long day = 0L;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
		}
		
		return day;
	}

	// 计算当月最后一天,返回字符串
	public static String getLastDayOfMonth(String dateFormat) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}
	
	public static String getLastDayOfMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获取当月第一天
	public static String getFirstDayOfMonth(String dateFormat) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		str = sdf.format(lastDate.getTime());
		return str;
	}
	
	public static String getFirstDayOfMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 上月第一天
	public static String getPreviousMonthFirst(String dateFormat) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号

		str = sdf.format(lastDate.getTime());
		return str;
	}
	
	public static String getPreviousMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号

		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 上月第最后一天
	public static String getPreviousMonthLast(String dateFormat) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}
	
	public static String getPreviousMonthLast() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得本年第一天的日期
	public static String getCurrentYearFirst() {
		int yearPlus = getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus);
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		return preYearDay;
	}

	// 获得本年最后一天的日期 *
	public static String getCurrentYearEnd() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		return years + "-12-31";
	}

	private static int getYearPlus() {
		Calendar cd = Calendar.getInstance();
		int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		if (yearOfNumber == 1) {
			return -MaxYear;
		} else {
			return 1 - yearOfNumber;
		}
	}
	
	// 获得当前时间的偏移日期
	public static String getCurrentDataOffset(int dayOffset){
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.DATE, dayOffset);
		str = sdf.format(lastDate.getTime());
		return str;
	}

}