package com.wonders.deptDoc.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.wonders.util.DbUtil;
import com.wonders.util.StringUtil;


/**流程操作相关方法
 * @author zhoushun
 * @version 1.0 2014-10-18
 */
@Component("deptDocUtil")
public class DeptDocUtil {	
	private static DbUtil dbUtil;
	
	public static DbUtil getDbUtil() {
		return dbUtil;
	}
	
	@Autowired(required=false)
	public void setDbUtil(@Qualifier("dbUtil")DbUtil dbUtil) {
		DeptDocUtil.dbUtil = dbUtil;
	}
	
	public static Map<String,Set<String>> getDeptAndPerosn(String process,String incident){
		String sql = "select distinct a.dept_id deptId,a.username loginName"
				+ " from t_approvedinfo a where a.process = ? and a.incidentno = ?";
		List<Map<String,Object>> result = dbUtil.getJdbcTemplate().
				queryForList(sql, new Object[]{process,incident});
		Map<String,Set<String>> map = new HashMap<String,Set<String>>();
		String deptId = "";
		String loginName = "";
		for(Map<String,Object> temp : result){
			deptId = StringUtil.getNotNullValueString(temp.get("deptId"));
			loginName = StringUtil.getNotNullValueString(temp.get("loginName"));
			if(!map.containsKey(temp.get("deptId"))){
				Set<String> set = new HashSet<String>();
				set.add(loginName);
				map.put(deptId, set);
			}else{
				map.get(deptId).add(loginName);
			}
		}
		return map;
	}
	
	public static Map<String,String> getDeptCode(){
		String sql = "select t.id id,t.new_dept_id deptId from t_dept_code t where t.removed = 0";
		List<Map<String,Object>> result = dbUtil.getJdbcTemplate().
				queryForList(sql);
		Map<String,String> map = new HashMap<String,String>();
		for(Map<String,Object> temp : result){
			map.put(StringUtil.getNotNullValueString(temp.get("deptId")), 
					StringUtil.getNotNullValueString(temp.get("id")));
		}
		return map;
	}
	
	public static Map<String,String> getCatelogId(List<String> l){
		Map<String,String> map = new HashMap<String,String>();
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = null;  
		//namedParameterJdbcTemplate =  
//		    new NamedParameterJdbcTemplate(dataSource);  
		namedParameterJdbcTemplate =  
		new NamedParameterJdbcTemplate(dbUtil.getJdbcTemplate());  
		Map<String, Object> paramMap = new HashMap<String, Object>();  
		paramMap.put("deptIds", l);  
		String sql = "select z.dept_id deptId,z.sid from z_docs_catalog z "
				+ "where z.state = 1 and z.flag = '000'and z.dept_id in (:deptIds)";
		List<Map<String,Object>> result = namedParameterJdbcTemplate.queryForList(sql, paramMap);
		for(Map<String,Object> temp : result){
			map.put(StringUtil.getNotNullValueString(temp.get("deptId")), 
					StringUtil.getNotNullValueString(temp.get("sid")));
		}
		return map;
	}
	
	public static String getFileId(Object[] o){
		String sql = "select f.sid from z_docs_file f,"
				+ " z_docs_catalog c where c.sid = f.parent_sid and c.state='1'"
				+ " and f.state='1' and c.flag='000' "
				+ " and c.dept_id = ? and f.ref_id = ?";
		
		String result = null;
		try{
			result = StringUtil.getNotNullValueString(
			dbUtil.getJdbcTemplate().queryForObject(sql, o, String.class));
		}catch(Exception e){
			
		}
		return result;
	}
	
	public static boolean isExist(String loginName,String fileId){
		String sql = "select count(*) from z_docs_right z where z.empid = ? and z.rightid = ?";
		int result = 0;
		try{
			result = dbUtil.getJdbcTemplate().queryForObject(sql,
					new Object[]{loginName,fileId},Integer.class);
		}catch(Exception e){
			
		}
				
		if(result > 0){
			return true;
		}
		return false;
	}
	
//	public static void main(String[] args){
//		ApplicationContext applicationContext = null;  
//		String[] fileUrl = new String[]{"classpath*:*Context*.xml"};  
//		applicationContext = new ClassPathXmlApplicationContext(fileUrl);  
//		List<String> l = new ArrayList<String>();
//		l.add("2549");l.add("2959");
//		NamedParameterJdbcTemplate namedParameterJdbcTemplate = null;  
//		//namedParameterJdbcTemplate =  
////		    new NamedParameterJdbcTemplate(dataSource);  
//		namedParameterJdbcTemplate =  
//		new NamedParameterJdbcTemplate(dbUtil.getJdbcTemplate());  
//		  Map<String, Object> paramMap = new HashMap<String, Object>();  
//		    paramMap.put("deptIds", l);  
//		String sql = "select z.dept_id,z.sid from z_docs_catalog z "
//				+ "where z.state = 1 and z.flag = '000'and z.dept_id in (:deptIds)";
//		List<Map<String,Object>> result = namedParameterJdbcTemplate.queryForList(sql, paramMap);
//		
//		System.out.println(result.size());
//	}
}
