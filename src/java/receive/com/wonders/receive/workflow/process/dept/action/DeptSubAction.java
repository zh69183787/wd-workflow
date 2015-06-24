/**
 * 
 */
package com.wonders.receive.workflow.process.dept.action;

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
import com.wonders.receive.workflow.process.dept.model.vo.DeptSubParamVo;
import com.wonders.receive.workflow.process.dept.model.vo.DeptSubVo;
import com.wonders.receive.workflow.process.dept.service.DeptSubService;
import com.wonders.receive.workflow.process.util.ProcessUtil;


/** 
 * @ClassName: DeptAction 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-16 上午11:33:21 
 *  
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/receive-deptSub")
@Controller("receive-deptSubAction")
@Scope("prototype")
public class DeptSubAction extends AbstractParamAction implements ModelDriven<DeptSubVo>{
	private DeptSubService service;
	private ActionWriter aw = new ActionWriter(response);
	public DeptSubVo vo = new DeptSubVo();
	public DeptSubParamVo params;
	
	public DeptSubAction(){
		if(params==null) params = new DeptSubParamVo();
		params.vo = vo;
		params.userInfo = LoginUtil.getUserInfo(session);
	}
	
	
	/** 部门接收人分发
	 * @return
	 */
	@Action(value="dispatcher")
	public String stepDispatcher(){
		ProcessUtil.prepareFlowInfo(request, params);
		try {
			service.flowStepDispatcher(params);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		aw.writeJsonWithAnnotation(params.resultInfo);
		
		return null;
	}
	
	/** 部门人员处理
	 * @return
	 */
	@Action(value="deal")
	public String stepDeal(){
		ProcessUtil.prepareFlowInfo(request, params);
		try {
			service.flowStepDeal(params);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		aw.writeJsonWithAnnotation(params.resultInfo);
		
		return null;
	}
	
	/** 部门领导审核
	 * @return
	 */
	@Action(value="leaderDeal")
	public String stepLeaderDeal(){
		ProcessUtil.prepareFlowInfo(request, params);
		try {
			service.flowStepLeaderDeal(params);
			
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
	public DeptSubVo getModel() {
		// TODO Auto-generated method stub
		return vo;
	}

	public DeptSubService getService() {
		return service;
	}

	@Autowired(required=false)
	public void setService(@Qualifier("receive-deptSubService")DeptSubService service) {
		this.service = service;
	}
	
	
}
