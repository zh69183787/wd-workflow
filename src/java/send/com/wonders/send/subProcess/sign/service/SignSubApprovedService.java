package com.wonders.send.subProcess.sign.service;

import com.wonders.send.approve.model.bo.SendApprovedinfo;
import com.wonders.send.subProcess.sign.model.vo.SignSubParamVo;

public abstract interface SignSubApprovedService
{
  public abstract SendApprovedinfo saveApprovedInfo(SignSubParamVo paramSignSubParamVo);
}

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.subProcess.sign.service.SignSubApprovedService
 * JD-Core Version:    0.6.0
 */