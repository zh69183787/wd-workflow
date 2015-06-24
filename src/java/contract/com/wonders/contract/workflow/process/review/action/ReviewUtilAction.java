/**
 * 
 */
package com.wonders.contract.workflow.process.review.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wonders.contract.workflow.process.util.ProcessUtil;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.wonders.constants.LoginConstants;
import com.wonders.contract.workflow.common.action.AbstractParamAction;
import com.wonders.contract.workflow.common.util.ActionWriter;
import com.wonders.contract.workflow.model.vo.ParamVo;
import com.wonders.contract.workflow.organTree.model.bo.OrganUserBo;
import com.wonders.contract.workflow.util.FlowUtil;
import com.wonders.contract.workflow.external.service.ExternalService;
import com.wonders.util.StringUtil;


/** 
 * @ClassName: ReviewMainAction 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:28:50 
 *  
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/contract-reviewUtil")
@Controller("contract-reviewUtilAction")
@Scope("prototype")
public class ReviewUtilAction extends AbstractParamAction {
	private ActionWriter aw = new ActionWriter(response);
	private ExternalService externalService;
	
	
	@Action(value="getDeptLeader")
	public String getDeptLeader(){
		String deptId = StringUtil.getNotNullValueString(request.getParameter("deptId"));
		this.externalService.setToken(StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.TOKEN)));
		List<OrganUserBo> list = this.externalService.getDeptLeaders(deptId);
		aw.writeJson(list);
		return null;
	}
	
	@Action(value="getTypeByCode")
	public String getTypeByCode(){
		String code = StringUtil.getNotNullValueString(request.getParameter("code"));
		List<Map<String,Object>> list = FlowUtil.getTypeByCode(code);
		aw.writeJson(list);
		return null;
	}

    @Action(value="dictionary")
    public String dictionary() throws Exception{
        String pid = StringUtil.getNotNullValueString(request.getParameter("pid"));
        String code = StringUtil.getNotNullValueString(request.getParameter("code"));
        List<Map<String,Object>> list = FlowUtil.getTypeByPid(pid, code);
        Map map = new HashMap();
        map.put("result",list);
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject resultJSON = JSONObject.fromObject(map); //根据需要拼装json
        String callback = request.getParameter("callback");//客户端请求参数
        out.println(callback+"("+resultJSON.toString(1,1)+")");//返回jsonp格式数据
        out.flush();
        out.close();
        return null;
    }

    @Action(value="getTypeByName")
    public String getTypeByName() throws Exception{
        String name = StringUtil.getNotNullValueString(request.getParameter("name"));
        String code = StringUtil.getNotNullValueString(request.getParameter("code"));
        List<Map<String,Object>> list = FlowUtil.getTypeByName(code,name);
        Map map = new HashMap();
        map.put("result",list);
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject resultJSON = JSONObject.fromObject(map); //根据需要拼装json
        String callback = request.getParameter("callback");//客户端请求参数
        out.println(callback+"("+resultJSON.toString(1,1)+")");//返回jsonp格式数据
        out.flush();
        out.close();
        return null;
    }
	
	@Action(value="getTypeByPid")
	public String getTypeByPid(){
		String pid = StringUtil.getNotNullValueString(request.getParameter("pid"));
		List<Map<String,Object>> list = FlowUtil.getTypeByPid(pid);
		aw.writeJson(list);
		return null;
	}
	
	@Action(value="getProjectInfo")
	public String getProjectInfo(){
		String keywords = StringUtil.getNotNullValueString(request.getParameter("keywords"));
		String maxrows = StringUtil.getNotNullValueString(request.getParameter("maxrows"));
		List<Map<String,Object>> list = FlowUtil.getProjectInfo(keywords,maxrows);
		
		aw.writeJson(list);
		return null;
	}
	
	@Action(value="getCodeInfo")
	public String getCodeInfo(){
		String typeCode = StringUtil.getNotNullValueString(request.getParameter("typeCode"));
		String code = StringUtil.getNotNullValueString(request.getParameter("code"));
		List<Map<String,Object>> list = FlowUtil.getCodeInfo(typeCode,code);
		
		aw.writeJson(list);
		return null;
	}
	
	@Action(value="generateNo")
	public String generateNo(){

		String contractName = StringUtil.getNotNullValueString(request.getParameter("contractName"));
		String projectNo = StringUtil.getNotNullValueString(request.getParameter("projectNo"));
		String subProjectNo = StringUtil.getNotNullValueString(request.getParameter("subProjectNo"));
		String signCompany = StringUtil.getNotNullValueString(request.getParameter("signCompany"));
		String line = StringUtil.getNotNullValueString(request.getParameter("line"));
		String division = StringUtil.getNotNullValueString(request.getParameter("division"));
		String section = StringUtil.getNotNullValueString(request.getParameter("section"));
		String execCompany = StringUtil.getNotNullValueString(request.getParameter("execCompany"));
		String year = StringUtil.getNotNullValueString(request.getParameter("year"));
		String userName = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.USERNAME));
		String result = FlowUtil.generateNo(userName,contractName,projectNo, subProjectNo, signCompany, line,division, section, execCompany, year);
		
		aw.writeAjax(result);
		return null;
	}


    /**
     *增加一条记录
     * @return
     */
    @Action(value="findSequence")
    public String add(){
        String year=StringUtil.getNotNullValueString(request.getParameter("year"));
        String exeBody=StringUtil.getNotNullValueString(request.getParameter("exeBody"));
        String depts=StringUtil.getNotNullValueString(request.getParameter("depts"));
        String fyear=StringUtil.getNotNullValueString(request.getParameter("fyear"));
        String fawen=StringUtil.getNotNullValueString(request.getParameter("fawen"));
        String subProject=StringUtil.getNotNullValueString(request.getParameter("subProject"));
        String dept=StringUtil.getNotNullValueString(request.getParameter("dept"));
        String line=StringUtil.getNotNullValueString(request.getParameter("line"));
        String mainType=StringUtil.getNotNullValueString(request.getParameter("mainType"));
        String subType=StringUtil.getNotNullValueString(request.getParameter("subType"));
        String userName = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.USERNAME));
        String result = FlowUtil.generateNo(userName,"",depts+fyear+fawen, subProject, dept, line,mainType, subType, exeBody, year);

        aw.writeAjax(result);
        return null;
    }

    /**
     *  查询序列号
     * @return
     */
    @Action(value="getSequence")
     public String getSequence(){
         String year=StringUtil.getNotNullValueString(request.getParameter("year"));
         String exeBody=StringUtil.getNotNullValueString(request.getParameter("exeBody"));
         String result = FlowUtil.getSequence(year,exeBody);
         aw.writeAjax(result);
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
	public void setExternalService(@Qualifier("contract-externalService")ExternalService externalService) {
		this.externalService = externalService;
	}

}
