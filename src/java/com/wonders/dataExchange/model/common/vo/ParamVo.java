/**
 * 
 */
package com.wonders.dataExchange.model.common.vo;

import java.util.HashMap;
import java.util.Map;

import com.wonders.dataExchange.model.common.xml.RegisterDataVo;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: ParamVo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 上午11:29:37 
 *  
 */
public abstract class ParamVo {
	public RegisterDataVo registerVo;
	public UserInfo userInfo;
	public Map<String,String> processParam = new HashMap<String,String>();
	public ResultInfo resultInfo = new ResultInfo();
	
	public String getProcessParamValue(String key){
		return StringUtil.getNotNullValueString(processParam.get(key));
	}
	public void addProcessParam(String key,String value){
		processParam.put(key,StringUtil.getNotNullValueString(value));
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public RegisterDataVo getRegisterVo() {
		return registerVo;
	}
	public void setRegisterVo(RegisterDataVo registerVo) {
		this.registerVo = registerVo;
	}
	
	
}
