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
	 * ������ʱ����ת���ַ��� ��:" 2002-07-01 11:40:02"
	 * 
	 * @param inDate
	 *            ����ʱ�� " 2002-07-01 11:40:02"
	 * @return String ת��������ʱ���ַ���
	 */
	public static String DateTimetoStr(Date inDate) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return fmt.format(inDate);
	}

	/**
	 * ��������ת���ַ��� ��:"2002-07-01"
	 * 
	 * @param inDate
	 *            ���� "2002-07-01"
	 * @return String ת���������ַ���
	 */
	public static String DatetoStr(Date inDate) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		if (inDate != null)
			return fmt.format(inDate);
		else
			return "";
	}

	/**
	 * ���ַ�����(Ӣ�ĸ�ʽ)ת�������� ��: "Tue Dec 26 14:45:20 CST 2000"
	 * 
	 * @param DateFormatStr
	 *            �ַ��� "Tue Dec 26 14:45:20 CST 2000"
	 * @return Date ����
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
	 * ���ַ�����(���ĸ�ʽ)ת�������� ��:"2002-07-01 22:09:55"
	 * 
	 * @param datestr
	 *            �ַ��� "2002-07-01 22:09:55"
	 * @return Date ����
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
	 * ���ַ�����(���ĸ�ʽ)ת�������� ��:"2002-07-01 22:09:55"
	 * 
	 * @param datestr
	 *            �ַ��� "2002-07-01 22:09:55"
	 * @return Date ����
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
	 * ת��util.date-->sql.date
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
	 * �õ��������ڼ�ļ������
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
	 * ����һ�����ڣ����������ڼ����ַ���
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getWeek(String sdate) {
		// ��ת��Ϊʱ��
		Date date = strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour�д�ľ������ڼ��ˣ��䷶Χ 1~7
		// 1=������ 7=����������������
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	/**
	 * ����ʱ���ʽ�ַ���ת��Ϊʱ�� yyyy-MM-dd
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
	 * ����ʱ��֮�������
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
		// ת��Ϊ��׼ʱ��
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

	// ���㵱�����һ��,�����ַ���
	public static String getLastDayOfMonth(String dateFormat) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// ��Ϊ��ǰ�µ�1��
		lastDate.add(Calendar.MONTH, 1);// ��һ���£���Ϊ���µ�1��
		lastDate.add(Calendar.DATE, -1);// ��ȥһ�죬��Ϊ�������һ��

		str = sdf.format(lastDate.getTime());
		return str;
	}
	
	public static String getLastDayOfMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// ��Ϊ��ǰ�µ�1��
		lastDate.add(Calendar.MONTH, 1);// ��һ���£���Ϊ���µ�1��
		lastDate.add(Calendar.DATE, -1);// ��ȥһ�죬��Ϊ�������һ��

		str = sdf.format(lastDate.getTime());
		return str;
	}

	// ��ȡ���µ�һ��
	public static String getFirstDayOfMonth(String dateFormat) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// ��Ϊ��ǰ�µ�1��
		str = sdf.format(lastDate.getTime());
		return str;
	}
	
	public static String getFirstDayOfMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// ��Ϊ��ǰ�µ�1��
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// ���µ�һ��
	public static String getPreviousMonthFirst(String dateFormat) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// ��Ϊ��ǰ�µ�1��
		lastDate.add(Calendar.MONTH, -1);// ��һ���£���Ϊ���µ�1��

		str = sdf.format(lastDate.getTime());
		return str;
	}
	
	public static String getPreviousMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// ��Ϊ��ǰ�µ�1��
		lastDate.add(Calendar.MONTH, -1);// ��һ���£���Ϊ���µ�1��

		str = sdf.format(lastDate.getTime());
		return str;
	}

	// ���µ����һ��
	public static String getPreviousMonthLast(String dateFormat) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// ��Ϊ��ǰ�µ�1��
		lastDate.add(Calendar.DATE, -1);// ��ȥһ�죬��Ϊ�������һ��
		str = sdf.format(lastDate.getTime());
		return str;
	}
	
	public static String getPreviousMonthLast() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// ��Ϊ��ǰ�µ�1��
		lastDate.add(Calendar.DATE, -1);// ��ȥһ�죬��Ϊ�������һ��
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// ��ñ����һ�������
	public static String getCurrentYearFirst() {
		int yearPlus = getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus);
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		return preYearDay;
	}

	// ��ñ������һ������� *
	public static String getCurrentYearEnd() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// ���Է�����޸����ڸ�ʽ
		String years = dateFormat.format(date);
		return years + "-12-31";
	}

	private static int getYearPlus() {
		Calendar cd = Calendar.getInstance();
		int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// ��õ�����һ���еĵڼ���
		cd.set(Calendar.DAY_OF_YEAR, 1);// ��������Ϊ�����һ��
		cd.roll(Calendar.DAY_OF_YEAR, -1);// �����ڻع�һ�졣
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		if (yearOfNumber == 1) {
			return -MaxYear;
		} else {
			return 1 - yearOfNumber;
		}
	}
	
	// ��õ�ǰʱ���ƫ������
	public static String getCurrentDataOffset(int dayOffset){
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.DATE, dayOffset);
		str = sdf.format(lastDate.getTime());
		return str;
	}

}