package com.wonders.contact.common.model.vo;

import java.util.HashMap;
import java.util.Map;

import com.wonders.contact.common.model.bo.ResultInfo;
import com.wonders.util.StringUtil;

public abstract class ParamVo {
	public UserInfo userInfo;
	
	public Map<String,String> processParam = new HashMap<String,String>();
	
	public Map<String,Object> param = new HashMap<String,Object>();
	
	public ResultInfo resultInfo = new ResultInfo();
	
	
	public String getProcessParamValue(String key){
		return StringUtil.getNotNullValueString(processParam.get(key));
	}
	
	public Object getParamObject(String key){
		return param.get(key);
	}
	
	public void addProcessParam(String key,String value){
		processParam.put(key,StringUtil.getNotNullValueString(value));
	}
	
	public void addParam(String key,Object obj){
		param.put(key,obj);
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	
}
