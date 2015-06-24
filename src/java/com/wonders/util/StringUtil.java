package com.wonders.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author XFZ
 * @version 1.0 2012-6-13
 */
public class StringUtil {
	
	/**
	 * 获得非空字符串
	 * @param o
	 * @return
	 */
	public static String getNotNullValueString(Object o){
		String showValue = "";
		try{
			if(o!=null){
				showValue = o.toString();
				if(showValue.trim().equals("null")){
					showValue = "";
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return showValue;
	}
	
	public static String getNotNullValueStringWithBlank(Object o){
		String showValue = "&nbsp;";
		if(o!=null){
			showValue = o.toString();
			if(showValue.trim().equals("null")){
				showValue = "&nbsp;";
			}
		}
		return showValue;
	}
	
	public static boolean isLetter(char c) { 
        int k = 0x80; 
        return c / k == 0 ? true : false; 
    }
	
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str){
		if(str==null||str.trim().equals("")||str.trim().equalsIgnoreCase("null")){
			return true;
		}else{
			return false;
		}
	}
	
	public static int getStrLength(String s){
		 if (s == null)
	            return 0;
		 return s.length();
	}
	
	/** 
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1 
     * @param String s 需要得到长度的字符串 
     * @return int 得到的字符串长度 
     */ 
    public static int length(String s) {
        if (s == null)
            return 0;
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }
	
    
    /** 
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为1,英文字符长度为0.5 
     * @param String s 需要得到长度的字符串 
     * @return int 得到的字符串长度 
     */ 
    public static double getLength(String s) {
    	double valueLength = 0;  
        String chinese = "[\u4e00-\u9fa5]";  
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1  
        for (int i = 0; i < s.length(); i++) {  
            // 获取一个字符  
            String temp = s.substring(i, i + 1);  
            // 判断是否为中文字符  
            if (temp.matches(chinese)) {  
                // 中文字符长度为1  
                valueLength += 1;  
            } else {  
                // 其他字符长度为0.5  
                valueLength += 0.5;  
            }  
        }  
        //进位取整  
        return  Math.ceil(valueLength);  
    }
	
     /** 
     * 截取一段字符的长度,不区分中英文,如果数字不正好，则少取一个字符位 
     * @param String origin 原始字符串 
     * @param int len 截取长度(一个汉字长度按2算的) 
     * @return String 返回的字符串 
     */ 
    public static String subStr(String origin, int len) {
        if (origin == null || origin.equals("")||len<1)
            return "";
        len = len*2;
        byte[] strByte = new byte[len];
        if (len >= length(origin)){
            return origin;}
        System.arraycopy(origin.getBytes(), 0, strByte, 0, len);
        int count = 0;
        for (int i = 0; i < len; i++) {
            int value = strByte[i];
            if (value < 0) {
                count++;
            }
        }
        if (count % 2 != 0) { 
            len = (len == 1) ? ++len : --len; 
        } 
        return new String(strByte, 0, len)+"..."; 
    }
    
    /**汉字UTF-8编码
	 * @param str
	 * @return
	 */
	public static String getEncodeStr(String str){
		String ret = "";
		try {
			ret= URLEncoder.encode(StringUtil.getNotNullValueString(str), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ret; 
	}
}

