package com.wonders.send.subProcess.sign.service;

import com.wonders.send.subProcess.sign.model.vo.SignSubParamVo;

public abstract interface SignSubService
{
  public abstract void init(SignSubParamVo paramSignSubParamVo);

  public abstract void flowStepSecretaryDeal(SignSubParamVo paramSignSubParamVo);

  public abstract void flowStepLeaderDeal(SignSubParamVo paramSignSubParamVo);
}

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.subProcess.sign.service.SignSubService
 * JD-Core Version:    0.6.0
 */