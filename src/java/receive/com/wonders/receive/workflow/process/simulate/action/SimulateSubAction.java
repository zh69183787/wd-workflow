/**
 * 
 */
package com.wonders.receive.workflow.process.simulate.action;

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
import com.wonders.receive.workflow.process.simulate.model.vo.SimulateSubParamVo;
import com.wonders.receive.workflow.process.simulate.model.vo.SimulateSubVo;
import com.wonders.receive.workflow.process.simulate.service.SimulateSubService;
import com.wonders.receive.workflow.process.util.ProcessUtil;


/** 
 * @ClassName: simulateAction 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-16 上午11:33:21 
 *  
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/receive-simulateSub")
@Controller("receive-simulateSubAction")
@Scope("prototype")
public class SimulateSubAction extends AbstractParamAction implements ModelDriven<SimulateSubVo>{
	private SimulateSubService service;
	private ActionWriter aw = new ActionWriter(response);
	public SimulateSubVo vo = new SimulateSubVo();
	public SimulateSubParamVo params;
	
	public SimulateSubAction(){
		if(params==null) params = new SimulateSubParamVo();
		params.vo = vo;
		params.userInfo = LoginUtil.getUserInfo(session);
	}
	
	
	/** 拟办
	 * @return
	 */
	@Action(value="simulate")
	public String stepSimulate(){
		ProcessUtil.prepareFlowInfo(request, params);
		try {
			service.flowStepSimulate(params);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		aw.writeJsonWithAnnotation(params.resultInfo);
		
		return null;
	}
	
	/** 拟办领导
	 * @return
	 */
	@Action(value="leaderSimulate")
	public String stepLeaderSimulate(){
		ProcessUtil.prepareFlowInfo(request, params);
		try {
			service.flowStepLeaderSimulate(params);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		aw.writeJsonWithAnnotation(params.resultInfo);
		
		return null;
	}
	
	/** 拟办建议
	 * @return
	 */
	@Action(value="suggest")
	public String stepSuggest(){
		ProcessUtil.prepareFlowInfo(request, params);
		try {
			service.flowStepSuggest(params);
			
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
	public SimulateSubVo getModel() {
		// TODO Auto-generated method stub
		return vo;
	}

	public SimulateSubService getService() {
		return service;
	}

	@Autowired(required=false)
	public void setService(@Qualifier("receive-simulateSubService")SimulateSubService service) {
		this.service = service;
	}
	
	
}
