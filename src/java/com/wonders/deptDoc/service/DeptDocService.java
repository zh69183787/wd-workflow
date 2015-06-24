package com.wonders.deptDoc.service;



/**
 * @author zhoushun
 *
 */
public interface DeptDocService {
	public void addDocs(String pname,String pincident,String taskUserLoginName,String userName,String deptId);
	public void addDocs(Object bo,String taskUserLoginName,String userName,String deptId);

}
