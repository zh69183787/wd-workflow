package com.wonders.send.approve.dao;

import com.wonders.send.approve.model.bo.SendApprovedinfo;
import java.util.List;

public abstract interface TApprovedinfoDao
{
  public abstract void save(Object paramObject);

  public abstract List<SendApprovedinfo> findByHQL(String paramString);
}

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.approve.dao.TApprovedinfoDao
 * JD-Core Version:    0.6.0
 */