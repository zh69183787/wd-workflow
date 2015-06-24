package com.wonders.contact.deptSubProcess.service;

import com.wonders.contact.deptSubProcess.model.vo.DeptSubProcessParamVo;

/**
 * @author XFZ
 * @version 1.0 2012-6-12
 */
public interface DeptSubProcessService {
	public void init(DeptSubProcessParamVo params);
	
	public void flowStepDispatcher(DeptSubProcessParamVo params);
	
	public void flowStepDeal(DeptSubProcessParamVo params);
	
	public void flowStepLeaderDeal(DeptSubProcessParamVo params);
}
