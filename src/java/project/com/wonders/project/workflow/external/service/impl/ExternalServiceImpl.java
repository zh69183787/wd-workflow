package com.wonders.project.workflow.external.service.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wonders.project.workflow.common.util.CommonUtil;
import com.wonders.project.workflow.common.util.LoginUtil;
import com.wonders.project.workflow.common.util.SimpleLogger;
import com.wonders.project.workflow.external.service.ExternalService;
import com.wonders.project.workflow.external.util.PortalUtil;
import com.wonders.project.workflow.organTree.model.bo.GroupBo;
import com.wonders.project.workflow.organTree.model.bo.GroupUserBo;
import com.wonders.project.workflow.organTree.model.bo.OrganDeptBo;
import com.wonders.project.workflow.organTree.model.bo.OrganLeaderBo;
import com.wonders.project.workflow.organTree.model.bo.OrganUserBo;
import com.wonders.util.StringUtil;


/**访问外部系统数据类
 * @author XFZ
 * @version 1.0 2012-6-3
 */
@Service("project-externalService")
@Scope("prototype")
public class ExternalServiceImpl implements ExternalService {
	private Logger log = SimpleLogger.getLogger(this.getClass());
	private Gson gson = new Gson();
	private String token = "";
	private PortalUtil portalUtil = new PortalUtil();
	
	
	
	@Override
	public List<OrganUserBo> getDeptReceivers(String deptIdStr){
		//String deptIdStr = CommonUtil.listToStringBySplit(deptIds);
		String result = portalUtil.getDeptReceivers(deptIdStr);
		
		if(!"error".equals(result)){
			return this.getUserList(result);
		}
		
		return null;
	}
	
	@Override
	public List<OrganUserBo> getReceivers(String processName,String stepName,String deptIds){
		//String deptIdStr = CommonUtil.listToStringBySplit(deptIds);
		String result = portalUtil.getReceivers(processName, stepName, deptIds);
		
		if(!"error".equals(result)){
			return this.getUserList(result);
		}
		
		return null;
	}
	
	@Override
	public List<OrganUserBo> getProcessReceivers(String processName,String stepName,String deptIds){
		//String deptIdStr = CommonUtil.listToStringBySplit(deptIds);
		String result = portalUtil.getProcessReceivers(processName, stepName, deptIds);
		
		if(!"error".equals(result)){
			return this.getUserList(result);
		}
		
		return null;
	}

	@Override
	public Map<String, String> getDeptReceiversMap(String processName,String stepName,List<String> deptIds) {
		String deptIdStr = CommonUtil.listToStringBySplit(deptIds);
		return getDeptReceiversMap(processName,stepName,deptIdStr);
	}
	
	@Override
	public Map<String,String> getDeptReceiversMap(String processName,String stepName,String deptIdStr) {
		
		Map<String, String> retMap = new HashMap<String, String>();
		
		List<OrganUserBo> list = this.getDeptReceivers(deptIdStr);
			
		if(list==null) return retMap;
		
		for(int i=0;i<list.size();i++){
			OrganUserBo user = list.get(i);
			
			String deptId = StringUtil.getNotNullValueString(user.pid);
			String loginName = StringUtil.getNotNullValueString(user.loginName);
			String name = StringUtil.getNotNullValueString(user.name);
			if(retMap.containsKey(deptId)){
//log.warn("getSubReceiver:覆盖部门("+deptId+")收发员");
			}
			retMap.put(deptId, loginName+","+name);
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
	public Map<String,String> getDeptSingleLeaderMap(String deptIdStr) {
		
		Map<String, String> ldMap = new HashMap<String, String>();
		
		List<OrganUserBo> list = this.getDeptSingleLeader(deptIdStr);
			
		if(list==null) return ldMap;
		
		for(int i=0;i<list.size();i++){
			OrganUserBo user = list.get(i);
			
			String deptId = StringUtil.getNotNullValueString(user.pid);
			String loginName = StringUtil.getNotNullValueString(user.loginName);
			String name = StringUtil.getNotNullValueString(user.name);
			
			if(ldMap.containsKey(deptId)){
//log.warn("getSubReceiver:覆盖部门("+deptId+")收发员");
			}
			ldMap.put(deptId, loginName+","+name);
		}
//log.debug("getSubReceiver:获得接收人数量="+retMap.size());
		

		return ldMap;
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
	public List<OrganDeptBo> getNodesFullInfo(String deptIds){
		String result = portalUtil.getNodesInfo(deptIds);
		
		if(!"error".equals(result)){
			List<OrganDeptBo> list = null;
			Map<String,String> reMap = null;
			Map<String,String> ldMap = null;
			
			list = this.getDeptList(result);

			reMap = getDeptReceiversMap("","部门接受人工作分发",deptIds);
			ldMap = getDeptSingleLeaderMap(deptIds);
			for(OrganDeptBo bo : list){
				bo.isParent = false;
				if(reMap.containsKey(bo.id)){
					bo.recvLoginName = reMap.get(bo.id).split(",")[0];
					bo.recvName = reMap.get(bo.id).split(",")[1];
				}else{
					bo.recvLoginName = "";
					bo.recvName = "";
				}
				if(ldMap.containsKey(bo.id)){
					bo.ldLoginName = ldMap.get(bo.id).split(",")[0];
					bo.ldName = ldMap.get(bo.id).split(",")[1];
				}else{
					bo.ldLoginName = "";
					bo.ldName = "";
				}
			}
			return list;
		}
		
		return null;
	}
	
	@Override
	public List<OrganDeptBo> getNodesInfo(List<String> deptIds){
		String deptIdStr = CommonUtil.listToStringBySplit(deptIds);
		return getNodesInfo(deptIdStr);
	}
	
	@Override
	public List<OrganDeptBo> getChildNodes(String processName,String deptId){
		String result = portalUtil.getChildNodes(deptId);
		String deptIds = "";
		List<OrganDeptBo> list = null;
		Map<String,String> reMap = null;
		Map<String,String> ldMap = null;
		if(!"error".equals(result)){
			list = this.getDeptList(result);
			for(OrganDeptBo bo : list){
				deptIds += bo.id + ",";
			}
			deptIds = deptIds.substring(0,deptIds.length()-1);
			reMap = getDeptReceiversMap(processName,"部门接受人工作分发",deptIds);
			ldMap = getDeptSingleLeaderMap(deptIds);
			for(OrganDeptBo bo : list){
				bo.isParent = false;
				if(reMap.containsKey(bo.id)){
					bo.recvLoginName = reMap.get(bo.id).split(",")[0];
					bo.recvName = reMap.get(bo.id).split(",")[1];
				}else{
					bo.recvLoginName = "";
					bo.recvName = "";
				}
				if(ldMap.containsKey(bo.id)){
					bo.ldLoginName = ldMap.get(bo.id).split(",")[0];
					bo.ldName = ldMap.get(bo.id).split(",")[1];
				}else{
					bo.ldLoginName = "";
					bo.ldName = "";
				}
			}
		}
		return list;
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
	
	public List<OrganLeaderBo> getComLeaderInfo(String type){
		String result = portalUtil.getComLeaderInfo("","");
		if(!"error".equals(result)){
			return this.getComLeaderList(result);
		}
		
		return null;
	}
	
	private List<OrganLeaderBo> getComLeaderList(String result){
		List<OrganLeaderBo> list = new ArrayList<OrganLeaderBo>();
		Type listType = new TypeToken<ArrayList<OrganLeaderBo>>(){}.getType();
		list = gson.fromJson(result, listType);
		for(OrganLeaderBo bo : list){
			bo.leaderLoginName = LoginUtil.getUserLoginName(bo.leaderLoginName);
			bo.secretaryLoginName = LoginUtil.getUserLoginName(bo.secretaryLoginName);
			bo.init();		
		}
//log.debug("getDeptList:"+list.size());
//log.debug(result);
		return list;
	}

	
	public List<GroupUserBo> getGroupUsersInfo(String code){
		String result = portalUtil.getGroupUsersInfo(code);
		if(!"error".equals(result)){
			return this.getGroupUsersInfoList(result);
		}
		
		return null;
	}
	
	private List<GroupUserBo> getGroupUsersInfoList(String result){
		List<GroupUserBo> list = new ArrayList<GroupUserBo>();
		Type listType = new TypeToken<ArrayList<GroupUserBo>>(){}.getType();
		list = gson.fromJson(result, listType);
		for(GroupUserBo bo : list){
			bo.loginName = LoginUtil.getUserLoginName(bo.loginName);
			bo.init();		
		}
//log.debug("getDeptList:"+list.size());
//log.debug(result);
		return list;
	}
	
	public List<GroupBo> getGroupsInfo(String code){
		String result = portalUtil.getGroupsInfo(code);
		if(!"error".equals(result)){
			return this.getGroupsInfoList(result);
		}
		
		return null;
	}
	
	private List<GroupBo> getGroupsInfoList(String result){
		List<GroupBo> list = new ArrayList<GroupBo>();
		Type listType = new TypeToken<ArrayList<GroupBo>>(){}.getType();
		list = gson.fromJson(result, listType);
		for(GroupBo bo : list){
			bo.init();		
		}
//log.debug("getDeptList:"+list.size());
//log.debug(result);
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
}
