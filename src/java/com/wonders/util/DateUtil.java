package com.wonders.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	/**
	 * 日期格式
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
	/**
	 * 时间格式
	 */
	public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static String getCurrDate(String formatStr) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		return sdf.format(date);
	}
	
	public static Date getDate(String dateStr,String formatStr){
		Date date = null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
			date = sdf.parse(dateStr);
		}catch(Exception e){
			e.printStackTrace();
		}
		return date;
	}
	
	public static String getDateStr(Date date,String formatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		return sdf.format(date);
	}
	
	public static String getNowDate(){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			String date = sdf.format(new Date());
			return date;
		}catch(Exception e){}
		return null;
	}
	
	public static String getNowTime(){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
			String date = sdf.format(new Date());
			return date;
		}catch(Exception e){}
		return null;
	}
}
