package com.wonders.contact.deptSubProcess.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.wonders.contact.common.action.AbstractParamAction;
import com.wonders.contact.common.model.vo.ParamVo;
import com.wonders.contact.common.util.LoginUtil;
import com.wonders.contact.deptContact.util.ActionWriter;
import com.wonders.contact.deptSubProcess.model.vo.DeptSubProcessParamVo;
import com.wonders.contact.deptSubProcess.model.vo.DeptSubProcessVo;
import com.wonders.contact.deptSubProcess.service.DeptSubProcessService;
import com.wonders.util.StringUtil;

/**
 * @author XFZ
 * @version 1.0 2012-6-12
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/contact-deptSubProcess")
@Controller("contact-deptSubProcessAction")
@Scope("prototype")
public class DeptSubProcessAction extends AbstractParamAction implements ModelDriven<DeptSubProcessVo> {
	private DeptSubProcessService service;
	
	//private SimpleLogger log = new SimpleLogger(this.getClass());
	private ActionWriter aw = new ActionWriter(response);
	
	public DeptSubProcessVo vo = new DeptSubProcessVo();
	public DeptSubProcessParamVo params;
	
	public String flowType = "";

	public String checkOnly = "";
	/**初始化
	 * 
	 */
	public DeptSubProcessAction(){
		if(params==null) params = new DeptSubProcessParamVo();
		params.vo = vo;
		params.userInfo = LoginUtil.getUserInfo(session);
	}
	
	/** 部门接收人分发
	 * @return
	 */
	@Action(value="dispatcher")
	public String stepDispatcher(){
		
		try {
			service.flowStepDispatcher(params);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		aw.writeJsonWithAnnotation(params.resultInfo);
		
		return null;
	}
	
	/** 部门人员处理
	 * @return
	 */
	@Action(value="deal")
	public String stepDeal(){
		try {
			service.flowStepDeal(params);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		aw.writeJsonWithAnnotation(params.resultInfo);
		
		return null;
	}
	
	/** 部门领导审核
	 * @return
	 */
	@Action(value="leaderDeal")
	public String stepLeaderDeal(){
		try {
			service.flowStepLeaderDeal(params);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		aw.writeJsonWithAnnotation(params.resultInfo);
		return null;
	}
	
	
	
	@Override
	public ParamVo getParams() {
		return params;
	}

	@Override
	public DeptSubProcessVo getModel() {
		return vo;
	}

	public DeptSubProcessService getService() {
		return service;
	}
	
	@Autowired
	public void setService(@Qualifier("contact-deptSubProcessService")DeptSubProcessService service) {
		this.service = service;
	}

	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}
	
	public String getCheckOnly() {
		return checkOnly;
	}

	public void setCheckOnly(String checkOnly) {
		this.checkOnly = StringUtil.getNotNullValueString(checkOnly);
		/**仅效验，不进行具体操作*/
//log.debug("checkOnly="+checkOnly);
		if(this.checkOnly.length()>0) params.resultInfo.checkOnly=true;
		
	}
}


