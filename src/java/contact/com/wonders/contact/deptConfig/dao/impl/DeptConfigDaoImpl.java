package com.wonders.contact.deptConfig.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.wonders.contact.common.service.CommonService;
import com.wonders.contact.deptConfig.model.bo.TDeptContactRelation;
import com.wonders.contact.deptContact.model.bo.TDeptContactConfig;

import com.wonders.contact.deptConfig.dao.DeptConfigDao;

@Repository("contact-deptConfigDao")
public class DeptConfigDaoImpl implements DeptConfigDao{
	private CommonService service;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TDeptContactRelation> getBoList(){
		String hql = "from TDeptContactRelation t where t.removed!=1";
		List<TDeptContactRelation> list = service.ListByHql(hql);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public int findConfigDept(String deptId){
		String hql = "from TDeptContactConfig t where t.deptid='"+deptId+"'";
		List<TDeptContactRelation> list = service.ListByHql(hql);
		return list.size();
	}
	
	public void saveConfigUser(TDeptContactConfig t){
		service.save(t);
	}
	
	public void updateConfigUser(String id,String loginName,String userName){
		String hql = "update TDeptContactConfig t set t.initiator='"+loginName+"',t.initiatorName='"+userName+
						"',t.receiver='"+loginName+"',t.receiverName='"+userName+"' where t.id='"+id+"'";
		service.UpdateByHql(hql);
	}
	
	
	public void switchConfigUser(String id,int flag){
		int removed = 0;
		if(flag==0) removed=1;
		String hql = "update TDeptContactConfig t set t.removed="+removed+" where t.id='"+id+"'";
		service.UpdateByHql(hql);
	}
	
	
	@Override
	public CommonService getService() {
		return service;
	}
	
	@Autowired(required=false)
	public void setService(@Qualifier(value="contact-commonService")CommonService service) {
		this.service = service;
	}
}
