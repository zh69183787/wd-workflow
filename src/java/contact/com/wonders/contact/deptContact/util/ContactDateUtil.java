package com.wonders.contact.deptContact.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ContactDateUtil {
	//private static SimpleLogger log = new SimpleLogger(ContactDateUtil.class);
	
	/**
	 * 传入两个日期计算日期差
	 * @param date1 日期1
	 * @param date2 日期2
	 * @return 日期之间的天数差
	 */
	@SuppressWarnings("deprecation")
	public static int DateMinus(String date1, String date2) {
		int dayNum = 0;
		
		try{
			java.util.Date d1 = new java.util.Date(date1.replaceAll("-", "/"));
			java.util.Date d2 = new java.util.Date(date2.replaceAll("-", "/"));
			dayNum = (int) ((d1.getTime() - d2.getTime()) / (24 * 60 * 60 * 1000));
			
//log.debug(date1+" - "+date2+" = "+dayNum);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		
		return dayNum;
	}

	/**
	 * 根据传入的日期加上传入天数返回新日期
	 * @param date1 日期
	 * @param date2 需要加的天数
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String DateAdd(String date1, int date2) {
		java.util.Date d1 = new java.util.Date(date1.replaceAll("-", "/"));
		Calendar d2 = Calendar.getInstance();
		d2.setTime(d1);
		// System.out.println(d2.getTime());
		d2.set(Calendar.DATE, d2.get(Calendar.DATE) + date2);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String ret = formatter.format(d2.getTime());
		return ret;
	}

	/**
	 * 生成日期字符串（yyyy-MM-dd HH:mm:ss）
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	/**
	 * 生成日期字符串（yyyy-MM-dd）
	 * @param date
	 * @return
	 */
	public static String formatDateYmd(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
	
	public static boolean checkDateFormat(String str,String dateFormat){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			
			@SuppressWarnings("unused")
			Date date = sdf.parse(str);
			
			return true;
		}catch(Exception e){}
		return false;
	}
}
