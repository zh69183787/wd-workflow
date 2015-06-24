/*     */ package com.wonders.send.subProcess.draft.action;
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
import com.wonders.send.organTree.model.bo.OrganUserBo;
import com.wonders.send.subProcess.draft.model.vo.DraftVo;
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
/*     */
/*     */
/*     */ 
/*     */ @ParentPackage("struts-default")
/*     */ @Namespace("/send-draftSubAction")
/*     */ @Controller("send-DraftSubAction")
/*     */ @Scope("prototype")
/*     */ public class DraftSubAction extends AbstractParamAction
/*     */   implements ModelDriven<DraftVo>
/*     */ {
/*  56 */   public DraftVo draftVo = new DraftVo();
/*     */   public CommonService commonService;
/*     */   public ExternalService externalService;
/*  59 */   DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */ 
/*  63 */   @Autowired(required=false)
/*     */   public void setCommonService(@Qualifier("send-commonService") CommonService commonService) { this.commonService = commonService; }
/*     */ 
/*     */   @Autowired(required=false)
/*     */   public void setExternalService(@Qualifier("send-externalService") ExternalService externalService) {
/*  68 */     this.externalService = externalService;
/*  69 */     this.externalService.setToken(LoginUtil.getUserInfo(this.session).getToken());
/*     */   }
/*     */ 
/*     */   public DraftVo getModel()
/*     */   {
/*  75 */     return this.draftVo;
/*     */   }
/*     */   @Action("leaderUpdate")
/*     */   public String leaderUpdate() throws UnsupportedEncodingException {
/*  80 */     Map map2 = new HashMap();
/*     */ 
/*  90 */     
/*     */ 
/* 140 */     String loginName = "";
/* 141 */     String user_name = "";
/* 142 */     String user_dept_id = "";
/* 143 */     String user_dept_name = "";
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
				
///* 144 */     Cookie[] cookies = this.request.getCookies();
///* 145 */     for (Cookie cookie : cookies) {
///* 146 */       if ("loginName".equals(cookie.getName()))
///*     */       {
///* 148 */         loginName = cookie.getValue();
///*     */       }
///* 150 */       if ("userName".equals(cookie.getName()))
///*     */       {
///* 152 */         user_name = URLDecoder.decode(cookie.getValue(), "utf-8");
///*     */       }
///* 154 */       if ("deptId".equals(cookie.getName()))
///*     */       {
///* 156 */         user_dept_id = cookie.getValue();
///*     */       }
///* 158 */       if (!"deptName".equals(cookie.getName()))
///*     */         continue;
///* 160 */       user_dept_name = URLDecoder.decode(cookie.getValue(), "utf-8");
///*     */     }
/*     */ 
/* 164 */     boolean level = true;
/* 165 */     Donull donull = new Donull();
/* 166 */     Map<String, Object> map = new HashMap();
/* 167 */     System.out.println("当前处理人账号->ST/" + loginName);
/* 168 */     ((Map)map).put("当前处理人帐号", "ST/" + loginName);
/* 169 */     ((Map)map).put("当前处理人姓名", user_name);
/* 170 */     ((Map)map).put("当前处理人部门名", user_dept_name);
/* 171 */     ((Map)map).put("当前处理人部门ID", user_dept_id);
/* 172 */     ((Map)map).put("当前步骤名", StringUtil.getNotNullValueString(this.draftVo.getStepName()));
/*     */ 
/* 174 */     map2.put("process", "起草子流程");
/* 175 */     map2.put("incident", StringUtil.getNotNullValueString(this.draftVo.getPinstanceId()));
/* 176 */     map2.put("step", "审稿");
/* 177 */     map2.put("operator", "ST/" + loginName);
/* 178 */     map2.put("operateTime", this.df.format(new Date()));
/*     */ 
/* 180 */     map2.put("KEY1", "ST/" + loginName);
/* 181 */     map2.put("KEY2", user_name);
/* 182 */     map2.put("KEY3", user_dept_name);
/* 183 */     map2.put("KEY4", user_dept_id);
/* 184 */     map2.put("KEY5", StringUtil.getNotNullValueString(this.draftVo.getStepName()));
/*     */ 
/* 187 */     SendApprovedinfo tapproved = new SendApprovedinfo();
/* 188 */     tapproved.setProcess(StringUtil.getNotNullValueString(this.draftVo.getModelName()));
/* 189 */     tapproved.setIncidentno(Long.valueOf(StringUtil.getNotNullValueString(this.draftVo.getIncidentNo())));
/* 190 */     tapproved.setDept(user_dept_name);
/* 191 */     tapproved.setDeptId(user_dept_id);
/* 192 */     tapproved.setStepname(StringUtil.getNotNullValueString(this.draftVo.getStepName()));
/* 193 */     tapproved.setUsername("ST/" + loginName);
/* 194 */     tapproved.setUserfullname(user_name);
/* 195 */     tapproved.setUpddate(new Date());
/* 196 */     tapproved.setAgree(Long.valueOf(1L));
/* 197 */     tapproved.setDisagree(Long.valueOf(0L));
/* 198 */     tapproved.setReturned(Long.valueOf(2L));
/* 199 */     tapproved.setStatus(Long.valueOf(1L));
/* 200 */     tapproved.setFllowFlag("0");
/* 201 */     tapproved.setReadFlag("1");
/*     */ 
/* 204 */     if ("1".equals(StringUtil.getNotNullValueString(this.draftVo.getChoice()))) {
/* 205 */       if ((StringUtil.getNotNullValueString(this.draftVo.getSuggest()) != null) && (StringUtil.getNotNullValueString(this.draftVo.getSuggest()).length() > 0)) {
/* 206 */         ((Map)map).put("当前意见", StringUtil.getNotNullValueString(this.draftVo.getSuggest()));
/*     */       }
/* 208 */       ((Map)map).put("同意", "1");
/* 209 */       ((Map)map).put("返回修改", "0");
/*     */ 
/* 211 */       map2.put("optionCode", "1");
/* 212 */       map2.put("KEY6", "1");
/* 213 */       map2.put("KEY7", "0");
/* 214 */       if ((StringUtil.getNotNullValueString(this.draftVo.getXbdeptId()) != null) && (StringUtil.getNotNullValueString(this.draftVo.getXbdeptId()).length() > 0)) {
				  List<OrganUserBo> listReseivers = this.externalService.getDeptReceivers(this.draftVo.getXbdeptId(),this.draftVo.getModelName(),this.draftVo.getIncidentNo(),"部门接受人工作分发");
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
	/* 103 */     this.draftVo.setXbdeptsfId(receiversId);
	/* 104 */     this.draftVo.setXbdeptsfName(receiversName);
	/*     */ 
	/* 106 */     String deptLeaderIdStr = "";
	/* 107 */     String deptLeaderNameStr = "";
	/* 108 */     List<OrganUserBo> listDeptLeaders = this.externalService.getDeptSingleLeader(this.draftVo.getXbdeptId());
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
	/* 119 */     this.draftVo.setXbdeptldId(deptLeaderIdStr);
	/* 120 */     this.draftVo.setXbdeptldName(deptLeaderNameStr);
	
	System.out.println("************************************");
/* 216 */         //System.out.println("xbdeptId=:" + StringUtil.getNotNullValueString(this.draftVo.getXbdeptId()));
/* 217 */         //System.out.println("xbdeptsfName=:" + StringUtil.getNotNullValueString(this.draftVo.getXbdeptsfName()));
/* 218 */         //System.out.println("xbdeptsfId=:" + StringUtil.getNotNullValueString(this.draftVo.getXbdeptsfId()));
/* 219 */        // System.out.println("xbdeptName=:" + StringUtil.getNotNullValueString(this.draftVo.getXbdeptName()));
/* 220 */        // System.out.println("xbdeptldId=:" + StringUtil.getNotNullValueString(this.draftVo.getXbdeptldId()));
/* 221 */         //System.out.println("xbdeptldName=:" + StringUtil.getNotNullValueString(this.draftVo.getXbdeptldName()));
/* 222 */         String[] deptreId = StringUtil.getNotNullValueString(this.draftVo.getXbdeptsfId()).split(",");
/* 223 */         String[] deptreName = StringUtil.getNotNullValueString(this.draftVo.getXbdeptsfName()).split(",");
/* 224 */         String[] deptId = StringUtil.getNotNullValueString(this.draftVo.getXbdeptId()).split(",");
/* 225 */         String[] deptName = StringUtil.getNotNullValueString(this.draftVo.getXbdeptName()).split(",");
/* 226 */         String[] deptLeaderId = StringUtil.getNotNullValueString(this.draftVo.getXbdeptldId()).split(",");
/* 227 */         String[] deptLeaderName = StringUtil.getNotNullValueString(this.draftVo.getXbdeptldName()).split(",");
/* 228 */         if (deptreId.length > 0) {
/* 229 */           List idList = new ArrayList();
/* 230 */           List nameList = new ArrayList();
/* 231 */           List deptIdList = new ArrayList();
/* 232 */           List deptNameList = new ArrayList();
/* 233 */           List deptLeaderIdist = new ArrayList();
/* 234 */           List deptLeaderNameList = new ArrayList();
/* 235 */           for (int i = 0; i < deptreId.length; i++)
/*     */           {
						if(PublicFunction.judgeDeptInfo(deptId[i])){
							idList.add(deptLeaderId[i]);
							nameList.add(deptLeaderName[i]);
						}else{
							idList.add(deptreId[i]);
							nameList.add(deptreName[i]);
						}
/* 240 */             deptIdList.add(deptId[i]);
/* 241 */             deptNameList.add(deptName[i]);
/* 242 */             deptLeaderIdist.add(deptLeaderId[i]);
/* 243 */             deptLeaderNameList.add(deptLeaderName[i]);
/*     */ 
/*     */           }
/*     */ 
/* 255 */           ((Map)map).put("协办部门ID", deptIdList);
/* 256 */           ((Map)map).put("协办部门名", deptNameList);
/* 257 */           ((Map)map).put("部门接收人姓名数组", nameList);
/* 258 */           ((Map)map).put("部门接收人帐号数组", idList);
/* 259 */           ((Map)map).put("部门领导帐号数组", deptLeaderIdist);
/* 260 */           ((Map)map).put("部门领导姓名数组", deptLeaderNameList);
/* 261 */           //System.out.println("idList->" + idList);
/* 262 */           ((Map)map).put("协办部门数量", deptreId.length);
/*     */ 
/* 265 */           map2.put("KEY8", deptIdList);
/* 266 */           map2.put("KEY9", deptNameList);
/* 267 */           map2.put("KEY10", nameList);
/* 268 */           map2.put("KEY11", idList);
/* 269 */           map2.put("KEY12", deptLeaderIdist);
/* 270 */           map2.put("KEY13", deptLeaderNameList);
/* 271 */           map2.put("KEY14", deptreId.length);
/*     */         }
/*     */       } else {
/* 274 */         ((Map)map).put("协办部门数量", "0");
/*     */ 
/* 276 */         map2.put("KEY14", "0");
/*     */       }
/* 278 */       if ((StringUtil.getNotNullValueString(this.draftVo.getHqldId()) != null) && (StringUtil.getNotNullValueString(this.draftVo.getHqldId()).length() > 0)) {
/* 279 */        // System.out.println("************************************");
/* 280 */        // System.out.println("hqldId=:" + StringUtil.getNotNullValueString(this.draftVo.getHqldId()));
/* 281 */        // System.out.println("hqldName=:" + StringUtil.getNotNullValueString(this.draftVo.getHqldName()));
/* 282 */        // System.out.println("hqldDeptId=:" + StringUtil.getNotNullValueString(this.draftVo.getHqldDeptId()));
/* 283 */         //System.out.println("hqldmsId=:" + StringUtil.getNotNullValueString(this.draftVo.getHqldmsId()));
/* 284 */        // System.out.println("hqldmsName=:" + StringUtil.getNotNullValueString(this.draftVo.getHqldmsName()));
/* 285 */         //System.out.println("hqldmsDeptId=:" + StringUtil.getNotNullValueString(this.draftVo.getHqldmsDeptId()));
/* 286 */         String[] hqldIds = StringUtil.getNotNullValueString(this.draftVo.getHqldId()).split(",");
/* 287 */         String[] hqldNames = StringUtil.getNotNullValueString(this.draftVo.getHqldName()).split(",");
/* 288 */         String[] hqldDeptIds = StringUtil.getNotNullValueString(this.draftVo.getHqldDeptId()).split(",");
/* 289 */         String[] hqldmsIds = StringUtil.getNotNullValueString(this.draftVo.getHqldmsId()).split(",");
/* 290 */         String[] hqldmsNames = StringUtil.getNotNullValueString(this.draftVo.getHqldmsName()).split(",");
/* 291 */         String[] hqldmsDeptIds = StringUtil.getNotNullValueString(this.draftVo.getHqldmsDeptId()).split(",");
/* 292 */         if (hqldIds.length > 0) {
/* 293 */           List ldIdList = new ArrayList();
/* 294 */           List ldNameList = new ArrayList();
/* 295 */           List ldDeptIdList = new ArrayList();
/* 296 */           List msIdList = new ArrayList();
/* 297 */           List msNameList = new ArrayList();
/* 298 */           List msDeptIdList = new ArrayList();
/* 299 */           for (int i = 0; i < hqldIds.length; i++) {
/* 300 */             ldIdList.add(hqldIds[i]);
/* 301 */             ldNameList.add(hqldNames[i]);
/*     */ 
/* 304 */             ldDeptIdList.add(hqldDeptIds[i]);
/* 305 */             msIdList.add(hqldmsIds[i]);
/* 306 */             msNameList.add(hqldmsNames[i]);
/*     */ 
/* 309 */             msDeptIdList.add(hqldmsDeptIds[i]);
/*     */           }
/*     */ 
/* 312 */           ((Map)map).put("会签领导帐号", ldIdList);
/* 313 */           ((Map)map).put("会签领导姓名", ldNameList);
/* 314 */           ((Map)map).put("会签领导秘书帐号", msIdList);
/* 315 */           ((Map)map).put("会签领导秘书姓名", msNameList);
/* 316 */           //System.out.println("ldIdList->" + ldIdList);
/* 317 */           ((Map)map).put("会签领导数量", hqldIds.length);
/* 318 */           ((Map)map).put("会签领导秘书数量", hqldIds.length);
/*     */ 
/* 320 */           map2.put("KEY15", ldIdList);
/* 321 */           map2.put("KEY16", ldNameList);
/* 322 */           map2.put("KEY17", msIdList);
/* 323 */           map2.put("KEY18", msNameList);
/* 324 */           map2.put("KEY19", hqldIds.length);
/* 325 */           map2.put("KEY20", hqldIds.length);
/*     */ 
/* 327 */           if (level) {
/* 328 */             ((Map)map).put("会签领导部门ID", ldDeptIdList);
/* 329 */             ((Map)map).put("会签领导秘书部门ID", msDeptIdList);
/*     */ 
/* 331 */             map2.put("KEY21", ldDeptIdList);
/* 332 */             map2.put("KEY22", msDeptIdList);
/*     */           }
/*     */         }
/*     */       } else {
/* 336 */         ((Map)map).put("会签领导数量", "0");
/* 337 */         ((Map)map).put("会签领导秘书数量", "0");
/*     */ 
/* 339 */         map2.put("KEY19", "0");
/* 340 */         map2.put("KEY20", "0");
/*     */       }
/*     */ 
/* 344 */       if ((StringUtil.getNotNullValueString(this.draftVo.getShldId()) != null) && (StringUtil.getNotNullValueString(this.draftVo.getShldId()).length() > 0)) {
/* 345 */        // System.out.println("************************************");
/* 346 */        // System.out.println("shldId=:" + StringUtil.getNotNullValueString(this.draftVo.getShldId()));
/* 347 */        // System.out.println("shldName=:" + StringUtil.getNotNullValueString(this.draftVo.getShldName()));
/* 348 */        // System.out.println("shldmsId=:" + StringUtil.getNotNullValueString(this.draftVo.getShldmsId()));
/* 349 */        // System.out.println("shldmsName=:" + StringUtil.getNotNullValueString(this.draftVo.getShldmsName()));
/* 350 */        // System.out.println("shldmsDeptId=:" + StringUtil.getNotNullValueString(this.draftVo.getShldmsDeptId()));
/* 351 */         List shldDeptIdList = new ArrayList();
/* 352 */         List shldmsDeptIdList = new ArrayList();
/* 353 */         ((Map)map).put("最终领导账号", StringUtil.getNotNullValueString(this.draftVo.getShldId()));
/* 354 */         ((Map)map).put("最终领导姓名", StringUtil.getNotNullValueString(this.draftVo.getShldName()));
/* 355 */         ((Map)map).put("最终领导秘书账号", StringUtil.getNotNullValueString(this.draftVo.getShldmsId()));
/* 356 */         ((Map)map).put("最终领导秘书姓名", StringUtil.getNotNullValueString(this.draftVo.getShldmsName()));
/* 357 */         ((Map)map).put("审核领导数量", "1");
/* 358 */         ((Map)map).put("审核领导秘书数量", "1");
/*     */ 
/* 360 */         map2.put("KEY23", StringUtil.getNotNullValueString(this.draftVo.getShldId()));
/* 361 */         map2.put("KEY24", StringUtil.getNotNullValueString(this.draftVo.getShldName()));
/* 362 */         map2.put("KEY25", StringUtil.getNotNullValueString(this.draftVo.getShldmsId()));
/* 363 */         map2.put("KEY26", StringUtil.getNotNullValueString(this.draftVo.getShldmsName()));
/* 364 */         map2.put("KEY27", "1");
/* 365 */         map2.put("KEY28", "1");
/* 366 */         if (level)
/*     */         {
/* 370 */           shldDeptIdList.add(StringUtil.getNotNullValueString(this.draftVo.getShldDeptId()));
/* 371 */           shldmsDeptIdList.add(StringUtil.getNotNullValueString(this.draftVo.getShldmsDeptId()));
/* 372 */           ((Map)map).put("签发领导部门ID", shldDeptIdList);
/* 373 */           ((Map)map).put("签发领导秘书部门ID", shldmsDeptIdList);
/* 374 */           ((Map)map).put("部门ID5", StringUtil.getNotNullValueString(this.draftVo.getShldmsDeptId()));
/*     */ 
/* 377 */           map2.put("29", shldDeptIdList);
/* 378 */           map2.put("30", shldmsDeptIdList);
/* 379 */           map2.put("31", StringUtil.getNotNullValueString(this.draftVo.getShldmsDeptId()));
/*     */         }

            //zhoushun 2014 11/26 签发领导 顾伟华 秘书 写死 为 俞楝 之后更改为配置读取
            if("新发文流程".equals(this.draftVo.getModelName()) && "ST/G001000000062501".equals(this.draftVo.getShldId())){
                ((Map)map).put("最终领导秘书账号", "ST/G010080000312510");
/* 254 */       ((Map)map).put("最终领导秘书姓名", "俞楝");
            }
            //zhoushun 2014 11/26 签发领导 顾伟华 秘书 写死 为 俞楝 之后更改为配置读取

/*     */       } else {
/* 382 */         ((Map)map).put("审核领导数量", "0");
/* 383 */         ((Map)map).put("审核领导秘书数量", "0");
/*     */ 
/* 385 */         map2.put("KEY27", "0");
/* 386 */         map2.put("KEY28", "0");
/*     */       }
/*     */ 
/* 398 */       tapproved.setRemark(StringUtil.getNotNullValueString(this.draftVo.getSuggest()));
/* 399 */       tapproved.setOptionCode("PRE_CHECK_PASS");
/* 400 */       this.commonService.save(tapproved);
/*     */     } else {
/* 402 */       ((Map)map).put("同意", "0");
/* 403 */       ((Map)map).put("返回修改", "1");
/*     */ 
/* 405 */       map2.put("optionCode", "2");
/* 406 */       map2.put("KEY6", "0");
/* 407 */       map2.put("KEY7", "1");
/*     */ 
/* 409 */       tapproved.setRemark(StringUtil.getNotNullValueString(this.draftVo.getSuggest2()));
/* 410 */       tapproved.setOptionCode("PRE_CHECK_FAIL");
/* 411 */       this.commonService.save(tapproved);
/*     */     }
/*     */ 
/* 414 */     String strSummary = StringUtil.getNotNullValueString(this.draftVo.getModelName()) + "-" + donull.dealNull(this.draftVo.getDocTitle());
/* 415 */     map2.put("summary", strSummary);
/*     */ 
/* 417 */    // System.out.println("map2(shengao)==" + map2);
/*     */ 		boolean flag = ProcessUtil.launchProcessStep("起草子流程", "ST/" + loginName, Integer.parseInt(this.draftVo.getPinstanceId()), "审稿", strSummary, "", map);
/* 419 */     //boolean flag = UltimusFunc.dealProcess(map2);
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
/*     */   @Action("draftUpdate")
/*     */   public String draftUpdate() throws UnsupportedEncodingException {
/* 478 */     String loginName = "";
/* 479 */     String user_name = "";
/* 480 */     String user_dept_id = "";
/* 481 */     String user_dept_name = "";

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
				
///* 482 */     Cookie[] cookies = this.request.getCookies();
///* 483 */     for (Cookie cookie : cookies) {
///* 484 */       if ("loginName".equals(cookie.getName()))
///*     */       {
///* 486 */         loginName = cookie.getValue();
///*     */       }
///* 488 */       if ("userName".equals(cookie.getName()))
///*     */       {
///* 490 */         user_name = URLDecoder.decode(cookie.getValue(), "utf-8");
///*     */       }
///* 492 */       if ("deptId".equals(cookie.getName()))
///*     */       {
///* 494 */         user_dept_id = cookie.getValue();
///*     */       }
///* 496 */       if (!"deptName".equals(cookie.getName()))
///*     */         continue;
///* 498 */       user_dept_name = URLDecoder.decode(cookie.getValue(), "utf-8");
///*     */     }
/*     */ 
/* 502 */     Donull donull = new Donull();
/* 503 */     Object map = new HashMap();
/* 504 */     ((Map)map).put("当前处理人帐号", "ST/" + loginName);
/* 505 */     ((Map)map).put("当前处理人姓名", user_name);
/* 506 */     ((Map)map).put("当前处理人部门名", user_dept_name);
/* 507 */     ((Map)map).put("当前处理人部门ID", user_dept_id);
/* 508 */     ((Map)map).put("当前步骤名", StringUtil.getNotNullValueString(this.draftVo.getStepName()));
/*     */ 
/* 510 */     Object map2 = new HashMap();
				String processname = "";
/* 511 */     if ("起草".equals(StringUtil.getNotNullValueString(this.draftVo.getStepName()))){
/* 512 */       ((Map)map2).put("process", "起草子流程");
				processname = "起草子流程";
/*     */     }else {
/* 514 */       ((Map)map2).put("process", this.draftVo.getModelName());
				processname = this.draftVo.getModelName();
/*     */     }
/* 516 */     ((Map)map2).put("incident", StringUtil.getNotNullValueString(this.draftVo.getPinstanceId()));
/* 517 */     ((Map)map2).put("step", StringUtil.getNotNullValueString(this.draftVo.getStepName()));
/* 518 */     ((Map)map2).put("operator", "ST/" + loginName);
/* 519 */     ((Map)map2).put("operateTime", this.df.format(new Date()));
/*     */ 
/* 521 */     ((Map)map2).put("KEY1", "ST/" + loginName);
/* 522 */     ((Map)map2).put("KEY2", user_name);
/* 523 */     ((Map)map2).put("KEY3", user_dept_name);
/* 524 */     ((Map)map2).put("KEY4", user_dept_id);
/* 525 */     ((Map)map2).put("KEY5", StringUtil.getNotNullValueString(this.draftVo.getStepName()));
/*     */ 
/* 527 */     SendApprovedinfo tapproved = new SendApprovedinfo();
/* 528 */     tapproved.setProcess(StringUtil.getNotNullValueString(this.draftVo.getModelName()));
/* 529 */     tapproved.setIncidentno(Long.valueOf(StringUtil.getNotNullValueString(this.draftVo.getIncidentNo())));
/* 530 */     tapproved.setDept(user_dept_name);
/* 531 */     tapproved.setDeptId(user_dept_id);
/* 532 */     tapproved.setStepname(StringUtil.getNotNullValueString(this.draftVo.getStepName()));
/* 533 */     tapproved.setUsername("ST/" + loginName);
/* 534 */     tapproved.setUserfullname(user_name);
/* 535 */     tapproved.setUpddate(new Date());
/* 536 */     tapproved.setAgree(Long.valueOf(1L));
/* 537 */     tapproved.setDisagree(Long.valueOf(0L));
/* 538 */     tapproved.setReturned(Long.valueOf(2L));
/* 539 */     tapproved.setStatus(Long.valueOf(1L));
/* 540 */     tapproved.setFllowFlag("0");
/* 541 */     tapproved.setReadFlag("1");
/*     */ 
/* 543 */     if ("1".equals(StringUtil.getNotNullValueString(this.draftVo.getChoice()))) {
/* 544 */       String suggest = StringUtil.getNotNullValueString(this.draftVo.getSuggest());
/* 545 */       if ((suggest != null) && (suggest.length() > 0)) {
/* 546 */         ((Map)map).put("当前意见", suggest);
/*     */       }
/* 548 */       ((Map)map).put("同意", "1");
/* 549 */       ((Map)map).put("不同意", "0");
/*     */ 
/* 551 */       ((Map)map2).put("optionCode", "1");
/* 552 */       ((Map)map2).put("KEY6", "1");
/* 553 */       ((Map)map2).put("KEY7", "0");
/* 554 */       tapproved.setRemark(suggest);
/* 555 */       tapproved.setOptionCode("MODIFY_DOC_SEND_BY_NOTION");
/* 556 */       this.commonService.save(tapproved);
/*     */ 
/* 560 */       String leader = this.request.getParameter("dept_leader");
/* 561 */       if ((leader != null) && (leader.length() > 0)) {
/* 562 */         String[] leader2 = leader.split("#");
/* 563 */         if (leader2.length > 1) {
/* 564 */           ((Map)map).put("当前处理人部门领导帐号", leader2[0]);
/* 565 */           ((Map)map).put("当前处理人部门领导姓名", leader2[1]);
/* 566 */           ((Map)map).put("部门ID1", user_dept_id);
/* 567 */           ((Map)map).put("部门ID1拼接", leader2[0] + ":" + user_dept_id + "<+>");
/*     */ 
/* 569 */           ((Map)map2).put("KEY8", leader2[0]);
/* 570 */           ((Map)map2).put("KEY9", leader2[1]);
/* 571 */           ((Map)map2).put("KEY10", user_dept_id);
/* 572 */           ((Map)map2).put("KEY11", leader2[0] + ":" + user_dept_id + "<+>");
/*     */         }
/*     */       }
/*     */     } else {
/* 576 */       ((Map)map).put("同意", "0");
/* 577 */       ((Map)map).put("不同意", "1");
/*     */ 
/* 579 */       ((Map)map2).put("optionCode", "2");
/* 580 */       ((Map)map2).put("KEY6", "0");
/* 581 */       ((Map)map2).put("KEY7", "1");
/* 582 */       tapproved.setRemark("");
/* 583 */       tapproved.setOptionCode("CANCEL_THIS_DOC_SEND");
/* 584 */       this.commonService.save(tapproved);
/*     */     }
/*     */ 
/* 587 */     String strSummary = StringUtil.getNotNullValueString(this.draftVo.getModelName()) + "-" + donull.dealNull(this.draftVo.getDocTitle());
/* 588 */     ((Map)map2).put("summary", strSummary);
/*     */ 
/* 597 */     //boolean flag = UltimusFunc.dealProcess((Map)map2);
				boolean flag = ProcessUtil.launchProcessStep(processname, "ST/" + loginName, Integer.parseInt(StringUtil.getNotNullValueString(this.draftVo.getPinstanceId())), StringUtil.getNotNullValueString(this.draftVo.getStepName()), strSummary, "", (Map)map);
/*     */ 
/* 599 */     ActionWriter aw = new ActionWriter(this.response);
/* 600 */     if (flag)
				if ("1".equals(StringUtil.getNotNullValueString(this.draftVo.getChoice()))) {
					aw.writeAjax("{\"if_success\":\"yes\",\"if_cancel\":\"no\"}");
				}else{
					aw.writeAjax("{\"if_success\":\"yes\",\"if_cancel\":\"yes\"}");
				}
/*     */     else {
/* 603 */       aw.writeAjax("{\"if_success\":\"no\"}");
/*     */     }
/* 605 */     return (String)(String)null;
/*     */   }
/*     */ }



/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.subProcess.draft.action.DraftSubAction
 * JD-Core Version:    0.6.0
 */