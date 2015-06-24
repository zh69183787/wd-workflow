package com.wonders.send.future.dao;

import com.wonders.send.future.model.bo.Future;

public abstract interface FutureDao
{
  public abstract Future findFuturesByPName(String paramString1, String paramString2);

  public abstract void updateFutures(Future paramFuture, String paramString);

  public abstract void justSaveOrUpdateFuture(Future paramFuture);

  public abstract String toOrdersByQFLeader(String paramString);

  public abstract String toOrdersByDept(String paramString);

  public abstract String getDeptsOrders(String paramString);

  public abstract void save(Object paramObject);
}

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.future.dao.FutureDao
 * JD-Core Version:    0.6.0
 */