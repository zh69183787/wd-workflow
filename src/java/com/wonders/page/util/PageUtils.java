/**
 * 
 */
package com.wonders.page.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wonders.util.StringUtil;

import net.sf.json.JSONObject;

/** 
 * @ClassName: PageUtils 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-1-24 下午02:44:37 
 *  
 */
public class PageUtils {
	public static Object getValueByParamName(Object obj, String paramName) {
		if (paramName == null || paramName.trim().length() == 0) {
			return null;
		}
		Field[] fields = obj.getClass().getDeclaredFields();
		String varName = null;
		for (int i = 0; i < fields.length; i++) {
			varName = fields[i].getName();
			if (paramName.equalsIgnoreCase(varName)) {
				boolean accessFlag = fields[i].isAccessible();
				fields[i].setAccessible(true);
				Object res = null;
				try {
					res = fields[i].get(obj);

				} catch (Exception e) {
				}
				fields[i].setAccessible(accessFlag);
				return res;
			}
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> generateMap(Object o,HttpServletRequest request){
		String key = null;
		String value = null;
		Map<String, Object> filter = new HashMap<String, Object>();
		JSONObject obj = JSONObject.fromObject(o);
		Iterator it = obj.keys();
		while (it.hasNext()) {
			key = (String) it.next();
			value = request.getParameter(key);
			if (value != null && value.trim().length() > 0) {
				Object res = getValueByParamName(o,key);
				if (res != null) {
					filter.put(key, res);
				}
			}
		}
		return filter;
	}
	
	public static String generateSQLByType(String sql, String[] queryNameArr, String[] queryResultArr, String[] queryTypeArr) {
		StringBuffer sb = new StringBuffer("select * from (" + sql + ")");
		//int count = 0;

		sb.append(" where 1=1");

		for (int i = 0; i < queryNameArr.length; i++) {
			String type = StringUtil.getNotNullValueString(queryTypeArr[i]);
			String name = StringUtil.getNotNullValueString(queryNameArr[i]);
			String value = StringUtil.getNotNullValueString(queryResultArr[i]);
			
//System.out.println(type+" "+name+" "+value);
			
			if (queryResultArr == null || value.equals("") || value.equals("#")) {

			} else {
				//"selectType","selectType","selectType","selectType","dateType","dateType","dateType"
				if (type.equals("textType")) {
					sb.append(" and " + name + " LIKE '%" + queryResultArr[i] + "%'");
				} else if (type.equals("dateType")) {
					// 查询条件为日期的有可能只选一个开始时间或一个结束时间
					String start = "";
					String end = "";
					try {
						start = StringUtil.getNotNullValueString(value.split("#")[0]);
						end = StringUtil.getNotNullValueString(value.split("#")[1]);
					} catch (Exception e) {}

					if (start.length() > 0) {
						sb.append(" and " + name + "" + " >= '" + start + "'");
					}
					if (end.length() > 0) {
						sb.append(" and " + name + "" + " <= '" + end + "'");
					}

				} else if (type.equals("selectType")) {
					sb.append(" and " + name + " = '" + value + "' ");
				}
			}
		}
		return sb.toString();
	}
	
	public static String generateHQLByType(String sql, String[] queryNameArr, String[] queryResultArr, String[] queryTypeArr) {
		StringBuffer sb = new StringBuffer(sql);
		//int count = 0;
		for (int i = 0; i < queryNameArr.length; i++) {
			String type = StringUtil.getNotNullValueString(queryTypeArr[i]);
			String name = StringUtil.getNotNullValueString(queryNameArr[i]);
			String value = StringUtil.getNotNullValueString(queryResultArr[i]);
			
//System.out.println(type+" "+name+" "+value);
			
			if (queryResultArr == null || value.equals("") || value.equals("#")) {

			} else {
				//"selectType","selectType","selectType","selectType","dateType","dateType","dateType"
				if (type.equals("textType")) {
					sb.append(" and " + name + " LIKE '%" + queryResultArr[i] + "%'");
				} else if (type.equals("dateType")) {
					// 查询条件为日期的有可能只选一个开始时间或一个结束时间
					String start = "";
					String end = "";
					try {
						start = StringUtil.getNotNullValueString(value.split("#")[0]);
						end = StringUtil.getNotNullValueString(value.split("#")[1]);
					} catch (Exception e) {}

					if (start.length() > 0) {
						sb.append(" and " + name + "" + " >= '" + start + "'");
					}
					if (end.length() > 0) {
						sb.append(" and " + name + "" + " <= '" + end + "'");
					}

				} else if (type.equals("selectType")) {
					sb.append(" and " + name + " = '" + value + "' ");
				}
			}
		}
		return sb.toString();
	}
}
