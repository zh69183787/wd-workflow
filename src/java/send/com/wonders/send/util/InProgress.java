package com.wonders.send.util;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.wonders.send.common.model.vo.UserInfo;
import com.wonders.send.common.util.LoginUtil;
import com.wonders.send.external.service.ExternalService;
import com.wonders.util.DbUtil;

@Component("inProgress")
public class InProgress {
	private static DbUtil dbUtil;
	public List<Map<String, Object>> findUserInProgress(String processname,String incident){
		String sql = "select t.processname,t.steplabel,t.taskuser login_name from tasks t "+
				  " where t.processname = '"+processname+"' and t.incident = "+incident+" and t.status = 1  and t.taskuser != 'STPTBPMSVR_WF' and t.taskuser != 'SYSTEMUSER' "+
					" union all  "+
					"  select t2.processname,t2.steplabel,t2.taskuser login_name from tasks t2,  "+
					" (select distinct t1.cname,t1.cincident from tasks t,t_subprocess t1  where t.processname = '"+processname+"' and t.incident = "+incident+" and t.status = 1 "+ 
					"  and t1.pname = t.processname and t1.pincident = t.incident and t1.cname != t.processname  "+ 
					"   ) c where c.cname = t2.processname and c.cincident = t2.incident and t2.status = 1 and t2.taskuser != 'STPTBPMSVR_WF' and t2.taskuser != 'SYSTEMUSER'  ";
		//System.out.println(sql);
		
		return dbUtil.getJdbcTemplate().queryForList(sql);		
	}
	
	public DbUtil getDbUtil() {
		return dbUtil;
	}
	
	@Autowired(required=false)
	public void setDbUtil(@Qualifier("dbUtil")DbUtil dbUtil) {
		this.dbUtil = dbUtil;
	}
}
