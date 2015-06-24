package com.wonders.contract.workflow.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.wonders.contract.workflow.constants.CommonConstants;
import com.wonders.util.StringUtil;


/**通用方法
 * @author XFZ
 * @version 1.0 2012-6-2
 */
public class CommonUtil {
	//private SimpleLogger log = new SimpleLogger(this.getClass());
	
	private static Gson gson = new Gson(); 
	
	
	/**按map字典翻译target集合
	 * @param target
	 * @param dict
	 * @return
	 */
	public static List<String> translateListByMap(List<String> target,Map<String,String> dict){
		List<String> list = new ArrayList<String>();
		
		for(int i=0;i<target.size();i++){
			list.add(StringUtil.getNotNullValueString(dict.get(target.get(i))));
		}
		
		return list;
	}
	
	/**
	 * 以分隔符(,)拼接list内字符串
	 * @param list 字符串集合
	 * @return
	 */
	public static String listToStringBySplit(List<String> list) {
		return listToStringBySplit(list,CommonConstants.SPLIT_EXP);
	}
	
	/**
	 * 以分隔符拼接list内字符串
	 * @param list 字符串集合
	 * @param split_str 分隔符
	 * @return
	 */
	public static String listToStringBySplit(List<String> list, String split_str) {
		String str = "";
		for (int i = 0; i < list.size(); i++) {
			str = str + split_str + list.get(i);
		}

		if (str.length() > 0 && str.startsWith(split_str))
			str = str.substring(1);

		return str;
	}
	
	/**根据分隔符获得list
	 * @param str 字符串
	 * @param splitExp 分隔符
	 * @return
	 */
	public static List<String> stringsToList(String str,String splitExp){
		List<String> list = new ArrayList<String>();
		try{
			Collections.addAll(list, str.split(splitExp));
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	/**根据分隔符(,)获得list
	 * @param str 字符串
	 * @param splitExp 分隔符
	 * @return
	 */
	public static List<String> stringsToList(String str){
		return stringsToList(str,CommonConstants.SPLIT_EXP);
	}
	
	/**数组转list
	 * @param array
	 * @return
	 */
	public static  List<String> arrayToList(String[] array){
		List<String> list = new ArrayList<String>();
		Collections.addAll(list, array);
		return list;
	}
	
	/**判断数组中是否存在字符串
	 * @param target 字符串
	 * @param array 目标数组
	 * @return
	 */
	public static boolean targetIsInArray(String target,String[] array){
		boolean flag = false;
		target = StringUtil.getNotNullValueString(target);
		try{
			for(int i=0;target.length()>0&&array!=null&&i<array.length;i++){
				String str = StringUtil.getNotNullValueString(array[i]);
				if(target.equals(str)) return true;
			}
		}catch(Exception e){}
		
		return flag;
	}
	
	/**根据次数重复字符串
	 * @param str
	 * @param times
	 * @return
	 */
	public static String getStrByTimes(String str,int times){
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<times;i++){
			sb.append(str);
		}
		return sb.toString();
	}
	
	/**获得指定json文件
	 * @param path
	 * @return
	 */
	public static String getJsonByPath(String path){
		StringBuilder sb = new StringBuilder();
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		InputStreamReader isr = null;
		
		try {
			isr = new InputStreamReader(is,"utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		BufferedReader br = new BufferedReader(isr);
		try {
			String data = null;
			while ((data = br.readLine()) != null) {
				sb.append(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println(e.getMessage());
			return null;
		} finally {
			try {
				br.close();
				isr.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return sb.toString();
	}
	
	/**根据JSON获得实例
	 * @param <T>
	 * @param json
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getInstanceByJson(String json,Class<T> clazz){
		try{
			java.lang.reflect.Type jsonType = clazz;
			return (T)gson.fromJson(json, jsonType);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getMD5(String value) {
		String result = null;
		try {
			byte[] valueByte = value.getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(valueByte);
			result = toHex(md.digest());
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		return result;
	}

	private static String toHex(byte[] buffer) {
		StringBuffer sb = new StringBuffer(buffer.length * 2);
		for (int i = 0; i < buffer.length; i++) {
			sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));
			sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
		}
		return sb.toString();
	} 
}
