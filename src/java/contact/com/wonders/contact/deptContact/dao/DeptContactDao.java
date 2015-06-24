package com.wonders.contact.deptContact.dao;

import com.wonders.contact.common.service.CommonService;
import com.wonders.contact.deptContact.model.bo.TDeptContactMain;
import com.wonders.contact.deptContact.model.bo.TDeptContactTree;

public interface DeptContactDao {
	
	public TDeptContactMain getMainBo(String pname,String pincident);
	
	public TDeptContactMain getMainBo(String Id);
	
	public TDeptContactMain getParentMainBoByKey(String key);
	
	public TDeptContactMain getSelfMainBoByKey(String key);
	
	public TDeptContactTree getTreeBo(String key);
	
	public TDeptContactTree getTreeBoByMainBoId(String mainBoId);
	
	public TDeptContactTree getTreeBoByCInfo(String cname,String cincident);
	//public CommonService getService();
	//public TDeptContactMain getMainById(String mainId);
	//public TDeptContactMain getMainByChildId(String cId);
	//public List<TDeptContact> getAllBoByMainId(String mainId);
	//public TDeptContact getBo(String mainId,String deptId);
	//public TDeptContact getBoByIncidentNo(String processName,String incidentNo);
	//public List<TDeptContact> getRefBoByMainId(String mainId);
	//public List<TDeptContactCcDept> getCcDeptByMainId(String mainId);
	
	
	
	
	public CommonService getService();

}
