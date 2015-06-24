package com.wonders.contact.external.action;

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
import com.opensymphony.xwork2.ModelDriven;
import com.wonders.contact.common.util.SimpleLogger;
import com.wonders.contact.deptContact.util.ActionWriter;
import com.wonders.contact.external.model.vo.TodoVo;
import com.wonders.contact.external.service.ApiService;

@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/contact-api")
@Controller("contact-apiTodoAction")
@Scope("prototype")
public class ApiTodoAction extends ActionSupport implements ModelDriven<TodoVo>{
	public ActionContext actionContext = ActionContext.getContext();
	public HttpServletRequest request = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
	public HttpServletResponse response = (HttpServletResponse) actionContext.get(ServletActionContext.HTTP_RESPONSE);
	public HttpSession session = request.getSession();
	
	private ApiService apiService;
	
	ActionWriter aw = new ActionWriter(response);
	
	SimpleLogger log = new SimpleLogger(this.getClass());
	
	private TodoVo todoVo = new TodoVo();
	
	
	
	@Action(value="todoList")
	public String list(){
//log.debug("list");
		try {
			aw.writeJson(apiService.getTodoData(todoVo));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public ApiService getApiService() {
		return apiService;
	}
	
	@Autowired(required=false)
	public void setApiService(@Qualifier("contact-apiService")ApiService apiService) {
		this.apiService = apiService;
	}
	
	@Override
	public TodoVo getModel() {
		return todoVo;
	}

}
