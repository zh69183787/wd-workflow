/**
 * 
 */
package com.wonders.receive.workflow.process.redv.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.wonders.receive.workflow.common.action.AbstractParamAction;
import com.wonders.receive.workflow.common.util.ActionWriter;
import com.wonders.receive.workflow.constants.LoginConstants;
import com.wonders.receive.workflow.external.service.ExternalService;
import com.wonders.receive.workflow.model.vo.ParamVo;
import com.wonders.receive.workflow.organTree.model.bo.OrganUserBo;
import com.wonders.receive.workflow.process.util.SerialUtil;
import com.wonders.receive.workflow.util.FlowUtil;
import com.wonders.util.StringUtil;


/** 
 * @ClassName: RedvMainAction 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:28:50 
 *  
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/receive-redvUtil")
@Controller("receive-redvUtilAction")
@Scope("prototype")
public class RedvUtilAction extends AbstractParamAction {
	private ActionWriter aw = new ActionWriter(response);
	private ExternalService externalService;
	
	@Action(value="getCodeList")
	public String getCodeList(){
		
		String codeType_code = StringUtil.getNotNullValueString(request.getParameter("type"));
		String code = StringUtil.getNotNullValueString(request.getParameter("code"));
		String name = StringUtil.getNotNullValueString(request.getParameter("name"));
		
		List<Map<String,Object>> list = FlowUtil.getCodeNameList(codeType_code,code,name);
		aw.writeJson(list);
		
		return null;
	}
	
	@Action(value="getCodeByName")
	public String getCodeByName(){
		
		String codeType_code = StringUtil.getNotNullValueString(request.getParameter("type"));
		String name = StringUtil.getNotNullValueString(request.getParameter("name"));
		
		String result = FlowUtil.getCodeByName(codeType_code,name);
		aw.writeAjax(result);
		
		return null;
	}
	
	@Action(value="getStatusList")
	public String getStatusList(){
		
		String code = StringUtil.getNotNullValueString(request.getParameter("code"));
		
		List<String> list = FlowUtil.getStatusName(code);
		aw.writeJson(list);
		
		return null;
	}
	
	@Action(value="getSerial")
	public String getSerial(){
		
		String prefix = StringUtil.getNotNullValueString(request.getParameter("prefix"));
		String postfix = StringUtil.getNotNullValueString(request.getParameter("postfix"));
		
		int serial = SerialUtil.getSerial(prefix,postfix);
		aw.writeAjax(serial+"");
		
		return null;
	}
	
	@Action(value="selSerial")
	public String selSerial(){
		
		String prefix = StringUtil.getNotNullValueString(request.getParameter("prefix"));
		String postfix = StringUtil.getNotNullValueString(request.getParameter("postfix"));
		
		
		List<Integer> serial = SerialUtil.selSerial(prefix,postfix);
		aw.writeJson(serial);
		
		return null;
	}
	
	
	@Action(value="updateSerial")
	public String updateSerial(){
		String id = StringUtil.getNotNullValueString(request.getParameter("id"));
		String prefix = StringUtil.getNotNullValueString(request.getParameter("prefix"));
		String postfix = StringUtil.getNotNullValueString(request.getParameter("postfix"));
		String serialno = StringUtil.getNotNullValueString(request.getParameter("serialno"));
		
		int result = SerialUtil.updateSerial(id,prefix,postfix,serialno);
		aw.writeAjax(result+"");
		
		return null;
	}
	
	@Action(value="prefill")
	public String prefill(){
		String id = StringUtil.getNotNullValueString(request.getParameter("id"));
		String flag = StringUtil.getNotNullValueString(request.getParameter("flag"));
		
		int result = SerialUtil.prefillRedv(id, flag);
		aw.writeAjax(result + "");
		
		return null;
	}
	
	
	@Action(value="getDeptLeader")
	public String getDeptLeader(){
		String deptId = StringUtil.getNotNullValueString(request.getParameter("deptId"));
		this.externalService.setToken(StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.TOKEN)));
		List<OrganUserBo> list = this.externalService.getDeptLeaders(deptId);
		aw.writeJson(list);
		return null;
	}

	@Override
	public ParamVo getParams() {
		// TODO Auto-generated method stub
		return null;
	}

	public ExternalService getExternalService() {
		return externalService;
	}
	
	@Autowired(required=false)
	public void setExternalService(@Qualifier("receive-externalService")ExternalService externalService) {
		this.externalService = externalService;
	}

	
}
