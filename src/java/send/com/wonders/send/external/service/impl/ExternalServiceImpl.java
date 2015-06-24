package com.wonders.send.external.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wonders.send.common.util.CommonUtil;
import com.wonders.send.common.util.LoginUtil;
import com.wonders.send.common.util.SimpleLogger;
import com.wonders.send.external.model.UserInfo;
import com.wonders.send.external.service.ExternalService;
import com.wonders.send.external.util.PortalUtil;
import com.wonders.send.organTree.model.bo.OrganDeptBo;
import com.wonders.send.organTree.model.bo.OrganLeaderBo;
import com.wonders.send.organTree.model.bo.OrganUserBo;
import com.wonders.send.util.PublicFunction;
import com.wonders.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**访问外部系统数据类
 * @author XFZ
 * @version 1.0 2012-6-3
 */
@Service("send-externalService")
@Scope("prototype")
public class ExternalServiceImpl implements ExternalService {
	private Logger log = SimpleLogger.getLogger(this.getClass());
	private Gson gson = new Gson();
	private String token = "";
	private PortalUtil portalUtil = new PortalUtil();
	
	
	
	@Override
	public List<OrganUserBo> getDeptReceivers(String deptIdStr,String pname,String pid,String stepName){
		//String deptIdStr = CommonUtil.listToStringBySplit(deptIds);
		String result = portalUtil.getDeptReceivers(deptIdStr,pname,pid,stepName);
		
		if(!"error".equals(result)){
			return this.getUserList(result);
		}
		
		return null;
	}

	@Override
	public Map<String, String> getDeptReceiversMap(List<String> deptIds,String pname,String pid,String stepName) {
		String deptIdStr = CommonUtil.listToStringBySplit(deptIds);
		return getDeptReceiversMap(deptIdStr,pname,pid,stepName);
	}
	
	@Override
	public Map<String,String> getDeptReceiversMap(String deptIdStr,String pname,String pid,String stepName) {
		
		Map<String, String> retMap = new HashMap<String, String>();
		
		List<OrganUserBo> list = this.getDeptReceivers(deptIdStr,pname,pid,stepName);
			
		if(list==null) return retMap;
		
		for(int i=0;i<list.size();i++){
			OrganUserBo user = list.get(i);
			
			String deptId = StringUtil.getNotNullValueString(user.pid);
			String loginName = StringUtil.getNotNullValueString(user.loginName);
			
			if(retMap.containsKey(deptId)){
//log.warn("getSubReceiver:覆盖部门("+deptId+")收发员");
			}
			retMap.put(deptId, loginName);
		}
//log.debug("getSubReceiver:获得接收人数量="+retMap.size());
		

		return retMap;
	}
		
	@Override
	public List<OrganUserBo> getDeptLeaders(String deptIdStr){
		//String deptIdStr = CommonUtil.listToStringBySplit(deptIds);
		String result = portalUtil.getDeptLeaders(deptIdStr);
//log.debug("getDeptLeaders:"+result);
		if(!"error".equals(result)){
			return this.getUserList(result);
		}
		
		return null;
	}
	
	@Override
	public List<OrganUserBo> getDeptSingleLeader(String deptIdStr){
		//String deptIdStr = CommonUtil.listToStringBySplit(deptIds);
		String result = portalUtil.getDeptSingleLeader(deptIdStr);
		
		if(!"error".equals(result)){
			return this.getUserList(result);
		}
		
		return null;
	}
	
	@Override
	public List<OrganUserBo> getDeptUsers(String deptIdStr){
		//String deptIdStr = CommonUtil.listToStringBySplit(deptIds);
		String result = portalUtil.getDeptUsers(deptIdStr);
		
		if(!"error".equals(result)){
			return this.getUserList(result);
		}
		
		return null;
	}
	
	@Override
	public List<OrganUserBo> getDeptUsersOffLeaders(String deptIdStr){
		//String deptIdStr = CommonUtil.listToStringBySplit(deptIds);
		String result = portalUtil.getDeptUsersOffLeaders(deptIdStr);
		
		if(!"error".equals(result)){
			return this.getUserList(result);
		}
		
		return null;
	}
	
	
	@Override
	public List<OrganDeptBo> getRelatedNodes(String deptIds){
		String result = portalUtil.getRelatedNodes(deptIds);
		
		if(!"error".equals(result)){
			return this.getDeptList(result);
		}
		
		return null;
	}
	
	@Override
	public List<OrganDeptBo> getNodesInfo(String deptIds){
		String result = portalUtil.getNodesInfo(deptIds);
		
		if(!"error".equals(result)){
			return this.getDeptList(result);
		}
		
		return null;
	}
	
	@Override
	public List<OrganDeptBo> getNodesInfo(List<String> deptIds){
		String deptIdStr = CommonUtil.listToStringBySplit(deptIds);
		return getNodesInfo(deptIdStr);
	}
	
	@Override
	public List<OrganDeptBo> getChildNodes(String deptId){
		String result = portalUtil.getChildNodes(deptId);
		
		if(!"error".equals(result)){
			return this.getDeptList(result);
		}
		return null;
	}
	
	
	
	
	
	private List<OrganDeptBo> getDeptList(String result){
		List<OrganDeptBo> list = new ArrayList<OrganDeptBo>();
		Type listType = new TypeToken<ArrayList<OrganDeptBo>>(){}.getType();
		list = gson.fromJson(result, listType);
//log.debug("getDeptList:"+list.size());
//log.debug(result);
		return list;
	}
	
	private List<OrganUserBo> getUserList(String result){
		List<OrganUserBo> list = new ArrayList<OrganUserBo>();
		Type listType = new TypeToken<ArrayList<OrganUserBo>>(){}.getType();
		list = gson.fromJson(result, listType);
		for(int i=0;list!=null&&i<list.size();i++){
			OrganUserBo bo = list.get(i);
			bo.loginName = LoginUtil.getUserLoginName(bo.loginName);
		}
		return list;
	}
	

	@Override
	public void setToken(String token) {
		this.token = token;
		portalUtil.setToken(token);
	}

	public String getToken() {
		return token;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
	
	 private List<OrganLeaderBo> getLeaderList(String result,String type) {
		 /* 195 */     List<OrganLeaderBo> list = new ArrayList<OrganLeaderBo>();
		 /* 196 */     Type listType = new TypeToken<ArrayList<OrganLeaderBo>>(){  } .getType();
		 /* 197 */     list = (List<OrganLeaderBo>)this.gson.fromJson(result, listType);
		 
		 				 String hqld_loginName = "";
						 if("2".equals(type)){
							 PublicFunction pb = new PublicFunction();
							 hqld_loginName = pb.getHqld();
						 }
		 /* 198 */     for (int i = 0; (list != null) && (i < list.size()); i++) {
		 /* 199 */       OrganLeaderBo bo = (OrganLeaderBo)list.get(i);
		 /* 200 */       bo.leaderLoginName = LoginUtil.getUserLoginName(bo.leaderLoginName);
		 /* 201 */       bo.secretaryLoginName = LoginUtil.getUserLoginName(bo.secretaryLoginName);
						 if("2".equals(type)){
							 if(hqld_loginName.indexOf(bo.leaderLoginName.replace("ST/","").substring(0,12))==-1){
								 list.remove(i);
								 i--;
							 }
						 }
		 /*     */     }
		 /* 203 */     return list;
		 /*     */   }

/*     */   public List<OrganLeaderBo> getShlds(String processName,String type) {
/* 227 */     String result = this.portalUtil.getShlds(processName);
/* 228 */     if (!"error".equals(result)) {
/* 229 */       return getLeaderList(result,type);
/*     */     }
/*     */ 
/* 232 */     return null;
/*     */   }

public List<UserInfo> getUserInfoByLoginName(String all_loginname){
	String result = this.portalUtil.getUserInfoByLoginName(all_loginname);
	if (!"error".equals(result)) {
       return getUserInfoList(result);
     }
 
    return null;
}

private List<UserInfo> getUserInfoList(String result){
	List<UserInfo> list = new ArrayList<UserInfo>();
	Type listType = new TypeToken<ArrayList<UserInfo>>(){}.getType();
	list = gson.fromJson(result, listType);
	return list;
}

/*     */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.external.service.impl.ExternalServiceImpl
 * JD-Core Version:    0.6.0
 */