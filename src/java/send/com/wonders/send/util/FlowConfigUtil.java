/*    */ package com.wonders.send.util;
/*    */ 
/*    */ import com.wonders.send.common.exception.ProcessException;
import com.wonders.util.DbUtil;
import com.wonders.util.StringUtil;

/*    */ import java.util.List;
/*    */ import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
/*    */ import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("flowConfigUtil")
/*    */ public class FlowConfigUtil
/*    */ {
			private static DbUtil dbUtil;
/*    */   public String getValueByKey(String key, String type, Map map)
/*    */   {
/* 12 */     String sql = "select t.value from T_FLOWFORM_CONFIG t where t.key like '" + key + "'";
/* 13 */     if ((type != null) && (type.length() > 0)) {
/* 14 */       sql = sql + " and t.type ='" + type + "'";
/*    */     }
/* 16 */     List list = dbUtil.getJdbcTemplate().queryForList(sql);
/* 17 */     String value = "";
/* 18 */     if (list.size() > 0) {
/* 19 */       value = StringUtil.getNotNullValueString(((Map)list.get(0)).get("value"));
/* 20 */       value = AuthorityFunction.toSQL(value, map);
/*    */     }
/*    */ 
/* 23 */     if (value.length() == 0) {
/* 24 */       throw new ProcessException("value为空！");
/*    */     }
/* 26 */     return value;
/*    */   }

public DbUtil getDbUtil() {
	return dbUtil;
}

@Autowired(required=false)
public void setDbUtil(@Qualifier("dbUtil")DbUtil dbUtil) {
	this.dbUtil = dbUtil;
}
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.util.FlowConfigUtil
 * JD-Core Version:    0.6.0
 */