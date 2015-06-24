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
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wonders.contact.common.util.SimpleLogger;
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

@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/contact-ultimus")
@Controller("contact-ultimusScanAction")
@Scope("prototype")
public class UltimusScanAction extends ActionSupport {
	public ActionContext actionContext = ActionContext.getContext();
	public HttpServletRequest request = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
	public HttpServletResponse response = (HttpServletResponse) actionContext.get(ServletActionContext.HTTP_RESPONSE);
	public HttpSession session = request.getSession();
	
	SimpleLogger log = new SimpleLogger(this.getClass());
	
	public String id = "";
	public String processname = "";
	public String incidentno = "";
	
	public String pid = "";

	public TDeptContactMain bo = new TDeptContactMain();
	public TDeptContactTree treeBo = new TDeptContactTree();
	
	public String imageUrl = "";
	
	private ActionWriter aw = new ActionWriter(response);
	
	@Action(value="scanPicture")
	public String scanPicture(){
		processname = StringUtil.getNotNullValueString(processname);
		incidentno = StringUtil.getNotNullValueString(incidentno);
		
		if(id.length()>0){
			FlowUtil.writeScanPicture(id, aw);
		}else if(processname.length()>0&&incidentno.length()>0){
			FlowUtil.writeScanPicture(processname,incidentno, aw);
		}
		
		return null;
	}
	

	@Action(value="scanList",results={
		@Result(name="success",location="/contact/ultimus/scanList.jsp")
	})
	public String scanList(){
		if(id.length()>0){
			try{
				bo = FlowUtil.getDeptContactDao().getMainBo(id);
				treeBo = FlowUtil.getDeptContactDao().getTreeBoByMainBoId(bo.getId());
				processname = StringUtil.getNotNullValueString(bo.getProcessname());
				incidentno = StringUtil.getNotNullValueString(bo.getIncidentno());
				pid = treeBo.getPId();
				imageUrl = "id="+treeBo.getCId();
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			try{
				treeBo = FlowUtil.getDeptContactDao().getTreeBoByCInfo(processname, incidentno);
				pid = treeBo.getPId();
				imageUrl = "processname="+processname+"&incidentno="+incidentno;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
//log.debug("("+id+"-"+processname+"-"+incidentno+")");
		
		DbUtil d = FlowUtil.getDbUtil();
		
		String sqlApprovedInfo = 
			"select t.steplabel,c.name,"+
			 " to_char(t.starttime,'YYYY-MM-DD HH24:MI:SS') as starttime,"+
			 " to_char(t.endtime,'YYYY-MM-DD HH24:MI:SS') as endtime,t.status from " +
			 " tasks t,cs_user c" +
			 " where t.processname= ? and t.incident=?" +
			 " and 'ST/'||c.login_name = t.assignedtouser" +
			 " and t.assignedtouser like 'ST/%'" +
			 " and t.steplabel != ?" +
			 //" and exists (" +
			 //" select '' from t_approvedinfo ta where ta.process = t.processname and ta.incidentno = t.incident " +
			 //" and ta.stepname = t.steplabel" +
			 //" )" +
			 " order by t.starttime";
		List<Map<String,Object>> approvedInfo = (List<Map<String,Object>>)d.getJdbcTemplate().queryForList(sqlApprovedInfo,new Object[]{processname,incidentno,DeptContactFlowConstants.STEPNAME_LOWER});
		
//log.debug("approvedInfo.size:"+approvedInfo.size());
		
		for(int i=0;i<approvedInfo.size();i++){
			Map<String,Object> map = approvedInfo.get(i);
			String status = StringUtil.getNotNullValueString(map.get("status".toUpperCase()));
			
			if(String.valueOf(CommonFlowConstants.STATUS_TASKS_ACTIVE).equals(status)){
				map.put("statusText", "进行中");
				map.put("endtime".toUpperCase(), "");
			}else if(String.valueOf(CommonFlowConstants.STATUS_TASKS_FINISH).equals(status)){
				map.put("statusText", "已完成");
			}
		}
		
		request.setAttribute("list1", approvedInfo);
		//actionContext.put("list1", approvedInfo);
		
		
		
		String cId = StringUtil.getNotNullValueString(treeBo.getCId());
		
		if(cId.length()>0){
		
			String sqlProcessInfo = "select tree.p_id,tree.c_id,tree.cname,tree.cincident,tree.main_unit_id,tree.type,tree.status " +
			" ,to_char(t.starttime,'YYYY-MM-DD HH24:MI:SS') as starttime,tree.operate_date as endtime" +
			" from " +
			" t_dept_contact_tree tree left join tasks t on t.processname=tree.cname and t.incident = tree.cincident and t.steplabel = 'Begin'" +
			" where tree.p_id = ? and tree.p_id is not null" +
			" order by t.starttime";
		
			List<Map<String,Object>> listInfo = (List<Map<String,Object>>)d.getJdbcTemplate().queryForList(sqlProcessInfo,new Object[]{bo.getId()});
			
//log.debug("listInfo.size:"+listInfo.size());
			
			List<String> deptIdList = new ArrayList<String>();
			
			for(int i=0;i<listInfo.size();i++){
				Map<String,Object> map = listInfo.get(i);
				
				String type = StringUtil.getNotNullValueString(map.get("type".toUpperCase()));
				String c_id = StringUtil.getNotNullValueString(map.get("c_id".toUpperCase()));
				String cname = StringUtil.getNotNullValueString(map.get("cname".toUpperCase()));
				String cincident = StringUtil.getNotNullValueString(map.get("cincident".toUpperCase()));
				String main_unit_id = StringUtil.getNotNullValueString(map.get("main_unit_id".toUpperCase()));
				String status = StringUtil.getNotNullValueString(map.get("status".toUpperCase()));
				
				deptIdList.add(main_unit_id);
				/*
				 * TODO 根据main_unit_id获得部门描述并在页面上显示
				 */
				
				if(DeptContactConstants.STATUS_LOWER_STR.equals(type)){
					map.put("flowType","下级流程");
					map.put("params", "id="+c_id);
				}else if(DeptContactConstants.STATUS_SUB_STR.equals(type)){
					map.put("flowType","主送部门内部子流程");
					map.put("params", "processname="+cname+"&incidentno="+cincident);
				}else if(DeptContactConstants.STATUS_MAIN_BACKSUB_STR.equals(type)){
					map.put("flowType","返回发起部门子流程");
					map.put("params", "processname="+cname+"&incidentno="+cincident);
				}
				
				if(String.valueOf(ProcessControllerConstants.STATUS_ACTIVE).equals(status)){
					map.put("statusText", "进行中");
					map.put("endtime".toUpperCase(), "");
				}else if(String.valueOf(ProcessControllerConstants.STATUS_FINISH).equals(status)){
					map.put("statusText", "已完成");
				}
			}
			request.setAttribute("list2", listInfo);
		}
		return "success";
	}
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcessname() {
		return processname;
	}

	public void setProcessname(String processname) {
		this.processname = processname;
	}

	public String getIncidentno() {
		return incidentno;
	}

	public void setIncidentno(String incidentno) {
		this.incidentno = incidentno;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
}
