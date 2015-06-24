package com.wonders.contact.common.model.vo;

import java.util.HashMap;
import java.util.Map;

import com.wonders.common.model.vo.TaskUserVo;



public class UserInfo {
	
	private String tLoginName = "";
	private String userId="";
	private String loginName="";
	private String userName="";
	
	private String deptId="";
	private String deptName="";
	
	private String token="";
	
	private Map<String,TaskUserVo> map = new HashMap<String,TaskUserVo>();
	private TaskUserVo taskUserVo = null; 
	
	public TaskUserVo getUserParam(String key){
		return map.get(key);
	}
	public void addUserParam(String key,TaskUserVo vo){
		map.put(key,vo);
	}
	
	public void initTaskUser(String loginName){
		if(map != null && map.containsKey(loginName)){
			taskUserVo = map.get(loginName);
		}
	}
	

	public Map<String, TaskUserVo> getMap() {
		return map;
	}
	public void setMap(Map<String, TaskUserVo> map) {
		this.map = map;
	}
	
	@Override
	public String toString(){
		return "userVo: userId:"+userId+" loginName:"+loginName+" userName"+userName+" deptId:"+deptId+" deptName:"+deptName+" token:"+token;
	}

	public String getUserId() {
		if(taskUserVo != null){
			return taskUserVo.getUserId();
		}
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		if(taskUserVo != null){
			return taskUserVo.getLoginName();
		}
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		if(taskUserVo != null){
			return taskUserVo.getUserName();
		}
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeptId() {
		if(taskUserVo != null){
			return taskUserVo.getDeptId();
		}
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		if(taskUserVo != null){
			return taskUserVo.getDeptName();
		}
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public TaskUserVo getTaskUserVo() {
		return taskUserVo;
	}
	public void setTaskUserVo(TaskUserVo taskUserVo) {
		this.taskUserVo = taskUserVo;
	}
	public String gettLoginName() {
		return tLoginName;
	}
	public void settLoginName(String tLoginName) {
		this.tLoginName = tLoginName;
	}
	
	
}
