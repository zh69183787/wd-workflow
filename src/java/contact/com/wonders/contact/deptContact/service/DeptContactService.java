package com.wonders.contact.deptContact.service;

import com.wonders.contact.deptContact.model.vo.DeptContactMainVo;
import com.wonders.contact.deptContact.model.vo.DeptContactParamVo;

/**
 * @author XFZ
 * @version 1.0 2012-6-12
 */
public interface DeptContactService {
	//public ResultInfo getResultInfo();
	//public void setResultInfo(ResultInfo resultInfo);
	
	public void init(DeptContactParamVo params);
	
	public DeptContactMainVo flowStepForward(DeptContactParamVo params);
	
	public DeptContactMainVo viewForward(DeptContactParamVo params);
	
	public void flowStepAdd(DeptContactParamVo params);
	
	public void flowStepUpdate(DeptContactParamVo params);
	
	public void flowStepApply(DeptContactParamVo params);
	
	public void flowStepSign(DeptContactParamVo params);
	
	public DeptContactMainVo view(String pname,String pincident,DeptContactParamVo params);
	
	/*
	public ControllerBo getInitProcess(DeptContactControllerVo vo,TDeptContactMain mainBo,TDeptContactTree treeBo);
	
	public String launchLowerProcess(DeptContactControllerVo vo,TDeptContactMain mainBo,TDeptContactTree mainTreeBo);
	
	public Map<String,Integer> getCount(DeptContactControllerVo vo);
	
	public String endLowerProcess(DeptContactControllerVo vo,TDeptContactMain mainBo);
	
	public String linkSubProcess(DeptContactControllerVo vo,TDeptContactMain mainBo,TDeptContactTree treeBo);
	
	public String finishProcess(DeptContactControllerVo vo,TDeptContactTree treeBo);
	*/
}
