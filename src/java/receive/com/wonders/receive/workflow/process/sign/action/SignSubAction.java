/**
 * 
 */
package com.wonders.receive.workflow.process.sign.action;

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
import com.wonders.receive.workflow.process.sign.model.vo.SignSubParamVo;
import com.wonders.receive.workflow.process.sign.model.vo.SignSubVo;
import com.wonders.receive.workflow.process.sign.service.SignSubService;
import com.wonders.receive.workflow.process.util.ProcessUtil;


/** 
 * @ClassName: SignAction 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-16 上午11:33:43 
 *  
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/receive-signSub")
@Controller("receive-signSubAction")
@Scope("prototype")
public class SignSubAction extends AbstractParamAction implements ModelDriven<SignSubVo>{

	private SignSubService service;
	private ActionWriter aw = new ActionWriter(response);
	public SignSubVo vo = new SignSubVo();
	public SignSubParamVo params;
	
	public SignSubAction(){
		if(params==null) params = new SignSubParamVo();
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


	public SignSubService getService() {
		return service;
	}

	@Autowired(required=false)
	public void setService(@Qualifier("receive-signSubService")SignSubService service) {
		this.service = service;
	}


	@Override
	public ParamVo getParams() {
		return params;
	}
	
	
	@Override
	public SignSubVo getModel() {
		// TODO Auto-generated method stub
		return vo;
	}
	
}