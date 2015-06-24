/**
 * 
 */
package com.wonders.dept.workflow.process.pass.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.wonders.dept.workflow.common.action.AbstractParamAction;
import com.wonders.dept.workflow.common.util.ActionWriter;
import com.wonders.dept.workflow.common.util.LoginUtil;
import com.wonders.dept.workflow.model.vo.ParamVo;
import com.wonders.dept.workflow.process.pass.model.vo.PassMainParamVo;
import com.wonders.dept.workflow.process.pass.model.vo.PassMainVo;
import com.wonders.dept.workflow.process.pass.service.PassMainService;
import com.wonders.dept.workflow.process.pass.util.PassMainUtil;
import com.wonders.dept.workflow.process.util.ProcessUtil;

/** 
 * @ClassName: PassMainAction 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:28:50 
 *  
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/dept-passMain")
@Controller("dept-passMainAction")
@Scope("prototype")
public class PassMainAction extends AbstractParamAction implements ModelDriven<PassMainVo>{
	private ActionWriter aw = new ActionWriter(response);
	private PassMainService service;
	public PassMainVo vo = new PassMainVo();
	public PassMainParamVo params;
	
	public PassMainAction(){
		if(params==null) params = new PassMainParamVo();
		params.vo = vo;
		params.userInfo = LoginUtil.getUserInfo(session);
	}
	
	@Action(value="sign",results={
			 @Result(name="success",location="/dept/process/pass/sign.jsp")
	})
	public String sign(){
		ProcessUtil.prepareFlowInfo(request, params);
		return "success";
	}
	
	@Action(value="add",results={
			 @Result(name="success",location="/dept/process/pass/add.jsp")
	})
	public String add(){
		ProcessUtil.prepareFlowInfo(request, params);
		return "success";
	}
	
	@Action(value="addWithFixedRemark",results={
			 @Result(name="success",location="/dept/process/pass/add_fixedRemark.jsp")
	})
	public String addWithFixedTitle(){
		ProcessUtil.prepareFlowInfo(request, params);
		return "success";
	}
	
	@Action(value="forward",results={
			 @Result(name="detail",location="/dept/process/pass/detail.jsp")
			,@Result(name="view",location="/dept/process/pass/view.jsp")
	})
	public String stepForward(){
		ProcessUtil.prepareFlowInfo(request, params);
		String operateType = PassMainUtil.getTypeByInfo(params);
		vo = this.service.flowStepForward(params);
		if(params.getProcessParamValue("operateType").length()>0
				|| "1".equals(request.getParameter("viewType"))){
			params.addProcessParam("operateType", "view");
			operateType = "view";
		}
		
		return operateType;
	}
	
	@Action(value="print",results={
			@Result(name="print",location="/dept/process/pass/print.jsp")
	})
	public String print(){
		ProcessUtil.prepareFlowInfo(request, params);
		vo = this.service.print(params);
		return "print";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/** 登记
	 * @return
	 */
	@Action(value="register")
	public String stepRegister(){
		ProcessUtil.prepareFlowInfo(request, params);
		try {
			service.flowStepRegister(params);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		aw.writeJsonWithAnnotation(params.resultInfo);
		
		return null;
	}
	
	
	/** 部门领导
	 * @return
	 */
	@Action(value="deptLeader")
	public String stepDeptLeader(){
		ProcessUtil.prepareFlowInfo(request, params);
		try {
			service.flowStepDeptLeader(params);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		aw.writeJsonWithAnnotation(params.resultInfo);
		
		return null;
	}
	
	
	/** 经办人
	 * @return
	 */
	@Action(value="dealPerson")
	public String stepContractDealPerson(){
		ProcessUtil.prepareFlowInfo(request, params);
		try {
			service.flowStepDealPerson(params);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		aw.writeJsonWithAnnotation(params.resultInfo);
		
		return null;
	}
	
	
	public PassMainService getService() {
		return service;
	}

	@Autowired(required=false)
	public void setService(@Qualifier("dept-passMainService")PassMainService service) {
		this.service = service;
	}

	@Override
	public ParamVo getParams() {
		return params;
	}
	
	@Override
	public PassMainVo getModel() {
		// TODO Auto-generated method stub
		return vo;
	}
}
