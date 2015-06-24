package com.wonders.contact.deptContact.action;

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
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.wonders.contact.common.model.vo.UserInfo;
import com.wonders.contact.common.util.LoginUtil;
import com.wonders.contact.common.util.SimpleLogger;
import com.wonders.contact.deptContact.model.vo.DeptContactRefCheckVo;
import com.wonders.contact.deptContact.util.FlowUtil;
import com.wonders.contact.page.util.Page;
import com.wonders.contact.page.util.PageHelp;
import com.wonders.contact.page.util.QueryUtil;
import com.wonders.contact.processController.constant.ProcessControllerConstants;

/**
 * @author XFZ
 * @version 1.0 2012-7-9
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/contact-deptContact")
@Controller("contact-deptContactListAction")
@Scope("prototype")
public class DeptContactListAction extends ActionSupport implements ModelDriven<DeptContactRefCheckVo>{
	public ActionContext actionContext = ActionContext.getContext();
	public HttpServletRequest request = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
	public HttpServletResponse response = (HttpServletResponse) actionContext.get(ServletActionContext.HTTP_RESPONSE);
	public HttpSession session = request.getSession();
	
	SimpleLogger log = new SimpleLogger(this.getClass());
	
	private UserInfo userInfo;
	private Page page = new Page();
	private QueryUtil queryUtil;
	
	private String hideOrShowParam = "show";
	
	private DeptContactRefCheckVo refCheckVo = new DeptContactRefCheckVo();
	
	private Map<String,String> statusMap = new HashMap<String,String>();
	
	public DeptContactListAction(){
		statusMap.put(String.valueOf(ProcessControllerConstants.STATUS_ACTIVE), "进行中");
		statusMap.put(String.valueOf(ProcessControllerConstants.STATUS_FINISH), "已完成");
		userInfo = LoginUtil.getUserInfo(session);
	}
	
	/**
	 * @return
	 */
	@Action(value="refCheckList",results={
			@Result(name="success",location="/contact/deptContact/list_ref.jsp")
	})
	public String refCheckList(){
	
		try{
			//String whereStr = "";
			
			String baseSQL = FlowUtil.getDoneSql(userInfo, "");
			
			//log.debug(baseSQL);
			
			String[] queryNameArr = {"main_unit","copy_unit","contact_date","reply_date","theme","status"};
			String[] queryTypeArr = { "textType", "textType", "dateType", "dateType", "textType", "selectType" };
			String[] queryResultArr = {refCheckVo.main_unit, refCheckVo.copy_unit, 
										refCheckVo.getContact_date(), refCheckVo.getReply_date(), 
										refCheckVo.theme, refCheckVo.status};
			
			baseSQL = QueryUtil.generateSQLByType(baseSQL, queryNameArr, queryResultArr, queryTypeArr);
	
//log.debug(baseSQL);
			
			pageResult(baseSQL);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return "success";
	}
	
	/**
	 * @return
	 */
	@Action(value="queryList",results={
			@Result(name="success",location="/contact/deptContact/list.jsp")
	})
	public String queryList(){
	
		try{
			String baseSQL = FlowUtil.getQueryListSql("","");
			
			String[] queryNameArr = {"main_unit","copy_unit","contact_date","reply_date","theme","status"};
			String[] queryTypeArr = { "textType", "textType", "dateType", "dateType", "textType", "selectType" };
			String[] queryResultArr = {refCheckVo.main_unit, refCheckVo.copy_unit, 
										refCheckVo.getContact_date(), refCheckVo.getReply_date(), 
										refCheckVo.theme, refCheckVo.status};
			
			baseSQL = QueryUtil.generateSQLByType(baseSQL, queryNameArr, queryResultArr, queryTypeArr);
			
			pageResult(baseSQL);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return "success";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void pageResult(String sql) {
		/* 记录总数 */
		int totalRows = (int) queryUtil.getPageService().countBySql(sql);
		page = PageHelp.getPager(request, totalRows);
		//int totalPages = page.getTotalPages();
		//IDs = new ArrayList<ID>();
		
		List<String[]> ps = queryUtil.getPageService().findPaginationInfo(sql, page.getStartRow(), page.getPageSize());
		page.result = ps;
		//return ps;
	}
	

	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}	
	
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	public QueryUtil getQueryUtil() {
		return queryUtil;
	}
	@Autowired(required = false)
	public void setQueryUtil(@Qualifier(value = "contact-queryUtil")QueryUtil queryUtil) {
		this.queryUtil = queryUtil;
	}
	
	@Override
	public DeptContactRefCheckVo getModel() {
		return refCheckVo;
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
}
