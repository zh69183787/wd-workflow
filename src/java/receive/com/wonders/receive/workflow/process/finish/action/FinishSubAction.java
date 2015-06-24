/**
 * 
 */
package com.wonders.receive.workflow.process.finish.action;

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
import com.wonders.receive.workflow.process.finish.model.vo.FinishSubParamVo;
import com.wonders.receive.workflow.process.finish.model.vo.FinishSubVo;
import com.wonders.receive.workflow.process.finish.service.FinishSubService;
import com.wonders.receive.workflow.process.util.ProcessUtil;


/** 
 * @ClassName: finishAction 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-16 上午11:33:21 
 *  
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/receive-finishSub")
@Controller("receive-finishSubAction")
@Scope("prototype")
public class FinishSubAction extends AbstractParamAction implements ModelDriven<FinishSubVo>{
	private FinishSubService service;
	private ActionWriter aw = new ActionWriter(response);
	public FinishSubVo vo = new FinishSubVo();
	public FinishSubParamVo params;
	
	public FinishSubAction(){
		if(params==null) params = new FinishSubParamVo();
		params.vo = vo;
		params.userInfo = LoginUtil.getUserInfo(session);
	}
	
	
	/** 办结
	 * @return
	 */
	@Action(value="finish")
	public String stepFinish(){
		ProcessUtil.prepareFlowInfo(request, params);
		try {
			service.flowStepFinish(params);
			
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
	public FinishSubVo getModel() {
		// TODO Auto-generated method stub
		return vo;
	}

	public FinishSubService getService() {
		return service;
	}

	@Autowired(required=false)
	public void setService(@Qualifier("receive-finishSubService")FinishSubService service) {
		this.service = service;
	}
	
	
}
