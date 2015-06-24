package com.wonders.dataExchange.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wonders.dataExchange.model.common.vo.ParamVo;

@SuppressWarnings("serial")
public abstract class AbstractParamAction extends ActionSupport{
	public ActionContext actionContext = ActionContext.getContext();
	public HttpServletRequest request = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
	public HttpServletResponse response = (HttpServletResponse) actionContext.get(ServletActionContext.HTTP_RESPONSE);
	
	//public ServletContext  application = (ServletContext)actionContext.getApplication();
	public HttpSession session = request.getSession();
	abstract public ParamVo getParams();
}
