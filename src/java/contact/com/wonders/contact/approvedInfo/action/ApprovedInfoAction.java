package com.wonders.contact.approvedInfo.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wonders.contact.approvedInfo.model.vo.DeptContactApprovedInfoVo;
import com.wonders.contact.approvedInfo.service.ApprovedInfoDeptContactService;
import com.wonders.contact.common.model.vo.UserInfo;
import com.wonders.contact.common.util.LoginUtil;
import com.wonders.contact.common.util.SimpleLogger;

/**
 * @author XFZ
 * @version 1.0 2012-6-12
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/contact-approvedInfo")
@Controller("contact-approvedInfoAction")
@Scope("prototype")
public class ApprovedInfoAction extends ActionSupport {
	public ActionContext actionContext = ActionContext.getContext();
	public HttpServletRequest request = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
	public HttpServletResponse response = (HttpServletResponse) actionContext.get(ServletActionContext.HTTP_RESPONSE);
	public HttpSession session = request.getSession();
	
	private ApprovedInfoDeptContactService approvedInfoDeptContactService;
	SimpleLogger log = new SimpleLogger(this.getClass());
	
	private String mainBoId = "";
	private DeptContactApprovedInfoVo deptContactApprovedInfoVo = new DeptContactApprovedInfoVo();
	
	public UserInfo userInfo;
	
	public ApprovedInfoAction(){
		userInfo = LoginUtil.getUserInfo(session);
	}
	
	
	@Action(value="deptContactApproveInfo",results={
		@Result(name="success",location="/contact/deptContact/zone/suggest.jsp")
	})
	public String deptContactApproveInfoList(){
		deptContactApprovedInfoVo = approvedInfoDeptContactService.getApprovedInfoVo(mainBoId, "");
//log.debug(deptContactApprovedInfoVo);
		return "success";
	}

	/**打印页面意见
	 * @return
	 */
	@Action(value="deptContactPrintApproveInfo",results={
		@Result(name="success",location="/contact/deptContact/zone/suggest_print.jsp")
	})
	public String deptContactPrintApproveInfoList(){
		deptContactApprovedInfoVo = approvedInfoDeptContactService.getApprovedInfoVo(mainBoId, "");
//log.debug(deptContactApprovedInfoVo);
		return "success";
	}

	public String getMainBoId() {
		return mainBoId;
	}

	public void setMainBoId(String mainBoId) {
		this.mainBoId = mainBoId;
	}

	public ApprovedInfoDeptContactService getApprovedInfoDeptContactService() {
		return approvedInfoDeptContactService;
	}

	@Autowired
	public void setApprovedInfoDeptContactService(@Qualifier("contact-approvedInfoDeptContactService")ApprovedInfoDeptContactService approvedInfoDeptContactService) {
		this.approvedInfoDeptContactService = approvedInfoDeptContactService;
	}

	public DeptContactApprovedInfoVo getDeptContactApprovedInfoVo() {
		return deptContactApprovedInfoVo;
	}

	public void setDeptContactApprovedInfoVo(DeptContactApprovedInfoVo deptContactApprovedInfoVo) {
		this.deptContactApprovedInfoVo = deptContactApprovedInfoVo;
	}
}
