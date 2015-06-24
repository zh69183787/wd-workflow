package com.wonders.contact.external.service.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wonders.contact.common.util.CommonUtil;
import com.wonders.contact.common.util.LoginUtil;
import com.wonders.contact.common.util.SimpleLogger;
import com.wonders.contact.external.service.ExternalService;
import com.wonders.contact.external.util.PortalUtil;
import com.wonders.contact.organTree.model.bo.OrganDeptBo;
import com.wonders.contact.organTree.model.bo.OrganUserBo;
import com.wonders.util.DbUtil;
import com.wonders.util.StringUtil;

/**访问外部系统数据类
 * @author XFZ
 * @version 1.0 2012-6-3
 */
@Service("contact-externalService")
@Scope("prototype")
public class ExternalServiceImpl implements ExternalService {
	private Logger log = SimpleLogger.getLogger(this.getClass());
	private Gson gson = new Gson();
	private DbUtil dbUtil;
	private String token = "";
	private PortalUtil portalUtil = new PortalUtil();
	
	
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
		
		List<OrganUserBo> list = this.getProcessReceivers(processName,stepName,deptIdStr);
			
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
	public List<OrganUserBo> getDeptReceivers(String deptIdStr){
		//String deptIdStr = CommonUtil.listToStringBySplit(deptIds);
		String result = portalUtil.getDeptReceivers(deptIdStr);
		
		if(!"error".equals(result)){
			return this.getUserList(result);
		}
		
		return null;
	}

	@Override
	public Map<String, String> getDeptReceiversMap(List<String> deptIds) {
		String deptIdStr = CommonUtil.listToStringBySplit(deptIds);
		return getDeptReceiversMap(deptIdStr);
	}
	
	@Override
	public Map<String,String> getDeptReceiversMap(String deptIdStr) {
		
		Map<String, String> retMap = new HashMap<String, String>();
		
		List<OrganUserBo> list = this.getDeptReceivers(deptIdStr);
			
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
	
	
	public DbUtil getDbUtil() {
		return dbUtil;
	}
	
	@Autowired(required=false)
	public void setDbUtil(@Qualifier("dbUtil")DbUtil dbUtil) {
		this.dbUtil = dbUtil;
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
