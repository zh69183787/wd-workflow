package com.wonders.contact.deptContact.service;

import com.wonders.contact.deptContact.model.vo.DeptContactParamVo;

/**
 * @author XFZ
 * @version 1.0 2012-6-12
 */
public interface DeptContactCheckService {
	
	public void init(DeptContactParamVo params);

	public void checkForward();
	
	public void checkAdd();
	
	public void checkUpdate();
	
	public void checkApply();
	
	public void checkSign();

	public boolean checkFlowIsInProcess();
		
}
