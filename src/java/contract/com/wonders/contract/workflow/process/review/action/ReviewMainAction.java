/**
 * 
 */
package com.wonders.contract.workflow.process.review.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ModelDriven;
import com.wonders.contract.workflow.common.action.AbstractParamAction;
import com.wonders.contract.workflow.common.util.ActionWriter;
import com.wonders.contract.workflow.common.util.LoginUtil;
import com.wonders.contract.workflow.model.vo.ParamVo;
import com.wonders.contract.workflow.process.review.model.vo.ReviewMainParamVo;
import com.wonders.contract.workflow.process.review.model.vo.ReviewMainVo;
import com.wonders.contract.workflow.process.review.service.ReviewMainService;
import com.wonders.contract.workflow.process.review.util.ReviewMainUtil;
import com.wonders.contract.workflow.process.util.ProcessUtil;

/** 
 * @ClassName: ReviewMainAction 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:28:50 
 *  
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/contract-reviewMain")
@Controller("contract-reviewMainAction")
@Scope("prototype")
public class ReviewMainAction extends AbstractParamAction implements ModelDriven<ReviewMainVo>{
	private ActionWriter aw = new ActionWriter(response);
	private ReviewMainService service;
	public ReviewMainVo vo = new ReviewMainVo();
	public ReviewMainParamVo params;
	
	public ReviewMainAction(){
		if(params==null) params = new ReviewMainParamVo();
		params.vo = vo;
		params.userInfo = LoginUtil.getUserInfo(session);
	}
	
	
	@Action(value="forward",results={
			 @Result(name="modify",location="/contract/process/review/modify.jsp")
			,@Result(name="detail",location="/contract/process/review/detail.jsp")
			,@Result(name="view",location="/contract/process/review/view.jsp")
	})
	public String stepForward(){
		ProcessUtil.prepareFlowInfo(request, params);
		String operateType = ReviewMainUtil.getTypeByInfo(params);
		vo = this.service.flowStepForward(params);
		if(params.getProcessParamValue("operateType").length()>0
				|| "1".equals(request.getParameter("viewType"))){
			params.addProcessParam("operateType", "view");
			operateType = "view";
		}
		
		return operateType;
	}
	
	@Action(value="print",results={
			@Result(name="print",location="/contract/process/review/print.jsp")
	})
	public String print(){
		ProcessUtil.prepareFlowInfo(request, params);
		vo = this.service.print(params);
		return "print";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/** 合同后审登记
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
	
	/** 合同后审修改
	 * @return
	 */
	@Action(value="modify")
	public String stepModify(){
		ProcessUtil.prepareFlowInfo(request, params);
		try {
			service.flowStepModify(params);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		aw.writeJsonWithAnnotation(params.resultInfo);
		
		return null;
	}
	
	
	/** 合同后审领导
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
	
	/** 合同后审初审
	 * @return
	 */
	@Action(value="contractPreTrial")
	public String stepContractPreTrial(){
		ProcessUtil.prepareFlowInfo(request, params);
		try {
			service.flowStepContractPreTrial(params);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		aw.writeJsonWithAnnotation(params.resultInfo);
		
		return null;
	}
	
	/** 合同后审经办人
	 * @return
	 */
	@Action(value="contractDealPerson")
	public String stepContractDealPerson(){
		ProcessUtil.prepareFlowInfo(request, params);
		try {
			service.flowStepContractDealPerson(params);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		aw.writeJsonWithAnnotation(params.resultInfo);
		
		return null;
	}
	
	/** 合同后审拟办
	 * @return
	 */
	@Action(value="contractSimulate")
	public String stepContractSimulate(){
		ProcessUtil.prepareFlowInfo(request, params);
		try {
			service.flowStepContractSimulate(params);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		aw.writeJsonWithAnnotation(params.resultInfo);
		
		return null;
	}
	
	/** 合同后审集团领导
	 * @return
	 */
	@Action(value="comLeader")
	public String stepComLeader(){
		ProcessUtil.prepareFlowInfo(request, params);
		try {
			service.flowStepComLeader(params);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		aw.writeJsonWithAnnotation(params.resultInfo);
		
		return null;
	}
	
	/** 合同后审办结
	 * @return
	 */
	@Action(value="contractFinish")
	public String stepContractFinish(){
		ProcessUtil.prepareFlowInfo(request, params);
		try {
			service.flowStepContractFinish(params);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		aw.writeJsonWithAnnotation(params.resultInfo);
		
		return null;
	}
	
	
	
	public ReviewMainService getService() {
		return service;
	}

	@Autowired(required=false)
	public void setService(@Qualifier("contract-reviewMainService")ReviewMainService service) {
		this.service = service;
	}

	@Override
	public ParamVo getParams() {
		return params;
	}
	
	@Override
	public ReviewMainVo getModel() {
		// TODO Auto-generated method stub
		return vo;
	}
}
