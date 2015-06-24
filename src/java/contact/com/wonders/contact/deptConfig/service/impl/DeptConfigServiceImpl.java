package com.wonders.contact.deptConfig.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.contact.deptConfig.model.bo.TDeptContactRelation;
import com.wonders.contact.deptConfig.service.DeptConfigService;
import com.wonders.contact.deptContact.model.bo.TDeptContactConfig;

import com.wonders.contact.deptConfig.dao.DeptConfigDao;

@Service("contact-deptConfigService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class DeptConfigServiceImpl implements DeptConfigService{
	private DeptConfigDao deptConfigDao;

	
	public List<TDeptContactRelation> getBoList(){
		return deptConfigDao.getBoList();
	}
	
	public void saveConfigUser(TDeptContactConfig t){
		deptConfigDao.saveConfigUser(t);
	}
	public void updateConfigUser(String id,String loginName,String userName){
		deptConfigDao.updateConfigUser(id, loginName, userName);
	}
	public void switchConfigUser(String id,int flag){
		deptConfigDao.switchConfigUser(id, flag);
	}
	
	public int findConfigDept(String deptId){
		return deptConfigDao.findConfigDept(deptId);
	}
	
	
	
	
	public DeptConfigDao getDeptConfigDao() {
		return deptConfigDao;
	}
	@Autowired
	public void setDeptConfigDao(@Qualifier("contact-deptConfigDao")DeptConfigDao deptConfigDao) {
		this.deptConfigDao = deptConfigDao;
	}	
}
