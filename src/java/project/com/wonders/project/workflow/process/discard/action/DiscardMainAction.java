/**
 * 
 */
package com.wonders.project.workflow.process.discard.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.wonders.project.workflow.page.util.Page;
import com.wonders.project.workflow.page.util.PageHelp;
import com.wonders.project.workflow.page.util.QueryUtil;
import com.wonders.project.workflow.util.FlowUtil;

import com.opensymphony.xwork2.ModelDriven;
import com.wonders.project.workflow.common.action.AbstractParamAction;
import com.wonders.project.workflow.common.util.ActionWriter;
import com.wonders.project.workflow.common.util.LoginUtil;
import com.wonders.project.workflow.model.vo.ParamVo;
import com.wonders.project.workflow.process.discard.model.vo.DiscardMainParamVo;
import com.wonders.project.workflow.process.discard.model.vo.DiscardMainVo;
import com.wonders.project.workflow.process.discard.service.DiscardMainService;
import com.wonders.project.workflow.process.discard.util.DiscardMainUtil;
import com.wonders.project.workflow.process.util.ProcessUtil;

/** 
 * @ClassName: DiscardMainAction 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:28:50 
 *  
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/project-discardMain")
@Controller("project-discardMain")
@Scope("prototype")
public class DiscardMainAction extends AbstractParamAction implements ModelDriven<DiscardMainVo>{
	private ActionWriter aw = new ActionWriter(response);
	private DiscardMainService service;
	public DiscardMainVo vo = new DiscardMainVo();
	public DiscardMainParamVo params;
	
	public DiscardMainAction(){
		if(params==null) params = new DiscardMainParamVo();
		params.vo = vo;
		params.userInfo = LoginUtil.getUserInfo(session);
	}
	
	
	@Action(value="forward",results={
			 @Result(name="modify",location="/project/process/discard/modify.jsp")
			,@Result(name="detail",location="/project/process/discard/detail.jsp")
			,@Result(name="view",location="/project/process/discard/view.jsp")
	})
	public String stepForward(){
		ProcessUtil.prepareFlowInfo(request, params);
		String operateType = DiscardMainUtil.getTypeByInfo(params);
		vo = this.service.flowStepForward(params);
		if(params.getProcessParamValue("operateType").length()>0
				|| "1".equals(request.getParameter("viewType"))){
			params.addProcessParam("operateType", "view");
			operateType = "view";
		}
		
		return operateType;
	}
	
	@Action(value="print",results={
			@Result(name="print",location="/project/process/discard/print.jsp")
	})
	public String print(){
		ProcessUtil.prepareFlowInfo(request, params);
		vo = this.service.print(params);
		return "print";
	}
	
	
	@Action(value="outPrint",results={
			@Result(name="outPrint",location="/project/process/discard/outPrint.jsp")
	})
	public String outPrint(){
		ProcessUtil.prepareFlowInfo(request, params);
		vo = this.service.print(params);
		return "outPrint";
	}
	

	
	
	
	
	
	/** 销项登记
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
	
	/** 发起部门领导审核
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
	
	/** 登记人修改
	 * @return
	 */
	@Action(value="registerModify")
	public String stepRegisterModify(){
		ProcessUtil.prepareFlowInfo(request, params);
		try {
			service.flowStepRegisterModify(params);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		aw.writeJsonWithAnnotation(params.resultInfo);
		
		return null;
	}
	
	
	/** 投资部收发员
	 * @return
	 */
	@Action(value="preTrial")
	public String stepPreTrial(){
		ProcessUtil.prepareFlowInfo(request, params);
		try {
			service.flowStepPreTrial(params);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		aw.writeJsonWithAnnotation(params.resultInfo);
		
		return null;
	}
	
	/** 投资部经办人
	 * @return
	 */
	@Action(value="personDeal")
	public String stepPersonDeal(){
		ProcessUtil.prepareFlowInfo(request, params);
		try {
			service.flowStepPersonDeal(params);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		aw.writeJsonWithAnnotation(params.resultInfo);
		
		return null;
	}
	
	
	/** 投资部领导办结
	 * @return
	 */
	@Action(value="dealFinish")
	public String stepDealFinish(){
		ProcessUtil.prepareFlowInfo(request, params);
		try {
			service.flowStepDealFinish(params);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		aw.writeJsonWithAnnotation(params.resultInfo);
		
		return null;
	}
	
	public DiscardMainService getService() {
		return service;
	}

	@Autowired(required=false)
	public void setService(@Qualifier("project-discardMainService")DiscardMainService service) {
		this.service = service;
	}

	@Override
	public ParamVo getParams() {
		return params;
	}
	
	@Override
	public DiscardMainVo getModel() {
		// TODO Auto-generated method stub
		return vo;
	}
	
}
