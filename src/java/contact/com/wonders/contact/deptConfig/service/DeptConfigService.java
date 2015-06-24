package com.wonders.contact.deptConfig.service;

import java.util.List;

import com.wonders.contact.deptConfig.model.bo.TDeptContactRelation;
import com.wonders.contact.deptContact.model.bo.TDeptContactConfig;

public interface DeptConfigService {
	public List<TDeptContactRelation> getBoList();
	public void saveConfigUser(TDeptContactConfig t);
	public void updateConfigUser(String id,String loginName,String userName);
	public void switchConfigUser(String id,int flag);
	public int findConfigDept(String deptId);
}
