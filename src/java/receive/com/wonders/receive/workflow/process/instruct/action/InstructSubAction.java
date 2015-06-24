/**
 * 
 */
package com.wonders.receive.workflow.process.instruct.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.wonders.receive.workflow.common.action.AbstractParamAction;
import com.wonders.receive.workflow.common.util.ActionWriter;
import com.wonders.receive.workflow.common.util.LoginUtil;
import com.wonders.receive.workflow.model.vo.ParamVo;
import com.wonders.receive.workflow.process.instruct.model.vo.InstructSubParamVo;
import com.wonders.receive.workflow.process.instruct.model.vo.InstructSubVo;
import com.wonders.receive.workflow.process.instruct.service.InstructSubService;
import com.wonders.receive.workflow.process.util.ProcessUtil;


/** 
 * @ClassName: InstructAction 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-16 上午11:33:43 
 *  
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/receive-instructSub")
@Controller("receive-instructSubAction")
@Scope("prototype")
public class InstructSubAction extends AbstractParamAction implements ModelDriven<InstructSubVo>{

	private InstructSubService service;
	private ActionWriter aw = new ActionWriter(response);
	public InstructSubVo vo = new InstructSubVo();
	public InstructSubParamVo params;
	
	public InstructSubAction(){
		if(params==null) params = new InstructSubParamVo();
		params.vo = vo;
		params.userInfo = LoginUtil.getUserInfo(session);
	}
	
	
	/**   
	 * 领导秘书处理
	 * @return
	 */
	@Action(value="secretaryDeal")
	public String stepSecretaryDeal (){
		ProcessUtil.prepareFlowInfo(request, params);
		try {
			service.flowStepSecretaryDeal(params);
			aw.writeJsonWithAnnotation(params.resultInfo);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		aw.writeJsonWithAnnotation(params.resultInfo);
		
		return null;
	}
	
	
	/**   
	 * 领导处理
	 * @return
	 */
	@Action(value="leaderDeal")
	public String stepLeaderDeal (){
		ProcessUtil.prepareFlowInfo(request, params);
		try {
			service.flowStepLeaderDeal(params);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		aw.writeJsonWithAnnotation(params.resultInfo);
		
		return null;
	}


	public InstructSubService getService() {
		return service;
	}

	@Autowired(required=false)
	public void setService(@Qualifier("receive-instructSubService")InstructSubService service) {
		this.service = service;
	}


	@Override
	public ParamVo getParams() {
		return params;
	}
	
	
	@Override
	public InstructSubVo getModel() {
		// TODO Auto-generated method stub
		return vo;
	}
	
}