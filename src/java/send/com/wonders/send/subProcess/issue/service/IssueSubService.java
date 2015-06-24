package com.wonders.send.subProcess.issue.service;

import com.wonders.send.subProcess.issue.model.vo.IssueSubParamVo;

public abstract interface IssueSubService
{
  public abstract void init(IssueSubParamVo paramIssueSubParamVo);

  public abstract void flowStepSecretaryDeal(IssueSubParamVo paramIssueSubParamVo);

  public abstract void flowStepLeaderDeal(IssueSubParamVo paramIssueSubParamVo);
}

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.subProcess.issue.service.IssueSubService
 * JD-Core Version:    0.6.0
 */