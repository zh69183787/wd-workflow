package com.wonders.contact.processController.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.wonders.contact.common.util.SimpleLogger;
import com.wonders.contact.deptContact.util.ActionWriter;
import com.wonders.contact.processController.model.bo.DeptContactResult;
import com.wonders.contact.processController.model.vo.DeptContactControllerVo;
import com.wonders.contact.processController.service.DeptContactController;
import com.wonders.util.StringUtil;

@ParentPackage("struts-default")
@Namespace(value="/processController")
@Controller("contact-deptContactControllerAction")
@Scope("prototype")
public class DeptContactControllerAction implements ModelDriven<DeptContactControllerVo>{
	ActionContext actionContext = ActionContext.getContext();
	HttpServletRequest request = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
	HttpServletResponse response = (HttpServletResponse) actionContext.get(ServletActionContext.HTTP_RESPONSE);
	
	Logger log = SimpleLogger.getLogger(this.getClass());
	
	private DeptContactController controller;
	private ActionWriter aw = new ActionWriter(response);
	private DeptContactControllerVo vo = new DeptContactControllerVo();
	
	@Action(value="/deptContact")
	public String ProcessController(){
		String type = StringUtil.getNotNullValueString(vo.getType());
//System.out.println(type);
log.debug(vo);
//log.debug("controller="+controller);

		vo.setContextPath(StringUtil.getNotNullValueString(request.getContextPath()));
		vo.setPort(StringUtil.getNotNullValueString(request.getServerPort()));

		try{
			DeptContactResult result = new DeptContactResult();
			/*
			 * 获得流程（子流程、下级流程）剩余个数
			 */
			if("getCount".equals(type)){
				result = controller.getCount(vo);
			}
	
			/*
			 * 关联子流程
			 */
			if("linkSub".equals(type)){
				result = controller.linkSubProcess(vo);
			}
			
			/*
			 * 初始化流程信息（子流程、下级流程）
			 */
			if("initProcess".equals(type)){
				result = controller.getInitProcess(vo);
			}
			
			/*
			 * 发起下级流程
			 */
			if("launchLower".equals(type)){
				result = controller.launchLowerProcess(vo);
			}
		
			/*
			 * 下级流程结束，触发上级流程节点
			 */
			if("endLower".equals(type)){
				result = controller.endLowerProcess(vo);
			}
			
			/*
			 * 设置当前流程完成status
			 */
			if("finishProcess".equals(type)){
				result = controller.finishProcess(vo);
			}
			
			result.filterNull();
			aw.writeJsonWithAnnotation(result);
			
		}catch(Exception e){
e.printStackTrace();
//log.error(e.getMessage());
			//aw.writeAjax(response, "failed@"+e.getMessage());
		}

		return null;
	}


	
	
	public DeptContactController getController() {
		return controller;
	}
	
	@Autowired
	public void setController(@Qualifier("contact-deptContactController")DeptContactController controller) {
		this.controller = controller;
	}
	

	@Override
	public DeptContactControllerVo getModel() {
		return vo;
	}
	
}
