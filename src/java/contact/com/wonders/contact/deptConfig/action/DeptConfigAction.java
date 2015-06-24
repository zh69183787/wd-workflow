package com.wonders.contact.deptConfig.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.wonders.contact.deptConfig.constant.DeptConfigConstants;
import com.wonders.contact.deptConfig.model.bo.TDeptContactRelation;
import com.wonders.contact.deptConfig.model.vo.DeptConfigSearchVo;
import com.wonders.contact.deptConfig.service.DeptConfigService;
import com.wonders.contact.deptContact.model.bo.TDeptContactConfig;
import com.wonders.contact.deptContact.util.ActionWriter;
import com.wonders.contact.page.util.Page;
import com.wonders.contact.page.util.PageHelp;
import com.wonders.contact.page.util.QueryUtil;

@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/contact-deptConfig")
@Controller("contact-deptConfigAction")
@Scope("prototype")
public class DeptConfigAction extends ActionSupport implements ModelDriven<DeptConfigSearchVo>{
	public ActionContext actionContext = ActionContext.getContext();
	public HttpServletRequest request = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
	public HttpServletResponse response = (HttpServletResponse) actionContext.get(ServletActionContext.HTTP_RESPONSE);
	public HttpSession session = request.getSession();
	private ActionWriter aw = new ActionWriter(response);
	private DeptConfigSearchVo vo = new DeptConfigSearchVo();
	private Page page = new Page();
	private QueryUtil queryUtil;
	private Map<String,String> statusMap = new HashMap<String,String>();
	private String hideOrShowParam = "show";
	private DeptConfigService deptConfigService;
	private List<TDeptContactRelation> relationList = new ArrayList<TDeptContactRelation>();
	public DeptConfigAction(){
		statusMap.put(String.valueOf(DeptConfigConstants.active), "启用");
		statusMap.put(String.valueOf(DeptConfigConstants.freeze), "禁用");
	}
	/**
	 * @return
	 */
	@Action(value="queryList")
	public String queryList(){
		try{		
			String[] queryNameArr = {"RECEIVER_NAME","DEPTNAME"};
			String[] queryTypeArr = { "textType", "textType"};
			String[] queryResultArr = {vo.userName,vo.deptName};
			
			String baseSQL = QueryUtil.generateSQLByType(DeptConfigConstants.baseSQL, queryNameArr, queryResultArr, queryTypeArr);
			
			pageResult(baseSQL);
			aw.writeJson(page);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void pageResult(String sql) {
		/* 记录总数 */
		int totalRows = (int) queryUtil.getPageService().countBySql(sql);
		page = PageHelp.getPager(request, totalRows);
		List<String[]> ps = queryUtil.getPageService().findPaginationInfo(sql, page.getStartRow(), page.getPageSize());
		page.result = ps;
	}
	
	@Action(value="queryRelation")
	public String queryRelation(){
		relationList = this.deptConfigService.getBoList();
		aw.writeJson(relationList);
		return null;
	}
	
	
	@Action(value="saveConfig")
	public String saveConfig(){
		if(this.deptConfigService.findConfigDept(request.getParameter("configDeptId"))>0){
			aw.writeAjax("0");
			return null;
		}
		TDeptContactConfig t = new TDeptContactConfig();
		t.setRemoved(0);t.setProcessname("多级工作联系单");t.setStepname("下级流程");
		t.setDeptid(request.getParameter("configDeptId"));
		t.setDeptname(request.getParameter("configDeptName"));
		t.setInitiator(stConfig(request.getParameter("configLoginName")));
		t.setInitiatorName(request.getParameter("configUserName"));
		t.setReceiver(stConfig(request.getParameter("configLoginName")));
		t.setReceiverName(request.getParameter("configUserName"));
		this.deptConfigService.saveConfigUser(t);
		aw.writeAjax("1");
		return null;
	}
	@Action(value="updateConfig")
	public String updateConfig(){
		this.deptConfigService.updateConfigUser(request.getParameter("configId"), stConfig(request.getParameter("configLoginName")), request.getParameter("configUserName"));
		aw.writeAjax("1");
		return null;
	}
	@Action(value="switchConfig")
	public String switchConfig(){
		this.deptConfigService.switchConfigUser(request.getParameter("configId"), Integer.parseInt(request.getParameter("flag")));
		aw.writeAjax("1");
		return null;
	}
	
	
	
	
	public String stConfig(String obj){
		if(obj!=null&&!obj.startsWith("ST/")){
			return "ST/"+obj;
		}else{
			return obj;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public DeptConfigSearchVo getModel(){
		return vo;
	}
	public Map<String, String> getStatusMap() {
		return statusMap;
	}
	public void setStatusMap(Map<String, String> statusMap) {
		this.statusMap = statusMap;
	}
	public String getHideOrShowParam() {
		return hideOrShowParam;
	}
	public void setHideOrShowParam(String hideOrShowParam) {
		this.hideOrShowParam = hideOrShowParam;
	}
	public QueryUtil getQueryUtil() {
		return queryUtil;
	}
	@Autowired(required = false)
	public void setQueryUtil(@Qualifier(value = "contact-queryUtil")QueryUtil queryUtil) {
		this.queryUtil = queryUtil;
	}
	public DeptConfigService getDeptConfigService() {
		return deptConfigService;
	}
	@Autowired(required = false)
	public void setDeptConfigService(@Qualifier(value = "contact-deptConfigService")DeptConfigService deptConfigService) {
		this.deptConfigService = deptConfigService;
	}
	public List<TDeptContactRelation> getRelationList() {
		return relationList;
	}
	public void setRelationList(List<TDeptContactRelation> relationList) {
		this.relationList = relationList;
	}
	
	
}
