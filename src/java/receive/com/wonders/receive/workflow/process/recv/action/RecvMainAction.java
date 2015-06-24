/**
 * 
 */
package com.wonders.receive.workflow.process.recv.action;

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
import com.wonders.receive.workflow.process.recv.model.vo.RecvMainParamVo;
import com.wonders.receive.workflow.process.recv.model.vo.RecvMainVo;
import com.wonders.receive.workflow.process.recv.service.RecvMainService;
import com.wonders.receive.workflow.process.recv.util.RecvMainUtil;
import com.wonders.receive.workflow.process.util.ProcessUtil;

/** 
 * @ClassName: RecvMainAction 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:28:50 
 *  
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/receive-recvMain")
@Controller("receive-recvMainAction")
@Scope("prototype")
public class RecvMainAction extends AbstractParamAction implements ModelDriven<RecvMainVo>{
	private ActionWriter aw = new ActionWriter(response);
	private RecvMainService service;
	public RecvMainVo vo = new RecvMainVo();
	public RecvMainParamVo params;
	
	public RecvMainAction(){
		if(params==null) params = new RecvMainParamVo();
		params.vo = vo;
		params.userInfo = LoginUtil.getUserInfo(session);
	}
	
	
	@Action(value="forward",results={
			@Result(name="update",location="/receive/process/recv/update.jsp")
			,@Result(name="modify",location="/receive/process/recv/modify.jsp")
			,@Result(name="detail",location="/receive/process/recv/detail.jsp")
			,@Result(name="view",location="/receive/process/recv/view.jsp")
	})
	public String stepForward(){
		ProcessUtil.prepareFlowInfo(request, params);
		String operateType = RecvMainUtil.getTypeByInfo(params);
		vo = this.service.flowStepForward(params);
		if(params.getProcessParamValue("operateType").length()>0
				|| "1".equals(request.getParameter("viewType"))){
			params.addProcessParam("operateType", "view");
			operateType = "view";
		}
		
		return operateType;
	}
	
	@Action(value="print",results={
			@Result(name="print",location="/receive/process/recv/print.jsp")
	})
	public String print(){
		ProcessUtil.prepareFlowInfo(request, params);
		vo = this.service.print(params);
		return "print";
	}
	
	
	@Action(value="outPrint",results={
			@Result(name="outPrint",location="/receive/process/recv/outPrint.jsp")
	})
	public String outPrint(){
		ProcessUtil.prepareFlowInfo(request, params);
		vo = this.service.print(params);
		return "outPrint";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/** 收文登记
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
	
	/** 收文编号
	 * @return
	 */
	@Action(value="getSerialNo")
	public String stepGetSerialNo(){
		ProcessUtil.prepareFlowInfo(request, params);
		try {
			service.flowStepGetSerialNo(params);
			
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
	
	
	
	
	
	public RecvMainService getService() {
		return service;
	}

	@Autowired(required=false)
	public void setService(@Qualifier("receive-recvMainService")RecvMainService service) {
		this.service = service;
	}

	@Override
	public ParamVo getParams() {
		return params;
	}
	
	@Override
	public RecvMainVo getModel() {
		// TODO Auto-generated method stub
		return vo;
	}
}
