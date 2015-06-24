package com.wonders.contact.external.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.wonders.contact.common.model.bo.ResultInfo;
import com.wonders.contact.common.model.vo.ParamVo;
import com.wonders.contact.common.util.SimpleLogger;
import com.wonders.contact.deptContact.constant.DeptContactFlowConstants;
import com.wonders.contact.deptContact.model.vo.DeptContactParamVo;
import com.wonders.contact.deptContact.service.DeptContactService;
import com.wonders.contact.deptContact.util.ActionWriter;
import com.wonders.contact.deptSubProcess.constant.DeptSubProcessFlowConstants;
import com.wonders.contact.deptSubProcess.model.vo.DeptSubProcessParamVo;
import com.wonders.contact.deptSubProcess.service.DeptSubProcessService;
import com.wonders.contact.external.model.vo.OperateVo;
import com.wonders.contact.external.service.ApiService;
import com.wonders.contact.model.bo.MessageBo;
import com.wonders.util.StringUtil;

@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/contact-api")
@Controller("apiDeptContactAction")
@Scope("prototype")
public class ApiDeptContactAction extends ActionSupport implements ModelDriven<OperateVo>{
	public ActionContext actionContext = ActionContext.getContext();
	public HttpServletRequest request = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
	public HttpServletResponse response = (HttpServletResponse) actionContext.get(ServletActionContext.HTTP_RESPONSE);
	public HttpSession session = request.getSession();
	
	private DeptContactService deptContactService;
	private DeptSubProcessService deptSubProcessService;
	
	private ApiService apiService;
	
	SimpleLogger log = new SimpleLogger(this.getClass());
	
	public OperateVo operateVo = new OperateVo();
	private Gson gson = new Gson();
	private ResultInfo resultInfo = new ResultInfo();
	ActionWriter aw = new ActionWriter(response);
	
	public ApiDeptContactAction(){}

	@SuppressWarnings("unchecked")
	@Action(value="/deptContactOperate")
	public String operate(){
//log.debug("operate");
		try {
			
			try{
				Map<String,String> data_map = gson.fromJson(operateVo.data,operateVo.data_map.getClass());
				if(data_map!=null) operateVo.data_map=data_map;
				
				operateVo.user = gson.fromJson(operateVo.userInfo,operateVo.user.getClass());
			}catch(Exception e){
				e.printStackTrace();
			}
			apiService.prepareOperateData(operateVo);
			
			//if(operate)
			//ParamVo params = null;
			
			//String steplabel = StringUtil.getNotNullValueString(operateVo.todoDataMap.get("STEPLABEL"));
			String steplabel = StringUtil.getNotNullValueString(operateVo.todoItem.getStepName());
			
//log.debug("steplabel="+steplabel);
			
			resultInfo = new ResultInfo();
			if(operateVo.user==null){
				resultInfo.addErrors(new MessageBo("用户信息错误！"));
			}else{
				if(steplabel.length()==0){
					resultInfo.addErrors(new MessageBo("流程信息错误！"));
				}else if (DeptContactFlowConstants.STEPNAME_SIGN.equals(steplabel)){
					//工作联系单-部门内部签发
					DeptContactParamVo params = new DeptContactParamVo();
					initParams(params);
					
					params.operateVo = operateVo.operateVoDC;
					params.mainBo = operateVo.main;
					params.userInfo = operateVo.user;
					
					try{
						deptContactService.flowStepSign(params);
						resultInfo = params.resultInfo;
						
						//resultInfo.refresh = null;
					}catch(Exception e){
						resultInfo.addErrors(new MessageBo(e.getMessage()));
					}
					
				}else if (DeptSubProcessFlowConstants.STEPNAME_LEADER.equals(steplabel)){
					//部门内部子流程 -部门领导审核
					DeptSubProcessParamVo params = new DeptSubProcessParamVo();
					initParams(params);
					
					params.vo = operateVo.operateVoDSP;
					params.userInfo = operateVo.user;
					try{
						deptSubProcessService.flowStepLeaderDeal(params);
						resultInfo = params.resultInfo;
					}catch(Exception e){
						resultInfo.addErrors(new MessageBo(e.getMessage()));
					}
				}
			}
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		resultInfo.url = null;
		aw.writeJsonWithAnnotation(resultInfo);
		return null;
	}
	
	@Action(value="/deptContactDetail")
	public String detail(){
//log.debug("detail");
		try {
			aw.writeJsonWithAnnotation(apiService.getDeptContactData(operateVo.todoId));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	private void initParams(ParamVo params){
		params.addProcessParam("pname", operateVo.todoDataMap.get("PNAME"));
		params.addProcessParam("pincident", operateVo.todoDataMap.get("PINCIDENT"));
		params.addProcessParam("cname", operateVo.todoDataMap.get("CNAME"));
		params.addProcessParam("cincident", operateVo.todoDataMap.get("CINCIDENT"));
		params.addProcessParam("steplabel", operateVo.todoDataMap.get("STEPLABEL"));
	}
	
	
	@Override
	public OperateVo getModel() {
		return operateVo;
	}
	
	
	
	
	public ApiService getApiService() {
		return apiService;
	}
	
	@Autowired(required=false)
	public void setApiService(@Qualifier("contact-apiService")ApiService apiService) {
		this.apiService = apiService;
	}

	public DeptContactService getDeptContactService() {
		return deptContactService;
	}

	@Autowired
	public void setDeptContactService(@Qualifier("contact-deptContactService")DeptContactService deptContactService) {
		this.deptContactService = deptContactService;
	}

	public DeptSubProcessService getDeptSubProcessService() {
		return deptSubProcessService;
	}

	@Autowired
	public void setDeptSubProcessService(@Qualifier("contact-deptSubProcessService")DeptSubProcessService deptSubProcessService) {
		this.deptSubProcessService = deptSubProcessService;
	}

}
