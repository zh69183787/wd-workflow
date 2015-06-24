package com.wonders.contact.common.util;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.wonders.common.model.vo.TaskUserVo;
import com.wonders.contact.common.model.vo.UserInfo;
import com.wonders.contact.constant.CommonConstants;
import com.wonders.contact.constant.LoginConstants;
import com.wonders.util.StringUtil;

/**
 * @author XFZ
 * @version 1.0 2012-6-19
 */
public class LoginUtil {
	@SuppressWarnings("unchecked")
	public static UserInfo getUserInfo(HttpSession session){
		UserInfo u = new UserInfo();
		SimpleLogger log = new SimpleLogger(LoginUtil.class);
		try{
			u.settLoginName(StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.T_LOGINNAME)));
			u.settLoginName(getUserLoginName(u.gettLoginName()));
			
			u.setUserId(StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.USERID)));
			u.setLoginName(StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.LOGINNAME)));
			
			u.setLoginName(getUserLoginName(u.getLoginName()));
			
			u.setUserName(StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.USERNAME)));
			
			u.setDeptId(StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.DEPTID)));
			u.setDeptName(StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.DEPTNAME)));
			u.setToken(StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.TOKEN)));
			if(session.getAttribute(LoginConstants.DEPT_USER) != null){
				u.setMap((Map<String, TaskUserVo>) session.getAttribute(LoginConstants.DEPT_USER));
			}
//log.debug(new com.google.gson.Gson().toJson(u)); 
		}catch(Exception e){
			log.warn(e.getMessage());
		}
		//log.debug(""+u);
		if(u.getUserId().length()==0) log.warn("session user init failed!");
		return u;
	}
	
	public static String getUserLoginName(UserInfo u){
		String prefix = CommonConstants.LOGINNAME_PREFIX;
		String loginName = u.getLoginName();
		
		if(!loginName.startsWith(prefix)){
			loginName=prefix + loginName;
		}
		
		if(u.getTaskUserVo() != null){
			return u.getTaskUserVo().getLoginName();
		}
		
		return loginName;
	}
	
	public static String getUserLoginName(String loginName){
		if(loginName.length()==0) return "";
		String prefix = CommonConstants.LOGINNAME_PREFIX;
		
		if(!loginName.startsWith(prefix)){
			loginName=prefix + loginName;
		}
		
		return loginName;
	}
}
