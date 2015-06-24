/*    */ package com.wonders.send.subProcess.issue.util;
/*    */ 
/*    */ import com.wonders.send.subProcess.issue.model.vo.IssueSubVo;
import com.wonders.util.StringUtil;
/*    */ 
/*    */ public class IssueSubUtil
/*    */ {
/*    */   public static String getSign(IssueSubVo vo)
/*    */   {
/* 22 */     String choice = StringUtil.getNotNullValueString(vo.getChoice());
		     String steplabel = StringUtil.getNotNullValueString(vo.getSteplabel());
/* 23 */     String sign = "";
/* 24 */     if ("1".equals(choice))
/* 25 */       sign = "同意";
/* 26 */     else if ("2".equals(choice))
/* 27 */       sign = "拟同意";
/* 28 */     else if (("3".equals(choice)&&"签发秘书".equals(steplabel)) || ("4".equals(choice)&&"签发领导".equals(steplabel)))
/* 29 */       sign = "退回拟稿部门";
/* 30 */     else if (("4".equals(choice)&&"签发秘书".equals(steplabel)) || ("5".equals(choice)&&"签发领导".equals(steplabel)))
/* 31 */       sign = "其他意见";
/* 32 */     else if ("5".equals(choice)&&"签发秘书".equals(steplabel))
/* 33 */       sign = "领导暂时不能处理,退回办公室";
/* 34 */     else if ("3".equals(choice)&&"签发领导".equals(steplabel)) {
/* 35 */       sign = "退回秘书";
/*    */     }
/*    */ 
/* 38 */     return sign;
/*    */   }
/*    */ 
/*    */   public static Long getDisAgree(IssueSubVo vo) {
/* 42 */     String choice = StringUtil.getNotNullValueString(vo.getChoice());
			 String steplabel = StringUtil.getNotNullValueString(vo.getSteplabel());
/* 43 */     Long disagree = Long.valueOf(0L);
/*    */ 
/* 45 */     if ("2".equals(choice)|| "1".equals(choice))
/* 47 */       disagree = Long.valueOf(0L);
/* 48 */     else if (("3".equals(choice)&&"签发秘书".equals(steplabel)) || ("4".equals(choice)&&"签发领导".equals(steplabel)))
/* 49 */       disagree = Long.valueOf(1L);
/* 50 */     else if (("4".equals(choice)&&"签发秘书".equals(steplabel)) || ("5".equals(choice)&&"签发领导".equals(steplabel)))
/* 51 */       disagree = Long.valueOf(1L);
/* 52 */     else if ("5".equals(choice)&&"签发秘书".equals(steplabel))
/* 53 */       disagree = Long.valueOf(1L);
/* 54 */     else if ("3".equals(choice)&&"签发领导".equals(steplabel)) {
/* 55 */       disagree = Long.valueOf(0L);
/*    */     }
/*    */ 
/* 58 */     return disagree;
/*    */   }
/*    */ 
/*    */   public static String getOptionCode(IssueSubVo vo) {
/* 62 */     String steplabel = StringUtil.getNotNullValueString(vo.getSteplabel());
/* 63 */     String type = StringUtil.getNotNullValueString(vo.getType());
/* 64 */     String choice = StringUtil.getNotNullValueString(vo.getChoice());
/* 65 */     String optionCode = "";
/* 66 */     if ("签发秘书".equals(steplabel)) {
/* 67 */       if ("1".equals(type)) {
/* 68 */         if ("1".equals(choice))
/* 69 */           optionCode = "LEADER_ASSISTANT_DEAL_AGREE";
/* 70 */         else if ("2".equals(choice))
/* 71 */           optionCode = "LEADER_ASSISTANT_DEAL_PRE_AGREE";
/* 72 */         else if ("3".equals(choice))
/* 73 */           optionCode = "LEADER_ASSISTANT_DEAL_DEALDELIVER_BACK";
/* 74 */         else if ("4".equals(choice))
/* 75 */           optionCode = "LEADER_ASSISTANT_DEAL_OTHER_NOTION";
/*    */       }
/* 77 */       else if (("0".equals(type)) && 
/* 78 */         ("5".equals(choice))) {
/* 79 */         optionCode = "LEADER_ASSISTANT_DEAL_LEADER_TMP_CANNT_DEAL";
/*    */       }
/*    */ 
/*    */     }
/* 83 */     else if ("签发领导".equals(steplabel)) {
/* 84 */       if ("1".equals(choice))
/* 85 */         optionCode = "LEADER_ASSISTANT_DEAL_AGREE";
/* 86 */       else if ("2".equals(choice))
/* 87 */         optionCode = "LEADER_ASSISTANT_DEAL_PRE_AGREE";
/* 88 */       else if ("4".equals(choice))
/* 89 */         optionCode = "LEADER_ASSISTANT_DEAL_DEALDELIVER_BACK";
/* 90 */       else if ("5".equals(choice)) {
/* 91 */         optionCode = "LEADER_ASSISTANT_DEAL_OTHER_NOTION";
/*    */       }
/*    */     }
/*    */ 
/* 95 */     return optionCode;
/*    */   }
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.subProcess.issue.util.IssueSubUtil
 * JD-Core Version:    0.6.0
 */