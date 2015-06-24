package com.wonders.contact.ultimus.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.wonders.contact.constant.CommonFlowConstants;
import com.wonders.contact.deptContact.constant.DeptContactConstants;
import com.wonders.contact.deptContact.constant.DeptContactFlowConstants;
import com.wonders.contact.deptContact.model.bo.TDeptContactMain;
import com.wonders.contact.deptContact.model.bo.TDeptContactTree;
import com.wonders.contact.deptContact.util.ActionWriter;
import com.wonders.contact.deptContact.util.FlowUtil;
import com.wonders.contact.processController.constant.ProcessControllerConstants;
import com.wonders.util.DbUtil;
import com.wonders.util.StringUtil;


@ParentPackage("struts-default")
@Namespace(value="/contact-ultimus")
@Controller("contact-ultimusStatusAction")
@Scope("prototype")
public class UltimusStatusAction {
	public static final String FIRST = "Begin,申请";
	public static final String SECOND = "部门内部签发";
	public static final String THIRD = "主送部门内部子流程,下级流程";
	public static final String FOURTH = "发起部门内部子流程";
	public static final String FIFTH = "End";
	
	
	public ActionContext actionContext = ActionContext.getContext();
	public HttpServletRequest request = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
	public HttpServletResponse response = (HttpServletResponse) actionContext.get(ServletActionContext.HTTP_RESPONSE);
	public HttpSession session = request.getSession();
	
	public String id = "";
	public String processname = "";
	public String incidentno = "";
	public String pid = "";
	private ActionWriter aw = new ActionWriter(response);
	public TDeptContactMain bo = new TDeptContactMain();
	public TDeptContactTree treeBo = new TDeptContactTree();

	@Action(value="statusList")
	public String statusList(){
		String val = "0";
		if(id.length()>0&&!"-1".equals(id)){
			List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
			bo = FlowUtil.getDeptContactDao().getMainBo(id);
			treeBo = FlowUtil.getDeptContactDao().getTreeBoByMainBoId(bo.getId());
			processname = StringUtil.getNotNullValueString(bo.getProcessname());
			incidentno = StringUtil.getNotNullValueString(bo.getIncidentno());
			pid = treeBo.getPId();
		
			DbUtil d = FlowUtil.getDbUtil();
			
			//主流程
			String sqlMainInfo = 
				"select t.steplabel as type,c.name,"+
				 " to_char(t.starttime,'YYYY-MM-DD HH24:MI:SS') as starttime,"+
				 " to_char(t.endtime,'YYYY-MM-DD HH24:MI:SS') as endtime,t.status from " +
				 " tasks t,cs_user c" +
				 " where t.processname= ? and t.incident=?" +
				 " and 'ST/'||c.login_name = t.assignedtouser" +
				 " and t.assignedtouser like 'ST/%'" +
				 " and t.steplabel != ?" +
				 " order by t.starttime";
			List<Map<String,Object>> mainInfo = (List<Map<String,Object>>)d.getJdbcTemplate().queryForList(sqlMainInfo,new Object[]{processname,incidentno,DeptContactFlowConstants.STEPNAME_LOWER});
			result.addAll(mainInfo);
			//子流程
			String cId = StringUtil.getNotNullValueString(treeBo.getCId());
			if(cId.length()>0){
			
				String sqlProcessInfo = "select tree.p_id,tree.c_id,tree.cname,tree.cincident,tree.main_unit_id,tree.type,tree.status " +
				" ,to_char(t.starttime,'YYYY-MM-DD HH24:MI:SS') as starttime,tree.operate_date as endtime" +
				" from " +
				" t_dept_contact_tree tree left join tasks t on t.processname=tree.cname and t.incident = tree.cincident and t.steplabel = 'Begin'" +
				" where tree.p_id = ? and tree.p_id is not null" +
				" order by t.starttime";
			
				List<Map<String,Object>> listInfo = (List<Map<String,Object>>)d.getJdbcTemplate().queryForList(sqlProcessInfo,new Object[]{bo.getId()});
				result.addAll(listInfo);
			}
			//结束标志位
			String endSql = "select t.type,t.status from t_dept_contact_tree t where t.c_id = ? and removed=0 and t.status=1 and t.type in (0,2)";
			List<Map<String,Object>> endList = (List<Map<String,Object>>)d.getJdbcTemplate().queryForList(endSql,new Object[]{bo.getId()});
			result.addAll(endList);
			if(result!=null){
				val = "-1";
				for(Map<String,Object> map:result){
					String type = StringUtil.getNotNullValueString(map.get("type"));
					String status = StringUtil.getNotNullValueString(map.get("status"));
					System.out.println("type="+type+"   status="+status);
					if(UltimusStatusAction.FIRST.indexOf(type)>-1&&String.valueOf(CommonFlowConstants.STATUS_TASKS_ACTIVE).equals(status)){
						val = "0";
						break;
					}else if(UltimusStatusAction.SECOND.indexOf(type)>-1&&String.valueOf(CommonFlowConstants.STATUS_TASKS_ACTIVE).equals(status)){
						val = "1";
						break;
					}else if((DeptContactConstants.STATUS_LOWER_STR.equals(type)&&String.valueOf(ProcessControllerConstants.STATUS_ACTIVE).equals(status))
							||(DeptContactConstants.STATUS_SUB_STR.equals(type)&&String.valueOf(ProcessControllerConstants.STATUS_ACTIVE).equals(status)) ){
						val = "2";
						break;
					}else if(DeptContactConstants.STATUS_MAIN_BACKSUB_STR.equals(type)&&String.valueOf(ProcessControllerConstants.STATUS_ACTIVE).equals(status)){
						val = "3";
						break;
					}else if((DeptContactConstants.STATUS_MAIN_STR.equals(type)&&String.valueOf(ProcessControllerConstants.STATUS_FINISH).equals(status))||
							(DeptContactConstants.STATUS_LOWER_STR.equals(type)&&String.valueOf(ProcessControllerConstants.STATUS_FINISH).equals(status))){
						val = "4";
						break;
					}
				}
				
			}
			
		}
		aw.writeAjax(val);
		return null;
	
	}
}
