/**
 * 
 */
package com.wonders.dept.workflow.process.pass.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.wonders.constants.LoginConstants;
import com.wonders.dept.workflow.common.action.AbstractParamAction;
import com.wonders.dept.workflow.common.util.ActionWriter;
import com.wonders.dept.workflow.external.service.ExternalService;
import com.wonders.dept.workflow.model.vo.ParamVo;
import com.wonders.dept.workflow.organTree.model.bo.OrganUserBo;
import com.wonders.util.StringUtil;


/** 
 * @ClassName: PassMainAction 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:28:50 
 *  
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/dept-reviewUtil")
@Controller("dept-reviewUtilAction")
@Scope("prototype")
public class PassUtilAction extends AbstractParamAction {
	private ActionWriter aw = new ActionWriter(response);
	private ExternalService externalService;
	
	
	@Action(value="getDeptLeader")
	public String getDeptLeader(){
		String deptId = StringUtil.getNotNullValueString(request.getParameter("deptId"));
		this.externalService.setToken(StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.TOKEN)));
		List<OrganUserBo> list = this.externalService.getDeptLeaders(deptId);
		aw.writeJson(list);
		return null;
	}
	
	

	@Override
	public ParamVo getParams() {
		// TODO Auto-generated method stub
		return null;
	}

	public ExternalService getExternalService() {
		return externalService;
	}
	
	@Autowired(required=false)
	public void setExternalService(@Qualifier("dept-externalService")ExternalService externalService) {
		this.externalService = externalService;
	}

}
