package com.wonders.send.subProcess.dept.service;

import com.wonders.send.subProcess.dept.model.vo.DeptSubParamVo;

public abstract interface DeptSubService
{
  public abstract void init(DeptSubParamVo paramDeptSubParamVo);

  public abstract void flowStepDispatcher(DeptSubParamVo paramDeptSubParamVo);

  public abstract void flowStepDeal(DeptSubParamVo paramDeptSubParamVo);

  public abstract void flowStepLeaderDeal(DeptSubParamVo paramDeptSubParamVo);
}

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.subProcess.dept.service.DeptSubService
 * JD-Core Version:    0.6.0
 */