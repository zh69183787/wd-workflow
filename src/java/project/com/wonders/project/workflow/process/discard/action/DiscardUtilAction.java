/**
 * 
 */
package com.wonders.project.workflow.process.discard.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.wonders.contract.workflow.common.action.AbstractParamAction;
import com.wonders.contract.workflow.common.util.ActionWriter;
import com.wonders.contract.workflow.model.vo.ParamVo;
import com.wonders.project.workflow.external.util.HttpRequestProxy;
import com.wonders.project.workflow.page.util.Page;
import com.wonders.project.workflow.page.util.PageHelp;
import com.wonders.project.workflow.page.util.QueryUtil;
import com.wonders.project.workflow.process.discard.model.vo.DiscardMainVo;
import com.wonders.project.workflow.util.FlowUtil;
import com.wonders.util.PropertyUtil;
import com.wonders.util.StringUtil;


@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/project-discardUtil")
@Controller("project-discardUtilAction")
@Scope("prototype")
public class DiscardUtilAction extends AbstractParamAction implements ModelDriven<DiscardMainVo>{
	private static final String projectCancelsQueryUrl = PropertyUtil.getValueByKey("config.properties", "projectCancelsQueryUrl");
	private static final String assetRecordQueryUrl = PropertyUtil.getValueByKey("config.properties", "assetRecordQueryUrl");
	
	private ActionWriter aw = new ActionWriter(response);
	public DiscardMainVo vo = new DiscardMainVo();
	
	private Page page = new Page();
	private QueryUtil queryUtil;
	
	@Action(value="getProjectInfo")
	public String getProjectInfo(){
		String projectName = StringUtil.getNotNullValueString(request.getParameter("projectName"));
		
        Map<String,String> map = new HashMap<String,String>();  
        map.put("projectName",projectName);  
		String results = HttpRequestProxy.doPost(projectCancelsQueryUrl, map);
		
		aw.writeAjax(results);
		return null;
	}
	
	@Action(value="getAssetInfo")
	public String getAssetInfo(){
		String dispatchNo = StringUtil.getNotNullValueString(request.getParameter("dispatchNo")).trim();
		
		Map<String,String> map = new HashMap<String,String>();  
        map.put("projectAppNo",dispatchNo);
        map.put("pageSize","99999");
		String results = HttpRequestProxy.doPost(assetRecordQueryUrl, map);
		
		aw.writeAjax(results);
		return null;
	}
	
	/**
	 * @return
	 */
	@Action(value="queryList",results={
			@Result(name="success",location="/project/process/discard/list.jsp")
	})
	public String queryList(){
	
		try{
			String baseSQL = FlowUtil.getQueryListSql("","");
			
			String[] queryNameArr = {"project_name","operator_name","flag"};
			String[] queryTypeArr = { "textType","textType","textType"};
			String[] queryResultArr = {vo.getProjectName(),vo.getOperatorName(),vo.getStatus()};
			
			baseSQL = QueryUtil.generateSQLByType(baseSQL, queryNameArr, queryResultArr, queryTypeArr);
			
			pageResult(baseSQL);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return "success";
	}
	
	/**
	 * @return
	 */
	@Action(value="assetListToDiscard",results={
			@Result(name="success",location="/project/process/discard/assetListToDiscard.jsp")
	})
	public String assetListToDiscard(){
	
		try{
	        StringBuffer sql = new StringBuffer("SELECT P.id," +
	                "               P.ASSET_NO," +
	                "               P.ASSET_NAME," +
	                "               P.OPERATE_PROJECT_ASSET," +
	                "               P.ORIGINAL_VALUE," +
	                "               P.MAINTAIN_COST," +
	                "               P.LIFE_LEFT," +
	                "               P.LIFE_EXTEND" +
	                "          FROM PCL_PROJECT_DISCARD_ASSET P" +
	                "         WHERE P.REMOVED = '0'" +
	                "			AND P.ASSET_TYPE = '计划报废'" +
	                "		order by P.OPERATE_TIME desc ");
			
			String[] queryNameArr = {"ASSET_NO","ASSET_NAME"};
			String[] queryTypeArr = { "textType","textType"};
			String[] queryResultArr = {vo.getDispatchNo(),vo.getProjectName()};
			
			String baseSQL = QueryUtil.generateSQLByType(sql.toString(), queryNameArr, queryResultArr, queryTypeArr);
			
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

	@Override
	public ParamVo getParams() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public DiscardMainVo getModel() {
		// TODO Auto-generated method stub
		return vo;
	}
	
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}	
	
	public QueryUtil getQueryUtil() {
		return queryUtil;
	}
	@Autowired(required = false)
	public void setQueryUtil(@Qualifier(value = "project-queryUtil")QueryUtil queryUtil) {
		this.queryUtil = queryUtil;
	}

}
