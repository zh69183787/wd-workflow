/*    */ package com.wonders.send.util;
/*    */ 
/*    */ import com.wonders.send.common.exception.ProcessException;
import com.wonders.util.DbUtil;
import com.wonders.util.StringUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
/*    */ import java.util.List;
/*    */ import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
/*    */ 
		@Component("sWUtil")
/*    */ public class SWUtil
/*    */ {
			private static DbUtil dbUtil;
/*    */   public static String showYLNew(String iPrefix, String iPostfix, String typeId, String parentCode)
/*    */   {
/* 13 */     String prefix = CodeUtil.getCodeName(parentCode, iPrefix);
/* 14 */     String returnValue = "";
/* 15 */     String sql = "select t.SERIALNO from t_serialno_reserve t where t.PREFIX='" + 
/* 16 */       prefix + "' and t.POSTFIX='" + iPostfix + "' and t.typeid = '" + typeId + "' and t.removed='0' order by t.SERIALNO";
/*    */ 
/* 18 */     List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql);
/* 19 */     if (list.size() > 0) {
/* 20 */       for (int i = 0; i < list.size(); i++) {
/* 21 */         returnValue = returnValue + StringUtil.getNotNullValueString(((Map<String,Object>)list.get(i)).get("SERIALNO")) + ";";
/*    */       }
/*    */     }
/*    */ 
/* 25 */     
/* 28 */     return returnValue;
/*    */   }

public static String saveYLNew(String iPrefix, String iPostfix,
		String iSerialNo,String typeId) {
	System.out.println("开始存预存号．．．");
	int count = 0;
	String sql = "select count(*) count_num from t_serialno_reserve t where t.PREFIX='"
			+ iPrefix
			+ "' and t.postfix = '"
			+ iPostfix
			+ "' and t.serialno = '" + iSerialNo + "' and t.typeId = '"+typeId+"'";
	//System.out.println(sql + "===============>saveYL");
	List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql);
	if(list!=null&&list.size()>0){
		count = Integer.valueOf(list.get(0).get("count_num").toString());
		if (count > 0) {
			return "fail";
		} else {
			sql = "insert into t_serialno_reserve(PREFIX,POSTFIX,SERIALNO,PROCESS,REMOVED,typeId) values "
					+ "('"
					+ iPrefix
					+ "' ,'"
					+ iPostfix
					+ "','"
					+ iSerialNo + "','收文流程',0,'"+typeId+"')";
			dbUtil.getJdbcTemplate().update(sql);
			System.out.println("存预存号结束．．．");
			return "success";
		}
	}
	return "fail";
}

public static String getSerialNoNew(String iPrefix, String iPostfix,
		String oSerialNo,String typeId) {
	System.out.println("开始取号码...");
	String returnValue = "" + oSerialNo;
	int intCnt = 0;
	String strSQL = "";
	String strPrefix = "";
	String strPostfix = "";
	String strTypeId = "";
	if ((iPrefix == null || "".equals(iPrefix))
			&& (iPostfix == null || "".equals(iPostfix))) {
		iPrefix = "SYSAUTO";
		iPostfix = "";
	}
	if (iPrefix != null && !"".equals(iPrefix)) {
		strPrefix = " and Prefix = '" + iPrefix + "'";
	}
	if (iPostfix != null && !"".equals(iPostfix)) {
		strPostfix = " and Postfix = '" + iPostfix + "'";
	}
	if (typeId != null && !"".equals(typeId)) {
		strTypeId = " and typeId = '" + typeId + "'";
	}
	
	if(iPrefix == null || "".equals(iPrefix)||iPostfix == null || "".equals(iPostfix))
		return "";
	
	if (oSerialNo == null || "".equals(oSerialNo.trim())) {
		strSQL = "select count(*) as CNT from T_SerialNo where 1 = 1"
				+ strPrefix + strPostfix + strTypeId;
		System.out.println("getSerialNoNew---strSQL:"+strSQL);
		List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(strSQL);
		if(list!=null&&list.size()>0){
			intCnt = Integer.valueOf(list.get(0).get("CNT").toString());
			if (intCnt > 0) {
				strSQL = "update T_SerialNo set SerialNo = (select max(SerialNo) + 1 from T_SerialNo where 1 = 1"
						+ strPrefix + strPostfix +strTypeId+ ") ";
				strSQL += " where 1 = 1" + strPrefix + strPostfix + strTypeId;
				System.out.println("getSerialNoNew---strSQL:"+strSQL);
			} else {
				strSQL = "insert into T_SerialNo(Prefix, SerialNo, Postfix,typeid) values('"
						+ iPrefix + "', 1, '" + iPostfix + "','" + typeId + "')";
				System.out.println("getSerialNoNew---strSQL:"+strSQL);
			}
			dbUtil.getJdbcTemplate().update(strSQL);
			//System.out.println(strSQL +"================>getSerialNo");
			returnValue = getMaxNoNew(iPrefix, iPostfix,typeId);
		}
		
	}
	System.out.println("iPrefix:" + iPrefix + "iPostfix:" + iPostfix
			+ "oSerialNo:" + oSerialNo);
	System.out.println("returnValue:" + returnValue);
	System.out.println("取号码成功...");
	return returnValue;
}

private static String getMaxNoNew(String iPrefix, String iPostfix,String typeId) {
	String result = "";
	String strPrefix = "";
	String strPostfix = "";
	String strTypeId = "";
	String sql = "select max(SerialNo) as MAXNO from T_SerialNo where 1 = 1 ";
	if (iPrefix != null && !"".equals(iPrefix)) {
		strPrefix = " and Prefix = '" + iPrefix + "'";
	}
	if (iPostfix != null && !"".equals(iPostfix)) {
		strPostfix = " and Postfix = '" + iPostfix + "'";
	}
	if (typeId != null && !"".equals(typeId)) {
		strTypeId = " and typeId = '" + typeId + "'";
	}
	sql +=  strPrefix + strPostfix + strTypeId;
	List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql);
	if(list!=null&&list.size()>0){
		result = list.get(0).get("MAXNO").toString();
	}
	return result;
}

public static void deleteYLNew(String iPrefix, String iPostfix,
		String iSerialNo,String typeId) {
	String sql = "update t_serialno_reserve t set t.removed='1' where t.PREFIX='"
			+ iPrefix
			+ "' and t.POSTFIX='"+ iPostfix
			+ "' and t.SERIALNO='" + iSerialNo + "'"
			+ " and t.typeId='" + typeId + "'";
	dbUtil.getJdbcTemplate().update(sql);
	System.out.println(sql + "===============>deleteYL");
}

public DbUtil getDbUtil() {
	return dbUtil;
}

@Autowired(required=false)
public void setDbUtil(@Qualifier("dbUtil")DbUtil dbUtil) {
	this.dbUtil = dbUtil;
}
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.util.SWUtil
 * JD-Core Version:    0.6.0
 */