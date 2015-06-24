/*     */ package com.wonders.send.subProcess.sign.service.impl;
/*     */ 
/*     */ import com.wonders.send.common.model.vo.UserInfo;
import com.wonders.send.common.service.CommonService;
import com.wonders.send.common.util.LoginUtil;
import com.wonders.send.external.service.ExternalService;
import com.wonders.send.subProcess.sign.model.vo.SignSubParamVo;
import com.wonders.send.subProcess.sign.model.vo.SignSubVo;
import com.wonders.send.subProcess.sign.service.SignSubApprovedService;
import com.wonders.send.subProcess.sign.service.SignSubService;
import com.wonders.send.subProcess.sign.util.SignSubUtil;
import com.wonders.send.subProcess.util.ProcessUtil;
import com.wonders.send.util.FlowUtil;
import com.wonders.util.StringUtil;
import com.wonders.send.util.UltimusFunc;

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
/*     */ @Service("send-signSubService")
/*     */ @Scope("prototype")
/*     */ @Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
/*     */ public class SignSubServiceImpl
/*     */   implements SignSubService
/*     */ {
/*     */   private CommonService commonService;
/*     */   private SignSubApprovedService signSubApprovedService;
/*     */   private ExternalService externalService;
/*     */   private SignSubParamVo params;
/*     */   private SignSubVo vo;
/*     */   private UserInfo userInfo;
/*  53 */   DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */ 
/*     */   public void init(SignSubParamVo params)
/*     */   {
/*  57 */     this.params = params;
/*  58 */     this.vo = params.vo;
/*  59 */     this.userInfo = params.userInfo;
/*     */   }
/*     */ 
/*     */   public void flowStepSecretaryDeal(SignSubParamVo params)
/*     */   {
/*  64 */     init(params);
/*  65 */     if (flowSecretaryDeal())
/*  66 */       this.signSubApprovedService.saveApprovedInfo(params);
/*     */   }
/*     */ 
/*     */   public void flowStepLeaderDeal(SignSubParamVo params)
/*     */   {
/*  75 */     init(params);
/*  76 */     if (flowLeaderDeal())
/*  77 */       this.signSubApprovedService.saveApprovedInfo(params);
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
/*  95 */     Map map2 = new HashMap();
/*  96 */     map2.put("process", "会签领导子流程");
/*  97 */     map2.put("incident", incidentNo);
/*     */ 
/*  99 */     map2.put("operateTime", this.df.format(new Date()));
/* 100 */     map2.put("summary", summary);
/*     */ 
/* 102 */     String suggest = StringUtil.getNotNullValueString(this.vo.getSuggest());
/* 103 */     String fileGroupId = StringUtil.getNotNullValueString(this.vo.getAttachId());
/* 104 */     String type = StringUtil.getNotNullValueString(this.vo.getType());
/* 105 */     String choice = StringUtil.getNotNullValueString(this.vo.getChoice());

/* 159 */       String leaderId = "";
/* 160 */       String info = ProcessUtil.getLeaderInfo(processName, incidentNo);
/* 161 */       if ((info.length() > 0) && (info.split(",").length > 0)) {
/* 162 */         leaderId = info.split(",")[0];
/*     */       }

/* 106 */     if ("0".equals(type)) {
/* 107 */       map.put("操作按钮", SignSubUtil.getSign(this.vo));
/* 108 */       map.put("是否同意操作", StringUtil.getNotNullValueString(SignSubUtil.getDisAgree(this.vo)));
/* 109 */       map.put("当前人的名字", this.userInfo.getUserName());
/* 110 */       map.put("部门编号", this.userInfo.getDeptId());
/* 111 */       map.put("附件值", fileGroupId);
/* 112 */       map.put("部门名称", this.userInfo.getDeptName());
/* 113 */       map.put("结点名称", steplabel);
/*     */ 
/* 115 */       map2.put("step", "秘书");
/* 116 */       map2.put("operator", taskUserLoginName);
/* 117 */       map2.put("KEY1", SignSubUtil.getSign(this.vo));
/* 118 */       map2.put("KEY2", StringUtil.getNotNullValueString(SignSubUtil.getDisAgree(this.vo)));
/* 119 */       map2.put("KEY3", this.userInfo.getUserName());
/* 120 */       map2.put("KEY4", this.userInfo.getDeptId());
/* 121 */       map2.put("KEY5", fileGroupId);
/* 122 */       map2.put("KEY6", this.userInfo.getDeptName());
/* 123 */       map2.put("KEY7", steplabel);


/* 124 */       if ("4".equals(choice)) {
/* 125 */         map.put("是否返回秘书", Integer.valueOf(8));
/* 126 */         map.put("意见", SignSubUtil.getSign(this.vo) + suggest);
/* 127 */         map.put("流程的实例号", pincident);
/* 128 */         map.put("流程的名称", pname);
/* 129 */         map.put("选项代码", SignSubUtil.getOptionCode(this.vo));

					taskUserLoginName = "ST/" + leaderId;
					steplabel = "领导";
/*     */ 
/* 131 */         map2.put("optionCode", "4");
/* 132 */         map2.put("KEY8", "8");
/* 133 */         map2.put("KEY9", SignSubUtil.getSign(this.vo) + suggest);
/* 134 */         map2.put("KEY10", pincident);
/* 135 */         map2.put("KEY11", pname);
/* 136 */         map2.put("KEY12", SignSubUtil.getOptionCode(this.vo));

/* 166 */       	map2.put("step", "领导");
/* 167 */       	map2.put("operator", "ST/" + leaderId);

/*     */       } else {
/* 138 */         map.put("是否返回秘书", Integer.valueOf(0));
/* 139 */         map.put("意见", SignSubUtil.getSign(this.vo) + suggest);
/* 140 */         map.put("流程的实例号", incidentNo);
/* 141 */         map.put("流程的名称", processName);
/*     */ 
/* 143 */         map2.put("optionCode", "1");
/* 144 */         map2.put("KEY8", "0");
/* 145 */         map2.put("KEY9", SignSubUtil.getSign(this.vo) + suggest);
/* 146 */         map2.put("KEY10", incidentNo);
/* 147 */         map2.put("KEY11", processName);
/*     */       }
/*     */ 
/*     */     }
/* 153 */     else if ("1".equals(type)) {
/* 154 */       map.put("是否返回秘书", Integer.valueOf(0));
/* 155 */       map.put("意见", SignSubUtil.getSign(this.vo) + suggest);
/* 156 */       map.put("流程的实例号", incidentNo);
/* 157 */       map.put("流程的名称", processName);
/*     */ 

				taskUserLoginName = "ST/" + leaderId;
				steplabel = "领导";
/*     */ 
/* 166 */       map2.put("step", "领导");
/* 167 */       map2.put("operator", "ST/" + leaderId);
/* 168 */       map2.put("optionCode", "5");
/* 169 */       map2.put("KEY1", "0");
/* 170 */       map2.put("KEY2", SignSubUtil.getSign(this.vo) + suggest);
/* 171 */       map2.put("KEY3", incidentNo);
/* 172 */       map2.put("KEY4", processName);
/*     */     }
/*     */ 
/* 178 */     //boolean flag = UltimusFunc.dealProcess(map2);
				boolean flag = ProcessUtil.launchProcessStep(processName, taskUserLoginName, Integer.parseInt(incidentNo), steplabel, summary, "", map);
/* 179 */     return flag;
/*     */   }
/*     */ 
/*     */   private boolean flowLeaderDeal()
/*     */   {
/* 184 */     String taskUserLoginName = LoginUtil.getUserLoginName(this.userInfo);
/* 185 */     String processName = this.params.getProcessParamValue("cname");
/* 186 */     String incidentNo = this.params.getProcessParamValue("cincident");
/* 187 */     String steplabel = this.params.getProcessParamValue("steplabel");
/* 188 */     this.vo.initList();
/* 189 */     String summary = FlowUtil.getSummaryByProcessInfo(processName, incidentNo);
/* 190 */     Map map = new HashMap();
/*     */ 
/* 192 */     Map map2 = new HashMap();
/* 193 */     map2.put("process", "会签领导子流程");
/* 194 */     map2.put("incident", incidentNo);
/* 195 */     map2.put("step", "领导");
/* 196 */     map2.put("operator", taskUserLoginName);
/* 197 */     map2.put("operateTime", this.df.format(new Date()));
/* 198 */     map2.put("summary", summary);
/*     */ 
/* 200 */     String choice = StringUtil.getNotNullValueString(this.vo.getChoice());
/* 201 */     if ("2".equals(choice)) {
/* 202 */       map.put("是否返回秘书", Integer.valueOf(1));
/*     */ 
/* 204 */       map2.put("optionCode", "2");
/* 205 */       map2.put("KEY1", "1");
/*     */     } else {
/* 207 */       map.put("是否返回秘书", Integer.valueOf(0));
/* 208 */       map2.put("optionCode", "1");
/* 209 */       map2.put("KEY1", "0");
/*     */     }
/*     */ 
/* 214 */     //boolean flag = UltimusFunc.dealProcess(map2);
				boolean flag = ProcessUtil.launchProcessStep(processName, taskUserLoginName, Integer.parseInt(incidentNo), steplabel, summary, "", map);
/* 215 */     return flag;
/*     */   }
/*     */ 
/*     */   public CommonService getCommonService()
/*     */   {
/* 220 */     return this.commonService;
/*     */   }
/*     */   @Autowired(required=false)
/*     */   public void setCommonService(@Qualifier("send-commonService") CommonService commonService) {
/* 225 */     this.commonService = commonService;
/*     */   }
/*     */ 
/*     */   public SignSubApprovedService getSignSubApprovedService() {
/* 229 */     return this.signSubApprovedService;
/*     */   }
/*     */ 
/*     */   @Autowired(required=false)
/*     */   public void setSignSubApprovedService(@Qualifier("send-signSubApprovedService") SignSubApprovedService signSubApprovedService) {
/* 235 */     this.signSubApprovedService = signSubApprovedService;
/*     */   }
/*     */ 
/*     */   public ExternalService getExternalService() {
/* 239 */     return this.externalService;
/*     */   }
/*     */   @Autowired(required=false)
/*     */   public void setExternalService(@Qualifier("send-externalService") ExternalService externalService) {
/* 244 */     this.externalService = externalService;
/*     */   }
/*     */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.subProcess.sign.service.impl.SignSubServiceImpl
 * JD-Core Version:    0.6.0
 */