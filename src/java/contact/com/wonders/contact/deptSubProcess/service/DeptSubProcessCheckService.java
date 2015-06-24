package com.wonders.contact.deptSubProcess.service;

import com.wonders.contact.deptSubProcess.model.vo.DeptSubProcessParamVo;

public interface DeptSubProcessCheckService {

	public void init(DeptSubProcessParamVo params);
	
	public void checkDispatcher();
	
	public void checkDeal();
	
	public void checkLeaderDeal();
	
	public boolean checkFlowIsInProcess();
}
