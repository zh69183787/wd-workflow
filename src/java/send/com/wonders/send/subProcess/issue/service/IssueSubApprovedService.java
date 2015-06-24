package com.wonders.send.subProcess.issue.service;

import com.wonders.send.approve.model.bo.SendApprovedinfo;
import com.wonders.send.subProcess.issue.model.vo.IssueSubParamVo;

public abstract interface IssueSubApprovedService
{
  public abstract SendApprovedinfo saveApprovedInfo(IssueSubParamVo paramIssueSubParamVo);
}

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.subProcess.issue.service.IssueSubApprovedService
 * JD-Core Version:    0.6.0
 */