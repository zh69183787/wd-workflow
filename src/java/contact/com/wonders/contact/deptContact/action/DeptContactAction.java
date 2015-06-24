package com.wonders.contact.deptContact.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.wonders.attach.util.AttachUtil;
import com.wonders.contact.common.action.AbstractParamAction;
import com.wonders.contact.common.model.vo.ParamVo;
import com.wonders.contact.common.util.LoginUtil;
import com.wonders.contact.common.util.SimpleLogger;
import com.wonders.contact.deptContact.constant.DeptContactConstants;
import com.wonders.contact.deptContact.model.vo.DeptContactMainVo;
import com.wonders.contact.deptContact.model.vo.DeptContactParamVo;
import com.wonders.contact.deptContact.service.DeptContactService;
import com.wonders.contact.deptContact.util.ActionWriter;
import com.wonders.contact.deptContact.util.FlowUtil;
import com.wonders.util.DateUtil;
import com.wonders.util.StringUtil;

/**
 * @author XFZ
 * @version 1.0 2012-6-12
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/contact-deptContact")
@Controller("contact-deptContactAction")
@Scope("prototype")
public class DeptContactAction extends AbstractParamAction implements ModelDriven<DeptContactMainVo> {	
	private ActionWriter aw = new ActionWriter(response);
	SimpleLogger log = new SimpleLogger(this.getClass());
	
	public DeptContactMainVo mainVo = new DeptContactMainVo();
	public DeptContactParamVo params = new DeptContactParamVo();
	
	public String flowType = "";
	
	public String checkOnly = "";
	
	private DeptContactService service;
	
	/**初始化
	 * 
	 */
	public DeptContactAction(){
//log.debug("DeptContactAction()");
		params.mainVo = mainVo;
		params.userInfo = LoginUtil.getUserInfo(session);
	}
	
	
	/**查看页面跳转action
	 * @return
	 */
	@Action(value="view",results={
			@Result(name="view",location="/contact/deptContact/detail.jsp")
	})
	public String view(){
		try{
			String pname = StringUtil.getNotNullValueString(request.getParameter("pname"));
			String pincident = StringUtil.getNotNullValueString(request.getParameter("pincident"));
			DeptContactMainVo tmpVo = service.view(pname,pincident,params);
			if(tmpVo!=null)	mainVo = tmpVo;
			
			flowType = StringUtil.getNotNullValueString(params.getProcessParamValue(DeptContactConstants.PARAMS_FLOW_TYPE));
		}catch(Exception e){
			e.printStackTrace();
		}
		return "view";
	}
	
	
	/**业务操作页面跳转action
	 * @return
	 */
	@Action(value="forward",results={
			@Result(name="add",location="/contact/deptContact/update.jsp")
			,@Result(name="update",location="/contact/deptContact/update.jsp")
			,@Result(name="operate",location="/contact/deptContact/operate.jsp")
			,@Result(name="detail",location="/contact/deptContact/detail.jsp")
	})
	public String stepForward(){
		String operateType = "";
		operateType = FlowUtil.getTypeByInfo(params);
		
		if("".equals(operateType)||"add".equals(operateType)){
			params.addParam(DeptContactConstants.PARAMS_KEY_REF_ID,"");
			AttachUtil au = new AttachUtil();
			mainVo = new DeptContactMainVo();
			mainVo.setContentAttachmentId(au.getFileGroupCode());
			mainVo.setContactDate(DateUtil.getCurrDate(DateUtil.DATE_FORMAT));
		}else{
			DeptContactMainVo tmpVo = service.flowStepForward(params);
			
			if(tmpVo!=null)	mainVo = tmpVo;
			
			if(params.getProcessParamValue("operateType").length()>0){
				operateType = "detail";
			}
			
			flowType = StringUtil.getNotNullValueString(params.getProcessParamValue(DeptContactConstants.PARAMS_FLOW_TYPE));
		}
		return operateType;
	}


    /**业务操作页面跳转action kpi专用
     * @return
     */
    @Action(value="kpiAdd",results={
            @Result(name="kpiAdd",location="/contact/deptContact/kpiAdd.jsp")
    })
    public String stepKpiForward(){
        String operateType = "kpiAdd";
        //operateType = FlowUtil.getTypeByInfo(params);
        params.addParam(DeptContactConstants.PARAMS_KEY_REF_ID,"");
        AttachUtil au = new AttachUtil();
        mainVo = new DeptContactMainVo();
        mainVo.setContentAttachmentId(au.getFileGroupCode());
        mainVo.setContactDate(DateUtil.getCurrDate(DateUtil.DATE_FORMAT));
        return operateType;
    }
	
	
	/**查看页面跳转action
	 * @return
	 */
	@Action(value="viewForward",results={
			@Result(name="detail",location="/contact/deptContact/detail.jsp")
	})
	public String viewForward(){
		try{
			service.init(params);
			DeptContactMainVo tmpVo = service.viewForward(params);
			if(tmpVo!=null)	mainVo = tmpVo;
			
			flowType = StringUtil.getNotNullValueString(params.getProcessParamValue(DeptContactConstants.PARAMS_FLOW_TYPE));
		}catch(Exception e){
			e.printStackTrace();
		}
		return "detail";
	}
	
	/**打印页面跳转action
	 * @return
	 */
	@Action(value="printForward",results={
			@Result(name="print",location="/contact/deptContact/print.jsp")
	})
	public String printForward(){
		try{
			service.init(params);
			DeptContactMainVo tmpVo = service.viewForward(params);
			if(tmpVo!=null)	mainVo = tmpVo;
			
			flowType = StringUtil.getNotNullValueString(params.getProcessParamValue(DeptContactConstants.PARAMS_FLOW_TYPE));
		}catch(Exception e){
			e.printStackTrace();
		}
		return "print";
	}
	
	
	/** 多级工作联系单添加
	 * @return
	 */
	@Action(value="add")
	public String stepAdd(){
		try {
			service.flowStepAdd(params);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		aw.writeJsonWithAnnotation(params.resultInfo);
		
		return null;
	}
	
	
	/** 多级工作联系单修改
	 * @return
	 */
	@Action(value="update")
	public String stepUpdate(){
		
		try {
			service.flowStepUpdate(params);
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
	public DeptContactMainVo getModel() {
//log.debug("getModel()");
		return mainVo;
	}

	public DeptContactService getService() {
		return service;
	}
	
	@Autowired
	public void setService(@Qualifier("contact-deptContactService")DeptContactService service) {
		this.service = service;
	}

	public DeptContactMainVo getMainVo() {
		return mainVo;
	}

	public void setMainVo(DeptContactMainVo mainVo) {
		this.mainVo = mainVo;
	}

	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}

	public String getCheckOnly() {
		return checkOnly;
	}

	public void setCheckOnly(String checkOnly) {
		this.checkOnly = StringUtil.getNotNullValueString(checkOnly);
		/**仅效验，不进行具体操作*/
//log.debug("checkOnly="+checkOnly);
		if(this.checkOnly.length()>0) params.resultInfo.checkOnly=true;
		
	}
}


