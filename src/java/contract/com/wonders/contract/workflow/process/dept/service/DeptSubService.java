/**
 * 
 */
package com.wonders.contract.workflow.process.dept.service;

import com.wonders.contract.workflow.process.dept.model.vo.DeptSubParamVo;

/** 
 * @ClassName: DeptSubService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 上午11:42:23 
 *  
 */
public interface DeptSubService {
	public void init(DeptSubParamVo params);
	public void flowStepDispatcher(DeptSubParamVo params);
	public void flowStepDeal(DeptSubParamVo params);
	public void flowStepLeaderDeal(DeptSubParamVo params);
}
