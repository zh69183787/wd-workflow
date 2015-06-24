package com.wonders.receive.oa.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.wonders.util.DbUtil;
import com.wonders.util.StringUtil;




/**流程操作相关方法
 * @author zhoushun
 * @version 1.0 2013-3-2
 */
@Component("oaUtil")
public class OaUtil {
	
	private static DbUtil dbUtil;
	
	public static Long getOaUserId(String loginName){
		Long user = 0L;
		if(loginName.length() > 12){
			String sql = "select distinct id from cs_user c " +
					" where c.login_name ='"+loginName.substring(0,12)+"' and c.removed=0";
			try {
				user = dbUtil.getJdbcTemplate().queryForObject(sql, Long.class);
			}catch(Exception e){
				user = 0L;
			}
		}
		return user;
	}
	
	public static Long getOaInitatorId(String pname,String pincident){
		Long user = 0L;
		String init = StringUtil.getNotNullValueString(getOaInitator(pname,pincident));
		if(init.length() > 12){
			user = OaUtil.getOaUserId(init);
		}
		return user;
	}
	
	public static String getOaInitator(String pname,String pincident){
		String sql = "select distinct substr(a.username,4) as initiator " +
				" from t_approvedinfo a where a.process = ? " +
				" and a.incidentno = ? and a.stepname='Begin'" +
				" and a.status=2 and a.username is not null";
		
		Object[] params = new Object[]{pname,pincident};
		String initiator = dbUtil.getJdbcTemplate().queryForObject(sql, String.class, params);
		return initiator;
	}
	
	public static String getOaLeaders(String pname,String pincident,String stepname){
		String sql = "select distinct substr(a.username,4) as leaderId from t_approvedinfo a " +
				" where a.process = ? and a.incidentno=? and a.stepname=? " +
				" and a.username is not null and a.status = 1";
		Object[] params = new Object[]{pname,pincident,stepname};
		String leaderId = "";
		List<String> list = dbUtil.getJdbcTemplate().queryForList(sql, String.class, params);
		
		if(list.size()>0){
			for(String temp : list){
				leaderId  += temp + ",";
			}
			leaderId = leaderId.substring(0,leaderId.length()-1);
		}	
		return leaderId;
	}
	

	
	public static List<Long> getOaMsgUsers(String pname,String pincident,String stepname){
		String init = StringUtil.getNotNullValueString(getOaInitator(pname,pincident));
		String leader = StringUtil.getNotNullValueString(getOaLeaders(pname,pincident,"部门领导审核"));
		String users = init + "," + leader;
		String[] temp = users.split(",");
		String user = "";
		for(String s: temp){
			if(!"".equals(s) && user.indexOf(s) < 0 && s.length()>12){
				user += "'"+s.substring(0,12)+"',";
			}
		}
		if(user.length() > 0){
			user = user.substring(0,user.length()-1);
		}
		String sql = "select distinct id from cs_user c " +
				" where c.login_name in ("+user+") and c.removed=0";
		List<Long> result = dbUtil.getJdbcTemplate().queryForList(sql, Long.class);
		return result;
	}
	
	
	
	
	public static List<String> getDocFileDept(String pname,String pincident,String stepname,String loginName){
		String users = "";
		List<Long> userList = OaUtil.getOaMsgUsers(pname, pincident, "");
		Long initUser = OaUtil.getOaInitatorId(pname, pincident);
		Long sendUser = OaUtil.getOaUserId(loginName);
		userList.add(initUser);userList.add(sendUser);
		if(userList != null && userList.size() > 0){
			for(Long u : userList){
				users += u + ",";
			}
			if(users.length() > 0){
				users = users.substring(0,users.length()-1);
			}
		}
		String sql = "select distinct v.parent_node_id dept" +
				" from v_userdep v where v.ID in ("+users+")";
		List<String> list = dbUtil.getJdbcTemplate().queryForList(sql, String.class);
		
		return list;
	}
	
	public static Map<String,String> getCatalog(List<String> src){
		String depts = "";
		if(src != null && src.size() > 0){
			for(String d : src){
				depts += "'" + d + "',";
			}
			if(depts.length() > 0){
				depts = depts.substring(0,depts.length()-1);
			}
		}
		Map<String,String> map = new HashMap<String,String>();
		String sql = "select distinct t.dept_id,tc.sid from t_docs_dept_cfg t,t_docs_catalog tc " +
				" where t.dept_id in ("+ depts +") and t.cate_sid=tc.parent_sid" +
				" and tc.state=1 and t.state = 1";
		List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql);
		if(list != null && list.size() > 0){
			for(Map<String,Object> m : list){
				map.put(StringUtil.getNotNullValueString(m.get("dept_id")), StringUtil.getNotNullValueString(m.get("sid")));
			}
		}
		return map;
	}
	
	public static String getOaDeptsPerson(String pname,String pincident,String stepname){
		String sql = "select distinct a.dept_id sd deptId from t_approvedinfo a " +
				" where a.process = ? and a.incidentno=? and a.stepname=? and a.dept_id is not null";
		Object[] params = new Object[]{pname,pincident,stepname};
		String deptId = "";
		List<String> list = dbUtil.getJdbcTemplate().queryForList(sql, String.class, params);
		
		if(list.size()>0){
			for(String temp : list){
				deptId  += temp + ",";
			}
			deptId = deptId.substring(0,deptId.length()-1);
		}	
		return deptId;
	}
	
	public static Map<String,String> getProcessRelation(String pname,String pincident,String type){
		Map<String,String> map = new HashMap<String,String>();
		String sql = " select "+
				" f.displayname as displayname, "+
				" c.NAME as NAME "+
				" from "+
				" T_FLOWFUNCTION_GUANLIAN f, "+
				" T_MSG_USERMESSAGE m, "+
				" CS_USER c "+
				" where "+
				" f.PROCESSNAME=? "+
				" and f.INCIDENTNO= ? "+
				" and f.TYPE=? "+
				" and f.YEWU_ID=to_char(m.SID) "+
				" and m.EMPIDREC=c.ID";
		Object[] params = new Object[]{pname,pincident,type};
		List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql, params);
		String key,value = "";
		if(list != null && list.size() > 0){
			for(Map<String,Object> m : list){
				key = m.get("displayname")+"";
				if(StringUtil.isNull(key)) continue;
					value = map.get(key);
				if(StringUtil.isNull(value)){
					value = StringUtil.getNotNullValueString(m.get("name"));
				}else{
					value +=(","+ StringUtil.getNotNullValueString(m.get("name")));
				}
				map.put(key, value);
			}
		}
		return map;
	}
	
	public static DbUtil getDbUtil() {
		return dbUtil;
	}
	
	@Autowired(required=false)
	public void setDbUtil(@Qualifier("dbUtil")DbUtil dbUtil) {
		OaUtil.dbUtil = dbUtil;
	}
	
	
}
