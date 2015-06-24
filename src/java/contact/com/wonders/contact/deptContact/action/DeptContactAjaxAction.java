package com.wonders.contact.deptContact.action;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wonders.contact.common.model.vo.UserInfo;
import com.wonders.contact.common.service.CommonService;
import com.wonders.contact.common.util.LoginUtil;
import com.wonders.contact.deptContact.model.bo.TDeptContactMain;
import com.wonders.contact.deptContact.util.ActionWriter;
import com.wonders.contact.deptContact.util.TextUtil;
import com.wonders.contact.external.service.ExternalService;
import com.wonders.util.StringUtil;

/**
 * @author XFZ
 * @version 1.0 2012-6-12
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/contact-deptContact")
@Controller("contact-deptContactAjaxAction")
@Scope("prototype")
public class DeptContactAjaxAction extends ActionSupport{
	ActionContext actionContext = ActionContext.getContext();
	HttpServletRequest request = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
	HttpServletResponse response = (HttpServletResponse) actionContext.get(ServletActionContext.HTTP_RESPONSE);
	HttpSession session = request.getSession();
	
	private UserInfo userInfo = new UserInfo();
	
	private ActionWriter aw = new ActionWriter(response);
	
	private CommonService commonService;
	private ExternalService externalService;
	
	public DeptContactAjaxAction(){
		userInfo = LoginUtil.getUserInfo(session);
		
	}
	
	@Action(value="signLeader")
	public String signLeader(){
		String taskuser = StringUtil.getNotNullValueString(request.getParameter("taskuser"));
		this.userInfo.initTaskUser(taskuser);
		String deptId = StringUtil.getNotNullValueString(userInfo.getDeptId());
//System.out.println("deptId="+deptId);
		aw.writeJson(externalService.getDeptLeaders(deptId));
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Action(value="getRef")
	public String getRef(){
		int limit = 20;
		String limit_str = StringUtil.getNotNullValueString(request.getParameter("limit"));
		
		try{limit = Integer.parseInt(limit_str);}catch(Exception e){}
		
		
		String ids = StringUtil.getNotNullValueString(request.getParameter("ids"));
//System.out.println("deptId="+deptId);
		//aw.writeJson(externalService.getDeptLeaders(deptId));
		List<Map<String,String>> retList = new ArrayList<Map<String,String>>();
		if(ids.length()==0){
			aw.writeJson(retList);
		}else{
			ids = TextUtil.generateWhereInClauseByStr(ids);
			
			String hql = "from TDeptContactMain t where t.id in("+ids+") and t.removed!=1";
			
			List<TDeptContactMain> list = commonService.ListByHql(hql);
			
			for(int i=0;i<list.size();i++){
				TDeptContactMain bo = list.get(i);
				
				String theme = bo.getTheme();
				String theme_short = StringUtil.subStr(bo.getTheme(),limit);
				String id = bo.getId();
				
				Map<String,String> map = new HashMap<String,String>();
				map.put("id", id);
				map.put("theme", theme);
				map.put("theme_short", theme_short);
				
				retList.add(map);
			}
			
			aw.writeJson(retList);
		}
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public ExternalService getExternalService() {
		return externalService;
	}
	
	@Autowired
	public void setExternalService(@Qualifier("contact-externalService")ExternalService externalService) {
		this.externalService = externalService;
//System.out.println("token="+userInfo.getToken());
		this.externalService.setToken(userInfo.getToken());
	}
	
	public CommonService getCommonService() {
		return commonService;
	}
	
	@Autowired
	public void setCommonService(@Qualifier(value="contact-commonService")CommonService commonService) {
		this.commonService = commonService;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public ActionWriter getAw() {
		return aw;
	}

	public void setAw(ActionWriter aw) {
		this.aw = aw;
	}
}
