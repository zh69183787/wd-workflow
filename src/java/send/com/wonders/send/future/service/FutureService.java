package com.wonders.send.future.service;

import com.wonders.send.future.model.bo.Future;

public abstract interface FutureService
{
  public abstract String findDeptName(String paramString1, String paramString2);

  public abstract String findLDName(String paramString1, String paramString2);

  public abstract String findByType(String paramString1, String paramString2, String paramString3);

  public abstract String findBJName(String paramString1, String paramString2);

  public abstract String findQFLeadName(String paramString1, String paramString2);

  public abstract String findHQLeadName(String paramString1, String paramString2);

  public abstract void addFutureFairs(Future paramFuture);

  public abstract String findStep(String paramString1, String paramString2, String paramString3);

  public abstract void updateFutures(Future paramFuture, String paramString);

  public abstract void saveOrUpdateFutures(String paramString1, String paramString2, Future paramFuture);

  public abstract Future findFuturesByPName(String paramString1, String paramString2);

  public abstract String findDeptId(String paramString1, String paramString2);

  public abstract void justSaveOrUpdateFuture(Future paramFuture);

  public abstract String toOrdersByQFLeader(String paramString);

  public abstract String getDeptsOrders(String paramString);

  public abstract String findDeptNameByOrders(String paramString1, String paramString2);
}

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.future.service.FutureService
 * JD-Core Version:    0.6.0
 */