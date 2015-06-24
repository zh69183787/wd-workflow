package com.wonders.contract.workflow.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.wonders.contract.workflow.common.exception.ProcessException;
import com.wonders.util.DateUtil;
import com.wonders.util.DbUtil;
import com.wonders.util.StringUtil;





/**流程操作相关方法
 * @author zhoushun
 * @version 1.0 2013-3-2
 */
@Component("contract-flowUtil")
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
	
	
	@SuppressWarnings("unchecked")
	public static String getLoginNameLink(Object o,String deptId){
		String result = "";
		if(o instanceof List){
			for(String temp : (List<String>)o){
				result += temp + ":" + deptId +"<+>";
			}
		}else{
			result += o.toString() + ":" +deptId +"<+>";
		}
		
		return result;
	}
	
	public static String getLoginNameLink(List<String> userId,List<String> deptId){
		String result = "";
		if(userId != null && deptId!=null && userId.size() > 0 && userId.size() == deptId.size()){
			for(int i=0;i<userId.size();i++){
				result += userId.get(i)+":"+deptId.get(i)+"<+>";
			}
		}
		return result;
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
	
	public static List<Map<String,Object>> getTypeByCode(String code){
		String sql = "select id,name from t_contract_review_code t "
				+ " where t.removed=0 and t.code = ? order by t.orders";
		return dbUtil.getJdbcTemplate().queryForList(sql, new Object[]{code});
	}
	
//	public static List<Map<String,Object>> getTypeByPid(String pid){
//		String sql = "select id,name from t_contract_review_code t "
//				+ " where t.removed=0 and t.p_id = ? order by t.orders";
//		return dbUtil.getJdbcTemplate().queryForList(sql, new Object[]{pid});
//	}

    public static List<Map<String,Object>> getTypeByPid(String pid){
        String sql = "select id,name from t_contract_review_code t "
                + " where t.removed=0 and t.p_id = ? and t.code = ? order by t.orders";
        return dbUtil.getJdbcTemplate().queryForList(sql, new Object[]{pid,"type2"});
    }

    public static List<Map<String,Object>> getTypeByName(String code,String name){
        String parameter = "";
        ArrayList list = new ArrayList();
        if(StringUtils.isNotBlank(name)){
            parameter += "and t.name = ? ";
            list.add(name);
        }
        if(StringUtils.isNotBlank(code)){
            parameter +="and t.code = ? ";
            list.add(code);
        }
        String sql = "select id,name from t_contract_review_code t "
                + " where t.removed=0 "+parameter+" order by t.orders";
        return dbUtil.getJdbcTemplate().queryForList(sql, list.toArray());
    }

    public static List<Map<String,Object>> getTypeByPid(String pid,String code){
        String parameter = "";
        ArrayList list = new ArrayList();
        if(StringUtils.isNotBlank(pid)){
            parameter += "and t.p_id = ? ";
            list.add(pid);
        }else{
            parameter += "and t.p_id is null ";
        }
        if(StringUtils.isNotBlank(code)){
            parameter +="and t.code = ? ";
            list.add(code);
        }
        String sql = "select id,name from t_contract_review_code t "
                + " where t.removed=0 "+parameter+" order by t.orders";
        return dbUtil.getJdbcTemplate().queryForList(sql, list.toArray());
    }
	
	public static List<Map<String,Object>> getProjectInfo(String keywords,String maxrows){
		String sql = "select * from t_contract_review_project t "
				+ " where t.removed=0 and t.project_name like ? escape '/' and rownum <= ?";
		return dbUtil.getJdbcTemplate().queryForList(sql, new Object[]{"%"+keywords.replaceAll("%","/%").replaceAll("_","/_")+"%",maxrows});
	}
	
	public static List<Map<String,Object>> getKPIInfo(){
		String sql = "select id,name from c_contract_kpi t order by orders";
		return dbUtil.getJdbcTemplate().queryForList(sql);
	}
	
	
	public static List<Map<String,Object>> getCodeInfo(String typeCode,String code){
		String sql = "select t.id,t.code,t.name,t.description" +
				" from cf_code_info t where t.removed = 0 and t.type_id = (select a.id" +
				" from cf_code_type a where a.removed = 0 and a.code = ?)" +
				" and t.code_info_id = (select c.id from cf_code_info c where c.removed = 0" +
				" and c.code = ? and c.type_id = (select b.id from cf_code_type b" +
				" where b.removed = 0 and b.code = ?)) ";
		Object[] params = null;
		params = new Object[]{typeCode,code,typeCode};
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
	
	public static int isExistsSelfNum(String selfNum){
		int count = 0;
		String sql = "select count(id) from t_contract_review t "
				+ " where t.contract_self_num = ? and t.removed='0'";
		count = dbUtil.getJdbcTemplate().queryForInt(sql,new Object[]{selfNum});
		
		return count;
	}
	
	public static String generateNo(String userName,String contractName,String projectNo,String subProjectNo,
						String signCompany,String line,
						String division,String section,
						String execCompany,String year){
		String countSql = "select nvl(max(t.serial_no),0) from "
				+ " t_contract_num t where t.removed = 0 and t.exec_company = ?"
				+ " and t.year = ? ";
		String insertSql = "insert into t_contract_num " +
				" (id,contract_name,exec_company,year,serial_no,contract_num,"
				+ " operator,operate_time,removed) " +
				" values(sys_guid(),?,?,?,?,?,?,?,'0') ";
		Object[] countObject = new Object[]{execCompany,year};
		try{
			int count = dbUtil.getJdbcTemplate().queryForInt(countSql,countObject);
			Object[] insertObject = new Object[]{contractName,execCompany,year,(count+1),
					projectNo+"-"+subProjectNo+"-"+signCompany+"-"+line+"-"+
					division+"-"+
					section+"-"+year+"-"+(count+1)+"-"+execCompany,userName,
					DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss")};
			int result = dbUtil.getJdbcTemplate().update(insertSql, insertObject);
			if(result > 0){
				return projectNo+"-"+subProjectNo+"-"+signCompany+"-"+line+"-"+
				division+"-"+section+"-"+
				year+"-"+new DecimalFormat("0000").format((count+1))+"-"+execCompany;
			}
		}catch(Exception e){
			e.printStackTrace();
		};
		return "";
		
	}



    public static String getSequence(String year,String exeBody){
        String countSql = "select nvl(max(t.serial_no),0) from "
                + " t_contract_num t where t.removed = 0 and t.exec_company = ?"
                + " and t.year = ? ";
        Object[] countObject = new Object[]{exeBody,year};
        int count = dbUtil.getJdbcTemplate().queryForInt(countSql,countObject);
        return new DecimalFormat("0000").format((count+1));
    }

	
	public static DbUtil getDbUtil() {
		return dbUtil;
	}
	
	@Autowired(required=false)
	public void setDbUtil(@Qualifier("dbUtil")DbUtil dbUtil) {
		FlowUtil.dbUtil = dbUtil;
	}
	
}
