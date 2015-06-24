package com.wonders.send.approve.service;

import com.wonders.send.approve.model.bo.SendApprovedinfo;

import java.util.List;
import java.util.Map;

public abstract interface TApprovedinfoService
{
  public abstract void save(Object paramObject);

  public abstract List<SendApprovedinfo> findByHQL(String paramString);

  public abstract Map tapprovedinfoServiceByFW(String paramString1, String paramString2);

  public abstract List quereyApprovedInfoByDeptIdWithLeaderSign(String paramString1, String paramString2, String[] paramArrayOfString, String paramString3, String paramString4);

  public abstract List queryApprovedinfoLikeByDeptId(String paramString1, String paramString2, String[] paramArrayOfString);

  public abstract List queryApprovedinfoLike(String paramString1, String paramString2, String[] paramArrayOfString, String paramString3);
  
  public abstract List queryApprovedinfo(String processName, String instanceId,String[] stepName,String dept); 
}

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.approve.service.TApprovedinfoService
 * JD-Core Version:    0.6.0
 */