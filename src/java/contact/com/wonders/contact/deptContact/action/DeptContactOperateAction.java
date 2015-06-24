package com.wonders.contact.deptContact.action;

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
import com.wonders.contact.deptContact.model.vo.DeptContactOperateVo;
import com.wonders.contact.deptContact.model.vo.DeptContactParamVo;
import com.wonders.contact.deptContact.service.DeptContactService;
import com.wonders.contact.deptContact.util.ActionWriter;
import com.wonders.util.StringUtil;

/**
 * @author XFZ
 * @version 1.0 2012-6-12
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/contact-deptContact")
@Controller("contact-deptContactOperateAction")
@Scope("prototype")
public class DeptContactOperateAction extends AbstractParamAction implements ModelDriven<DeptContactOperateVo> {
	private DeptContactService service;
	
	public DeptContactOperateVo operateVo = new DeptContactOperateVo();
	public DeptContactParamVo params = new DeptContactParamVo();
	
	public String checkOnly = "";
	
	//private SimpleLogger log = new SimpleLogger(this.getClass());
	private ActionWriter aw = new ActionWriter(response);
	
	public DeptContactOperateAction(){
		params.operateVo = operateVo;
		params.userInfo = LoginUtil.getUserInfo(session);
	}
	
	@Action(value="operateApply")
	public String stepApply(){
		try {
			service.flowStepApply(params);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		aw.writeJsonWithAnnotation(params.resultInfo);
		return null;
	}
	
	@Action(value="operateSign")
	public String stepSign(){
		try {
			service.flowStepSign(params);
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
	public DeptContactOperateVo getModel() {
		return operateVo;
	}
	
	public DeptContactService getService() {
		return service;
	}
	
	@Autowired(required=false)
	public void setService(@Qualifier("contact-deptContactService")DeptContactService service) {
		this.service = service;
	}

	public DeptContactOperateVo getOperateVo() {
		return operateVo;
	}

	public void setOperateVo(DeptContactOperateVo operateVo) {
		this.operateVo = operateVo;
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
