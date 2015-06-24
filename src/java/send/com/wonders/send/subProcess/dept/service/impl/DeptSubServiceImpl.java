/*     */ package com.wonders.send.subProcess.dept.service.impl;
/*     */ 
/*     */ import com.wonders.send.common.action.AbstractParamAction;
import com.wonders.send.common.model.vo.UserInfo;
import com.wonders.send.common.service.CommonService;
import com.wonders.send.common.util.LoginUtil;
import com.wonders.send.common.util.SimpleLogger;
import com.wonders.send.external.service.ExternalService;
import com.wonders.send.organTree.model.bo.OrganUserBo;
import com.wonders.send.subProcess.dept.model.vo.DeptSubParamVo;
import com.wonders.send.subProcess.dept.model.vo.DeptSubVo;
import com.wonders.send.subProcess.dept.service.DeptSubApprovedService;
import com.wonders.send.subProcess.dept.service.DeptSubService;
import com.wonders.send.subProcess.util.ProcessUtil;
import com.wonders.send.util.FlowUtil;
import com.wonders.util.StringUtil;
import com.wonders.send.util.UltimusFunc;

/*     */ import java.io.PrintStream;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Qualifier;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service("send-deptSubService")
/*     */ @Scope("prototype")
/*     */ @Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
/*     */ public class DeptSubServiceImpl extends AbstractParamAction
/*     */   implements DeptSubService
/*     */ {
/*  49 */   Logger log = SimpleLogger.getLogger(getClass());
/*     */   private CommonService commonService;
/*     */   private DeptSubApprovedService deptSubApprovedService;
/*     */   private ExternalService externalService;
/*     */   private DeptSubParamVo params;
/*     */   private DeptSubVo vo;
/*     */   private UserInfo userInfo;
/*  57 */   DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */ 
/*     */   public void init(DeptSubParamVo params)
/*     */   {
/*  61 */     this.params = params;
/*  62 */     this.vo = params.vo;
/*  63 */     this.userInfo = params.userInfo;
/*     */   }
/*     */ 
/*     */   public void flowStepDispatcher(DeptSubParamVo params)
/*     */   {
/*  68 */     init(params);
/*  69 */     if (flowDispatcher())
/*  70 */       this.deptSubApprovedService.saveApprovedInfo(params);
/*     */   }
/*     */ 
/*     */   public void flowStepDeal(DeptSubParamVo params)
/*     */   {
/*  78 */     init(params);
/*  79 */     if (flowDeal())
/*  80 */       this.deptSubApprovedService.saveApprovedInfo(params);
/*     */   }
/*     */ 
/*     */   public void flowStepLeaderDeal(DeptSubParamVo params)
/*     */   {
/*  89 */     init(params);
/*  90 */     if (flowLeaderDeal())
/*  91 */       this.deptSubApprovedService.saveApprovedInfo(params);
/*     */   }
/*     */ 
/*     */   private boolean flowDispatcher()
/*     */   {
/* 100 */     String taskUserLoginName = LoginUtil.getUserLoginName(this.userInfo);
/* 101 */     String processName = this.params.getProcessParamValue("cname");
/* 102 */     String incidentNo = this.params.getProcessParamValue("cincident");
/* 103 */     String steplabel = this.params.getProcessParamValue("steplabel");
/*     */ 
/* 105 */     this.vo.initList();
/* 106 */     String summary = FlowUtil.getSummaryByProcessInfo(processName, incidentNo);
/*     */ 
/* 108 */     Map map = new HashMap();

			  Map map2 = new HashMap();
/* 160 */     map2.put("process", "部门内部子流程");
/* 161 */     map2.put("incident", incidentNo);
/* 162 */     map2.put("step", "部门接受人工作分发");
/* 163 */     map2.put("operator", taskUserLoginName);
/* 164 */     map2.put("operateTime", this.df.format(new Date()));
/* 165 */     map2.put("summary", summary);
			  map2.put("optionCode", "1");
/*     */ 
/* 110 */     if (this.vo.getDealPersonList().size() > 0) {
				FlowUtil.putUltimusMap("部门处理人帐号",vo.getDealPersonList(),map);
			    map2.put("KEY1", this.vo.getDealPersonList());
/*     */     }
/* 113 */     map.put("部门处理人姓名汇总", Integer.valueOf(this.vo.getDealPersonList().size()));
/* 115 */     map.put("部门领导帐号", this.vo.getDealLeaderList());
/* 116 */     map.put("部门领导姓名", Integer.valueOf(this.vo.getDealLeaderList().size()));
/* 118 */     map.put("当前步骤名", steplabel);

			  map2.put("KEY2", this.vo.getDealPersonList().size());
			  map2.put("KEY3", this.vo.getDealLeaderList());
			  map2.put("KEY4", this.vo.getDealLeaderList().size());
			  map2.put("KEY5", steplabel);
			  
			  //System.out.println("map2===="+map2);
/*     */ 
/* 120 */     boolean flag = ProcessUtil.launchProcessStep(processName, taskUserLoginName, Integer.parseInt(incidentNo), steplabel, summary, "", map);
/*     */     //boolean flag = UltimusFunc.dealProcess(map2);
/* 122 */     return flag;
/*     */   }
/*     */ 
/*     */   private boolean flowDeal() {
/* 126 */     String taskUserLoginName = LoginUtil.getUserLoginName(this.userInfo);
/* 127 */     String processName = this.params.getProcessParamValue("cname");
/* 128 */     String incidentNo = this.params.getProcessParamValue("cincident");
/* 129 */     String steplabel = this.params.getProcessParamValue("steplabel");
/* 130 */     String summary = FlowUtil.getSummaryByProcessInfo(processName, incidentNo);
/*     */ 
/* 132 */     Map map2 = new HashMap();
/* 133 */     map2.put("process", "部门内部子流程");
/* 134 */     map2.put("incident", incidentNo);
/* 135 */     map2.put("step", "部门业务人员处理");
/* 136 */     map2.put("operator", taskUserLoginName);
/* 137 */     map2.put("operateTime", this.df.format(new Date()));
/* 138 */     map2.put("summary", summary);
/* 139 */     map2.put("optionCode", "1");
/*     */ 
/* 142 */     //boolean flag = UltimusFunc.dealProcess(map2);
				boolean flag = ProcessUtil.launchProcessStep(processName, taskUserLoginName, Integer.parseInt(incidentNo), steplabel, summary, "", null);
/* 143 */     return flag;
/*     */   }
/*     */ 
/*     */   private boolean flowLeaderDeal()
/*     */   {
/* 149 */     String taskUserLoginName = LoginUtil.getUserLoginName(this.userInfo);
/* 150 */     String processName = this.params.getProcessParamValue("cname");
/* 151 */     String incidentNo = this.params.getProcessParamValue("cincident");
		      String pname = this.params.getProcessParamValue("cname");
/* 151 */     String pid = this.params.getProcessParamValue("cincident");
/* 152 */     String steplabel = this.params.getProcessParamValue("steplabel");
/*     */ 
/* 154 */     this.vo.initList();
/* 155 */     String summary = FlowUtil.getSummaryByProcessInfo(processName, incidentNo);
/*     */ 
/* 157 */     Map map = new HashMap();
/*     */ 
/* 159 */     Map map2 = new HashMap();
/* 160 */     map2.put("process", "部门内部子流程");
/* 161 */     map2.put("incident", incidentNo);
/* 162 */     map2.put("step", "部门领导审核");
/* 163 */     map2.put("operator", taskUserLoginName);
/* 164 */     map2.put("operateTime", this.df.format(new Date()));
/* 165 */     map2.put("summary", summary);
/*     */ 
/* 168 */     String choice = StringUtil.getNotNullValueString(this.vo.getChoice());
/* 169 */     if ("1".equals(choice)) {
/* 170 */       map.put("部门处理人姓名汇总", Integer.valueOf(0));
/* 171 */       map.put("部门领导姓名", Integer.valueOf(0));
/*     */ 
/* 173 */       map2.put("optionCode", "1");
/* 174 */       map2.put("KEY1", "0");
/* 175 */       map2.put("KEY2", "0");
/*     */     }
/* 177 */     if ("2".equals(choice)) {
/* 178 */       map.put("部门处理人姓名汇总", Integer.valueOf(0));
/* 179 */       map.put("部门领导姓名", Integer.valueOf(0));
/*     */ 
/* 181 */       map2.put("optionCode", "2");
/* 182 */       map2.put("KEY1", "0");
/* 183 */       map2.put("KEY2", "0");
/*     */     }
/* 185 */     if ("3".equals(choice)) {
/* 186 */       List<OrganUserBo> listReseivers = this.externalService.getDeptReceivers((String)this.session.getAttribute("dept_id"),pname,pid,"部门接受人工作分发");
/* 187 */       String receiversId = "";
/* 188 */       String receiversName = "";
/* 189 */       if ((listReseivers != null) && (listReseivers.size() > 0)) {
/* 190 */         for (int i = 0; i < listReseivers.size(); i++) {
/* 191 */           if (i > 0) {
/* 192 */             receiversId = receiversId + ",";
/* 193 */             receiversName = receiversName + ",";
/*     */           }
/* 195 */           receiversId = receiversId + ((OrganUserBo)listReseivers.get(i)).loginName;
/* 196 */           receiversName = receiversName + ((OrganUserBo)listReseivers.get(i)).name;
/*     */         }
/*     */       }
/* 199 */       map.put("部门接收人帐号", receiversId);
/*     */ 
/* 202 */       String choice2 = StringUtil.getNotNullValueString(this.vo.getChoice2());
/* 203 */       if ("1".equals(choice2)) {
/* 204 */         map.put("返回修改", "1");
/*     */ 
/* 206 */         map2.put("optionCode", "3");
/* 207 */         map2.put("KEY1", "1");
/* 208 */         map2.put("KEY2", receiversId);
/*     */       }
/*     */ 
/* 211 */       if ("2".equals(choice2)) {
/* 212 */         map2.put("optionCode", "4");
/* 213 */         map2.put("KEY5", receiversId);
/* 214 */         if (this.vo.getDealPersonList().size() > 0) {
/* 215 */          FlowUtil.putUltimusMap("部门处理人帐号",vo.getDealPersonList(),map);
/*     */ 
/* 217 */           map2.put("KEY3", this.vo.getDealPersonList());
/*     */         }
/* 219 */         map.put("部门处理人姓名汇总", Integer.valueOf(this.vo.getDealPersonList().size()));
/*     */ 
/* 221 */         map2.put("KEY1", this.vo.getDealPersonList().size());
/*     */ 
/* 223 */         if (this.vo.getDealLeaderList().size() > 0) {
/* 224 */           map.put("部门领导帐号", this.vo.getDealLeaderList());
/*     */ 
/* 226 */           map2.put("KEY4", this.vo.getDealLeaderList());
/*     */         }
/* 228 */         map.put("部门领导姓名", Integer.valueOf(this.vo.getDealLeaderList().size()));
/*     */ 
/* 230 */         map2.put("KEY2", this.vo.getDealLeaderList().size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 237 */     System.out.println("部门领导审核选择业务人员处理==" + map2);
/*     */ 
/* 242 */     //boolean flag = UltimusFunc.dealProcess(map2);
				boolean flag = ProcessUtil.launchProcessStep(processName, taskUserLoginName, Integer.parseInt(incidentNo), steplabel, summary, "", map);
/* 243 */     return flag;
/*     */   }
/*     */ 
/*     */   public CommonService getCommonService()
/*     */   {
/* 249 */     return this.commonService;
/*     */   }
/*     */   @Autowired(required=false)
/*     */   public void setCommonService(@Qualifier("send-commonService") CommonService commonService) {
/* 254 */     this.commonService = commonService;
/*     */   }
/*     */ 
/*     */   public DeptSubApprovedService getDeptSubApprovedService() {
/* 258 */     return this.deptSubApprovedService;
/*     */   }
/*     */ 
/*     */   @Autowired(required=false)
/*     */   public void setDeptSubApprovedService(@Qualifier("send-deptSubApprovedService") DeptSubApprovedService deptSubApprovedService) {
/* 264 */     this.deptSubApprovedService = deptSubApprovedService;
/*     */   }
/*     */ 
/*     */   public ExternalService getExternalService() {
/* 268 */     return this.externalService;
/*     */   }
/*     */   @Autowired(required=false)
/*     */   public void setExternalService(@Qualifier("send-externalService") ExternalService externalService) {
/* 273 */     this.externalService = externalService;
/* 274 */     this.externalService.setToken(LoginUtil.getUserInfo(this.session).getToken());
/*     */   }
/*     */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.subProcess.dept.service.impl.DeptSubServiceImpl
 * JD-Core Version:    0.6.0
 */