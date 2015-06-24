package com.wonders.contact.approvedInfo.service;

import java.util.Comparator;
import java.util.Map;

import com.wonders.util.DateUtil;
import com.wonders.util.StringUtil;

public class DeptTimeComparator implements Comparator<Map<String,Object>> {
	private final String timeFormat = DateUtil.TIME_FORMAT;
	
	@Override
	public int compare(Map<String,Object> map1, Map<String,Object> map2) {
		int result = 0;
		int bigger = 1;
		int smaller = -1;
		String str1 = StringUtil.getNotNullValueString(map1.get("UPDDATE"));
		String str2 = StringUtil.getNotNullValueString(map2.get("UPDDATE"));
		try{
			java.util.Date d1 = DateUtil.getDate(str1, timeFormat);
			java.util.Date d2 = DateUtil.getDate(str2, timeFormat);
			if(d1==null){
				if(d2!=null){
					result = smaller;
				}
			}else{
				if(d2==null){
					result = bigger;
				}else{
					if(d1.getTime()>d2.getTime()){
						result = smaller;
					}else{
						result = bigger;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			result = 0;;
		}
//System.out.println(str1+" "+str2+" result:"+result);
		return result;
	}
}
