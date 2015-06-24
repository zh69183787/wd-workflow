/**
 * 
 */
package com.wonders.project.workflow.process.discard.service.impl;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.contract.workflow.common.util.SimpleLogger;
import com.wonders.project.workflow.common.model.vo.UserInfo;
import com.wonders.project.workflow.common.service.CommonService;
import com.wonders.project.workflow.external.service.ExternalService;
import com.wonders.project.workflow.external.util.HttpRequestProxy;
import com.wonders.project.workflow.process.discard.model.bo.DiscardAssetBo;
import com.wonders.project.workflow.process.discard.model.bo.DiscardMainBo;
import com.wonders.project.workflow.process.discard.service.DiscardMainMessageService;
import com.wonders.util.DateUtil;
import com.wonders.util.PropertyUtil;

/** 
 * @ClassName: DiscardMainMessageServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:53:14 
 *  
 */
@Service("project-discardMainMessageService")
@Scope("prototype")
public class DiscardMainMessageServiceImpl implements DiscardMainMessageService{
	Logger log = SimpleLogger.getLogger(this.getClass());
	
	private CommonService service;
	private ExternalService externalService;
	private UserInfo userInfo = new UserInfo();
	
	private static final String assetRecordUpdateUrl = PropertyUtil.getValueByKey("config.properties", "assetRecordUpdateUrl");
	private static final String projectCancelsUpdateUrl = PropertyUtil.getValueByKey("config.properties", "projectCancelsUpdateUrl");
	private static final String deptPassAddWithFixedRemarkUrl = PropertyUtil.getValueByKey("config.properties", "deptPassAddWithFixedRemarkUrl");
	private static final String addTaskUrl = PropertyUtil.getValueByKey("config.properties", "addTaskUrl");
	private static final String formViewUrl = PropertyUtil.getValueByKey("config.properties", "formViewUrl");
	
	/**
	 * 办结信息传递
	 */
	@Override
	public void sendMessageOnFinish(DiscardMainBo bo, UserInfo userInfo){
		this.userInfo = userInfo;
		//1.维修内容更新到资产履历表
		for(DiscardAssetBo asset : bo.getAssets()){
			if(StringUtils.isNotEmpty(asset.getContent())){
				Map<String,String> p = new HashMap<String,String>();  
		        p.put("maintainContent",asset.getContent());
		        p.put("id",asset.getAssetRecordId());
		        String results = HttpRequestProxy.doPost(assetRecordUpdateUrl, p);
		        if(StringUtils.isEmpty(results) || results.contains("1001")){
		        	log.error("项目销项流程办结后续步骤1-维修内容更新到资产履历表失败,params["+p+"],results["+results+"],销项主表ID["+bo.getId()+"]");
		        }	
			}
		}
		
		//2.传阅财务部部门领导
		passToCW(bo);
		
		//3.将项目状态更新为“销项完成”
		Map<String,String> p = new HashMap<String,String>();  
        p.put("projectState","3");
        p.put("projectDestoryDate",DateUtil.getNowDate());
        p.put("settlementPrice",bo.getFinishPrice());
        p.put("id",bo.getProjectId());		
        String results = HttpRequestProxy.doPost(projectCancelsUpdateUrl, p);
        if(StringUtils.isEmpty(results) || results.contains("1001")){
        	log.error("项目销项流程办结后续步骤3-项目状态更新为销项完成失败,params["+p+"],results["+results+"],项目ID["+bo.getProjectId()+"]");
        }        
	}
	
	/**
	 * 传阅财务部领导-待办参数
	 * @return
	 */
	private void passToCW(DiscardMainBo bo){
		this.externalService.setToken(userInfo.getToken());
		String loginName = externalService.getDeptSingleLeader("2507").get(0).loginName; //投资部领导
		Map param = new HashMap();
		
		try {
			String url = deptPassAddWithFixedRemarkUrl;
			String modelName = URLEncoder.encode("项目销项结果传阅","UTF-8");
			
			String title = URLEncoder.encode(bo.getProjectName()+"（"+bo.getProjectNo()+"）已经通过销项审批","UTF-8");
			
			String targetUrl = formViewUrl + "?pincident=" + bo.getInstanceId();
			String remark = URLEncoder.encode("&lt;a href='"+targetUrl+"' target='_blank'&gt;点击查看销项表单&lt;/a&gt;","UTF-8");
			
			url += "?modelName="+modelName+"&attach=&title="+title+"&mainId="+bo.getId()+"&mainTable=PCL_PROJECT_DISCARD&remark="+remark+"&taskuser="+loginName;
			
			param.put("app", "workflow");
			param.put("type", "0");
			param.put("occurTime", DateUtil.getNowTime());
			param.put("title", bo.getProjectName()+"（"+bo.getProjectNo()+"）已经通过销项审批");
			param.put("loginName", loginName);
			param.put("status", "0");
			param.put("removed", "0");
			param.put("typename", "项目销项结果传阅");
			param.put("url", "");
			param.put("pname", "项目销项结果传阅");
			param.put("pincident", "1");
			param.put("cname", "子流程实例号");
			param.put("cincident", "1");
			param.put("stepName", "项目销项结果传阅财务部");
			param.put("initiator", loginName);
			
			String todoId = HttpRequestProxy.doPost(addTaskUrl, "&data="+ JSONObject.fromObject(param).toString());
			this.service.UpdateByHql("update TodoItem set url = '" + (url+"&todoId="+todoId) + "' where id = '" + todoId + "'");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public CommonService getService() {
		return service;
	}
	
	@Autowired(required=false)
	public void setService(@Qualifier(value="project-commonService")CommonService service) {
		this.service = service;
	}

	
	public ExternalService getExternalService() {
		return externalService;
	}
	
	@Autowired
	public void setExternalService(@Qualifier("project-externalService")ExternalService externalService) {
		this.externalService = externalService;
	}
	
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
}
