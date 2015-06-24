/*    */ package com.wonders.send.util;
/*    */ 
/*    */ import com.wonders.send.common.exception.ProcessException;
import com.wonders.util.DbUtil;
import com.wonders.util.StringUtil;

/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
/*    */ import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("codeUtil")
/*    */ public class CodeUtil
/*    */ {
			private static DbUtil dbUtil;
/*    */   public static String getCodeName(String codeType_code, String code)
/*    */   {
/* 21 */     String sql = "select t.name from cf_code_info t where t.type_id = (select c.id from cf_code_type c where c.removed = 0 and  c.code = ?) and t.code = ? and t.removed = 0";
/* 22 */     Object[] params = { codeType_code, code };
/* 23 */     List list = dbUtil.getJdbcTemplate().queryForList(sql, params);
/* 24 */     String name = "";
/* 25 */     if (list.size() > 0) {
/* 26 */       name = StringUtil.getNotNullValueString(((Map)list.get(0)).get("name"));
/*    */     }
/*    */ 
/* 29 */     if (name.length() == 0) {
/* 30 */       throw new ProcessException("name为空！");
/*    */     }
/* 32 */     return name;
/*    */   }
/*    */ 
/*    */   public static List<String> findCodeInfoByCode(String codeType_code, String codeInfo_code)
/*    */   {
/* 46 */     String sql = "select t.code, t.name,t.id,t.description from cf_code_info t where t.removed = 0 and  t.type_id =(select  a.id from cf_code_type a where a.removed =0 and a.code = ?) and t.code_info_id=(select  c.id from cf_code_info c where c.removed = 0 and c.code = ? and c.type_id =(select  b.id from cf_code_type b where b.removed = 0 and b.code = ?))  order by t.disp_order";
/* 47 */     Object[] params = { codeType_code, codeInfo_code, codeType_code };
/* 48 */     List list = dbUtil.getJdbcTemplate().queryForList(sql, params);
/* 49 */     List lists = new ArrayList();
/* 50 */     String code = "";
/* 51 */     String name = "";
/* 52 */     String description = "";
/* 53 */     String id = "";
/* 54 */     if (list.size() > 0) {
/* 55 */       for (int i = 0; i < list.size(); i++) {
/* 56 */         code = StringUtil.getNotNullValueString(((Map)list.get(i)).get("code"));
/* 57 */         name = StringUtil.getNotNullValueString(((Map)list.get(i)).get("name"));
/* 58 */         description = StringUtil.getNotNullValueString(((Map)list.get(i)).get("description"));
/* 59 */         id = StringUtil.getNotNullValueString(((Map)list.get(i)).get("id"));
/* 60 */         lists.add(code + ":" + name + ":" + description + ":" + id);
/*    */       }
/*    */     }
/*    */ 
/* 64 */     if (lists.size() == 0) {
/* 65 */       //throw new ProcessException("lists为空！");
				return null;
/*    */     }
/* 67 */     return lists;
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
 * Qualified Name:     com.wonders.workflow.util.CodeUtil
 * JD-Core Version:    0.6.0
 */