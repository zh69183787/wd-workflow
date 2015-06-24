/**
 * 
 */
package com.wonders.receive.workflow.process.redv.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ModelDriven;
import com.wonders.receive.workflow.common.action.AbstractParamAction;
import com.wonders.receive.workflow.common.util.ActionWriter;
import com.wonders.receive.workflow.common.util.LoginUtil;
import com.wonders.receive.workflow.model.vo.ParamVo;
import com.wonders.receive.workflow.process.redv.model.vo.RedvMainParamVo;
import com.wonders.receive.workflow.process.redv.model.vo.RedvMainVo;
import com.wonders.receive.workflow.process.redv.service.RedvMainService;
import com.wonders.receive.workflow.process.redv.util.RedvMainUtil;
import com.wonders.receive.workflow.process.util.ProcessUtil;

/** 
 * @ClassName: RedvMainAction 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:28:50 
 *  
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/receive-redvMain")
@Controller("receive-redvMainAction")
@Scope("prototype")
public class RedvMainAction extends AbstractParamAction implements ModelDriven<RedvMainVo>{
	private ActionWriter aw = new ActionWriter(response);
	private RedvMainService service;
	public RedvMainVo vo = new RedvMainVo();
	public RedvMainParamVo params;
	
	public RedvMainAction(){
		if(params==null) params = new RedvMainParamVo();
		params.vo = vo;
		params.userInfo = LoginUtil.getUserInfo(session);
	}
	
	
	@Action(value="forward",results={
			@Result(name="update",location="/receive/process/redv/update.jsp")
			,@Result(name="detail",location="/receive/process/redv/detail.jsp")
			,@Result(name="view",location="/receive/process/redv/view.jsp")
	})
	public String stepForward(){
		ProcessUtil.prepareFlowInfo(request, params);
		String operateType = RedvMainUtil.getTypeByInfo(params);
		vo = this.service.flowStepForward(params);
		if(params.getProcessParamValue("operateType").length()>0
				|| "1".equals(request.getParameter("viewType"))){
			params.addProcessParam("operateType", "view");
			operateType = "view";
		}
		
		return operateType;
	}
	
	@Action(value="print",results={
			@Result(name="print",location="/receive/process/redv/print.jsp")
	})
	public String print(){
		ProcessUtil.prepareFlowInfo(request, params);
		vo = this.service.print(params);
		return "print";
	}
	
	
	
	@Action(value="outPrint",results={
			@Result(name="outPrint",location="/receive/process/redv/outPrint.jsp")
	})
	public String outPrint(){
		ProcessUtil.prepareFlowInfo(request, params);
		vo = this.service.print(params);
		return "outPrint";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/** 呈批件登记
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
	
	/** 呈批件领导审核
	 * @return
	 */
	@Action(value="leaderCheck")
	public String stepLeaderCheck(){
		ProcessUtil.prepareFlowInfo(request, params);
		try {
			service.flowStepLeaderCheck(params);
			
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
	
	/** 备案
	 * @return
	 */
	@Action(value="record")
	public String stepRecord(){
		ProcessUtil.prepareFlowInfo(request, params);
		try {
			service.flowStepRecord(params);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		aw.writeJsonWithAnnotation(params.resultInfo);
		
		return null;
	}
	
	
	
	
	
	public RedvMainService getService() {
		return service;
	}

	@Autowired(required=false)
	public void setService(@Qualifier("receive-redvMainService")RedvMainService service) {
		this.service = service;
	}

	@Override
	public ParamVo getParams() {
		return params;
	}
	
	@Override
	public RedvMainVo getModel() {
		// TODO Auto-generated method stub
		return vo;
	}
}
