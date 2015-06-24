/*     */ package com.wonders.send.mainProcess.send.action;
/*     */ 
/*     */ import com.opensymphony.xwork2.ModelDriven;
import com.wonders.send.approve.model.bo.SendApprovedinfo;
import com.wonders.send.common.action.AbstractParamAction;
import com.wonders.send.common.service.CommonService;
import com.wonders.send.common.util.ActionWriter;
import com.wonders.constants.LoginConstants;
import com.wonders.send.mainProcess.send.model.vo.SendFlowVo;
import com.wonders.common.model.vo.TaskUserVo;
import com.wonders.send.subProcess.util.ProcessUtil;
import com.wonders.send.util.Donull;
import com.wonders.util.StringUtil;
import com.wonders.send.util.UltimusFunc;

/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLDecoder;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*     */ import org.apache.struts2.convention.annotation.Action;
/*     */ import org.apache.struts2.convention.annotation.Namespace;
/*     */ import org.apache.struts2.convention.annotation.ParentPackage;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Qualifier;
/*     */ import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
/*     */ 
/*     */ @ParentPackage("struts-default")
/*     */ @Namespace("/send-tDocSend")
/*     */ @Controller("send-SendFlowAction")
/*     */ @Scope("prototype")
/*     */ public class SendFlowAction extends AbstractParamAction
/*     */   implements ModelDriven<SendFlowVo>
/*     */ {
/*  53 */   public SendFlowVo sendFlowVo = new SendFlowVo();
/*     */   public CommonService commonService;
/*  55 */   DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */ 
/*  59 */   @Autowired(required=false)
/*     */   public void setCommonService(@Qualifier("send-commonService") CommonService commonService) { this.commonService = commonService;
/*     */   }
/*     */ 
/*     */   public SendFlowVo getModel()
/*     */   {
/*  65 */     return this.sendFlowVo;
/*     */   }
/*     */   @Action("deal")
/*     */   public String deal() throws UnsupportedEncodingException {
/*  70 */     String loginName = "";
/*  71 */     String user_name = "";
/*  72 */     String user_dept_id = "";
/*  73 */     String user_dept_name = "";
				HttpSession session = request.getSession();
				loginName = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.LOGINNAME));
				user_name = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.USERNAME));
				user_dept_id = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.DEPTID));
				user_dept_name = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.DEPTNAME));
				
				String assignedtouser = this.request.getParameter("assignedtouser");
				Map<String,TaskUserVo> userMap = (Map<String, TaskUserVo>) session.getAttribute(LoginConstants.DEPT_USER);
				if(userMap!=null){
					TaskUserVo taskUserVo = userMap.get(assignedtouser);
					if(taskUserVo!=null){
						loginName = taskUserVo.getLoginName().replace("ST/","");
						user_name = taskUserVo.getUserName();
						user_dept_id = taskUserVo.getDeptId();
						user_dept_name = taskUserVo.getDeptName();
					}
				}
				
///*  74 */     Cookie[] cookies = this.request.getCookies();
///*  75 */     for (Cookie cookie : cookies) {
///*  76 */       if ("loginName".equals(cookie.getName()))
///*     */       {
///*  78 */         loginName = cookie.getValue();
///*     */       }
///*  80 */       if ("userName".equals(cookie.getName()))
///*     */       {
///*  82 */         user_name = URLDecoder.decode(cookie.getValue(), "utf-8");
///*     */       }
///*  84 */       if ("deptId".equals(cookie.getName()))
///*     */       {
///*  86 */         user_dept_id = cookie.getValue();
///*     */       }
///*  88 */       if (!"deptName".equals(cookie.getName()))
///*     */         continue;
///*  90 */       user_dept_name = URLDecoder.decode(cookie.getValue(), "utf-8");
///*     */     }
/*     */ 
/*  94 */     Donull donull = new Donull();
/*  95 */     Object map = new HashMap();
/*     */ 
/*  97 */     Object map2 = new HashMap();
/*  98 */     ((Map)map2).put("process", this.sendFlowVo.getModelName());
/*  99 */     ((Map)map2).put("incident", StringUtil.getNotNullValueString(this.sendFlowVo.getPinstanceId()));
/* 100 */     ((Map)map2).put("operator", "ST/" + loginName);
/* 101 */     ((Map)map2).put("operateTime", this.df.format(new Date()));
/*     */ 
/* 104 */     SendApprovedinfo tapproved = new SendApprovedinfo();
/* 105 */     tapproved.setProcess(StringUtil.getNotNullValueString(this.sendFlowVo.getModelName()));
/* 106 */     tapproved.setIncidentno(Long.valueOf(StringUtil.getNotNullValueString(this.sendFlowVo.getIncidentNo())));
/* 107 */     tapproved.setDept(user_dept_name);
/* 108 */     tapproved.setDeptId(user_dept_id);
/* 109 */     tapproved.setStepname(StringUtil.getNotNullValueString(this.sendFlowVo.getStepName()));
/* 110 */     tapproved.setUsername("ST/" + loginName);
/* 111 */     tapproved.setUserfullname(user_name);
/* 112 */     tapproved.setUpddate(new Date());
/* 113 */     tapproved.setAgree(Long.valueOf(1L));
/* 114 */     tapproved.setDisagree(Long.valueOf(0L));
/* 115 */     tapproved.setReturned(Long.valueOf(2L));
/* 116 */     tapproved.setStatus(Long.valueOf(1L));
/* 117 */     tapproved.setFllowFlag("0");
/* 118 */     tapproved.setReadFlag("1");
/*     */ 
/* 120 */     String suggest = StringUtil.getNotNullValueString(this.sendFlowVo.getSuggest());
/* 121 */     if ((suggest != null) && (suggest.length() > 0)) {
/* 122 */       ((Map)map).put("当前意见", suggest);
/*     */     }
/* 124 */     tapproved.setRemark(suggest);
/* 125 */     String stepName = StringUtil.getNotNullValueString(this.sendFlowVo.getStepName());
/* 126 */     if ("核稿".equals(stepName)) {
/* 127 */       ((Map)map).put("当前处理人帐号", "ST/" + loginName);
/* 128 */       ((Map)map).put("当前处理人姓名", user_name);
/* 129 */       ((Map)map).put("当前处理人部门名", user_dept_name);
/* 130 */       ((Map)map).put("当前处理人部门ID", user_dept_id);
/* 131 */       ((Map)map).put("当前步骤名", stepName);
/*     */ 
/* 133 */       ((Map)map2).put("step", "核稿");
/* 134 */       ((Map)map2).put("KEY1", "ST/" + loginName);
/* 135 */       ((Map)map2).put("KEY2", user_name);
/* 136 */       ((Map)map2).put("KEY3", user_dept_name);
/* 137 */       ((Map)map2).put("KEY4", user_dept_id);
/* 138 */       ((Map)map2).put("KEY5", stepName);
/*     */ 
/* 140 */       if ("1".equals(StringUtil.getNotNullValueString(this.sendFlowVo.getChoice()))) {
/* 141 */         ((Map)map).put("同意", "1");
/* 142 */         ((Map)map).put("返回修改", "0");
/*     */ 
/* 144 */         ((Map)map2).put("optionCode", "1");
/* 145 */         ((Map)map2).put("KEY6", "1");
/* 146 */         ((Map)map2).put("KEY7", "0");
/* 147 */         tapproved.setOptionCode("CHECK_DRAFT_PASS");
/*     */       }
/*     */       else {
/* 150 */         ((Map)map).put("同意", "0");
/* 151 */         ((Map)map).put("返回修改", "1");
/*     */ 
/* 153 */         ((Map)map2).put("optionCode", "2");
/* 154 */         ((Map)map2).put("KEY6", "0");
/* 155 */         ((Map)map2).put("KEY7", "1");
/* 156 */         tapproved.setOptionCode("CHECK_DRAFT_RETURN_MODIFY");
/*     */       }
/* 158 */     } else if ("排版".equals(stepName)) {
/* 159 */       tapproved.setOptionCode("");
/*     */ 
/* 161 */       ((Map)map2).put("step", "排版");
/* 162 */       ((Map)map2).put("optionCode", "1");
/* 163 */     } else if ("校稿".equals(stepName)) {
/* 164 */       String fileGroupId = StringUtil.getNotNullValueString(this.sendFlowVo.getAttachId());
/* 165 */       tapproved.setFileGroupId(fileGroupId);
/* 166 */       tapproved.setOptionCode("");
/*     */ 
/* 168 */       ((Map)map2).put("step", "校稿");
/* 169 */       ((Map)map2).put("optionCode", "1");
/* 170 */     } else if ("套头".equals(stepName)) {
/* 171 */       String fileGroupId = StringUtil.getNotNullValueString(this.sendFlowVo.getAttachId());
/* 172 */       tapproved.setFileGroupId(fileGroupId);
/*     */ 
/* 174 */       ((Map)map).put("当前处理人帐号", "ST/" + loginName);
/* 175 */       ((Map)map).put("当前处理人姓名", user_name);
/* 176 */       ((Map)map).put("当前处理人部门名", user_dept_name);
/* 177 */       ((Map)map).put("当前处理人部门ID", user_dept_id);
/* 178 */       ((Map)map).put("当前步骤名", stepName);
/*     */ 
/* 180 */       ((Map)map2).put("step", "套头");
/* 181 */       ((Map)map2).put("KEY1", "ST/" + loginName);
/* 182 */       ((Map)map2).put("KEY2", user_name);
/* 183 */       ((Map)map2).put("KEY3", user_dept_name);
/* 184 */       ((Map)map2).put("KEY4", user_dept_id);
/* 185 */       ((Map)map2).put("KEY5", stepName);
/*     */ 
/* 187 */       if ("1".equals(StringUtil.getNotNullValueString(this.sendFlowVo.getChoice()))) {
/* 188 */         ((Map)map).put("同意", "1");
/* 189 */         ((Map)map).put("返回修改", "0");
/*     */ 
/* 191 */         ((Map)map2).put("optionCode", "1");
/* 192 */         ((Map)map2).put("KEY6", "1");
/* 193 */         ((Map)map2).put("KEY7", "0");
/* 194 */         tapproved.setOptionCode("COVER_CONFIRM_SEND");
/*     */       } else {
/* 196 */         ((Map)map).put("同意", "0");
/* 197 */         ((Map)map).put("返回修改", "1");
/*     */ 
/* 199 */         ((Map)map2).put("optionCode", "2");
/* 200 */         ((Map)map2).put("KEY6", "0");
/* 201 */         ((Map)map2).put("KEY7", "1");
/* 202 */         tapproved.setOptionCode("COVER_DELIVER_BACK");
/*     */       }
/*     */     }
/* 205 */     this.commonService.save(tapproved);
/* 206 */     String strSummary = StringUtil.getNotNullValueString(this.sendFlowVo.getModelName()) + "-" + donull.dealNull(this.sendFlowVo.getDocTitle());
/* 207 */     ((Map)map2).put("summary", strSummary);
/*     */ 
/* 212 */     //boolean flag = UltimusFunc.dealProcess((Map)map2);
/*     */ 		boolean flag = ProcessUtil.launchProcessStep(this.sendFlowVo.getModelName(), "ST/" + loginName, Integer.parseInt(StringUtil.getNotNullValueString(this.sendFlowVo.getPinstanceId())), stepName, strSummary, "", (Map)map);
				//boolean flag = true;
/* 214 */     ActionWriter aw = new ActionWriter(this.response);
/* 215 */     if (flag)
/* 216 */       aw.writeAjax("{\"if_success\":\"yes\"}");
/*     */     else {
/* 218 */       aw.writeAjax("{\"if_success\":\"no\"}");
/*     */     }
/* 220 */     return (String)(String)null;
/*     */   }
/*     */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.mainProcess.send.action.SendFlowAction
 * JD-Core Version:    0.6.0
 */