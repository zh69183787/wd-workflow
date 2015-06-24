/*    */ package com.wonders.send.subProcess.sign.util;
/*    */ 
/*    */ import com.wonders.send.subProcess.sign.model.vo.SignSubVo;
import com.wonders.util.StringUtil;
/*    */ 
/*    */ public class SignSubUtil
/*    */ {
/*    */   public static String getSign(SignSubVo vo)
/*    */   {
/* 20 */     String choice = StringUtil.getNotNullValueString(vo.getChoice());
/* 21 */     String steplabel = StringUtil.getNotNullValueString(vo.getSteplabel());
/* 22 */     String sign = "";
/* 23 */     if (("1".equals(choice)) || ("1".equals(choice)))
/* 24 */       sign = "拟同意";
/* 25 */     else if ((("2".equals(choice)) && ("秘书".equals(steplabel))) || (("3".equals(choice)) && ("领导".equals(steplabel))))
/* 26 */       sign = "退回拟稿部门";
/* 27 */     else if ((("3".equals(choice)) && ("秘书".equals(steplabel))) || (("4".equals(choice)) && ("领导".equals(steplabel))))
/* 28 */       sign = "其他意见";
/* 29 */     else if (("4".equals(choice)) && ("秘书".equals(steplabel)))
/* 30 */       sign = "领导暂时不能处理,退回办公室";
/* 31 */     else if (("2".equals(choice)) && ("领导".equals(steplabel))) {
/* 32 */       sign = "退回秘书";
/*    */     }
/*    */ 
/* 35 */     return sign;
/*    */   }
/*    */ 
/*    */   public static Long getDisAgree(SignSubVo vo) {
/* 39 */     String choice = StringUtil.getNotNullValueString(vo.getChoice());
/* 40 */     String steplabel = StringUtil.getNotNullValueString(vo.getSteplabel());
/* 41 */     Long disagree = Long.valueOf(0L);
/*    */ 
/* 43 */     if (("1".equals(choice)) || ("1".equals(choice)))
/* 44 */       disagree = Long.valueOf(0L);
/* 45 */     else if ((("2".equals(choice)) && ("秘书".equals(steplabel))) || (("3".equals(choice)) && ("领导".equals(steplabel))))
/* 46 */       disagree = Long.valueOf(1L);
/* 47 */     else if ((("3".equals(choice)) && ("秘书".equals(steplabel))) || (("4".equals(choice)) && ("领导".equals(steplabel))))
/* 48 */       disagree = Long.valueOf(1L);
/* 49 */     else if (("4".equals(choice)) && ("秘书".equals(steplabel)))
/* 50 */       disagree = Long.valueOf(1L);
/* 51 */     else if (("2".equals(choice)) && ("领导".equals(steplabel))) {
/* 52 */       disagree = Long.valueOf(0L);
/*    */     }
/*    */ 
/* 55 */     return disagree;
/*    */   }
/*    */ 
/*    */   public static String getOptionCode(SignSubVo vo) {
/* 59 */     String steplabel = StringUtil.getNotNullValueString(vo.getSteplabel());
/* 60 */     String type = StringUtil.getNotNullValueString(vo.getType());
/* 61 */     String choice = StringUtil.getNotNullValueString(vo.getChoice());
/* 62 */     String optionCode = "";
/* 63 */     if ("秘书".equals(steplabel)) {
/* 64 */       if ("1".equals(type)) {
/* 65 */         if ("1".equals(choice))
/* 66 */           optionCode = "LEADER_DEAL_PRE_AGREE";
/* 67 */         else if ("2".equals(choice))
/* 68 */           optionCode = "LEADER_DEAL_DELIVER_BACK";
/* 69 */         else if ("3".equals(choice))
/* 70 */           optionCode = "LEADER_DEAL_OTHER_NOTION";
/*    */       }
/* 72 */       else if (("0".equals(type)) && 
/* 73 */         ("4".equals(choice))) {
/* 74 */         optionCode = "LEADER_DEAL_LEADER_TMP_CANNT_DEAL";
/*    */       }
/*    */ 
/*    */     }
/* 78 */     else if ("领导".equals(steplabel))
/*    */     {
/* 80 */       if ("1".equals(choice))
/* 81 */         optionCode = "LEADER_DEAL_PRE_AGREE";
/* 82 */       else if ("3".equals(choice))
/* 83 */         optionCode = "LEADER_DEAL_DELIVER_BACK";
/* 84 */       else if ("4".equals(choice)) {
/* 85 */         optionCode = "LEADER_DEAL_OTHER_NOTION";
/*    */       }
/*    */     }
/*    */ 
/* 89 */     return optionCode;
/*    */   }
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.subProcess.sign.util.SignSubUtil
 * JD-Core Version:    0.6.0
 */