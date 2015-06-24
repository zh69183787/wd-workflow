/*     */ package com.wonders.send.subProcess.dept.service.impl;
/*     */ 
/*     */ import com.wonders.send.approve.model.bo.SendApprovedinfo;
import com.wonders.send.common.model.vo.UserInfo;
import com.wonders.send.common.service.CommonService;
import com.wonders.send.common.util.LoginUtil;
import com.wonders.send.subProcess.dept.model.vo.DeptSubParamVo;
import com.wonders.send.subProcess.dept.model.vo.DeptSubVo;
import com.wonders.send.subProcess.dept.service.DeptSubApprovedService;
import com.wonders.util.StringUtil;

/*     */ import java.util.Date;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Qualifier;
/*     */ import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
/*     */ 
/*     */ @Service("send-deptSubApprovedService")
/*     */ @Scope("prototype")
/*     */ public class DeptSubApprovedServiceImpl
/*     */   implements DeptSubApprovedService
/*     */ {
/*     */   private CommonService service;
/*     */ 
/*     */   public SendApprovedinfo saveApprovedInfo(DeptSubParamVo params)
/*     */   {
/*  40 */     SendApprovedinfo tai = new SendApprovedinfo();
/*  41 */     UserInfo userInfo = params.userInfo;
/*  42 */     DeptSubVo vo = params.vo;
/*     */ 
/*  44 */     String optionCode = "";
/*     */ 
/*  46 */     String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
/*  47 */     String processName = params.getProcessParamValue("pname");
/*  48 */     String incidentNo = params.getProcessParamValue("pincident");
/*     */ 
/*  50 */     String steplabel = StringUtil.getNotNullValueString(vo.getSteplabel());
/*  51 */     String suggest = StringUtil.getNotNullValueString(vo.getSuggest());
/*  52 */     String fileGroupId = StringUtil.getNotNullValueString(vo.getAttachId());
/*     */ 
/*  54 */     String choice = StringUtil.getNotNullValueString(vo.getChoice());
/*  55 */     String choice2 = StringUtil.getNotNullValueString(vo.getChoice2());
/*     */ 
/*  58 */     if ("部门接受人工作分发".equals(steplabel)) {
/*  59 */       if ("1".equals(choice)) {
/*  60 */         optionCode = "DEPT_OPERATOR_DISPATCH_BUSS";
/*     */       }
/*  62 */       if ("2".equals(choice)) {
/*  63 */         if ("1".equals(choice2)) {
/*  64 */           optionCode = "DEPT_OPERATOR_DEAL_BUSS_NO_NOTION";
/*     */         }
/*  66 */         if ("2".equals(choice2)) {
/*  67 */           optionCode = "DEPT_OPERATOR_DEAL_BUSS_NEW_NOTION";
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  72 */     if ("部门业务人员处理".equals(steplabel)) {
/*  73 */       if ("1".equals(choice)) {
/*  74 */         optionCode = "DEAL_NO_SUGGEST";
/*     */       }
/*  76 */       if ("2".equals(choice)) {
/*  77 */         optionCode = "DEAL_HAS_SUGGEST";
/*     */       }
/*  79 */       if ("3".equals(choice)) {
/*  80 */         optionCode = "DEAL_NOT_MY_BUSINESS";
/*     */       }
/*     */     }
/*     */ 
/*  84 */     if ("部门领导审核".equals(steplabel)) {
/*  85 */       if ("1".equals(choice)) {
/*  86 */         optionCode = "LEADER_PASS";
/*     */       }
/*  88 */       if ("2".equals(choice)) {
/*  89 */         optionCode = "LEADER_FAILED";
/*     */       }
/*  91 */       if ("3".equals(choice)) {
/*  92 */         if ("1".equals(choice2)) {
/*  93 */           optionCode = "LEADER_CONTINUE_DEAL";
/*     */         }
/*  95 */         if ("2".equals(choice2)) {
/*  96 */           optionCode = "LEADER_CONTINUE_CHOICE_PERSON";
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 101 */     tai.setReturned(Long.valueOf(2L));
/* 102 */     tai.setAgree(Long.valueOf(1L));
/* 103 */     tai.setDisagree(Long.valueOf(0L));
/* 104 */     tai.setReturned(Long.valueOf(2L));
/* 105 */     tai.setRounds(Integer.valueOf(0));
/* 106 */     tai.setFllowFlag("0");
/* 107 */     tai.setDeptId(userInfo.getDeptId());
/* 108 */     tai.setDept(userInfo.getDeptName());
/* 109 */     tai.setUsername(taskUserLoginName);
/* 110 */     tai.setUserfullname(userInfo.getUserName());
/* 111 */     tai.setReadFlag("1");
/* 112 */     tai.setUpddate(new Date());
/*     */ 
/* 114 */     tai.setProcess(processName);
/* 115 */     tai.setIncidentno(Long.valueOf(Long.parseLong(incidentNo)));
/*     */ 
/* 117 */     tai.setStepname(steplabel);
/*     */ 
/* 119 */     tai.setFileGroupId(fileGroupId);
/*     */ 
/* 121 */     tai.setRemark(suggest);
/* 122 */     tai.setStatus(Long.valueOf(1L));
/*     */ 
/* 124 */     tai.setOptionCode(optionCode);
/*     */ 
/* 126 */     this.service.save(tai);
/*     */ 
/* 128 */     return tai;
/*     */   }
/*     */ 
/*     */   public CommonService getService()
/*     */   {
/* 134 */     return this.service;
/*     */   }
/*     */   @Autowired(required=false)
/*     */   public void setService(@Qualifier("send-commonService") CommonService service) {
/* 139 */     this.service = service;
/*     */   }
/*     */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.subProcess.dept.service.impl.DeptSubApprovedServiceImpl
 * JD-Core Version:    0.6.0
 */