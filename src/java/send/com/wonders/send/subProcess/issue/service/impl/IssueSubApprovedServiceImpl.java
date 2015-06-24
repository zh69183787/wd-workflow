/*     */ package com.wonders.send.subProcess.issue.service.impl;
/*     */ 
/*     */ import com.wonders.send.approve.model.bo.SendApprovedinfo;
import com.wonders.send.common.model.vo.UserInfo;
import com.wonders.send.common.service.CommonService;
import com.wonders.send.common.util.LoginUtil;
import com.wonders.constants.CommonConstants;
import com.wonders.send.subProcess.issue.model.vo.IssueSubParamVo;
import com.wonders.send.subProcess.issue.model.vo.IssueSubVo;
import com.wonders.send.subProcess.issue.service.IssueSubApprovedService;
import com.wonders.send.subProcess.issue.util.IssueSubUtil;
import com.wonders.send.subProcess.util.ProcessUtil;
import com.wonders.util.StringUtil;

/*     */ import java.util.Date;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Qualifier;
/*     */ import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
/*     */ 
/*     */ @Service("send-issueSubApprovedService")
/*     */ @Scope("prototype")
/*     */ public class IssueSubApprovedServiceImpl
/*     */   implements IssueSubApprovedService
/*     */ {
/*     */   private CommonService service;
/*     */ 
/*     */   public SendApprovedinfo saveApprovedInfo(IssueSubParamVo params)
/*     */   {
/*  39 */     SendApprovedinfo tai = new SendApprovedinfo();
/*  40 */     UserInfo userInfo = params.userInfo;
/*  41 */     IssueSubVo vo = params.vo;
/*     */ 
/*     */ 
/*  45 */     String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
/*  46 */     String processName = params.getProcessParamValue("cname");
/*  47 */     String incidentNo = params.getProcessParamValue("cincident");
/*  48 */     String pname = params.getProcessParamValue("pname");
/*  49 */     String pincident = params.getProcessParamValue("pincident");
/*  50 */     String steplabel = StringUtil.getNotNullValueString(vo.getSteplabel());
/*  51 */     String suggest = StringUtil.getNotNullValueString(vo.getSuggest());
/*  52 */     String fileGroupId = StringUtil.getNotNullValueString(vo.getAttachId());
/*  53 */     String type = StringUtil.getNotNullValueString(vo.getType());
/*  54 */     String choice = StringUtil.getNotNullValueString(vo.getChoice());
			
				boolean flag = false;
/*     */ 
/*  57 */     if ("签发秘书".equals(steplabel)) {
/*  58 */       tai.setUsername(taskUserLoginName);
/*  59 */       tai.setUserfullname(userInfo.getUserName());
/*  60 */       tai.setProcess(processName);
/*  61 */       tai.setIncidentno(Long.valueOf(Long.parseLong(incidentNo)));
/*  62 */       if ("5".equals(choice)) {
/*  63 */         tai.setProcess(pname);
/*  64 */         tai.setIncidentno(Long.valueOf(Long.parseLong(pincident)));
					suggest = "领导暂时不能处理，退回办公室。"+suggest;
/*  65 */       } else if ("1".equals(type))
/*     */       {
					flag = true;
/*  67 */         String leaderId = "";
/*  68 */         String leaderName = "";
/*  69 */         String info = ProcessUtil.getLeaderInfo(processName, incidentNo);
/*  70 */         if ((info.length() > 0) && (info.split(",").length > 0)) {
/*  71 */           leaderId = info.split(",")[0];
/*  72 */           leaderName = info.split(",")[1];
/*     */         }
/*  74 */         tai.setUsername(CommonConstants.LOGINNAME_PREFIX + leaderId);
/*  75 */         tai.setUserfullname(leaderName + "（办公室代为处理）");
/*  76 */         tai.setProcess(pname);
/*  77 */         tai.setIncidentno(Long.valueOf(Long.parseLong(pincident)));
/*  78 */         steplabel = "签发领导";
/*     */       }
/*  80 */     } else if ("签发领导".equals(steplabel)) {
				flag = true;
/*  81 */       tai.setProcess(pname);
/*  82 */       tai.setIncidentno(Long.valueOf(Long.parseLong(pincident)));
/*  83 */       tai.setUsername(taskUserLoginName);
/*  84 */       tai.setUserfullname(userInfo.getUserName());
/*     */     }
/*     */ 
/*  87 */     tai.setOptionCode(IssueSubUtil.getOptionCode(vo));
/*  88 */     tai.setDisagree(IssueSubUtil.getDisAgree(vo));
/*  89 */     tai.setSign(IssueSubUtil.getSign(vo));
/*  90 */     tai.setDeptId(userInfo.getDeptId());
/*  91 */     tai.setDept(userInfo.getDeptName());
/*  92 */     tai.setUpddate(new Date());
/*     */ 
/*  94 */     tai.setAgree(Long.valueOf(1L));
/*  95 */     tai.setStepname(steplabel);
/*  96 */     tai.setReturned(Long.valueOf(2L));
/*  97 */     tai.setFileGroupId(fileGroupId);
/*  98 */     tai.setReadFlag("1");
/*  99 */     tai.setRemark(suggest);
/* 100 */     tai.setStatus(Long.valueOf(1L));
/* 101 */     tai.setFllowFlag("0");
/*     */ 
			if(flag){
				this.service.save(tai);
			}
/*     */ 
/* 106 */     return tai;
/*     */   }
/*     */ 
/*     */   public CommonService getService()
/*     */   {
/* 114 */     return this.service;
/*     */   }
/*     */   @Autowired(required=false)
/*     */   public void setService(@Qualifier("send-commonService") CommonService service) {
/* 119 */     this.service = service;
/*     */   }
/*     */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.subProcess.issue.service.impl.IssueSubApprovedServiceImpl
 * JD-Core Version:    0.6.0
 */