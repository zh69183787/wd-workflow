/**
 * 
 */
package com.wonders.discipline.workflow.process.recv.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ModelDriven;
import com.wonders.discipline.workflow.common.action.AbstractParamAction;
import com.wonders.discipline.workflow.common.util.ActionWriter;
import com.wonders.discipline.workflow.common.util.LoginUtil;
import com.wonders.discipline.workflow.model.vo.ParamVo;
import com.wonders.discipline.workflow.process.recv.model.vo.DcpRecvMainParamVo;
import com.wonders.discipline.workflow.process.recv.model.vo.DcpRecvMainVo;
import com.wonders.discipline.workflow.process.recv.service.DcpRecvMainService;
import com.wonders.discipline.workflow.process.recv.util.DcpRecvMainUtil;
import com.wonders.discipline.workflow.process.util.ProcessUtil;

/** 
 * @ClassName: DcpRecvMainAction 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:28:50 
 *  
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/discipline-dcpRecvMain")
@Controller("discipline-dcpRecvMainAction")
@Scope("prototype")
public class DcpRecvMainAction extends AbstractParamAction implements ModelDriven<DcpRecvMainVo>{
	private ActionWriter aw = new ActionWriter(response);
	private DcpRecvMainService service;
	public DcpRecvMainVo vo = new DcpRecvMainVo();
	public DcpRecvMainParamVo params;
	
	public DcpRecvMainAction(){
		if(params==null) params = new DcpRecvMainParamVo();
		params.vo = vo;
		params.userInfo = LoginUtil.getUserInfo(session);
	}
	
	
	@Action(value="forward",results={
			@Result(name="update",location="/discipline/process/recv/update.jsp")
			,@Result(name="modify",location="/discipline/process/recv/update.jsp")
			,@Result(name="detail",location="/discipline/process/recv/detail.jsp")
			,@Result(name="view",location="/discipline/process/recv/view.jsp")
			,@Result(name="removed",location="/discipline/process/recv/removed.jsp")
	})
	public String stepForward(){
		ProcessUtil.prepareFlowInfo(request, params);
		String operateType = "";
		vo = this.service.flowStepForward(params);
		if(vo != null){
			operateType = DcpRecvMainUtil.getTypeByInfo(params);
			if(params.getProcessParamValue("operateType").length()>0
					|| "1".equals(request.getParameter("viewType"))){
				params.addProcessParam("operateType", "view");
				operateType = "view";
			}
		}else{
			operateType = "removed";
		}
		
		return operateType;
	}
	
	@Action(value="print",results={
			@Result(name="print",location="/discipline/process/recv/print.jsp")
	})
	public String print(){
		ProcessUtil.prepareFlowInfo(request, params);
		vo = this.service.print(params);
		return "print";
	}
	
	
	@Action(value="outPrint",results={
			@Result(name="outPrint",location="/discipline/process/recv/outPrint.jsp")
	})
	public String outPrint(){
		ProcessUtil.prepareFlowInfo(request, params);
		vo = this.service.print(params);
		return "outPrint";
	}
	

	
	
	
	
	
	/** 纪委收文登记
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
	
	/** 纪委收文 部门领导审核
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
	
	/** 纪委收文-登记人修改
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
	
	/** 纪委委员处理 集团领导处理 
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
	
	
	/** 纪委收文-汇总人
	 * @return
	 */
	@Action(value="summary")
	public String stepSummary(){
		ProcessUtil.prepareFlowInfo(request, params);
		try {
			service.flowStepSummary(params);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		aw.writeJsonWithAnnotation(params.resultInfo);
		
		return null;
	}
	
	
	/** 纪委收文-办结
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
	
	
	
	public DcpRecvMainService getService() {
		return service;
	}

	@Autowired(required=false)
	public void setService(@Qualifier("discipline-dcpRecvMainService")DcpRecvMainService service) {
		this.service = service;
	}

	@Override
	public ParamVo getParams() {
		return params;
	}
	
	@Override
	public DcpRecvMainVo getModel() {
		// TODO Auto-generated method stub
		return vo;
	}
}
