/*     */ package com.wonders.send.mainProcess.send.action;
/*     */ 
/*     */ import com.opensymphony.xwork2.ModelDriven;
import com.wonders.common.model.vo.TaskUserVo;
import com.wonders.constants.LoginConstants;
import com.wonders.send.approve.model.bo.SendApprovedinfo;
import com.wonders.send.common.action.AbstractParamAction;
import com.wonders.send.common.service.CommonService;
import com.wonders.send.common.util.ActionWriter;
import com.wonders.send.common.util.LoginUtil;
import com.wonders.send.external.service.ExternalService;
import com.wonders.send.mainProcess.send.model.vo.DealFinishVo;
import com.wonders.send.organTree.model.bo.OrganUserBo;
import com.wonders.send.subProcess.util.ProcessUtil;
import com.wonders.send.util.Donull;
import com.wonders.send.util.PublicFunction;
import com.wonders.util.StringUtil;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ 
/*     */ @ParentPackage("struts-default")
/*     */ @Namespace("/send-tDocSend")
/*     */ @Controller("send-DealFinishAction")
/*     */ @Scope("prototype")
/*     */ public class DealFinishAction extends AbstractParamAction
/*     */   implements ModelDriven<DealFinishVo>
/*     */ {
/*  46 */   public DealFinishVo dealFinishVo = new DealFinishVo();
/*     */   public CommonService commonService;
			public ExternalService externalService;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */ 
/*     */   @Autowired(required=false)
/*     */   public void setCommonService(@Qualifier("send-commonService") CommonService commonService)
/*     */   {
/*  51 */     this.commonService = commonService;
/*     */   }

/*     */   @Autowired(required=false)
/*     */   public void setExternalService(@Qualifier("send-externalService") ExternalService externalService) {
/*  68 */     this.externalService = externalService;
/*  69 */     this.externalService.setToken(LoginUtil.getUserInfo(this.session).getToken());
/*     */   }
/*     */ 
/*     */   public DealFinishVo getModel()
/*     */   {
/*  57 */     return this.dealFinishVo;
/*     */   }
/*     */ 
/*     */   @Action(value="dealFinish")
/*     */   public String dealFinish() throws UnsupportedEncodingException
/*     */   {
/*  64 */     String loginName = "";
/*  65 */     String user_name = "";
/*  66 */     String user_dept_id = "";
/*  67 */     String user_dept_name = "";
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
				
///*  68 */     Cookie[] cookies = this.request.getCookies();
///*  69 */     for (Cookie cookie : cookies) {
///*  70 */       if ("loginName".equals(cookie.getName()))
///*     */       {
///*  72 */         loginName = cookie.getValue();
///*     */       }
///*  74 */       if ("userName".equals(cookie.getName()))
///*     */       {
///*  76 */         user_name = URLDecoder.decode(cookie.getValue(), "utf-8");
///*     */       }
///*  78 */       if ("deptId".equals(cookie.getName()))
///*     */       {
///*  80 */         user_dept_id = cookie.getValue();
///*     */       }
///*  82 */       if (!"deptName".equals(cookie.getName()))
///*     */         continue;
///*  84 */       user_dept_name = URLDecoder.decode(cookie.getValue(), "utf-8");
///*     */     }
/*     */ 
/*  88 */     Donull donull = new Donull();
/*  89 */     boolean level = true;
/*  90 */     Object map = new HashMap();
/*     */ 
/*  92 */     ((Map)map).put("当前处理人帐号", "ST/" + loginName);
/*  93 */     ((Map)map).put("当前处理人姓名", user_name);
/*  94 */     ((Map)map).put("当前处理人部门名", user_dept_name);
/*  95 */     ((Map)map).put("当前处理人部门ID", user_dept_id);
/*  96 */     ((Map)map).put("当前步骤名", StringUtil.getNotNullValueString(this.dealFinishVo.getStepName()));

			  Map map2 = new HashMap();
/* 174 */     map2.put("process", this.dealFinishVo.getModelName());
/* 175 */     map2.put("incident", StringUtil.getNotNullValueString(this.dealFinishVo.getIncidentNo()));
/* 176 */     map2.put("step", "办结");
/* 177 */     map2.put("operator", "ST/" + loginName);
/* 178 */     map2.put("operateTime", this.df.format(new Date()));
/*     */ 
/* 180 */     map2.put("KEY1", "ST/" + loginName);
/* 181 */     map2.put("KEY2", user_name);
/* 182 */     map2.put("KEY3", user_dept_name);
/* 183 */     map2.put("KEY4", user_dept_id);
/* 184 */     map2.put("KEY5", StringUtil.getNotNullValueString(this.dealFinishVo.getStepName()));
/*     */ 
/* 102 */     SendApprovedinfo tapproved = new SendApprovedinfo();
/* 103 */     tapproved.setProcess(StringUtil.getNotNullValueString(this.dealFinishVo.getModelName()));
/* 104 */     tapproved.setIncidentno(Long.valueOf(StringUtil.getNotNullValueString(this.dealFinishVo.getIncidentNo())));
/* 105 */     tapproved.setDept(user_dept_name);
/* 106 */     tapproved.setDeptId(user_dept_id);
/* 107 */     tapproved.setStepname(StringUtil.getNotNullValueString(this.dealFinishVo.getStepName()));
/* 108 */     tapproved.setUsername("ST/" + loginName);
/* 109 */     tapproved.setUserfullname(user_name);
/* 110 */     tapproved.setUpddate(new Date());
/* 111 */     tapproved.setAgree(Long.valueOf(1L));
/* 112 */     tapproved.setDisagree(Long.valueOf(0L));
/* 113 */     tapproved.setReturned(Long.valueOf(2L));
/* 114 */     tapproved.setStatus(Long.valueOf(1L));
/* 115 */     tapproved.setFllowFlag("0");
/* 116 */     tapproved.setReadFlag("1");
/*     */ 
/* 118 */     String choice = StringUtil.getNotNullValueString(this.dealFinishVo.getChoice());
/* 119 */     if ("1".equals(choice)) {
/* 120 */       ((Map)map).put("同意", "1");
/* 121 */       ((Map)map).put("不同意", "0");
/* 122 */       ((Map)map).put("返回修改", "0");

/* 405 */       map2.put("optionCode", "1");
				map2.put("KEY6", "1");
				map2.put("KEY7", "0");
				map2.put("KEY8", "0");
				
/* 123 */       tapproved.setRemark(StringUtil.getNotNullValueString(this.dealFinishVo.getSuggest()));
/* 124 */       tapproved.setOptionCode("PROC_FINISH_AGREE_SIGNOUT");
/* 125 */       String fileGroupId = StringUtil.getNotNullValueString(this.dealFinishVo.getAttachId());
/* 126 */       tapproved.setFileGroupId(fileGroupId);
/* 127 */     } else if ("2".equals(choice)) {
/* 128 */       if (StringUtil.getNotNullValueString(this.dealFinishVo.getSuggest2()).length() > 0) {
/* 129 */         ((Map)map).put("当前意见", StringUtil.getNotNullValueString(this.dealFinishVo.getSuggest2()));
					map2.put("KEY9", StringUtil.getNotNullValueString(this.dealFinishVo.getSuggest2()));
/*     */       }
/* 131 */       ((Map)map).put("同意", "0");
/* 132 */       ((Map)map).put("不同意", "1");
/* 133 */       ((Map)map).put("返回修改", "0");

/* 405 */       map2.put("optionCode", "2");
				map2.put("KEY6", "0");
				map2.put("KEY7", "1");
				map2.put("KEY8", "0");

/* 134 */       String xbdeptId = StringUtil.getNotNullValueString(this.dealFinishVo.getXbdeptId());
/* 135 */       if ((xbdeptId != null) && (xbdeptId.length() > 0))
/*     */       {
/* 143 */         List<OrganUserBo> listReseivers = this.externalService.getDeptReceivers(this.dealFinishVo.getXbdeptId(),this.dealFinishVo.getModelName(),this.dealFinishVo.getIncidentNo(),"部门接受人工作分发");
	/*  91 */     String receiversId = "";
	/*  92 */     String receiversName = "";
	/*  93 */     if ((listReseivers != null) && (listReseivers.size() > 0)) {
	/*  94 */       for (int i = 0; i < listReseivers.size(); i++) {
	/*  95 */         if (i > 0) {
	/*  96 */           receiversId = receiversId + ",";
	/*  97 */           receiversName = receiversName + ",";
	/*     */         }
	/*  99 */         receiversId = receiversId + ((OrganUserBo)listReseivers.get(i)).loginName;
	/* 100 */         receiversName = receiversName + ((OrganUserBo)listReseivers.get(i)).name;
	/*     */       }
	/*     */     }
	/* 103 */     this.dealFinishVo.setXbdeptsfId(receiversId);
	/* 104 */     this.dealFinishVo.setXbdeptsfName(receiversName);
	/*     */ 
	/* 106 */     String deptLeaderIdStr = "";
	/* 107 */     String deptLeaderNameStr = "";
	/* 108 */     List<OrganUserBo> listDeptLeaders = this.externalService.getDeptSingleLeader(this.dealFinishVo.getXbdeptId());
	/* 109 */     if ((listDeptLeaders != null) && (listDeptLeaders.size() > 0)) {
	/* 110 */       for (int i = 0; i < listDeptLeaders.size(); i++) {
	/* 111 */         if (i > 0) {
	/* 112 */           deptLeaderIdStr = deptLeaderIdStr + ",";
	/* 113 */           deptLeaderNameStr = deptLeaderNameStr + ",";
	/*     */         }
	/* 115 */         deptLeaderIdStr = deptLeaderIdStr + ((OrganUserBo)listDeptLeaders.get(i)).loginName;
	/* 116 */         deptLeaderNameStr = deptLeaderNameStr + ((OrganUserBo)listDeptLeaders.get(i)).name;
	/*     */       }
	/*     */     }
	/* 119 */     this.dealFinishVo.setXbdeptldId(deptLeaderIdStr);
	/* 120 */     this.dealFinishVo.setXbdeptldName(deptLeaderNameStr);
	
				  String[] deptreId = StringUtil.getNotNullValueString(this.dealFinishVo.getXbdeptsfId()).split(",");
/* 144 */         String[] deptreName = StringUtil.getNotNullValueString(this.dealFinishVo.getXbdeptsfName()).split(",");
/* 145 */         String[] deptId = xbdeptId.split(",");
/* 146 */         String[] deptName = StringUtil.getNotNullValueString(this.dealFinishVo.getXbdeptName()).split(",");
/* 147 */         String[] deptLeaderId = StringUtil.getNotNullValueString(this.dealFinishVo.getXbdeptldId()).split(",");
/* 148 */         String[] deptLeaderName = StringUtil.getNotNullValueString(this.dealFinishVo.getXbdeptldName()).split(",");
/* 149 */         if (deptreId.length > 0) {
/* 150 */           List idList = new ArrayList();
/* 151 */           List nameList = new ArrayList();
/* 152 */           List deptIdList = new ArrayList();
/* 153 */           List deptNameList = new ArrayList();
/* 154 */           List deptLeaderIdist = new ArrayList();
/* 155 */           List deptLeaderNameList = new ArrayList();
/* 156 */           for (int i = 0; i < deptreId.length; i++) {
						if(PublicFunction.judgeDeptInfo(deptId[i])){
							idList.add(deptLeaderId[i]);
							nameList.add(deptLeaderName[i]);
						}else{
							idList.add(deptreId[i]);
							nameList.add(deptreName[i]);
						}
/* 159 */             deptIdList.add(deptId[i]);
/* 160 */             deptNameList.add(deptName[i]);
/* 161 */             deptLeaderIdist.add(deptLeaderId[i]);
/* 162 */             deptLeaderNameList.add(deptLeaderName[i]);
/*     */           }
/*     */ 
/* 168 */           ((Map)map).put("协办部门ID", deptIdList);
/* 169 */           ((Map)map).put("协办部门名", deptNameList);
/* 170 */           ((Map)map).put("部门接收人姓名数组", nameList);
/* 171 */           ((Map)map).put("部门接收人帐号数组", idList);
/* 172 */           ((Map)map).put("部门领导帐号数组", deptLeaderIdist);
/* 173 */           ((Map)map).put("部门领导姓名数组", deptLeaderNameList);
/* 174 */           //System.out.println("idList->" + idList);
/* 175 */           ((Map)map).put("协办部门数量", deptreId.length);

/* 265 */           map2.put("KEY9", deptIdList);
/* 266 */           map2.put("KEY10", deptNameList);
/* 267 */           map2.put("KEY11", nameList);
/* 268 */           map2.put("KEY12", idList);
/* 269 */           map2.put("KEY13", deptLeaderIdist);
/* 270 */           map2.put("KEY14", deptLeaderNameList);
/* 271 */           map2.put("KEY15", deptreId.length);
/*     */         }
/*     */       } else {
/* 178 */         ((Map)map).put("协办部门数量", "0");

					map2.put("KEY15", "0");
/*     */       }
/* 180 */       String hqldId = StringUtil.getNotNullValueString(this.dealFinishVo.getHqldId());
/* 181 */       if ((hqldId != null) && (hqldId.length() > 0))
/*     */       {
/* 189 */         String[] hqldIds = hqldId.split(",");
/* 190 */         String[] hqldNames = StringUtil.getNotNullValueString(this.dealFinishVo.getHqldName()).split(",");
/* 191 */         String[] hqldDeptIds = StringUtil.getNotNullValueString(this.dealFinishVo.getHqldId()).split(",");
/* 192 */         String[] hqldmsIds = StringUtil.getNotNullValueString(this.dealFinishVo.getHqldmsId()).split(",");
/* 193 */         String[] hqldmsNames = StringUtil.getNotNullValueString(this.dealFinishVo.getHqldmsName()).split(",");
/* 194 */         String[] hqldmsDeptIds = StringUtil.getNotNullValueString(this.dealFinishVo.getHqldmsDeptId()).split(",");
/* 195 */         if (hqldIds.length > 0) {
/* 196 */           List ldIdList = new ArrayList();
/* 197 */           List ldNameList = new ArrayList();
/* 198 */           List ldDeptIdList = new ArrayList();
/* 199 */           List msIdList = new ArrayList();
/* 200 */           List msNameList = new ArrayList();
/* 201 */           List msDeptIdList = new ArrayList();
/* 202 */           for (int i = 0; i < hqldIds.length; i++) {
/* 203 */             ldIdList.add(hqldIds[i]);
/* 204 */             ldNameList.add(hqldNames[i]);
/*     */ 
/* 207 */             ldDeptIdList.add(hqldDeptIds[i]);
/* 208 */             msIdList.add(hqldmsIds[i]);
/* 209 */             msNameList.add(hqldmsNames[i]);
/*     */ 
/* 212 */             msDeptIdList.add(hqldmsDeptIds[i]);
/*     */           }
/*     */ 
/* 225 */           ((Map)map).put("会签领导帐号", ldIdList);
/* 226 */           ((Map)map).put("会签领导姓名", ldNameList);
/* 227 */           ((Map)map).put("会签领导秘书帐号", msIdList);
/* 228 */           ((Map)map).put("会签领导秘书姓名", msNameList);
/* 229 */           //System.out.println("ldIdList->" + ldIdList);
/* 230 */           ((Map)map).put("会签领导数量", hqldIds.length);
/* 231 */           ((Map)map).put("会签领导秘书数量", hqldIds.length);

/* 320 */           map2.put("KEY16", ldIdList);
/* 321 */           map2.put("KEY17", ldNameList);
/* 322 */           map2.put("KEY18", msIdList);
/* 323 */           map2.put("KEY19", msNameList);
/* 324 */           map2.put("KEY20", hqldIds.length);
/* 325 */           map2.put("KEY21", hqldIds.length);
/* 232 */           if (level) {
/* 233 */             ((Map)map).put("会签领导部门ID", ldDeptIdList);
/* 234 */             ((Map)map).put("会签领导秘书部门ID", msDeptIdList);

/* 331 */             map2.put("KEY22", ldDeptIdList);
/* 332 */             map2.put("KEY23", msDeptIdList);
/*     */           }
/*     */         }
/*     */       } else {
/* 238 */         ((Map)map).put("会签领导数量", "0");
/* 239 */         ((Map)map).put("会签领导秘书数量", "0");

/* 339 */         map2.put("KEY20", "0");
/* 340 */         map2.put("KEY21", "0");
/*     */       }
/* 241 */       String shldId = StringUtil.getNotNullValueString(this.dealFinishVo.getShldId());
/* 242 */       if ((shldId != null) && (shldId.length() > 0))
/*     */       {
/* 249 */         List shldDeptIdList = new ArrayList();
/* 250 */         List shldmsDeptIdList = new ArrayList();
/* 251 */         ((Map)map).put("最终领导账号", shldId);
/* 252 */         ((Map)map).put("最终领导姓名", StringUtil.getNotNullValueString(this.dealFinishVo.getShldName()));
/* 253 */         ((Map)map).put("最终领导秘书账号", StringUtil.getNotNullValueString(this.dealFinishVo.getShldmsId()));
/* 254 */         ((Map)map).put("最终领导秘书姓名", StringUtil.getNotNullValueString(this.dealFinishVo.getShldmsName()));
/* 255 */         ((Map)map).put("审核领导数量", "1");
/* 256 */         ((Map)map).put("审核领导秘书数量", "1");


/* 360 */         map2.put("KEY24", StringUtil.getNotNullValueString(this.dealFinishVo.getShldId()));
/* 361 */         map2.put("KEY25", StringUtil.getNotNullValueString(this.dealFinishVo.getShldName()));
/* 362 */         map2.put("KEY26", StringUtil.getNotNullValueString(this.dealFinishVo.getShldmsId()));
/* 363 */         map2.put("KEY27", StringUtil.getNotNullValueString(this.dealFinishVo.getShldmsName()));
/* 364 */         map2.put("KEY28", "1");
/* 365 */         map2.put("KEY29", "1");

/* 257 */         if (level)
/*     */         {
/* 261 */           shldDeptIdList.add(StringUtil.getNotNullValueString(this.dealFinishVo.getShldDeptId()));
/* 262 */           shldmsDeptIdList.add(StringUtil.getNotNullValueString(this.dealFinishVo.getShldmsDeptId()));
/* 263 */           ((Map)map).put("签发领导部门ID", shldDeptIdList);
/* 264 */           ((Map)map).put("签发领导秘书部门ID", shldmsDeptIdList);
/* 265 */           ((Map)map).put("部门ID5", StringUtil.getNotNullValueString(this.dealFinishVo.getShldmsDeptId()));
/* 266 */           ((Map)map).put("部门ID5拼接", StringUtil.getNotNullValueString(this.dealFinishVo.getShldmsId()) + ":" + StringUtil.getNotNullValueString(this.dealFinishVo.getShldmsDeptId()) + "<+>");

/* 377 */           map2.put("30", shldDeptIdList);
/* 378 */           map2.put("31", shldmsDeptIdList);
/* 379 */           map2.put("32", StringUtil.getNotNullValueString(this.dealFinishVo.getShldmsDeptId()));
/*     */         }
/*     */
            //zhoushun 2014 11/26 签发领导 顾伟华 秘书 写死 为 俞楝 之后更改为配置读取
            if("新发文流程".equals(this.dealFinishVo.getModelName()) && "ST/G001000000062501".equals(this.dealFinishVo.getShldId())){
                ((Map)map).put("最终领导秘书账号", "ST/G010080000312510");
/* 254 */       ((Map)map).put("最终领导秘书姓名", "俞楝");
                ((Map)map).put("部门ID5拼接", "ST/G010080000312510" + ":" + StringUtil.getNotNullValueString(this.dealFinishVo.getShldmsDeptId()) + "<+>");
            }
            //zhoushun 2014 11/26 签发领导 顾伟华 秘书 写死 为 俞楝 之后更改为配置读取

/*     */       }
/*     */       else
/*     */       {
/* 280 */         ((Map)map).put("审核领导数量", "0");
/* 281 */         ((Map)map).put("审核领导秘书数量", "0");

/* 385 */         map2.put("KEY28", "0");
/* 386 */         map2.put("KEY29", "0");
/*     */       }
/* 283 */       tapproved.setRemark(StringUtil.getNotNullValueString(this.dealFinishVo.getSuggest2()));
/* 284 */       tapproved.setOptionCode("PROC_FINISH_RE_PROCESS");
/*     */     }
/*     */     else
/*     */     {
/* 288 */       ((Map)map).put("同意", "0");
/* 289 */       ((Map)map).put("不同意", "0");
/* 290 */       ((Map)map).put("返回修改", "1");

/* 405 */       map2.put("optionCode", "3");
				map2.put("KEY6", "0");
				map2.put("KEY7", "0");
				map2.put("KEY8", "1");
				
/* 291 */       tapproved.setRemark(StringUtil.getNotNullValueString(this.dealFinishVo.getSuggest3()));
/* 292 */       tapproved.setOptionCode("PROC_FINISH_DELIVER_BACK");
/* 293 */       String fileGroupId = StringUtil.getNotNullValueString(this.dealFinishVo.getAttachId());
/* 295 */       tapproved.setFileGroupId(fileGroupId);
/*     */     }
/*     */ 
/* 299 */     this.commonService.save(tapproved);
/* 300 */     String strSummary = StringUtil.getNotNullValueString(this.dealFinishVo.getModelName()) + "-" + donull.dealNull(this.dealFinishVo.getDocTitle());
/* 415 */     map2.put("summary", strSummary);
/* 417 */    //System.out.println("map2(banjie)" + map2);
/* 419 */     //boolean flag = UltimusFunc.dealProcess(map2);
				boolean flag = ProcessUtil.launchProcessStep(this.dealFinishVo.getModelName(), "ST/" + loginName, Integer.parseInt(StringUtil.getNotNullValueString(this.dealFinishVo.getIncidentNo())), "办结", strSummary, "", (Map)map);
/*     */ 
/* 466 */     ActionWriter aw = new ActionWriter(this.response);
/* 467 */     if (flag)
/* 468 */       aw.writeAjax("{\"if_success\":\"yes\"}");
/*     */     else {
/* 470 */       aw.writeAjax("{\"if_success\":\"no\"}");
/*     */     }
/*     */ 
/* 473 */     return (String)null;
/*     */   }
/*     */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.mainProcess.send.action.DealFinishAction
 * JD-Core Version:    0.6.0
 */