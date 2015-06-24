package com.wonders.contact.deptContact.util;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.wonders.contact.common.exception.ProcessException;
import com.wonders.contact.common.model.vo.UserInfo;
import com.wonders.contact.common.util.CommonUtil;
import com.wonders.contact.common.util.LoginUtil;
import com.wonders.contact.common.util.SimpleLogger;
import com.wonders.contact.constant.CommonFlowConstants;
import com.wonders.contact.deptContact.constant.DeptContactConstants;
import com.wonders.contact.deptContact.constant.DeptContactFlowConstants;
import com.wonders.contact.deptContact.dao.DeptContactDao;
import com.wonders.contact.deptContact.model.bo.TDeptContactTree;
import com.wonders.contact.deptContact.model.vo.DeptContactParamVo;
import com.wonders.contact.processController.constant.ProcessControllerConstants;
import com.wonders.util.DbUtil;
import com.wonders.util.PWSProperties;
import com.wonders.util.StringUtil;

/**多级工作联系单流程操作相关方法
 * @author XFZ
 * @version 1.0 2012-6-2
 */
@Component("contact-flowUtil")
public class FlowUtil {
	static SimpleLogger log = new SimpleLogger(FlowUtil.class);
	private static DeptContactDao deptContactDao;
	private static DbUtil dbUtil;
	
	/**
	 * 去除重复项
	 * @param str 字符串
	 * @param split 分隔符
	 * @return 去除重复项后的字符串
	 */
	public static String deleteMulti(String str, String split) {
		String rets = "";
		if (str != null && str.length() > 0) {
			List<String> list = new ArrayList<String>();
			String[] strs = str.split(split);
			// System.out.println(strs.length);
			for (int i = 0; i < strs.length; i++) {
				String stri = strs[i];
				if (stri != null && !"".equals(stri)) {
					if (!list.contains(stri)) {
						list.add(stri);
					}
				}
			}

			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					String ret = list.get(i);
					rets = rets + split + ret;
				}
			}
		}

		if ((!"".equals(rets)) && (rets.startsWith(split))) {
			rets = rets.substring(1, rets.length());
		}
		return rets;
	}

	/**
	 * 过滤主送部门和抄送部门重叠项及重复项
	 * @param mainDeptIds
	 * @param mainDeptNames
	 * @param copyDeptIds
	 * @param copyDeptNames
	 * @param split
	 * @return
	 */
	public static Map<String, List<String>> filterDeptInfo(String mainDeptIds, String mainDeptNames, String copyDeptIds, String copyDeptNames, String split) {
		Map<String, List<String>> retMap = new HashMap<String, List<String>>();

		String[] mIds = StringUtil.getNotNullValueString(mainDeptIds).split(split);
		String[] mNames = StringUtil.getNotNullValueString(mainDeptNames).split(split);
		String[] cIds = StringUtil.getNotNullValueString(copyDeptIds).split(split);
		String[] cNames = StringUtil.getNotNullValueString(copyDeptNames).split(split);

		List<String> mainDeptIdsList = new ArrayList<String>();
		List<String> mainDeptNamesList = new ArrayList<String>();
		List<String> copyDeptIdsList = new ArrayList<String>();
		List<String> copyDeptNamesList = new ArrayList<String>();

		
		try{
			if (StringUtil.getNotNullValueString(mainDeptIds).length() > 0) {
				for (int i = 0; i < mIds.length; i++) {
					if (!mainDeptIdsList.contains(StringUtil.getNotNullValueString(mIds[i]))) {
						mainDeptIdsList.add(StringUtil.getNotNullValueString(mIds[i]));
						mainDeptNamesList.add(StringUtil.getNotNullValueString(mNames[i]));
					}
				}
			}
	
			if (StringUtil.getNotNullValueString(copyDeptIds).length() > 0) {
				for (int i = 0; i < cIds.length; i++) {
					if (!mainDeptIdsList.contains(StringUtil.getNotNullValueString(cIds[i]))
							&& !copyDeptIdsList.contains(StringUtil.getNotNullValueString(cIds[i]))) {
						copyDeptIdsList.add(StringUtil.getNotNullValueString(cIds[i]));
						copyDeptNamesList.add(StringUtil.getNotNullValueString(cNames[i]));
					}
				}
			}
		}catch(Exception e){}
		

		retMap.put("mainDeptIdsList", mainDeptIdsList);
		retMap.put("mainDeptNamesList", mainDeptNamesList);
		retMap.put("copyDeptIdsList", copyDeptIdsList);
		retMap.put("copyDeptNamesList", copyDeptNamesList);

		return retMap;
	}

	
	
	
	/**根据步骤获得类型
	 * add:新增
	 * operate:业务处理
	 * update:修改&业务处理
	 * detail:查看
	 * @return
	 */
	public static String getTypeByInfo(DeptContactParamVo params){
		/*
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String cname = params.getProcessParamValue("cname");
		String cincident = params.getProcessParamValue("cincident");
		
		String steplabel = params.getProcessParamValue("steplabel");
		*/
		String type = "detail";
		
		if(isTypeAddOnly(params)) type = "add";
		if(isTypeOperateOnly(params)) type = "operate";
		if(isTypeUpdateAndOperate(params)) type = "update";
		
//log.debug("type="+type);
		
		return type;
	}

	/**是否跳转添加页面
	 * @param operateType 操作类型
	 * @return
	 */
	public static boolean isTypeAddOnly(DeptContactParamVo params){
		boolean flag = false;
		String steplabel = params.getProcessParamValue("steplabel");
		if(DeptContactFlowConstants.STEPNAME_BEGIN.equals(steplabel)){
			flag = true;
		}
		
		return flag;
	}
	
	/**是否跳转操作页面
	 * @param operateType 操作类型
	 * @return
	 */
	public static boolean isTypeOperateOnly(DeptContactParamVo params){
		boolean flag = false;
		
		//String cname = params.getProcessParamValue("cname");
		//String cincident = params.getProcessParamValue("cincident");
		
		String steplabel = params.getProcessParamValue("steplabel");
				
		String[] steps = DeptContactFlowConstants.TYPE_OPERATE_STEP;
		flag = CommonUtil.targetIsInArray(steplabel, steps);
		
		return flag;
	}

	/**是否跳转更新&操作页面
	 * @param operateType 操作类型
	 * @return
	 */
	public static boolean isTypeUpdateAndOperate(DeptContactParamVo params){
		boolean flag = false;
		//String cname = params.getProcessParamValue("cname");
		//String cincident = params.getProcessParamValue("cincident");
		
		String steplabel = params.getProcessParamValue("steplabel");
		
		String[] steps = DeptContactFlowConstants.TYPE_OPERATE_UPDATE_STEP;
		
		flag = CommonUtil.targetIsInArray(steplabel, steps);
		
		return flag;
	}
	
	
	
	
	
	
	/**检查流程操作
	 * @param processname
	 * @param incidentNo
	 * @param steplabel
	 * @param userInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean checkActiveProcess(String processname, String incidentNo, String steplabel,UserInfo userInfo) {
		List list = getActiveProcess(processname,incidentNo,steplabel,userInfo);
		
		if(list.size()>0){
			return true;
		}else{
			return false;
		}
	}

	/**根据用户信息获得待办事项
	 * @param userVo
	 * @return
	 */
	public static List<Map<String,Object>> getActiveProcess(UserInfo userInfo){
		return 	getActiveProcess("","","",userInfo);
	}
	
	/**获得待办事项
	 * @param processname
	 * @param incidentNo
	 * @param steplabel
	 * @param userVo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String,Object>> getActiveProcess(String processname, String incidentNo, String steplabel,UserInfo userInfo){
		processname = StringUtil.getNotNullValueString(processname);
		incidentNo = StringUtil.getNotNullValueString(incidentNo);
		steplabel = StringUtil.getNotNullValueString(steplabel);
		
		String sql = " select main.*,tree.pname,tree.pincident,tree.cname,tree.cincident,t.steplabel," +
				" t.assignedtouser as taskuser from \n"+
					" tasks t,\n"+
					" (select td.pname,td.pincident,td.cname,td.cincident,td.status from t_dept_contact_tree td \n"+
					" where td.cname=? and td.removed!=1\n"+
					" union\n"+
					" select td.cname,td.cincident,td.cname,td.cincident,td.status from t_dept_contact_tree td \n"+
					" where td.cname=? and td.removed!=1\n"+
					" ) tree,\n"+
					" t_dept_contact_main main,incidents i \n"+
					" where 1=1 \n" +
					" and i.processname = tree.pname and i.incident = tree.pincident \n"+
					" and i.status = ? and t.status = ? and tree.status = ?\n"+
					" and t.processname = tree.cname and t.incident = tree.cincident\n"+
					" and tree.pname = main.processname\n"+
					" and tree.pincident = main.incidentno\n"+
					" and t.assignedtouser = ?\n" +
					" and t.steplabel != '"+DeptContactFlowConstants.STEPNAME_LOWER+"'"+
					//" and t.helpurl like ?\n"+
					(processname.length()>0?" and t.processname = ?\n":"") +
					(incidentNo.length()>0?" and t.incident = ?\n":"") +
					(steplabel.length()>0?" and t.steplabel = ?\n":"") +
					" order by t.starttime desc";
		
		List<String> paramsList = new ArrayList<String>();
		
		String loginName = userInfo.getLoginName();
		//String deptInfo = "%"+loginName +":"+userVo.deptId+"<+>%";
		
//log.debug(loginName+" "+deptInfo+" "+processname+" "+incidentNo+" "+steplabel);
		
		paramsList.add(DeptContactConstants.SUBPROCESSNAME);
		paramsList.add(DeptContactConstants.PROCESSNAME);
		paramsList.add(CommonFlowConstants.STATUS_INCIDENTS_ACTIVE);
		paramsList.add(CommonFlowConstants.STATUS_TASKS_ACTIVE);
		paramsList.add(String.valueOf(ProcessControllerConstants.STATUS_ACTIVE));
		paramsList.add(loginName);
		
		if(processname.length()>0) paramsList.add(processname);
		if(incidentNo.length()>0) paramsList.add(incidentNo);
		if(steplabel.length()>0) paramsList.add(steplabel);
		
		Object[] params = paramsList.toArray();
//log.debug(sql);
//log.debug(params.length);

		List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql,params);
		
		return list;
	}
	
	
	/**获得待办事项
	 * @param processname
	 * @param incidentNo
	 * @param steplabel
	 * @param userVo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String,Object>> getActiveProcesses(String processname, String incidentNo, String steplabel,UserInfo userInfo){
		processname = StringUtil.getNotNullValueString(processname);
		incidentNo = StringUtil.getNotNullValueString(incidentNo);
		steplabel = StringUtil.getNotNullValueString(steplabel);
		
		String sql = " select main.*,tree.pname,tree.pincident,tree.cname,tree.cincident,t.steplabel," +
				" t.assignedtouser as taskuser from \n"+
					" tasks t,\n"+
					" (select td.pname,td.pincident,td.cname,td.cincident,td.status from t_dept_contact_tree td \n"+
					" where td.cname=? and td.removed!=1\n"+
					" union\n"+
					" select td.cname,td.cincident,td.cname,td.cincident,td.status from t_dept_contact_tree td \n"+
					" where td.cname=? and td.removed!=1\n"+
					" ) tree,\n"+
					" t_dept_contact_main main,incidents i \n"+
					" where 1=1 \n" +
					" and i.processname = tree.pname and i.incident = tree.pincident \n"+
					" and i.status = ? and t.status = ? and tree.status = ?\n"+
					" and t.processname = tree.cname and t.incident = tree.cincident\n"+
					" and tree.pname = main.processname\n"+
					" and tree.pincident = main.incidentno\n"+
					" and t.assignedtouser like ?\n" +
					" and t.steplabel != '"+DeptContactFlowConstants.STEPNAME_LOWER+"'"+
					//" and t.helpurl like ?\n"+
					(processname.length()>0?" and t.processname = ?\n":"") +
					(incidentNo.length()>0?" and t.incident = ?\n":"") +
					(steplabel.length()>0?" and t.steplabel = ?\n":"") +
					" order by t.starttime desc";
		
		List<String> paramsList = new ArrayList<String>();
		
		String loginName = userInfo.gettLoginName();
		//String deptInfo = "%"+loginName +":"+userVo.deptId+"<+>%";
		
//log.debug(loginName+" "+deptInfo+" "+processname+" "+incidentNo+" "+steplabel);
		
		paramsList.add(DeptContactConstants.SUBPROCESSNAME);
		paramsList.add(DeptContactConstants.PROCESSNAME);
		paramsList.add(CommonFlowConstants.STATUS_INCIDENTS_ACTIVE);
		paramsList.add(CommonFlowConstants.STATUS_TASKS_ACTIVE);
		paramsList.add(String.valueOf(ProcessControllerConstants.STATUS_ACTIVE));
		paramsList.add(""+loginName+"%");
		
		if(processname.length()>0) paramsList.add(processname);
		if(incidentNo.length()>0) paramsList.add(incidentNo);
		if(steplabel.length()>0) paramsList.add(steplabel);
		
		Object[] params = paramsList.toArray();
//log.debug(sql);
//log.debug(params.length);

		List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql,params);
		
		return list;
	}
	
	
	
	/**获得已办事项
	 * @param processname
	 * @param incidentNo
	 * @param steplabel
	 * @param userVo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String,Object>> getFinishProcess(UserInfo userInfo){
		//processname = StringUtil.getNotNullValueString(processname);
		//incidentNo = StringUtil.getNotNullValueString(incidentNo);
		//steplabel = StringUtil.getNotNullValueString(steplabel);
		
		String sql = "select distinct main.* " +
					" from " +
					" (select tree.pname,tree.pincident,tree.cname,tree.cincident from t_dept_contact_tree tree " +
					" where tree.cname=? and tree.removed!=1" +
					" union" +
					" select tree.cname,tree.cincident,tree.cname,tree.cincident from t_dept_contact_tree tree " +
					" where tree.cname=? and tree.removed!=1) tree" +
					" ,t_dept_contact_main main" +
					" where 1=1 " +
					" and tree.pname = main.processname" +
					" and tree.pincident = main.incidentno" +
					" and exists (select '' from tasks t,incidents i " +
					" where t.processname=tree.cname and t.incident = tree.cincident" +
					" and t.processname = i.processname and t.incident = i.incident" +
					" and t.status = ?" +
					" and t.assignedtouser = ?" +
					" and t.steplabel != ?" +
					//" and t.steplabel != ?" +
					" )" +
					" order by main.start_time desc";
		
		List<String> paramsList = new ArrayList<String>();
		
		String loginName = userInfo.getLoginName();
		//String deptInfo = "%"+loginName +":"+userVo.deptId+"<+>%";
		
//log.debug(loginName+" "+deptInfo+" "+processname+" "+incidentNo+" "+steplabel);
		
		paramsList.add(DeptContactConstants.SUBPROCESSNAME);
		paramsList.add(DeptContactConstants.PROCESSNAME);
		//paramsList.add(CommonFlowConstants.STATUS_INCIDENTS_FINISH);
		paramsList.add(CommonFlowConstants.STATUS_TASKS_FINISH);
		paramsList.add(loginName);
		paramsList.add(DeptContactFlowConstants.STEPNAME_LOWER);
		//paramsList.add("Begin");
		
		//if(processname.length()>0) paramsList.add(processname);
		//if(incidentNo.length()>0) paramsList.add(incidentNo);
		//if(steplabel.length()>0) paramsList.add(steplabel);
		
		Object[] params = paramsList.toArray();
//log.debug(sql);
//log.debug(params.length);
		
		List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql,params);
		
		return list;
	}
	
	
	/**已办事项查询
	 * @param userInfo
	 * @param whereStr
	 * @return
	 */
	public static String getDoneSql(UserInfo userInfo,String whereStr){
		String loginName = LoginUtil.getUserLoginName(userInfo.getLoginName());
		
		String sql = getQueryListSql(whereStr," and t.assignedtouser = '"+loginName+"'");
		
		return sql;
	}
	
	
	/**查询列表（全部）
	 * @param whereStr
	 * @param taskWhereStr
	 * @return
	 */
	public static String getQueryListSql(String whereStr,String taskWhereStr){		
		String sql = 
				 " select main.id,main.main_unit,main.copy_unit,main.contact_date,main.reply_date,main.theme,main.initiator_name,main.create_deptname,tree.status " +
				 " from t_dept_contact_main main,t_dept_contact_tree tree ,incidents i" +
				 " where " +
				 " main.processname=i.processname and main.incidentno = i.incident" +
				 " and exists (" +
				 " select '' from " +
				 " (select tree.pname,tree.pincident,tree.cname,tree.cincident from t_dept_contact_tree tree " +
				 " where tree.cname='"+DeptContactConstants.SUBPROCESSNAME+"' and tree.removed!=1" +
				 " union" +
				 " select tree.cname,tree.cincident,tree.cname,tree.cincident from t_dept_contact_tree tree " +
				 " where tree.cname='"+DeptContactConstants.PROCESSNAME+"' and tree.removed!=1) tree" +
				 " where 1=1 " +
				 " and exists (select '' from tasks t " +
				 " where t.processname=tree.cname and t.incident = tree.cincident" +
				 " and t.status = '"+CommonFlowConstants.STATUS_TASKS_FINISH+"'" +
				 //" and t.assignedtouser = '"+loginName+"'" +
				 taskWhereStr +
				 " and t.steplabel != '"+DeptContactFlowConstants.STEPNAME_LOWER+"'" +
				 " and tree.pname = i.processname" +
				 " and tree.pincident = i.incident" +
				 " )" +
				 " )" +
				 " and tree.cname = main.processname" +
				 " and tree.cincident = main.incidentno" +
				 " and tree.removed != 1" +
				 " and main.removed != 1" +
				 " " + whereStr +
				 " order by main.start_time desc";
		
		return sql;
	}
	
	
	
	/**根据KEY获得同级流程状态信息
	 * @param key tree表key
	 * @param filterStr 过滤条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String,Object>> getStatusInfoByProcess(String key,String filterStr) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		String sql = "select t.id,t.p_id,t.c_id,t.cname,t.cincident,to_char(t.type) as type, "+
					" to_char(t.status) as status,t.main_unit_id from t_dept_contact_tree t "+
					" where "+
					" exists(select '' from t_dept_contact_tree tree "+
					" where tree.id = ? "+
					" and tree.cname=t.pname and tree.cincident=t.pincident)" +
					(filterStr.length()>0?filterStr:"");
		
		list = dbUtil.getJdbcTemplate().queryForList(sql,new Object[]{key});
		
		return list;
	}
	
	
	
	
	

	/**根据t_subprocess关联表获得子流程上级流程对应mainBo主键ID
	 * @param processname
	 * @param incident
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getMainBoIdBySubProcessInfo(String processname,String incident){
		
		processname = StringUtil.getNotNullValueString(processname);
		incident = StringUtil.getNotNullValueString(incident);
		
		if(processname.length()==0||incident.length()==0){
			throw new ProcessException("子流程信息错误！");
		}
		
		String sql = "select main.id from t_subprocess t,t_dept_contact_main main where " +
					" t.cname=? and t.cincident=?" +
					" and main.processname = t.pname and main.incidentno = t.pincident" +
					" and t.pname != t.cname and t.pname is not null and t.pincident is not null";

		Object[] params = new Object[]{processname,incident};
		
		List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql, params);
		
		String pid = "";
		
		if(list.size()>0){
			pid = StringUtil.getNotNullValueString(list.get(0).get("id"));
			log.debug("找到Pid:"+pid);
			return pid;
		}
		
		if(pid.length()==0){
			throw new ProcessException("找不到pid！");
		}
		
		return null;
	}
	
	
	/**从incidents表获得流程summary
	 * @param processname
	 * @param incident
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getSummaryByProcessInfo(String processname,String incident){
		if(processname.length()==0||incident.length()==0){
			throw new ProcessException("子流程信息错误！");
		}
		String summary = "";
		
		String sql = "select i.summary from incidents i where i.processname=? and i.incident=?";
		Object[] params = new Object[]{processname,incident};
		
		List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql, params);
		
		if(list.size()>0){
			summary = StringUtil.getNotNullValueString(list.get(0).get("summary"));
			return summary;
		}
		
		if(summary.length()==0){
			throw new ProcessException("summary为空！");
		}
		
		
		return summary;
	}
	
	
	/**获得流程监控所需taskId
	 * @param processname
	 * @param incident
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getTaskIdByProcessInfo(String processname,String incident){
		if(processname.length()==0||incident.length()==0){
			throw new ProcessException("子流程信息错误！");
		}
		String summary = "";
		
		String sql = "select t.taskid from tasks t where t.steplabel='Begin' and t.processname=? and t.incident=?";
		Object[] params = new Object[]{processname,incident};
		
		List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql, params);
		
		if(list.size()>0){
			summary = StringUtil.getNotNullValueString(list.get(0).get("taskid"));
			return summary;
		}
		
		if(summary.length()==0){
			throw new ProcessException("summary为空！");
		}
		
		return summary;
	}
	
	
	
	/**输出流程监控流
	 * @param processname
	 * @param incidentno
	 * @param aw
	 */
	public static void writeScanPicture(String processname,String incidentno,ActionWriter aw){
		String ultimusIp = StringUtil.getNotNullValueString(PWSProperties.getValueByKey("pws_server_ip"));
		if(ultimusIp.length()==0) return;
		
		String taskId = getTaskIdByProcessInfo(processname,incidentno);
		String urlStr = "http://"+ultimusIp+"/sLogin/workflow/GraphicalView.aspx?TaskID="+taskId;
		try{
			URL url = new URL(urlStr);
	        //载入图片到输入流
	        java.io.BufferedInputStream bis = new BufferedInputStream(url.openStream());
	        aw.writeJpeg(bis);
		}catch(Exception e){
			e.printStackTrace();
			aw.writeAjax("图片读取出错！");
		}
	}
	
	/**输出流程监控流
	 * @param cid
	 * @param aw
	 */
	public static void writeScanPicture(String cid,ActionWriter aw){
		String ultimusIp = StringUtil.getNotNullValueString(PWSProperties.getValueByKey("pws_server_ip"));
		if(ultimusIp.length()==0) return;
		
		TDeptContactTree bo = deptContactDao.getTreeBoByMainBoId(cid);
		
		writeScanPicture(bo.getCname(),bo.getCincident(),aw);
	}
	
	/**Ultimus多值字段赋值
	 * @param key
	 * @param key_rest
	 * @param values
	 * @param paramMap
	 */
	@SuppressWarnings("unchecked")
	public static void setMultiValueForUltimus(String key,String key_rest,List<String> values,Map paramMap){
		if(values.size()>0){
			paramMap.put(key, StringUtil.getNotNullValueString(values.get(0)));
		}
		
		if(values.size()>1){
			for(int i=1;i<values.size();i++){
				paramMap.put(key_rest, StringUtil.getNotNullValueString(values.get(i)));
			}
		}
	}

	
	public static List<String> getUltimusListInfo(List<String> src){
		//List<String> src = (List<String>) obj;
		List<String> target = new ArrayList<String>();
		if(src != null && src.size()>0){
			target.addAll(src);
			if(target.size() < 15){
				for(int i = 0 ; i<(15-target.size());i++){
					target.add("");
				}
			}
		}else{
			for(int i = 0; i< 15; i++){
				target.add("");
			}
		}
		return target;
	}
	
	public static List<String> getUltimusDeptListInfo(List<String> src){
		//List<String> src = (List<String>) obj;
		List<String> target = new ArrayList<String>();
		if(src != null && src.size()>0){
			target.addAll(src);
		}
		return target;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void putUltimusMap(String code ,List<String> list,Map map){
		List<String> newList = getUltimusListInfo(list);
		map.put(code , newList);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void putUltimusDeptMap(String code ,List<String> list,Map map){
		if(list != null && list.size() > 0){
			List<String> newList = getUltimusDeptListInfo(list);
			map.put(code , newList);
		}else{
			
		}
	}
	
	
	/* */
	public static DbUtil getDbUtil() {
		return FlowUtil.dbUtil;
	}
	
	
	@Autowired(required=false)
	public void setDbUtil(@Qualifier("dbUtil")DbUtil dbUtil) {
		FlowUtil.dbUtil = dbUtil;
	}

	public static DeptContactDao getDeptContactDao() {
		return deptContactDao;
	}	
	
	@Autowired
	public void setDeptContactDao(@Qualifier(value="contact-deptContactDao")DeptContactDao deptContactDao) {
		FlowUtil.deptContactDao = deptContactDao;
	}
}
