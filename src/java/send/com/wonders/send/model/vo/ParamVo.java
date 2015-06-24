/*    */ package com.wonders.send.model.vo;
/*    */ 
/*    */ import com.wonders.send.common.model.vo.UserInfo;
import com.wonders.util.StringUtil;

/*    */ import java.util.HashMap;
import java.util.Map;
/*    */ 
/*    */ public abstract class ParamVo
/*    */ {
/*    */   public UserInfo userInfo;
/* 21 */   public Map<String, String> processParam = new HashMap();
/*    */ 
/*    */   public String getProcessParamValue(String key) {
/* 24 */     return StringUtil.getNotNullValueString(this.processParam.get(key));
/*    */   }
/*    */   public void addProcessParam(String key, String value) {
/* 27 */     this.processParam.put(key, StringUtil.getNotNullValueString(value));
/*    */   }
/*    */   public UserInfo getUserInfo() {
/* 30 */     return this.userInfo;
/*    */   }
/*    */ 
/*    */   public void setUserInfo(UserInfo userInfo) {
/* 34 */     this.userInfo = userInfo;
/*    */   }
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.model.vo.ParamVo
 * JD-Core Version:    0.6.0
 */