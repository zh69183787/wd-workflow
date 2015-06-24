/**
 * 
 */
package com.wonders.discipline.workflow.process.recv.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.wonders.discipline.workflow.common.action.AbstractParamAction;
import com.wonders.discipline.workflow.common.util.ActionWriter;
import com.wonders.discipline.workflow.constants.UltimusConstants;
import com.wonders.discipline.workflow.model.vo.ParamVo;
import com.wonders.discipline.workflow.process.util.SerialUtil;
import com.wonders.discipline.workflow.util.FlowUtil;
import com.wonders.util.StringUtil;


/** 
 * @ClassName: DcpRecvMainAction 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:28:50 
 *  
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/discipline-dcpRecvUtil")
@Controller("discipline-dcpRecvUtilAction")
@Scope("prototype")
public class DcpRecvUtilAction extends AbstractParamAction {
	private ActionWriter aw = new ActionWriter(response);
	
	@Action(value="getSimulateLeader")
	public String getSimulateLeader(){
		List<Map<String,Object>> list = FlowUtil.getConfigUser
				(UltimusConstants.DISCIPLINE_RECV_DICTIONARY_NAME, 
						"拟办领导", UltimusConstants.DISCIPLINE_RECV_DICTIONARY_CODE);
		aw.writeJson(list);
		return null;
	}
	
	@Action(value="getFinishLeader")
	public String getFinishLeader(){
		List<Map<String,Object>> list = FlowUtil.getConfigUser
				(UltimusConstants.DISCIPLINE_RECV_DICTIONARY_NAME, 
						"办结人", UltimusConstants.DISCIPLINE_RECV_DICTIONARY_CODE);
		aw.writeJson(list);
		return null;
	}
	
	@Action(value="getCodeList")
	public String getCodeList(){
		
		String codeType_code = StringUtil.getNotNullValueString(request.getParameter("type"));
		String code = StringUtil.getNotNullValueString(request.getParameter("code"));
		String name = StringUtil.getNotNullValueString(request.getParameter("name"));
		
		List<Map<String,Object>> list = FlowUtil.getCodeNameList(codeType_code,code,name);
		aw.writeJson(list);
		
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
		String typeid = StringUtil.getNotNullValueString(request.getParameter("typeid"));
		
		int serial = SerialUtil.getSerial(prefix,postfix,typeid);
		aw.writeAjax(serial+"");
		
		return null;
	}
	
	@Action(value="selSerial")
	public String selSerial(){
		
		String prefix = StringUtil.getNotNullValueString(request.getParameter("prefix"));
		String postfix = StringUtil.getNotNullValueString(request.getParameter("postfix"));
		String typeid = StringUtil.getNotNullValueString(request.getParameter("typeid"));
		
		
		List<Integer> serial = SerialUtil.selSerial(prefix,postfix,typeid);
		aw.writeJson(serial);
		
		return null;
	}
	
	@Action(value="prefill")
	public String prefill(){
		String id = StringUtil.getNotNullValueString(request.getParameter("id"));
		String flag = StringUtil.getNotNullValueString(request.getParameter("flag"));
		
		int result = SerialUtil.prefillRecv(id, flag);
		aw.writeAjax(result + "");
		
		return null;
	}
	

	@Override
	public ParamVo getParams() {
		// TODO Auto-generated method stub
		return null;
	}

}
