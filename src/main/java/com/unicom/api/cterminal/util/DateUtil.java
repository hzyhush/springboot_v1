package com.unicom.api.cterminal.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

	private final static SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM");

	private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");

	private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");

	private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private final static SimpleDateFormat sdfTime2 = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * 获取YYYY格式
	 * 
	 * @return
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}

	/**
	 * 获取YYYY-MM格式
	 *
	 * @return
	 */
	public static String getMOnth() {
		return sdfMonth.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式
	 * 
	 * @return
	 */
	public static String getDay() {
		return sdfDay.format(new Date());
	}

	/**
	 * 获取YYYYMMDD格式
	 * 
	 * @return
	 */
	public static String getDays() {
		return sdfDays.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 * 
	 * @return
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 *
	 * @return
	 */
	public static String getTime3(Long time) {
		return sdfTime.format(new Date(time));
	}

	/**
	 * 获取YYYYMMDDHHmmss格式
	 *
	 * @return
	 */
	public static String getTime2() {
		return sdfTime2.format(new Date());
	}

	/**
	 * @Title: compareDate
	 * @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
	 * @param s
	 * @param e
	 * @return boolean
	 * @throws @author luguosui
	 */
	public static boolean compareDate(String s, String e) {
		if (fomatDate(s) == null || fomatDate(e) == null) {
			return false;
		}
		return fomatDate(s).getTime() >= fomatDate(e).getTime();
	}

	/**
	 * 获得当月1号
	 * @return
	 */
	public static String initDateByMonth(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date dt1 = calendar.getTime();
		String reStr = sdfDay.format(dt1);
		return reStr;

	}

	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 校验日期是否合法
	 * 
	 * @return
	 */
	public static boolean isValidDate(String s) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}

	public static int getDiffYear(String startTime, String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			long aa = 0;
			int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}

	/**
	 * <li>功能描述：时间相减得到天数
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 * @author Administrator
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = null;
		Date endDate = null;

		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		// System.out.println("相隔的天数="+day);

		return day;
	}

	/**
	 * 得到n天之后的日期
	 * 
	 * @param days
	 * @return
	 */
	public static String getAfterDayDate(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	/**
	 * 当前日期加一天
	 * @return
	 */
	public static String getDateAddOne(){
		Date today = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(today);
		c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天
		Date tomorrow = c.getTime();
		return sdfDay.format(tomorrow);
	}

	/****
	 * 传入具体日期 ，返回具体日期增加一个月。
	 * @param date 日期(2017-04-13)
	 * @return 2017-05-13
	 * @throws ParseException
	 */
	public static String subMonth(Date date) throws ParseException {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.add(Calendar.MONTH, 1);
		Date dt1 = rightNow.getTime();
		String reStr = sdfDay.format(dt1);
		return reStr;
	}


	/**
	 * 得到n天之后是周几
	 * 
	 * @param days
	 * @return
	 */
	public static String getAfterDayWeek(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);

		return dateStr;
	}

	/**
	 * 指定时间添加月份
	 * @param date
	 * @param months
	 * @return
	 * @throws Exception
	 */
	public static  String addMonth(String date,Integer months) throws  Exception{
		Date time = sdfDay.parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);//设置起时间
		cal.add(Calendar.MONTH, months);//增加月份
		time = cal.getTime();
		return sdfDay.format(time);
	}

	/**
	 * String(yyyy-MM-dd HH:mm:ss)转10位时间戳
	 * @param
	 * @return
	 */
	public static Integer StringToTimestamp(){
		int times = (int) ((Timestamp.valueOf(sdfTime.format(new Date())).getTime())/1000);
		return times;

	}

	/**
	 * 比较两个时间的大小
	 * @param date1
	 * @param date2
	 * @return
	 * @throws Exception
	 */
	public static  boolean compare(String date1,String  date2) throws  Exception{
		Date time1 = sdfDay.parse(date1);
		Date time2 = sdfDay.parse(date2);
		return time1.after(time2);
	}

	public static String convertTime(String time) throws  Exception{
		Date date =sdfTime2.parse(time);
		return sdfTime.format(date);
	}

	public static String convertDate(String time) throws  Exception{
		Date date =sdfTime2.parse(time);
		return sdfDay.format(date);
	}

	public static void main(String[] args) {
		try {

			System.out.println(getTime3(1554700299147L));
			//System.out.println(addMonth("2019-01-23",6));
			//System.out.println(compare("2019-01-23","2019-01-22"));
		}catch (Exception e){
			e.toString();
		}


	}

}
