/*    */ package com.wonders.send.mainProcess.send.constant;
/*    */ 
/*    */ import com.wonders.send.model.bo.MessageBo;
/*    */ 
/*    */ public class DeptContactMessage
/*    */ {
/*    */   private static final String error1 = "10";
/*    */   private static final String error2 = "11";
/*    */   private static final String info1 = "00";
/* 13 */   public static final MessageBo FAIL_TO_LAUNCH_PROCESS = new MessageBo("发起流程失败！", "", "10");
/* 14 */   public static final MessageBo FAIL_TO_COMPLETE_STEP = new MessageBo("流程操作失败！", "", "10");
/* 15 */   public static final MessageBo FAIL_TO_SAVE_DATA = new MessageBo("数据保存失败！", "", "10");
/*    */ 
/* 18 */   public static final MessageBo CHECK_SUCCESS = new MessageBo("效验成功！", "", "00");
/* 19 */   public static final MessageBo CHECK_WRONG_PROCESS_INFO = new MessageBo("流程信息错误！", "", "10");
/* 20 */   public static final MessageBo CHECK_IS_FINISH = new MessageBo("操作不存在或操作已完成！", "", "10");
/*    */ 
/* 25 */   public static final MessageBo CHECK_NO_MAINUNIT = new MessageBo("主送部门不能为空！", "mainUnit", "11");
/* 26 */   public static final MessageBo CHECK_NO_THEME = new MessageBo("主题不能为空！", "theme", "11");
/* 27 */   public static final MessageBo CHECK_NO_CONTENT = new MessageBo("联系内容不能为空！", "content", "11");
/*    */ 
/* 29 */   public static final MessageBo CHECK_CONTENT_LIMIT_1000 = new MessageBo("联系内容应小于1000字！", "content", "11");
/*    */ 
/* 31 */   public static final MessageBo CHECK_NO_CONTACTDATE = new MessageBo("联系日期不能为空！", "contactDate", "11");
/* 32 */   public static final MessageBo CHECK_WRONG_CONTACTDATE = new MessageBo("联系日期格式错误！", "contactDate", "11");
/*    */ 
/* 34 */   public static final MessageBo CHECK_NO_REPLYDATE = new MessageBo("要求回复日期不能为空！", "replyDate", "11");
/* 35 */   public static final MessageBo CHECK_WRONG_REPLYDATE = new MessageBo("要求回复日期格式错误！", "replyDate", "11");
/*    */ 
/* 37 */   public static final MessageBo CHECK_WRONG_DATE_COMPARE = new MessageBo("要求回复日期应晚于联系日期！", "replyDate", "11");
/*    */ 
/* 42 */   public static final MessageBo APPLY_NO_CHOICE = new MessageBo("请选择申请结果！", "choice", "11");
/*    */ 
/* 47 */   public static final MessageBo SIGN_NO_LEADER = new MessageBo("签发领导不能为空！", "SIGN_LEADER", "11");
/*    */ 
/* 49 */   public static final MessageBo SIGN_NO_CHOICE = new MessageBo("请选择签发结果！", "choice", "11");
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.mainProcess.send.constant.DeptContactMessage
 * JD-Core Version:    0.6.0
 */