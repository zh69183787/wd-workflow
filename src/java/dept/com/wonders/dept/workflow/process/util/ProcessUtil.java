/**
 * 
 */
package com.wonders.dept.workflow.process.util;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.wonders.dept.workflow.common.exception.ProcessException;
import com.wonders.dept.workflow.model.vo.ParamVo;
import com.wonders.dept.workflow.process.pass.constants.PassMainConstants;
import com.wonders.util.DateUtil;
import com.wonders.util.DbUtil;
import com.wonders.util.PWSUtil;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: ProcessUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 下午01:45:50 
 *  
 */
@Component("dept-processUtil")
public class ProcessUtil {
	public final static String domainName = "ST/";
	private static DbUtil dbUtil;
	public static void prepareFlowInfo(HttpServletRequest request,ParamVo params){
		String pname = StringUtil.getNotNullValueString(request.getParameter("pname"));
		String pincident = StringUtil.getNotNullValueString(request.getParameter("pincident"));
		String cname = StringUtil.getNotNullValueString(request.getParameter("cname"));
		String cincident = StringUtil.getNotNullValueString(request.getParameter("cincident"));
		
		String steplabel = StringUtil.getNotNullValueString(request.getParameter("steplabel"));
		String taskid = StringUtil.getNotNullValueString(request.getParameter("taskid"));
		String taskuser = StringUtil.getNotNullValueString(request.getParameter("taskuser"));
		
		String todoId = StringUtil.getNotNullValueString(request.getParameter("todoId"));
		
		params.addProcessParam("todoId", todoId);
		params.addProcessParam("pname", pname);
		params.addProcessParam("pincident", pincident);
		params.addProcessParam("cname", cname);
		params.addProcessParam("cincident", cincident);
		params.addProcessParam("steplabel", steplabel);
		params.addProcessParam("taskid", taskid);
		params.addProcessParam("taskuser", taskuser);
		
		params.userInfo.initTaskUser(params.getProcessParamValue("taskuser"));
	}
	
	public static String getTaskUser(ParamVo params){
		String taskuser = params.getProcessParamValue("taskuser");
		if(taskuser.length() == 0){
			taskuser = params.userInfo.getLoginName();
		}
		return taskuser;
		
	}
	
	public static String getTaskDept(ParamVo params){
		String taskdept = params.getProcessParamValue("taskdept");
		if(taskdept.length() == 0){
			taskdept = params.userInfo.getDeptId();
		}		
		return taskdept;
	}
	
	
	public static int launchProcess(String processName, String taskUserLoginName,String summary, Map<String,Object> map){
		return PWSUtil.launchIncident(processName, taskUserLoginName, summary, map);
	}
	public static boolean launchProcessStep(String processName, String taskUserLoginName, int incidentno, String steplabel, String summary,String memo, Map<String,Object> map){
//log.debug("触发流程开始");
		//boolean flag=true;
		System.out.println(DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
		boolean flag=PWSUtil.completeStepTest(processName, taskUserLoginName, incidentno, steplabel, summary, memo, map);
		//System.out.println(flag);
		System.out.println(DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
		return flag;
	}
	
	
	/**秘书代领导操作 获取领导工号及姓名
	 * @param processname
	 * @param incident
	 * @return
	 */
	public static String getLeaderInfo(String processName, String incident){
		if(processName.length() == 0 ||incident.length() == 0 ){
			throw new ProcessException("流程信息错误！");
		}
		String val = "";
		String sql = "select c.login_name ,c.name from cs_user c where 'ST/' ||  c.login_name = " +
				"(select o.taskuser from tasks o where o.processname = ? and o.incident = ? " +
				" and o.steplabel like '%领导%'and o.recipient <> 'WONDERS-TEST_WF' ) and c.removed = 0";
		
		Object[] params = new Object[]{processName,Long.valueOf(incident)};
		
		List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql, params);
		if(list.size()>0){
			val = StringUtil.getNotNullValueString(list.get(0).get("login_name")) + ","+
					StringUtil.getNotNullValueString(list.get(0).get("name"));
			return val;
		}
		if(val.length() == 0){
				throw new ProcessException("领导信息为空！");
		}
		
		return val;
	}
	
	
	/**秘书退回时 取得领导当前节点名称
	 * @param processname
	 * @param incident
	 * @return
	 */
	public static String getStepInfo(String loginName, String processName, String incident){
		if(processName.length() == 0 ||incident.length() == 0 ){
			throw new ProcessException("流程信息错误！");
		}
		String val = "";
		String sql = "select t.steplabel as stepName from tasks t where t.taskuser = ? " +
				" and t.processname = ? and t.incident = ?"; 
		
		
		Object[] params = new Object[]{loginName, processName,Long.valueOf(incident)};
		
		List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql, params);
		if(list.size()>0){
			val = StringUtil.getNotNullValueString(list.get(0).get("stepname"));
			return val;
		}
		if(val.length() == 0){
				throw new ProcessException("节点信息为空！");
		}
		
		return val;
	}

	
	public static List<Map<String,Object>> generateDeptQueryList(){
		String sql = "select t.taskid,main.* " +
				" from (select t.title,t.reg_name," +
				"  t.process, t.incident," +
				" case t.flag when '2' then '3' else to_char(ins.status)" +
				" end as pstatus from incidents ins, t_dept_pass t " +
				" where t.removed = 0 and ins.processname = t.process " +
				" and to_char(ins.incident) = t.incident and ins.status in (1, 2, 8)" +
				" and t.process = '部门内部审阅') main,tasks t where t.incident = main.incident " +
				" and t.processname = main.process and t.steplabel = 'Begin'";
		List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql);
		System.out.println(list.size());
		return list;
	}
	
	
	//流程待办项
	public static List<Map<String,Object>> gengerateTodoItemsNewSql(String loginName){
		String sql =
			" select " +
			"'' deptId, " +
			"'' userId, " +
			" 'controller' app,  " +
			" inci.summary title, "+
        	" part.assignedtouser as loginName,"+
        	" part.steplabel stepName, "+
			" part.endtime as occurTime," +
			" part.pname typename, "+
			" part.pname pname, " +
			" part.pincident pincident, " +
			" part.cname cname, " +
			" part.cincident cincident, " +
			" part.taskid taskId ,"+
			" '' taskType,"+
			" '' dbxType, "+
			" inci.initiator,"+
			" '' initdept"+
        	" from incidents inci,"+
        	" (" +
        	
        	"(select t.processname as pname, "+
        	" to_char(t.incident) as pincident, "+
        	" t.processname as cname, "+
        	" to_char(t.incident) as cincident, "+
        	" t.steplabel, "+
        	" t.overduetime, "+
        	" to_char(t.endtime, 'yyyy-mm-dd hh24:mi:ss') as endtime, "+
        	" t.taskid, "+
        	" t.assignedtouser "+
        	" from tasks t "+
        	" where exists (select '' "+
        	" from processes a "+
        	" where t.processname = a.processname "+
        	" and a.launchtype = 0 "+
        	" and a.processname <> '拟办子流程' "+
        	" and a.processname <> '办结子流程' "+
        	" ) "+
        	//待修改 新发文流程
        	" and (t.processname='"+PassMainConstants.PASS_PROCESS+"')"+
        	" and t.status = 1 "+
        	" and t.assignedtouser like '" + loginName + "%' "+
        	" ) " +
        	
        	"union "+
        	
        	" (select b.pname as pname, "+
        	" b.pincident as pincident, "+
        	" b.cname as cname, "+
        	" b.cincident as cincident, "+
        	" t.steplabel, "+
        	" t.overduetime, "+
        	" to_char(t.endtime, 'yyyy-mm-dd hh24:mi:ss') as endtime, "+
        	" t.taskid, "+
        	" t.assignedtouser "+
        	" from t_subprocess b, tasks t "+
        	" where b.cname = t.processname " +
        	//待修改 新发文流程
        	" and (b.pname='"+PassMainConstants.PASS_PROCESS+"')"+
        	" and b.cincident = t.incident "+
        	" and not exists "+
        	" (select '' "+
        	" from processes a "+
        	" where t.processname = a.processname "+
        	" and a.launchtype = 0 "+
        	" and a.processname <> '拟办子流程' "+
        	" and a.processname <> '办结子流程' "+
        	" ) "+
        	" and t.status = 1 "+
        	" and t.assignedtouser like '" + loginName + "%' "+
        	" )" +
        	
        	") part "+
        	
        	" where part.pname = inci.processname "+
        	" and part.pincident = inci.incident";
		
		List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql);
		System.out.println(list.size());
		return list;
	}
	
	public static DbUtil getDbUtil() {
		return dbUtil;
	}
	
	@Autowired(required=false)
	public void setDbUtil(@Qualifier("dbUtil")DbUtil dbUtil) {
		ProcessUtil.dbUtil = dbUtil;
	}
	
	
}
