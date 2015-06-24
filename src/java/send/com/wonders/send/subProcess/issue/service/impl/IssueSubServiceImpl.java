/*     */ package com.wonders.send.subProcess.issue.service.impl;
/*     */ 
/*     */ import com.wonders.send.common.model.vo.UserInfo;
import com.wonders.send.common.service.CommonService;
import com.wonders.send.common.util.LoginUtil;
import com.wonders.send.external.service.ExternalService;
import com.wonders.send.subProcess.issue.model.vo.IssueSubParamVo;
import com.wonders.send.subProcess.issue.model.vo.IssueSubVo;
import com.wonders.send.subProcess.issue.service.IssueSubApprovedService;
import com.wonders.send.subProcess.issue.service.IssueSubService;
import com.wonders.send.subProcess.issue.util.IssueSubUtil;
import com.wonders.send.subProcess.util.ProcessUtil;
import com.wonders.send.util.FlowUtil;
import com.wonders.util.StringUtil;
import com.wonders.send.util.UltimusFunc;
import com.wonders.send.subProcess.sign.util.SignSubUtil;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Qualifier;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service("send-issueSubService")
/*     */ @Scope("prototype")
/*     */ @Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
/*     */ public class IssueSubServiceImpl
/*     */   implements IssueSubService
/*     */ {
/*     */   private CommonService commonService;
/*     */   private IssueSubApprovedService issueSubApprovedService;
/*     */   private ExternalService externalService;
/*     */   private IssueSubParamVo params;
/*     */   private IssueSubVo vo;
/*     */   private UserInfo userInfo;
/*  53 */   DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */ 
/*     */   public void init(IssueSubParamVo params)
/*     */   {
/*  57 */     this.params = params;
/*  58 */     this.vo = params.vo;
/*  59 */     this.userInfo = params.userInfo;
/*     */   }
/*     */ 
/*     */   public void flowStepSecretaryDeal(IssueSubParamVo params)
/*     */   {
/*  64 */     init(params);
/*  65 */     if (flowSecretaryDeal())
/*  66 */       this.issueSubApprovedService.saveApprovedInfo(params);
/*     */   }
/*     */ 
/*     */   public void flowStepLeaderDeal(IssueSubParamVo params)
/*     */   {
/*  75 */     init(params);
/*  76 */     if (flowLeaderDeal())
/*  77 */       this.issueSubApprovedService.saveApprovedInfo(params);
/*     */   }
/*     */ 
/*     */   private boolean flowSecretaryDeal()
/*     */   {
/*  85 */     String taskUserLoginName = LoginUtil.getUserLoginName(this.userInfo);
/*  86 */     String pname = this.params.getProcessParamValue("pname");
/*  87 */     String pincident = this.params.getProcessParamValue("pincident");
/*  88 */     String processName = this.params.getProcessParamValue("cname");
/*  89 */     String incidentNo = this.params.getProcessParamValue("cincident");
/*  90 */     String steplabel = this.params.getProcessParamValue("steplabel");
/*  91 */     this.vo.initList();
/*  92 */     String summary = FlowUtil.getSummaryByProcessInfo(processName, incidentNo);
/*  93 */     Map map = new HashMap();
/*     */ 
/* 145 */     Map map2 = new HashMap();
/* 146 */     map2.put("process", "签发领导子流程");
/* 147 */     map2.put("incident", incidentNo);
/* 150 */     map2.put("operateTime", this.df.format(new Date()));
/* 151 */     map2.put("summary", summary);

/*  95 */     String suggest = StringUtil.getNotNullValueString(this.vo.getSuggest());
/*  96 */     String fileGroupId = StringUtil.getNotNullValueString(this.vo.getAttachId());
/*  97 */     String type = StringUtil.getNotNullValueString(this.vo.getType());
/*  98 */     String choice = StringUtil.getNotNullValueString(this.vo.getChoice());
/*  99 */     if ("0".equals(type)) {
/* 100 */       map.put("操作按钮", IssueSubUtil.getSign(this.vo));
/* 101 */       map.put("是否同意操作", StringUtil.getNotNullValueString(IssueSubUtil.getDisAgree(this.vo)));
/* 102 */       map.put("当前人的名字", this.userInfo.getUserName());
/* 103 */       map.put("部门编号", this.userInfo.getDeptId());
/* 104 */       map.put("附件值", fileGroupId);
/* 105 */       map.put("部门名称", this.userInfo.getDeptName());
/* 106 */       map.put("结点名称", steplabel);

				map2.put("step", "签发秘书");
				map2.put("operator", taskUserLoginName);
				map2.put("KEY1", IssueSubUtil.getSign(this.vo));
				map2.put("KEY2", StringUtil.getNotNullValueString(IssueSubUtil.getDisAgree(this.vo)));
				map2.put("KEY3", this.userInfo.getUserName());
				map2.put("KEY4", this.userInfo.getDeptId());
				map2.put("KEY5", fileGroupId);
				map2.put("KEY6", this.userInfo.getDeptName());
				map2.put("KEY7", steplabel);

/* 107 */       if ("5".equals(choice)) {
/* 108 */         map.put("意见", IssueSubUtil.getSign(this.vo) + suggest);
/* 109 */         map.put("是否返回秘书", Integer.valueOf(8));
/* 110 */         map.put("选项代码", IssueSubUtil.getOptionCode(this.vo));
/* 111 */         map.put("流程的实例号", pincident);
/* 112 */         map.put("流程的名称", pname);

					map2.put("optionCode", "5");
					map2.put("KEY8", IssueSubUtil.getSign(this.vo) + suggest);
					map2.put("KEY9", "8");
					map2.put("KEY10", IssueSubUtil.getOptionCode(this.vo));
					map2.put("KEY11", pincident);
					map2.put("KEY12", pname);

/*     */       } else {
/* 114 */         map.put("意见", IssueSubUtil.getSign(this.vo) + suggest);
/* 115 */         map.put("流程的实例号", incidentNo);
/* 116 */         map.put("流程的名称", processName);
/* 117 */         map.put("是否返回秘书", Integer.valueOf(0));

					map2.put("optionCode", "1");
					map2.put("KEY8", IssueSubUtil.getSign(this.vo) + suggest);
					map2.put("KEY9", "0");
					map2.put("KEY11", incidentNo);
					map2.put("KEY12", processName);
/*     */       }
/*     */     }
/* 122 */     else if ("1".equals(type)) {
/* 123 */       map.put("意见", IssueSubUtil.getSign(this.vo) + suggest);
				map.put("是否返回秘书", Integer.valueOf(0));
/* 115 */       map.put("流程的实例号", incidentNo);
/* 116 */       map.put("流程的名称", processName);

				String leaderId = "";
/* 160 */       String info = ProcessUtil.getLeaderInfo(processName, incidentNo);
/* 161 */       if ((info.length() > 0) && (info.split(",").length > 0)) {
/* 162 */         leaderId = info.split(",")[0];
/*     */       }
				taskUserLoginName = "ST/" + leaderId;
				steplabel = "签发领导";
/*     */ 
/* 166 */       map2.put("step", "签发领导");
/* 167 */       map2.put("operator", "ST/" + leaderId);
/* 168 */       map2.put("optionCode", "6");
/* 169 */       map2.put("KEY2", "0");
/* 170 */       map2.put("KEY1", IssueSubUtil.getSign(this.vo) + suggest);
/* 171 */       map2.put("KEY3", incidentNo);
/* 172 */       map2.put("KEY4", processName);
/*     */     }
//System.out.println("map======"+map);
/*     */ 
/* 128 */     boolean flag = ProcessUtil.launchProcessStep(processName, taskUserLoginName, Integer.parseInt(incidentNo), steplabel, summary, "", map);
				//boolean flag = UltimusFunc.dealProcess(map2);  
				return flag;
/*     */   }
/*     */ 
/*     */   private boolean flowLeaderDeal()
/*     */   {
/* 134 */     String taskUserLoginName = LoginUtil.getUserLoginName(this.userInfo);
/*     */ 
/* 138 */     String processName = this.params.getProcessParamValue("cname");
/* 139 */     String incidentNo = this.params.getProcessParamValue("cincident");
/* 140 */     String steplabel = this.params.getProcessParamValue("steplabel");
/* 141 */     this.vo.initList();
/* 142 */     String summary = FlowUtil.getSummaryByProcessInfo(processName, incidentNo);
/* 143 */     Map map = new HashMap();
/*     */ 
/* 145 */     Map map2 = new HashMap();
/* 146 */     map2.put("process", "签发领导子流程");
/* 147 */     map2.put("incident", incidentNo);
/* 148 */     map2.put("step", "签发领导");
/* 149 */     map2.put("operator", taskUserLoginName);
/* 150 */     map2.put("operateTime", this.df.format(new Date()));
/* 151 */     map2.put("summary", summary);
/*     */ 
/* 153 */     String choice = StringUtil.getNotNullValueString(this.vo.getChoice());
/* 154 */     if ("3".equals(choice)) {
/* 155 */       map.put("是否返回秘书", Integer.valueOf(1));
/*     */ 
/* 157 */       map2.put("optionCode", "3");
/* 158 */       map2.put("KEY1", "1");
/*     */     } else {
/* 160 */       map.put("是否返回秘书", Integer.valueOf(0));
/*     */ 
/* 162 */       map2.put("optionCode", "1");
/* 163 */       map2.put("KEY1", "0");
/*     */     }
/*     */ 
/* 168 */     //boolean flag = UltimusFunc.dealProcess(map2);
				boolean flag = ProcessUtil.launchProcessStep(processName, taskUserLoginName, Integer.parseInt(incidentNo), steplabel, summary, "", map);
/* 169 */     return true;
/*     */   }
/*     */ 
/*     */   public CommonService getCommonService()
/*     */   {
/* 174 */     return this.commonService;
/*     */   }
/*     */   @Autowired(required=false)
/*     */   public void setCommonService(@Qualifier("send-commonService") CommonService commonService) {
/* 179 */     this.commonService = commonService;
/*     */   }
/*     */ 
/*     */   public IssueSubApprovedService getIssueSubApprovedService() {
/* 183 */     return this.issueSubApprovedService;
/*     */   }
/*     */ 
/*     */   @Autowired(required=false)
/*     */   public void setIssueSubApprovedService(@Qualifier("send-issueSubApprovedService") IssueSubApprovedService issueSubApprovedService) {
/* 189 */     this.issueSubApprovedService = issueSubApprovedService;
/*     */   }
/*     */ 
/*     */   public ExternalService getExternalService() {
/* 193 */     return this.externalService;
/*     */   }
/*     */   @Autowired(required=false)
/*     */   public void setExternalService(@Qualifier("send-externalService") ExternalService externalService) {
/* 198 */     this.externalService = externalService;
/*     */   }
/*     */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.subProcess.issue.service.impl.IssueSubServiceImpl
 * JD-Core Version:    0.6.0
 */