package com.wonders.contact.external.action;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.wonders.contact.common.util.SimpleLogger;
import com.wonders.contact.deptContact.util.ActionWriter;
import com.wonders.contact.external.model.vo.AttachVo;
import com.wonders.contact.external.service.ApiService;

@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/contact-api")
@Controller("contact-apiAttachAction")
@Scope("prototype")
public class ApiAttachAction extends ActionSupport implements ModelDriven<AttachVo>{
	public ActionContext actionContext = ActionContext.getContext();
	public HttpServletRequest request = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
	public HttpServletResponse response = (HttpServletResponse) actionContext.get(ServletActionContext.HTTP_RESPONSE);
	public HttpSession session = request.getSession();
	
	SimpleLogger log = new SimpleLogger(this.getClass());
	
	ActionWriter aw = new ActionWriter(response);
	private Gson gson = new Gson();
	private String groupId = "";
	private String attachId = "";
	public AttachVo attachVo = new AttachVo();
	private ApiService apiService;
	
	@Action(value="attachDetail")
	public String detail(){
//log.debug("detail");
		try {
			aw.writeJson(apiService.getAttachDetail(attachId));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Action(value="attachList")
	public String list(){
//log.debug("list");
		try {
			aw.writeJson(apiService.getAttachList(groupId));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Action(value="beginUpload")
	public String beginUpload(){
		//System.out.println(attachVo.data);
		Map<String,String> data_map = gson.fromJson(attachVo.data,attachVo.data_map.getClass());
		attachVo.user = gson.fromJson(attachVo.userInfo,attachVo.user.getClass());
		if(data_map!=null) attachVo.data_map=data_map;
		aw.writeJson(apiService.getAttachStatus(attachVo));
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Action(value="underUpload")
	public String underUpload() throws IOException{
		String data = request.getHeader("data"); 
		String userInfo = request.getHeader("userInfo"); 
		userInfo = new String(userInfo.getBytes("iso-8859-1"),"GBK");
		//System.out.println(data);
		//System.out.println(userInfo);
		Map<String,String> data_map = gson.fromJson(data,attachVo.data_map.getClass());
		if(data_map!=null) attachVo.data_map=data_map;
		attachVo.user = gson.fromJson(userInfo,attachVo.user.getClass());
		apiService.attachUpload(attachVo,new BufferedInputStream(request.getInputStream()));
		//response.setStatus(200);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Action(value="commitUpload")
	public String commitUpload(){
		Map<String,String> data_map = gson.fromJson(attachVo.data,attachVo.data_map.getClass());
		attachVo.user = gson.fromJson(attachVo.userInfo,attachVo.user.getClass());
		if(data_map!=null) attachVo.data_map=data_map;
		aw.writeJson(apiService.attachCommit(attachVo));
		return null;
	}
	
	public ApiService getApiService() {
		return apiService;
	}
	
	@Autowired(required=false)
	public void setApiService(@Qualifier("contact-apiService")ApiService apiService) {
		this.apiService = apiService;
	}
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getAttachId() {
		return attachId;
	}
	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}
	
	@Override
	public AttachVo getModel() {
		return attachVo;
	}
	
}
