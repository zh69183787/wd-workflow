package com.wonders.receive.workflow.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.wonders.receive.workflow.common.exception.ProcessException;
import com.wonders.util.DbUtil;
import com.wonders.util.StringUtil;





/**流程操作相关方法
 * @author zhoushun
 * @version 1.0 2013-3-2
 */
@Component("receive-flowUtil")
public class FlowUtil {
	
	private static DbUtil dbUtil;
	
	
	/**从incidents表获得流程summary
	 * @param processname
	 * @param incident
	 * @return
	 */
	public static String getSummaryByProcessInfo(String processname,String incident){
		if(processname.length() == 0 ||incident.length() == 0 ){
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
	
	/** 
	* @Title: getNodeUsersByConfig 
	* @Description: 根据相关属性得到配置表中节点处理人
	* @param @param processName
	* @param @param incidentNo
	* @param @param stepName
	* @param @param typeId
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	public static Map<String,Object> getNodeUsersByConfig(String processName, String stepName,String typeId){
		Map<String,Object> map = new HashMap<String,Object>();
		String sql = "select 'ST/'||username username,userfullname,parent_dept from t_flowsetp_config where processname=?" +
				"and stepname = ? and typeid = ?";
		Object[] params = new Object[]{processName,stepName,typeId};
		
		List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql, params);
		if(list.size()>0){
			map = list.get(0);
			return map;
		}
		return map;
	}
	
	/**
	 * 
	 * @param codeType_code  	cf_code_type中的code
	 * @param code				cf_code_info中的code
	 * @return					通过code 找到code名称
	 */
	public static String getCodeName(String codeType_code,String code){
		String codeName = "";
		String sql ="select t.name from cf_code_info t where t.type_id = (select c.id from cf_code_type c where c.removed = 0 and  c.code = ? ) and t.code = ? and t.removed = 0";
		Object[] params = new Object[]{codeType_code,code};
		
		List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql, params);
		if(list.size()>0){
			codeName = StringUtil.getNotNullValueString(list.get(0).get("codeName"));
			return codeName;
		}
		return codeName;
	}
	
	/**
	 * 
	 * @param codeType_code  	cf_code_type中的code
	 * @param code				cf_code_info中的code
	 * @return					通过code 找到code名称
	 */
	public static String getCodeByName(String codeType_code,String name){
		String codeName = "";
		String sql ="select c.description from cf_code_info c, cf_code_type t " +
				" where t.code=? and t.removed=0 and c.removed=0" +
				" and t.id=c.type_id and c.name=?";
		Object[] params = new Object[]{codeType_code,name};
		
		List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql, params);
		if(list.size()>0){
			codeName = StringUtil.getNotNullValueString(list.get(0).get("description"));
			return codeName;
		}
		return codeName;
	}
	
	
	
	public static List<Map<String,Object>> getCodeNameList(String codeType_code,String code,String name){
		String sql = "select t.code, t.name,t.description" +
				" from cf_code_info t where t.removed = 0 and t.type_id = (select a.id" +
				" from cf_code_type a where a.removed = 0 and a.code = ?)" +
				" and t.code_info_id = (select c.id from cf_code_info c where c.removed = 0" +
				" and c.code = ? and c.type_id = (select b.id from cf_code_type b" +
				" where b.removed = 0 and b.code = ?)) ";
		Object[] params = null;
		if(!"".equals(StringUtil.getNotNullValueString(name))){
			sql += " and t.name like ?";
			params = new Object[]{codeType_code,code,codeType_code,"%"+name+"%"};
		}else{
			params = new Object[]{codeType_code,code,codeType_code};
		}
		sql += " order by t.disp_order";
		
		List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql, params);
		if(list != null && list.size() > 0){
			for(Map<String,Object> map : list){
					for(Map.Entry<String, Object> e : map.entrySet()){
						map.put(e.getKey(), StringUtil.getNotNullValueString(e.getValue()));
					}
			}
		}
		return list;
		
	}
	
	
	public static List<String> getStatusName(String code){
		String sql ="select l.content from t_list_status l " +
				" where l.infotype= ? and state=1 order by l.optorder";
		Object[] params = new Object[]{code};
		
		List<String> list = dbUtil.getJdbcTemplate().queryForList(sql, String.class, params);
		return list;
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
	
	
	/**
	 * 按给定条件在流程步骤配置表中查找部门ids信息
	 * @author liu_feng
	 * @param processName - 流程名称
	 * @param stepName - 步骤名称
	 * @param typeId - 业务分类ID
	 * @param loginName - 登录名

	 * @return 部门ids - deptId1,deptId2,...deptIdn
	 * @throws HibernateException
	 */
	public static String findDeptIdsFromFC(String processName, String stepName, String typeId, String loginName ) {
		String deptId = "";
		String sql = "select distinct fc.dept" +
			" from t_flowsetp_config fc" +
			" where fc.processname = ? and fc.stepname = ?" +
			" and fc.typeid = ? and and fc.username = ?";		
		Object[] params = new Object[]{processName,stepName,typeId,loginName};		
		List<String> list = dbUtil.getJdbcTemplate().queryForList(sql, String.class, params);
		if(list !=null && list.size() > 0){
			deptId = list.get(0);
		}
		return deptId;	
	}
	

	//判断部门为 单位 或者 部门
	public static boolean judgeDeptInfo(String deptId){
		//在流程步骤配置表中查找部门ids
		String deps = findDeptIdsFromFC("new_processes", "new_dept", "0", "ADMIN");
		if(deptId!=null&&deps.indexOf(deptId)>-1){
			return true;
		}
		return false;
	}
	
	
	/**检查流程操作
	 * @param processname
	 * @param incidentNo
	 * @param steplabel
	 * @param userInfo
	 * @return
	 */
	public static boolean checkActiveProcess(String processname, String incidentNo, String steplabel,String taskuser) {
		List<Map<String,Object>> list = getActiveProcess(processname,incidentNo,steplabel,taskuser);
		
		if(list.size()>0){
			return true;
		}else{
			return false;
		}
	}

	//流程待办项
	public static List<Map<String,Object>> getActiveProcess(String processname, String incidentNo, String steplabel,String taskuser){
		String sql =
			"select * from tasks t , incidents i " +
			" where t.processname=i.processname" +
			" and t.incident=i.incident and " +
			" t.status=1 and i.status=1 and"+
			" t.steplabel=? and"+
			" i.processname = ? and"+
			" i.incident = ? and "+
			" t.assignedtouser = ?";
		//System.out.println(sql);
		Object[] params = new Object[]{steplabel,processname,incidentNo,taskuser};	
		List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql,params);
		//System.out.println(list.size());
		return list;
	}
	
	public static Map<String,Object> getSimulateFuture(String pname,String pincident){
		Map<String,Object> map = new HashMap<String,Object>();
		String sql = "select * from t_simulate_future t " +
				" where pname = ? and pincident = ? " +
				" order by t.updatetime desc";
		Object[] params = new Object[]{pname,pincident};
		List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql, params);
		if(list != null && list.size() > 0){
			map = list.get(0);
		}
		return map;
	}
	
	
	public static Map<String,Object> getUserApproved(String pname,String pincident,String stepname){
		Map<String,Object> map = new HashMap<String,Object>();
		String sql = "select a.* from t_approvedinfo a " +
				" where a.process= ? and a.incidentno = ?" +
				" and a.stepname = ? and a.status=1 order by a.upddate desc ";
		Object[] params = new Object[]{pname,pincident,stepname};
		List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql, params);
		if(list != null && list.size() > 0){
			map = list.get(0);
		}
		return map;
	}
	
	public static int getAttachCount(String groupid){
		int count = 0;
		String sql = "select count(*) as count from t_attach a where a.groupid=? and a.removed=0";
		Object[] params = new Object[]{groupid};
		count = dbUtil.getJdbcTemplate().queryForInt(sql, params);
		
		return count;
	}
	
	public static List<Integer> getMaxRound(String pname,String pincident){
		String sql = "select distinct t.rounds from t_approvedinfo t " +
				" where t.process = ? and t.incidentno = ? and t.status = 1" +
				" and t.rounds is not null";
		Object[] params = new Object[]{pname,pincident};
		List<Integer> list = dbUtil.getJdbcTemplate().queryForList(sql, Integer.class,params);
		return list;
	}
	
	public static DbUtil getDbUtil() {
		return dbUtil;
	}
	
	@Autowired(required=false)
	public void setDbUtil(@Qualifier("dbUtil")DbUtil dbUtil) {
		FlowUtil.dbUtil = dbUtil;
	}
	
	
}
