/**
 * 
 */
package com.wonders.common.model.vo;

import com.wonders.util.StringUtil;

/** 
 * @ClassName: UserBo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-8-20 上午11:54:12 
 *  
 */
public class TaskUserVo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5716952443627624863L;
	private String loginName = "";
	private String userId = "";
	private String userName = "";
	private String deptId = "";
	private String deptName = "";
	private String login_Name = "";
	
	public TaskUserVo(String userId,String loginName,String userName,String deptId,String deptName,String login_Name){
		this.userId = StringUtil.getNotNullValueString(userId);
		this.loginName = StringUtil.getNotNullValueString(loginName);
		this.userName = StringUtil.getNotNullValueString(userName);
		this.deptId = StringUtil.getNotNullValueString(deptId);
		this.deptName = StringUtil.getNotNullValueString(deptName);
		this.login_Name = StringUtil.getNotNullValueString(login_Name);
	}
	
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}


	public String getLogin_Name() {
		return login_Name;
	}


	public void setLogin_name(String login_Name) {
		this.login_Name = login_Name;
	}
	
	
}
