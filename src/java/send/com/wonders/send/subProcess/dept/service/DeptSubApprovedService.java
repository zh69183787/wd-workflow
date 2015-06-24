package com.wonders.send.subProcess.dept.service;

import com.wonders.send.approve.model.bo.SendApprovedinfo;
import com.wonders.send.subProcess.dept.model.vo.DeptSubParamVo;

public abstract interface DeptSubApprovedService
{
  public abstract SendApprovedinfo saveApprovedInfo(DeptSubParamVo paramDeptSubParamVo);
}

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.subProcess.dept.service.DeptSubApprovedService
 * JD-Core Version:    0.6.0
 */