/**
 * 
 */
package com.wonders.todo.action;

import java.util.ArrayList;
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
import com.wonders.common.model.vo.TaskUserVo;
import com.wonders.page.model.PageInfo;
import com.wonders.page.model.PageResultSet;
import com.wonders.page.service.PageService;
import com.wonders.receive.workflow.common.action.AbstractParamAction;
import com.wonders.receive.workflow.constants.LoginConstants;
import com.wonders.receive.workflow.model.vo.ParamVo;
import com.wonders.rest.RestClient;
import com.wonders.todo.model.vo.TodoItemVo;
import com.wonders.todo.util.TodoUtil;
import com.wonders.util.StringUtil;


/** 
 * @ClassName: TodoItemAction 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-9-4 下午8:58:48 
 *  
 */

@ParentPackage("struts-default")
@Namespace(value="/todo")
@Controller("todoItemAction")
@Scope("prototype")
public class TodoItemAction extends AbstractParamAction implements ModelDriven<TodoItemVo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4918462429207183409L;

	private TodoItemVo vo = new TodoItemVo();
	
	private PageService pageService;
	
	public PageService getPageService() {
		return pageService;
	}

	@Autowired(required = false)
	public void setPageService(@Qualifier(value = "pageService")PageService pageService) {
		this.pageService = pageService;
	}


	private PageResultSet<Map<String,String>> pageResultSet;

	public PageResultSet<Map<String, String>> getPageResultSet() {
		return pageResultSet;
	}

	public void setPageResultSet(PageResultSet<Map<String, String>> pageResultSet) {
		this.pageResultSet = pageResultSet;
	}

	
	
	@SuppressWarnings("unchecked")
	@Action(value="todoItem",results={
			@Result(name="success",location="/todo/todoItem.jsp")
			})
	public String todoItemList(){
			String loginNames = "";
			Map<String, TaskUserVo> userMap = 
					(Map<String, TaskUserVo>)this.request.getSession().getAttribute(LoginConstants.DEPT_USER);
			for(Map.Entry<String, TaskUserVo> entry : userMap.entrySet()){
				loginNames += "'"+StringUtil.getNotNullValueString(entry.getKey()) +"'"+",";
			}
			if(loginNames.length() > 0){
				loginNames = loginNames.substring(0, loginNames.length()-1);
			}
			
			String response = RestClient.restForTodoItem(loginNames, "", "", "", "0");
			
			if("1".equals(response)){
				String sql = TodoUtil.generateSql(loginNames,vo);
				/* 记录总数 */
				int totalRows = (int) this.pageService.countBySql(sql);
				PageInfo pageinfo = new PageInfo(totalRows, vo.pageSize, vo.page);	
				
				List<String[]> list = this.pageService.findPaginationInfo(sql, pageinfo.getBeginIndex(), vo.pageSize);
				List<Map<String,String>> result = new ArrayList<Map<String,String>>();
				for(String[] s : list){
					Map<String,String> map = new HashMap<String,String>();
					map.put("title", s[0]);map.put("pname", s[1]);map.put("pincident", s[2]);
					map.put("cname", s[3]);map.put("cincident", s[4]);map.put("stepname", s[5]);
					map.put("occurtime", s[6]);map.put("taskid", s[7]);map.put("tasktype", s[8]);
					map.put("loginname", s[9]);map.put("url", s[10]);
					result.add(map);
				}
				this.pageResultSet = new PageResultSet<Map<String, String>>();
				pageResultSet.setList(result);
				pageResultSet.setPageInfo(pageinfo);
			}
		return "success";
	}
	
	
	
	@Override
	public TodoItemVo getModel() {
		// TODO Auto-generated method stub
		return vo;
	}

	@Override
	public ParamVo getParams() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
