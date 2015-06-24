/*     */ package com.wonders.send.subProcess.sign.service.impl;
/*     */ 
/*     */ import com.wonders.send.approve.model.bo.SendApprovedinfo;
import com.wonders.send.common.model.vo.UserInfo;
import com.wonders.send.common.service.CommonService;
import com.wonders.send.common.util.LoginUtil;
import com.wonders.constants.CommonConstants;
import com.wonders.send.subProcess.issue.util.IssueSubUtil;
import com.wonders.send.subProcess.sign.model.vo.SignSubParamVo;
import com.wonders.send.subProcess.sign.model.vo.SignSubVo;
import com.wonders.send.subProcess.sign.service.SignSubApprovedService;
import com.wonders.send.subProcess.sign.util.SignSubUtil;
import com.wonders.send.subProcess.util.ProcessUtil;
import com.wonders.util.StringUtil;

/*     */ import java.util.Date;

import org.springframework.beans.BeanUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Qualifier;
/*     */ import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
/*     */ 
/*     */ @Service("send-signSubApprovedService")
/*     */ @Scope("prototype")
/*     */ public class SignSubApprovedServiceImpl
/*     */   implements SignSubApprovedService
/*     */ {
/*     */   private CommonService service;
/*     */ 
		  public SendApprovedinfo saveApprovedInfo(SignSubParamVo params)
/*     */   {
/*  39 */     SendApprovedinfo tai = new SendApprovedinfo();
/*  40 */     UserInfo userInfo = params.userInfo;
/*  41 */     SignSubVo vo = params.vo;
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
/*  67 */         String leaderId = "";
/*  68 */         String leaderName = "";
				boolean flag = false;

/*  57 */     if ("秘书".equals(steplabel)) {
/*  58 */       tai.setUsername(taskUserLoginName);
/*  59 */       tai.setUserfullname(userInfo.getUserName());
/*  60 */       tai.setProcess(processName);
/*  61 */       tai.setIncidentno(Long.valueOf(Long.parseLong(incidentNo)));


/*  69 */         String info = ProcessUtil.getLeaderInfo(processName, incidentNo);
/*  70 */         if ((info.length() > 0) && (info.split(",").length > 0)) {
/*  71 */           leaderId = info.split(",")[0];
/*  72 */           leaderName = info.split(",")[1];
/*     */         }

/*  62 */       if ("4".equals(choice)) {
					flag = true;
/*  63 */         tai.setProcess(pname);
/*  64 */         tai.setIncidentno(Long.valueOf(Long.parseLong(pincident)));
					suggest = "领导暂时不能处理，退回办公室。"+suggest;
/*  65 */       } else if ("1".equals(type))
/*     */       {

/*  74 */         tai.setUsername(CommonConstants.LOGINNAME_PREFIX + leaderId);
/*  75 */         tai.setUserfullname(leaderName + "（办公室代为处理）");
/*  76 */         tai.setProcess(pname);
/*  77 */         tai.setIncidentno(Long.valueOf(Long.parseLong(pincident)));
/*  78 */         steplabel = "领导";
/*     */       }
/*  80 */     } else if ("领导".equals(steplabel)) {
/*  81 */       tai.setProcess(pname);
/*  82 */       tai.setIncidentno(Long.valueOf(Long.parseLong(pincident)));
/*  83 */       tai.setUsername(taskUserLoginName);
/*  84 */       tai.setUserfullname(userInfo.getUserName());
/*     */     }
/*     */ 
/*  87 */     tai.setOptionCode(SignSubUtil.getOptionCode(vo));
/*  88 */     tai.setDisagree(SignSubUtil.getDisAgree(vo));
/*  89 */     tai.setSign(SignSubUtil.getSign(vo));
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
/* 104 */     this.service.save(tai);

if(flag){
	SendApprovedinfo tai2 = new SendApprovedinfo();
	BeanUtils.copyProperties(tai,tai2);
	tai2.setGuid(null);
	tai2.setStatus(2L);
	tai2.setStepname("领导");
	tai2.setUsername(CommonConstants.LOGINNAME_PREFIX + leaderId);
	this.service.save(tai2);
}
/*     */ 
/* 132 */     return tai;
/*     */   }


///*     */   public TApprovedinfo saveApprovedInfo(SignSubParamVo params)
///*     */   {
///*  39 */     TApprovedinfo tai = new TApprovedinfo();
///*  40 */     UserInfo userInfo = params.userInfo;
///*  41 */     SignSubVo vo = params.vo;
///*     */ 
///*  45 */     String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
///*  46 */     String processName = params.getProcessParamValue("cname");
///*  47 */     String incidentNo = params.getProcessParamValue("cincident");
///*  48 */     String pname = params.getProcessParamValue("pname");
///*  49 */     String pincident = params.getProcessParamValue("pincident");
///*  50 */     String steplabel = StringUtil.getNotNullValueString(vo.getSteplabel());
///*  51 */     String suggest = StringUtil.getNotNullValueString(vo.getSuggest());
///*  52 */     String fileGroupId = StringUtil.getNotNullValueString(vo.getAttachId());
///*  53 */     String type = StringUtil.getNotNullValueString(vo.getType());
///*  54 */     String choice = StringUtil.getNotNullValueString(vo.getChoice());
///*     */ 
///*  57 */     if ("秘书".equals(steplabel)) {
///*  58 */       tai.setUsername(taskUserLoginName);
///*  59 */       tai.setUserfullname(userInfo.getUserName());
///*  60 */       tai.setProcess(processName);
///*  61 */       tai.setIncidentno(Long.valueOf(Long.parseLong(incidentNo)));
///*  62 */       if ("4".equals(choice)) {
///*  63 */         String info = ProcessUtil.getLeaderInfo(processName, incidentNo);
///*  64 */         if ((info.length() > 0) && (info.split(",").length > 0)) {
///*  65 */           taskUserLoginName = CommonConstants.LOGINNAME_PREFIX + info.split(",")[0];
///*     */         }
///*  67 */         String steplabelTemp = ProcessUtil.getStepInfo(taskUserLoginName, processName, incidentNo);
///*  68 */         TApprovedinfo tai2 = new TApprovedinfo();
///*  69 */         tai2.setOptionCode(SignSubUtil.getOptionCode(vo));
///*  70 */         tai2.setDisagree(SignSubUtil.getDisAgree(vo));
///*  71 */         tai2.setSign(SignSubUtil.getSign(vo));
///*  72 */         tai2.setDeptId(userInfo.getDeptId());
///*  73 */         tai2.setDept(userInfo.getDeptName());
///*  74 */         tai2.setUpddate(new Date());
///*  75 */         tai2.setProcess(pname);
///*  76 */         tai2.setIncidentno(Long.valueOf(Long.parseLong(pincident)));
///*  77 */         tai2.setAgree(Long.valueOf(1L));
///*  78 */         tai2.setStepname(steplabelTemp);
///*  79 */         tai2.setReturned(Long.valueOf(2L));
///*  80 */         tai2.setFileGroupId(fileGroupId);
///*  81 */         tai2.setReadFlag("1");
///*  82 */         tai2.setRemark(suggest);
///*  83 */         tai2.setStatus(Long.valueOf(2L));
///*  84 */         tai2.setFllowFlag("0");
///*     */ 
///*  86 */         tai2.setUsername(taskUserLoginName);
///*  87 */         tai2.setUserfullname(userInfo.getUserName());
///*     */ 
///*  89 */         tai.setProcess(pname);
///*  90 */         tai.setIncidentno(Long.valueOf(Long.parseLong(pincident)));
///*     */ 
///*  92 */         this.service.save(tai2);
///*  93 */       } else if ("1".equals(type))
///*     */       {
///*  95 */         String leaderId = "";
///*  96 */         String leaderName = "";
///*  97 */         String info = ProcessUtil.getLeaderInfo(processName, incidentNo);
///*  98 */         if ((info.length() > 0) && (info.split(",").length > 0)) {
///*  99 */           leaderId = info.split(",")[0];
///* 100 */           leaderName = info.split(",")[1];
///*     */         }
///* 102 */         tai.setUsername(CommonConstants.LOGINNAME_PREFIX + leaderId);
///* 103 */         tai.setUserfullname(leaderName + "（办公室代为处理）");
///* 104 */         steplabel = "领导";
///*     */       }
///* 106 */     } else if ("领导".equals(steplabel)) {
///* 107 */       tai.setProcess(pname);
///* 108 */       tai.setIncidentno(Long.valueOf(Long.parseLong(pincident)));
///* 109 */       tai.setUsername(taskUserLoginName);
///* 110 */       tai.setUserfullname(userInfo.getUserName());
///*     */     }
///*     */ 
///* 113 */     tai.setOptionCode(SignSubUtil.getOptionCode(vo));
///* 114 */     tai.setDisagree(SignSubUtil.getDisAgree(vo));
///* 115 */     tai.setSign(SignSubUtil.getSign(vo));
///* 116 */     tai.setDeptId(userInfo.getDeptId());
///* 117 */     tai.setDept(userInfo.getDeptName());
///* 118 */     tai.setUpddate(new Date());
///*     */ 
///* 120 */     tai.setAgree(Long.valueOf(1L));
///* 121 */     tai.setStepname(steplabel);
///* 122 */     tai.setReturned(Long.valueOf(2L));
///* 123 */     tai.setFileGroupId(fileGroupId);
///* 124 */     tai.setReadFlag("1");
///* 125 */     tai.setRemark(suggest);
///* 126 */     tai.setStatus(Long.valueOf(1L));
///* 127 */     tai.setFllowFlag("0");
///*     */ 
///* 130 */     this.service.save(tai);
///*     */ 
///* 132 */     return tai;
///*     */   }
/*     */ 
/*     */   public CommonService getService()
/*     */   {
/* 140 */     return this.service;
/*     */   }
/*     */   @Autowired(required=false)
/*     */   public void setService(@Qualifier("send-commonService") CommonService service) {
/* 145 */     this.service = service;
/*     */   }
/*     */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.subProcess.sign.service.impl.SignSubApprovedServiceImpl
 * JD-Core Version:    0.6.0
 */