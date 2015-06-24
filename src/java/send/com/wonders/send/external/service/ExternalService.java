package com.wonders.send.external.service;

import com.wonders.send.external.model.UserInfo;
import com.wonders.send.organTree.model.bo.OrganDeptBo;
import com.wonders.send.organTree.model.bo.OrganLeaderBo;
import com.wonders.send.organTree.model.bo.OrganUserBo;
import java.util.List;
import java.util.Map;

public abstract interface ExternalService
{
  public abstract void setToken(String paramString);

  public abstract Map<String, String> getDeptReceiversMap(String paramString,String pname,String pid,String stepName);

  public abstract Map<String, String> getDeptReceiversMap(List<String> paramList,String pname,String pid,String stepName);

  public abstract List<OrganUserBo> getDeptReceivers(String paramString,String pname,String pid,String stepName);

  public abstract List<OrganUserBo> getDeptLeaders(String paramString);

  public abstract List<OrganUserBo> getDeptSingleLeader(String paramString);

  public abstract List<OrganUserBo> getDeptUsers(String paramString);

  public abstract List<OrganUserBo> getDeptUsersOffLeaders(String paramString);

  public abstract List<OrganDeptBo> getRelatedNodes(String paramString);

  public abstract List<OrganDeptBo> getChildNodes(String paramString);

  public abstract List<OrganDeptBo> getNodesInfo(String paramString);

  public abstract List<OrganDeptBo> getNodesInfo(List<String> paramList);

  public abstract List<OrganLeaderBo> getShlds(String processName,String type);
  
  public List<UserInfo> getUserInfoByLoginName(String all_loginname);
}

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.external.service.ExternalService
 * JD-Core Version:    0.6.0
 */