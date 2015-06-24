/*     */ package com.wonders.send.mainProcess.send.action;
/*     */ 
/*     */ import com.opensymphony.xwork2.ModelDriven;
import com.wonders.common.model.vo.TaskUserVo;
import com.wonders.constants.CommonConstants;
import com.wonders.constants.LoginConstants;
import com.wonders.deptDoc.service.DeptDocService;
import com.wonders.send.approve.model.bo.SendApprovedinfo;
import com.wonders.send.approve.service.TApprovedinfoService;
import com.wonders.send.common.action.AbstractParamAction;
import com.wonders.send.common.service.CommonService;
import com.wonders.send.common.util.ActionWriter;
import com.wonders.send.common.util.LoginUtil;
import com.wonders.send.constant.Constants;
import com.wonders.send.external.service.ExternalService;
import com.wonders.send.mainProcess.send.model.bo.TDocSend;
import com.wonders.send.mainProcess.send.model.bo.TNormativeDoc;
import com.wonders.send.mainProcess.send.service.TDocSendService;
import com.wonders.send.model.bo.*;
import com.wonders.send.organTree.model.bo.OrganUserBo;
import com.wonders.send.util.*;
import com.wonders.util.PWSUtil;
import com.wonders.util.StringUtil;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
/*     */ 
/*     */ @ParentPackage("struts-default")
/*     */ @Namespace("/send-tDocSend")
/*     */ @Controller("send-TDocSendAction")
/*     */ @Scope("prototype")
/*     */ public class TDocSendAction extends AbstractParamAction
/*     */   implements ModelDriven<TDocSend>
/*     */ {

/*  56 */   public TDocSend tDocSend = new TDocSend();
			//部门文件柜 2014-10-18 zhoushun
			@Autowired
			@Qualifier("docSendDeptDocService")
			private DeptDocService deptDocService;

			@Autowired
			private TDocSendService tDocSendService;
/*     */   public CommonService commonService;
/*     */   public ExternalService externalService;
/*     */   public TApprovedinfoService tApprovedInfoService;
/*     */   //private static final String sendType = "120403";
/*     */   //private static final String strProcessName = ProcessName.tDocSendProcessName;
/*  62 */   DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*  63 */   SimpleDateFormat sdf_simple = new SimpleDateFormat("yyyy-MM-dd");
/*     */
/*  67 */   @Autowired(required=false)
/*     */   public void setExternalService(@Qualifier("send-externalService") ExternalService externalService) { this.externalService = externalService;
/*  68 */     this.externalService.setToken(LoginUtil.getUserInfo(this.session).getToken()); }
/*     */
/*     */   @Autowired(required=false)
/*     */   public void setCommonService(@Qualifier("send-commonService") CommonService commonService) {
/*  73 */     this.commonService = commonService;
/*     */   }
/*     */   @Autowired(required=false)
/*     */   public void setTApprovedinfoService(@Qualifier("send-tApprovedInfoService") TApprovedinfoService tApprovedInfoService) {
/*  78 */     this.tApprovedInfoService = tApprovedInfoService;
/*     */   }
/*     */
/*     */   public TDocSend getModel()
/*     */   {
/*  84 */     return this.tDocSend;
/*     */   }
/*     */

    @Action(value="queryByCode")
    public String queryByCode(){
        String code1 = StringUtil.getNotNullValueString(this.request.getParameter("code1"));
        String code2 = StringUtil.getNotNullValueString(this.request.getParameter("code2"));
        String code3 = StringUtil.getNotNullValueString(this.request.getParameter("code3"));
        String hql = "from TDocSend t where t.removed='0' and t.modelid='新发文流程' " +
                "and t.code1= ? and t.code2 = ? and t.code3= ?";
        List<Object> list = this.commonService.ListByHql(hql, new Object[]{code1, code2, code3});
        String attach = "";
        String title = "";
        String mainId = "";
        String code = "";
        if(list != null && list.size()>0){
            title = ((TDocSend)list.get(0)).getDocTitle();
            attach = ((TDocSend)list.get(0)).getContentAttMain();
            mainId = ((TDocSend)list.get(0)).getId();
            code = ((TDocSend)list.get(0)).getSendId();
        }
        ActionWriter aw = new ActionWriter(response);
        aw.writeAjax("{\"code\":\"" + code + "\",\"mainId\":\"" + mainId + "\",\"title\":\"" + title + "\",\"attach\":\"" + attach + "\"}");
        return null;
    }



    /*     */   @Action(value="toFormPage", results={
				@org.apache.struts2.convention.annotation.Result(name="update", location="/send/mainProcess/send/update.jsp"),
				@org.apache.struts2.convention.annotation.Result(name="view", location="/send/mainProcess/send/view.jsp"),
				@org.apache.struts2.convention.annotation.Result(name="viewAttach", location="/send/mainProcess/send/viewAttach.jsp")})
/*     */   public String toFormPage()
/*     */   {
/*  94 */     String stepName = URLDecoder.decode(StringUtil.getNotNullValueString(this.request.getParameter("stepName")));

/*     */ 
/*  96 */     this.request.setAttribute("stepName", stepName);
/*  97 */     String incidentNo = this.request.getParameter("incidentNo");
/*     */ 
/* 107 */     if (incidentNo != null) {
/* 108 */       String pinstanceId = this.request.getParameter("pinstanceId");
/* 109 */       incidentNo = (incidentNo == null) || (incidentNo.length() <= 0) ? "" : incidentNo;
/* 110 */       this.request.setAttribute("incidentNo", incidentNo);
/* 111 */       pinstanceId = (pinstanceId == null) || (pinstanceId.length() <= 0) ? "" : pinstanceId;
/* 112 */       this.request.setAttribute("pinstanceId", pinstanceId);
/* 113 */       String modelName = URLDecoder.decode(StringUtil.getNotNullValueString(this.request.getParameter("modelName")));
/* 114 */       this.request.setAttribute("modelName", modelName);
/* 115 */       String processName = URLDecoder.decode(StringUtil.getNotNullValueString(this.request.getParameter("processName")));
/* 116 */       this.request.setAttribute("processName", processName);
/* 117 */       String taskId = this.request.getParameter("taskId");
/* 118 */       taskId = (taskId == null) || (taskId.length() <= 0) ? "" : taskId;
/* 119 */       this.request.setAttribute("taskId", taskId);
/* 120 */       String taskUserName = this.request.getParameter("taskUserName");
/* 121 */       taskUserName = (taskUserName == null) || (taskUserName.length() <= 0) ? "" : taskUserName;
/* 122 */       this.request.setAttribute("taskUserName", taskUserName);
				String assignedtouser = this.request.getParameter("taskuser");
/* 122 */       this.request.setAttribute("assignedtouser", assignedtouser);
/* 123 */       String id = PublicFunction.getBoByFlow("T_DOC_SEND", "MODELID", modelName, "PINSTANCEID", incidentNo, "id");

String loginName = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.LOGINNAME));
Map<String,TaskUserVo> userMap = (Map<String, TaskUserVo>) session.getAttribute(LoginConstants.DEPT_USER);
if(userMap!=null){
	TaskUserVo taskUserVo = userMap.get(assignedtouser);
	if(taskUserVo!=null){
		loginName = taskUserVo.getLoginName().replace("ST/","");
	}
}
/* 125 */       if (id != null) {
/* 126 */         this.tDocSend = ((TDocSend)this.commonService.load(id, TDocSend.class));
/* 127 */         this.request.setAttribute("model", this.tDocSend);
/* 128 */         Map map = this.tApprovedInfoService.tapprovedinfoServiceByFW(this.tDocSend.getModelid(), this.tDocSend.getPinstanceid());
/* 129 */         this.request.setAttribute("pbApprovedinfoList", map.get("pbApprovedinfoList"));
/* 130 */         this.request.setAttribute("hqApprovedinfoList", map.get("hqApprovedinfoList"));
/* 131 */         this.request.setAttribute("xgApprovedinfoList", map.get("xgApprovedinfoList"));
/* 132 */         this.request.setAttribute("sgApprovedinfoList", map.get("sgApprovedinfoList"));
/* 133 */         this.request.setAttribute("qcApprovedinfoList", map.get("qcApprovedinfoList"));
/* 134 */         this.request.setAttribute("jgApprovedinfoList", map.get("jgApprovedinfoList"));
/*     */ 
/* 136 */         String hql = "from SendTasks as tasks where trim(tasks.processName) = '" + modelName + "' and tasks.incident = '" + incidentNo + "' order by tasks.startTime";
/* 137 */         List tasksList = this.commonService.findByHQL(hql);
/* 138 */         SendTasks sendTasks = new SendTasks();
/* 139 */         if ((tasksList != null) && (tasksList.size() > 0)) {
/* 140 */           sendTasks = (SendTasks)tasksList.get(0);
/*     */         }
/* 142 */         this.request.setAttribute("tasks", sendTasks);

				PublicFunction pb = new PublicFunction();

				if(processName!=null&&!"".equals(processName)&&pinstanceId!=null&&!"".equals(pinstanceId)&&stepName!=null&&!"".equals(stepName)){
					boolean ifDbx = pb.ifDbx(processName, pinstanceId, loginName,stepName);
					//System.out.println("ifDbxResult==="+ifDbx);
					if(ifDbx){
						this.request.setAttribute("ifDbx", "yes");
					}
				}

				//规范性设置-------------------------------------------------------------------
				this.request.setAttribute("normative", tDocSend.getNormative());
				this.request.setAttribute("normativeMemo", this.tDocSendService.getNormativeInfo(id));
				//--------------------------------------------------------------------------

/*     */       }
/* 144 */       if (("审稿".equals(stepName)) || ("起草".equals(stepName)) || ("拟稿人修改".equals(stepName)) || ("排版".equals(stepName)) || ("套头".equals(stepName))) {
/* 145 */         return "update";
/*     */       }
/*     */
				String print = this.request.getParameter("print");
				if(print!=null){
					this.request.setAttribute("print", print);
				}

        //规范性文件显示 seal
        String vhql = "select t,r.attach from ValidFile t,ValidRemark r where t.id = r.mainId and t.mainId = '"+id+"' and r.removed=0 and t.removed=0 order by r.operateTime desc ";
        List<Object> vItem= commonService.findByHQL(vhql);
        if(vItem !=null && vItem.size() > 0){
            Object[] oo = (Object[])vItem.get(0);
            ValidFile v = (ValidFile)oo[0];
            String attach = (String)oo[1];
            request.setAttribute("validFile",v);
            request.setAttribute("validAttach",attach);
        }
        //规范性文件显示seal

				if("发文通知".equals(stepName)){
					String hql = "from SendTodoItem t where t.app = 'sendNotice' and t.pname = '"+modelName+"' and t.pincident = "+incidentNo+" and t.stepName = '"+stepName+"' and t.status = 0 and t.removed = 0 and t.loginName = 'ST/"+loginName+"'";
					List<Object> listItem = commonService.findByHQL(hql);
					if(listItem!=null&&listItem.size()>0){
						SendTodoItem sendTodoItem = (SendTodoItem)listItem.get(0);
						sendTodoItem.setStatus(1);
						commonService.update(sendTodoItem);

						if(sendTodoItem.getData()!=null&&sendTodoItem.getData().length()>0){
							TMsgUserMassage tMsgUserMassage = (TMsgUserMassage) commonService.load(Long.valueOf(sendTodoItem.getData()), TMsgUserMassage.class);
							tMsgUserMassage.setState(1);
							tMsgUserMassage.setSeedate(df.format(new Date()));
							commonService.update(tMsgUserMassage);
						}
					}

					return "viewAttach";
				}

/* 162 */       return "view";
/*     */     }
/*     */ 
/* 165 */     this.request.setAttribute("testv", "11");

				String sendType = this.request.getParameter("sendType");
				this.request.setAttribute("sendType", sendType);
				String ngDept = this.request.getParameter("ngDept");
				this.request.setAttribute("ngDept", ngDept);
/* 166 */     return "update";
/*     */   }



@Action(value="printDocSend", results={
		@org.apache.struts2.convention.annotation.Result(name="view", location="/send/mainProcess/send/printDocSend.jsp")})
/*     */   public String printDocSend()
/*     */   {
/*  94 */     String stepName = URLDecoder.decode(StringUtil.getNotNullValueString(this.request.getParameter("stepName")));

/*     */ 
/*  96 */     this.request.setAttribute("stepName", stepName);
/*  97 */     String incidentNo = this.request.getParameter("incidentNo");
/*     */ 
/* 107 */     if (incidentNo != null) {
/* 108 */       String pinstanceId = this.request.getParameter("pinstanceId");
/* 109 */       incidentNo = (incidentNo == null) || (incidentNo.length() <= 0) ? "" : incidentNo;
/* 110 */       this.request.setAttribute("incidentNo", incidentNo);
/* 111 */       pinstanceId = (pinstanceId == null) || (pinstanceId.length() <= 0) ? "" : pinstanceId;
/* 112 */       this.request.setAttribute("pinstanceId", pinstanceId);
/* 113 */       String modelName = URLDecoder.decode(StringUtil.getNotNullValueString(this.request.getParameter("modelName")));
/* 114 */       this.request.setAttribute("modelName", modelName);
/* 115 */       String processName = URLDecoder.decode(StringUtil.getNotNullValueString(this.request.getParameter("processName")));
/* 116 */       this.request.setAttribute("processName", processName);
/* 117 */       String taskId = this.request.getParameter("taskId");
/* 118 */       taskId = (taskId == null) || (taskId.length() <= 0) ? "" : taskId;
/* 119 */       this.request.setAttribute("taskId", taskId);
/* 120 */       String taskUserName = this.request.getParameter("taskUserName");
/* 121 */       taskUserName = (taskUserName == null) || (taskUserName.length() <= 0) ? "" : taskUserName;
/* 122 */       this.request.setAttribute("taskUserName", taskUserName);
		String assignedtouser = this.request.getParameter("taskuser");
/* 122 */       this.request.setAttribute("assignedtouser", assignedtouser);
/* 123 */       String id = PublicFunction.getBoByFlow("T_DOC_SEND", "MODELID", modelName, "PINSTANCEID", incidentNo, "id");

String loginName = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.LOGINNAME));
Map<String,TaskUserVo> userMap = (Map<String, TaskUserVo>) session.getAttribute(LoginConstants.DEPT_USER);
if(userMap!=null){
TaskUserVo taskUserVo = userMap.get(assignedtouser);
if(taskUserVo!=null){
loginName = taskUserVo.getLoginName().replace("ST/","");
}
}
/* 125 */       if (id != null) {
/* 126 */         this.tDocSend = ((TDocSend)this.commonService.load(id, TDocSend.class));
/* 127 */         this.request.setAttribute("model", this.tDocSend);
/* 128 */         Map map = this.tApprovedInfoService.tapprovedinfoServiceByFW(this.tDocSend.getModelid(), this.tDocSend.getPinstanceid());
/* 129 */         this.request.setAttribute("pbApprovedinfoList", map.get("pbApprovedinfoList"));
/* 130 */         this.request.setAttribute("hqApprovedinfoList", map.get("hqApprovedinfoList"));
/* 131 */         this.request.setAttribute("xgApprovedinfoList", map.get("xgApprovedinfoList"));
/* 132 */         this.request.setAttribute("sgApprovedinfoList", map.get("sgApprovedinfoList"));
/* 133 */         this.request.setAttribute("qcApprovedinfoList", map.get("qcApprovedinfoList"));
/* 134 */         this.request.setAttribute("jgApprovedinfoList", map.get("jgApprovedinfoList"));
/*     */ 
/* 136 */         String hql = "from SendTasks as tasks where trim(tasks.processName) = '" + modelName + "' and tasks.incident = '" + incidentNo + "' order by tasks.startTime";
/* 137 */         List tasksList = this.commonService.findByHQL(hql);
/* 138 */         SendTasks sendTasks = new SendTasks();
/* 139 */         if ((tasksList != null) && (tasksList.size() > 0)) {
/* 140 */           sendTasks = (SendTasks)tasksList.get(0);
/*     */         }
/* 142 */         this.request.setAttribute("tasks", sendTasks);


/*     */       }
/*     */
		//String print = this.request.getParameter("print");
		//if(print!=null){
			this.request.setAttribute("print", "1");
		//}
/* 162 */       return "view";
/*     */     }
/*     */
			return null;
/*     */   }



@Action(value="validFile", results={
		@org.apache.struts2.convention.annotation.Result(name="view", location="/send/mainProcess/send/validFile.jsp")})
/*     */   public String validFile()
/*     */   {
/*     */ String id = this.request.getParameter("id");
/* 125 */       if (id != null) {
/* 126 */         this.tDocSend = ((TDocSend)this.commonService.load(id, TDocSend.class));
					if(tDocSend!=null){
						this.request.setAttribute("incidentNo", tDocSend.getPinstanceid());
						this.request.setAttribute("modelName", tDocSend.getModelid());
					
	/* 127 */         this.request.setAttribute("model", this.tDocSend);
	/* 128 */         Map map = this.tApprovedInfoService.tapprovedinfoServiceByFW(this.tDocSend.getModelid(), this.tDocSend.getPinstanceid());
	/* 129 */         this.request.setAttribute("pbApprovedinfoList", map.get("pbApprovedinfoList"));
	/* 130 */         this.request.setAttribute("hqApprovedinfoList", map.get("hqApprovedinfoList"));
	/* 131 */         this.request.setAttribute("xgApprovedinfoList", map.get("xgApprovedinfoList"));
	/* 132 */         this.request.setAttribute("sgApprovedinfoList", map.get("sgApprovedinfoList"));
	/* 133 */         this.request.setAttribute("qcApprovedinfoList", map.get("qcApprovedinfoList"));
	/* 134 */         this.request.setAttribute("jgApprovedinfoList", map.get("jgApprovedinfoList"));

                        //规范性设置-------------------------------------------------------------------
                        this.request.setAttribute("normative", tDocSend.getNormative());
                        this.request.setAttribute("normativeMemo", this.tDocSendService.getNormativeInfo(id));
	/*     */ 
	/* 136 */         String hql = "from SendTasks as tasks where trim(tasks.processName) = '" + tDocSend.getModelid() + "' and tasks.incident = '" + tDocSend.getPinstanceid() + "' order by tasks.startTime";
	/* 137 */         List tasksList = this.commonService.findByHQL(hql);
	/* 138 */         SendTasks sendTasks = new SendTasks();
	/* 139 */         if ((tasksList != null) && (tasksList.size() > 0)) {
	/* 140 */           sendTasks = (SendTasks)tasksList.get(0);
	/*     */         }
	/* 142 */         this.request.setAttribute("tasks", sendTasks);
						this.request.setAttribute("taskId", sendTasks.getTaskId());

                        //规范性文件显示 seal
                        String vhql = "select t,r.attach from ValidFile t,ValidRemark r where t.id = r.mainId and t.mainId = '"+id+"' and r.removed=0 and t.removed=0 order by r.operateTime desc";
                        List<Object> vItem= commonService.findByHQL(vhql);
                        if(vItem !=null && vItem.size() > 0){
                            Object[] oo = (Object[])vItem.get(0);
                            ValidFile v = (ValidFile)oo[0];
                            String attach = (String)oo[1];
                            request.setAttribute("validFile",v);
                            request.setAttribute("validAttach",attach);
                        }
                        //规范性文件显示seal

						return "view";
					}
/*     */       }
/* 162 */       return null;
/*     */ 
/*     */   }



/*     */
/*     */   @Action("startFlow")
/*     */   public String startFlow() throws UnsupportedEncodingException
/*     */   {
/* 173 */     Donull donull = new Donull();
/*     */ 
/* 175 */     String date = this.sdf_simple.format(new Date());
/*     */ 
/* 177 */     String loginName = "";
/* 178 */     String user_name = "";
/* 179 */     String user_dept_id = "";
/* 180 */     String user_dept_name = "";
			HttpSession session = request.getSession();
			loginName = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.LOGINNAME));
			user_name = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.USERNAME));
			user_dept_id = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.DEPTID));
			user_dept_name = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.DEPTNAME));

///* 181 */     Cookie[] cookies = this.request.getCookies();
///* 182 */     for (Cookie cookie : cookies) {
///* 183 */       if ("loginName".equals(cookie.getName()))
///*     */       {
///* 185 */         loginName = cookie.getValue();
///*     */       }
///* 187 */       if ("userName".equals(cookie.getName()))
///*     */       {
///* 189 */         user_name = URLDecoder.decode(cookie.getValue(), "utf-8");
///*     */       }
///* 191 */       if ("deptId".equals(cookie.getName()))
///*     */       {
///* 193 */         user_dept_id = cookie.getValue();
///*     */       }
///* 195 */       if (!"deptName".equals(cookie.getName()))
///*     */         continue;
///* 197 */       user_dept_name = URLDecoder.decode(cookie.getValue(), "utf-8");
///*     */     }
/*     */
			String strProcessName = "";
			String sendType = "";
			if("行政文".equals(tDocSend.getFileType())){
				strProcessName = "新发文流程";
				sendType = "120403";
			}else if("党委文".equals(tDocSend.getFileType())){
				strProcessName = "新党委发文流程";
				sendType = "120411";
			}else if("纪委文".equals(tDocSend.getFileType())){
				strProcessName = "新纪委发文流程";
				sendType = "120444";
			}
/* 201 */     Map map2 = new HashMap();
/* 202 */     map2.put("process", strProcessName);
/* 203 */     map2.put("incident", "");
/* 204 */     map2.put("step", "Begin");
/* 205 */     map2.put("operator", "ST/" + loginName);
/* 206 */     map2.put("operateTime", this.df.format(new Date()));
/* 207 */     map2.put("optionCode", "1");
/*     */ 
/* 209 */     this.tDocSend.setModelid(strProcessName);
/* 210 */     this.tDocSend.setOperator(user_name);
/* 211 */     this.tDocSend.setSendUser(user_name);
/* 212 */     this.tDocSend.setSendUserdept(user_dept_name);
/* 213 */     this.tDocSend.setOperateTime(date);
/* 214 */     this.tDocSend.setRemoved(Long.valueOf(0L));
/*     */ 
/* 216 */     this.tDocSend.setLineType("信");
/* 217 */     this.tDocSend.setFlag("0");
/* 218 */     this.tDocSend.setAutScanFlag(Long.valueOf(0L));
/* 219 */     this.tDocSend.setSendType(sendType);
/* 220 */     this.tDocSend.setDocCount("1");
/* 221 */     String attach = this.request.getParameter("contentAtt");
/*     */ 
/* 223 */     if ((attach != null) && (attach.length() > 0)) {
/* 224 */       this.tDocSend.setAttFlag("Y");
/* 225 */       this.tDocSend.setContentAtt(attach);
/*     */     } else {
/* 227 */       this.tDocSend.setAttFlag("Y");
/*     */     }



/* 229 */     this.commonService.save(this.tDocSend);
/* 230 */     String domainName = CommonConstants.LOGINNAME_PREFIX;
/*     */ 
/* 232 */     Object map = new HashMap();
/* 233 */     Map vMap = new HashMap();
/* 234 */     vMap.put("id", this.tDocSend.getId());
/* 235 */     ((Map)map).put("业务相关人员帐号", domainName + loginName);
/* 236 */     ((Map)map).put("业务相关人员姓名", user_name);
/* 237 */     map2.put("KEY1", domainName + loginName);
/* 238 */     map2.put("KEY2", user_name);
/* 239 */     String leader = this.request.getParameter("dept_leader");
/*     */ 
/* 241 */     if (leader == null) {
/* 242 */       leader = "";
/*     */     }
/* 244 */     if ((leader != null) && (leader.length() > 0)) {
/* 245 */       String[] leader2 = leader.split("#");
/* 246 */       if (leader2.length > 1) {
/* 247 */         ((Map)map).put("当前处理人部门领导帐号", leader2[0]);
/*     */ 
/* 249 */         ((Map)map).put("当前处理人部门领导姓名", leader2[1]);
/* 250 */         ((Map)map).put("部门ID1", user_dept_id);
/* 251 */         ((Map)map).put("部门ID1拼接", leader2[0] + ":" + user_dept_id + "<+>");
/*     */ 
/* 253 */         map2.put("KEY3", leader2[0]);
/* 254 */         map2.put("KEY4", leader2[1]);
/* 255 */         map2.put("KEY5", user_dept_id);
/* 256 */         map2.put("KEY6", leader2[0] + ":" + user_dept_id + "<+>");
/*     */       }
/*     */     }
/* 259 */     ((Map)map).put("业务表单ID1", sendType);
/* 260 */     ((Map)map).put("业务表ID", this.tDocSend.getId());
/* 261 */     ((Map)map).put("流程名称", strProcessName);
/*     */ 
/* 263 */     map2.put("KEY7", sendType);
/* 264 */     map2.put("KEY8", this.tDocSend.getId());
/* 265 */     map2.put("KEY9", strProcessName);
/*     */ 
/* 267 */     //String detailUrl = "";
/* 268 */     //String updateUrl = "";
/* 269 */     //String suggestUrl = "";
/* 270 */     FlowConfigUtil flowConfigUtil = new FlowConfigUtil();
/* 271 */     //detailUrl = flowConfigUtil.getValueByKey(sendType, "1", vMap);
/* 272 */     //updateUrl = flowConfigUtil.getValueByKey(sendType, "3", vMap);
/* 273 */     //suggestUrl = flowConfigUtil.getValueByKey(sendType, "4", vMap);
/* 274 */     //((Map)map).put("业务详细URL", detailUrl);
/* 275 */     //((Map)map).put("业务更新URL", updateUrl);
/* 276 */     //((Map)map).put("意见URL", suggestUrl);
/* 277 */     ((Map)map).put("发起人部门ID", user_dept_id);
/* 278 */     ((Map)map).put("发起人部门拼接", "ST/" + loginName + ":" + user_dept_id + "<+>");
/*     */ 
/* 280 */     map2.put("KEY10", user_dept_id);
/* 281 */     map2.put("KEY11", "ST/" + loginName + ":" + user_dept_id + "<+>");
/*     */ 
/* 283 */     List list = new ArrayList();
/* 284 */     list = PublicFunction.getUserInWorkFLow(strProcessName, null, "办结", sendType);
/* 285 */     if ((list != null) && (list.size() > 0)) {
/* 286 */       String temp = (String)list.get(0);
/* 287 */       String[] temps = temp.split(":");
/* 288 */       if (temps.length > 1) {
/* 289 */         if (temps[0].startsWith(domainName)) {
/* 290 */           ((Map)map).put("办结人帐号", temps[0]);
/* 291 */           map2.put("KEY12", temps[0]);
/*     */         } else {
/* 293 */           ((Map)map).put("办结人帐号", domainName + temps[0]);
/* 294 */           map2.put("KEY12", domainName + temps[0]);
/*     */         }
/* 296 */         ((Map)map).put("办结人姓名", temps[1]);
/* 297 */         map2.put("KEY13", temps[1]);
/*     */       }
/*     */     }
/*     */ 
/* 301 */     String strUserName = domainName + loginName;
/* 302 */     String strSummary = strProcessName+"-" + donull.dealNull(this.tDocSend.getDocTitle());
/* 303 */     map2.put("summary", strSummary);
/*     */ 
/* 305 */     String contend = strSummary;
/*     */ 
/* 307 */     ((Map)map).put("短信内容", contend);
/* 308 */     map2.put("KEY14", contend);
/*     */ 
/* 311 */     //int oincidentNo = UltimusFunc.launchProcess(map2);
				int oincidentNo = PWSUtil.launchIncident(strProcessName, "ST/"+loginName, strSummary, (Map)map);
/*     */ 
/* 313 */     //System.out.println("oincidentNo======" + oincidentNo);
				
/*     */
			//---------------------------
				
/* 315 */     if (oincidentNo > 0)
/*     */     {
				//规范性文件设置
				List<TNormativeDoc> normativeList = new ArrayList<TNormativeDoc>();
				this.normativeManage(request, tDocSend, normativeList);
				this.commonService.saveOrUpdateAll(normativeList);
				//--------------------------------------------------------
/* 317 */       this.tDocSend.setPinstanceid(String.valueOf(oincidentNo));
/* 318 */       this.commonService.update(this.tDocSend);
/* 319 */       SendApprovedinfo tapproved = new SendApprovedinfo();
/* 320 */       tapproved.setProcess(strProcessName);
/* 321 */       tapproved.setIncidentno(Long.valueOf(oincidentNo));
/* 322 */       tapproved.setDept(user_dept_name);
/* 323 */       tapproved.setDeptId(user_dept_id);
/* 324 */       tapproved.setStepname("Begin");
/* 325 */       tapproved.setUsername("ST/" + loginName);
/* 326 */       tapproved.setUserfullname(user_name);
/* 327 */       tapproved.setUpddate(new Date());
/* 328 */       tapproved.setAgree(Long.valueOf(1L));
/* 329 */       tapproved.setDisagree(Long.valueOf(0L));
/* 330 */       tapproved.setReturned(Long.valueOf(2L));
/* 331 */       tapproved.setStatus(Long.valueOf(2L));
/* 332 */       tapproved.setFllowFlag("0");
/* 333 */       tapproved.setReadFlag("1");
/* 334 */       tapproved.setRemark("");
/* 335 */       tapproved.setOptionCode("");
/* 336 */       tapproved.setRounds(Integer.valueOf(0));
/* 337 */       this.commonService.save(tapproved);
/*     */     }
/*     */ 
/* 340 */     ActionWriter aw = new ActionWriter(this.response);
/* 341 */     aw.writeAjax("{\"oincidentNo\":\"" + oincidentNo + "\",\"modelName\":\"" + strProcessName + "\"}");
/* 342 */     return (String)null;
/*     */   }
/*     */
public static String getStatusCn(String status){
	if("2".equals(status)){
		return "部分有效";
	}else if("3".equals(status)){
		return "失效";
	}else if("4".equals(status)){
		return "废止";
	}
	return "";
}

/*     */   @Action("updateForm")
/*     */   public String updateForm() {
/* 378 */     String stepName = this.request.getParameter("stepName");
				String taskId = this.request.getParameter("taskId");
				String assignedtouser = this.request.getParameter("assignedtouser");
/* 379 */     TDocSend bo = (TDocSend)this.commonService.load(this.tDocSend.getId(), TDocSend.class);
				tDocSend.setFileType(bo.getFileType());
				tDocSend.setNormative(bo.getNormative());

               // tDocSend.setSendFileType(tDocSend.getSendFileType());
/* 380 */     if ("套头".equals(stepName)) {
/* 381 */       String choice = this.request.getParameter("choice");
/* 382 */       if ("1".equals(choice)) {
/* 383 */           tDocSend.setFlag("1");
					sendNotice(tDocSend.getModelid(),tDocSend.getPinstanceid(),assignedtouser,taskId,tDocSend.getId());

					//保存规范行文件
					String normative = this.request.getParameter("normative");
					String override = this.request.getParameter("override");
            //更新过往文件状态
                    String lastObject = this.request.getParameter("lastObject");
                    if("1".equals(override) && lastObject != null && lastObject.length() > 0){
                        List<ValidRemark> remarkList = new ArrayList<ValidRemark>();
                        String[] normativeIds = request.getParameterValues("normativeId");
                        String[] normativeTitles = request.getParameterValues("normativeTitle");
                        String[] normativeCodes = request.getParameterValues("normativeCode");
                        String[] normativeAttachs = request.getParameterValues("normativeAttach");
                        String[] normativeStatuss = request.getParameterValues("normativeStatus");
                        String hql = "from ValidFile v where v.removed='0' and v.mainId=?";
                        String[] s = lastObject.split(";");
                        Pattern pattern = Pattern.compile("(\\S+):(\\d+)");
                        for(int k=0;k<s.length;k++){
                            Matcher matcher = pattern.matcher(s[k]);
                            if(matcher.find()){
                                String mainId = matcher.group(1);
                                String status = matcher.group(2);
                                List last = this.commonService.ListByHql(hql,new Object[]{mainId});
                                if(last!=null && last.size()>0){
                                    ValidFile vv = (ValidFile)last.get(0);
                                    vv.setStatus(status);
                                    if(normativeIds != null && normativeIds.length > 0){
                                        ValidRemark remarkBo = new ValidRemark();
                                        remarkBo.setMainId(vv.getId());
                                        remarkBo.setRemark(normativeTitles[k]+" "+normativeCodes[k]+" "+getStatusCn(normativeStatuss[k]) );
                                        remarkBo.setAttach(normativeAttachs[k]);
                                        remarkBo.setRemoved("0");
                                        remarkBo.setOperateTime(sdf_simple.format(new Date()));
                                        remarkBo.setOperatePerson((String)session.getAttribute(LoginConstants.USERNAME));
                                        vv.setRemark("0");
                                        remarkList.add(remarkBo);
                                    }
                                    this.commonService.update(vv);
                                }
                            }
                        }
                        this.commonService.saveOrUpdateAll(remarkList);
                    }
//
					if("1".equals(normative)&&"新发文流程".equals(tDocSend.getModelid())){
						ValidFile validFile = new ValidFile();
						if("1".equals(override)){
							validFile.setRemark("1");
						}
						validFile.setMainId(tDocSend.getId());
						validFile.setStatus("1");
						validFile.setRemoved("0");
						validFile.setOperateTime(df.format(new Date()));
						validFile.setOperatePerson((String)session.getAttribute(LoginConstants.USERNAME));
						commonService.save(validFile);

					}

/*     */       }
/*     */     }else if ("起草".equals(stepName)||"拟稿人修改".equals(stepName)) {
	/* 381 */       String choice = this.request.getParameter("choice");
	/* 382 */       if ("0".equals(choice)) {
						tDocSend.setFlag("3");
						tDocSend.setRemoved(1L);
						tDocSend.setDocTitle(tDocSend.getDocTitle()+"<font color='red' style='display:inline;'>(此流程已终止)</font>");
	/*     */       }
	/*     */  }else if("排版".equals(stepName)){
					String code1 = tDocSend.getCode1();
					code1=CodeUtil.getCodeName(Constants.DocSend__Dictionary,code1);
					tDocSend.setCode1(code1);
					String code4 = this.request.getParameter("code4");
					if(code4==null||"null".equals(code4)){
						code4 = "";
					}
					tDocSend.setLineType(code4);
					String code=code1+code4+"("+tDocSend.getCode2()+")"+tDocSend.getCode3()+"号";
					tDocSend.setSendId(code);
					SWUtil.deleteYLNew(code1, tDocSend.getCode2(), tDocSend.getCode3(),tDocSend.getSendType());
				}

			//规范性文件设置
			if("审稿".equals(stepName) || "起草".equals(stepName)||"拟稿人修改".equals(stepName)
					||"套头".equals(stepName) || "排版".equals(stepName)){
				List<TNormativeDoc> list = new ArrayList<TNormativeDoc>();
				this.normativeManage(request, tDocSend, list);
				this.commonService.saveOrUpdateAll(list);
			}

			//-----------------------------------------------------------------
			  this.commonService.update(tDocSend);
/* 387 */     return null;
/*     */   }

/**
 * 获取编号
 */
@Action("getSerialNo")
public String getSerialNo(){
	String perfix = this.request.getParameter("drSwtype");
	String perpost = this.request.getParameter("swYear");
	String serialNo = this.request.getParameter("drSwid");
	String parentCode = this.request.getParameter("parentCode");
	String typeId = this.request.getParameter("typeId");
	String qianzhui = CodeUtil.getCodeName(parentCode,perfix);
	//System.out.println(qianzhui + "-----------------" + perfix);
	String swbh = SWUtil.getSerialNoNew(qianzhui,perpost,serialNo,typeId);
	//System.out.println("SWBH:" +swbh);
	SWUtil.saveYLNew(qianzhui, perpost, swbh,typeId);
	this.response.setContentType("text/html");
	Writer w = null;
	try {
		this.response.setCharacterEncoding("UTF-8");
		w = this.response.getWriter();

		w.write(swbh);

	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		try {
			if (w != null)
				w.flush();
			if (w != null)
				w.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	return null;
}

private void sendNotice(String modelId,String pincident,String assignedtouser,String taskId,String mainId){
	Properties properties = new Properties();
	String path = Thread.currentThread().getContextClassLoader().getResource("config.properties").getPath();
	FileInputStream fis;
	try {
		fis = new FileInputStream(path);
		properties.load(fis);
	} catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	String contextIpPath = properties.getProperty("contextIpPath");
	String listType = "0";
	if("新发文流程".equals(modelId)){
		listType = "2";
	}

	String sendMainIds = tDocSend.getSendMainId();//主送
	String sendInsideIds = tDocSend.getSendInsideId();//抄送
	String sendReportIds = tDocSend.getSendReportId();//内发
	String newDeptIds = "";
	PublicFunction func = new PublicFunction();
	String sender = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.LOGINNAME));
	//System.out.println("sender"+sender);
	String senderName = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.USERNAME));
	Map<String,TaskUserVo> userMap = (Map<String, TaskUserVo>) session.getAttribute(LoginConstants.DEPT_USER);
	if(userMap!=null){
		TaskUserVo taskUserVo = userMap.get(assignedtouser);
		if(taskUserVo!=null){
			sender = taskUserVo.getLoginName();
			senderName = taskUserVo.getUserName();
		}
	}
	if(sender!=null&&sender.length()>0){//根据12位工号得到id
		sender = sender.replace("ST/","");
	}
	String Initiator = sender;
	String senderId = "";
	String title = "系统中已有一条业务流程完成流转，摘要："+modelId+"-"+tDocSend.getDocTitle()+"。请关注。";
	String content = "您有条发文流程相关通知:<a target='_blank' href='"+contextIpPath+"/attach/loadFileList.action?"+
				"attachMemo=fawen_att_dic&procType=view&fileGroup=contentAttMain&fileGroupName=contentAttMainGroup&fileGroupId="+tDocSend.getContentAttMain()+
				"&userName="+sender+"&loginName="+sender+"&targetType=dialog&listType="+listType+"' ><font color='red'>请点击此处查看详细内容。</font></a>";
	String docsFileUrl = contextIpPath+"/attach/loadFileList.action?"+
			"attachMemo=fawen_att_dic&procType=view&fileGroup=contentAttMain&fileGroupName=contentAttMainGroup&fileGroupId="+tDocSend.getContentAttMain()+
			"&userName="+sender+"&loginName="+sender+"&targetType=dialog&listType="+listType;

	if(sender!=null&&sender.length()>0){//根据12位工号得到id
		sender = sender.substring(0,12);
		senderId = func.getIdByLoginName(sender);
	}
	if(senderId.length()==0&&senderName!=null&&senderName.length()>0){//根据姓名得到id
		senderId = func.getIdByName(senderName);
	}

	if(sendMainIds!=null&&sendMainIds.length()>0){
		newDeptIds += sendMainIds;
	}
	if(sendInsideIds!=null&&sendInsideIds.length()>0){
		String[] idSplit = sendInsideIds.split(",");
		String ids = ","+newDeptIds+",";
		for(int i=0;i<idSplit.length;i++){
			if(ids.indexOf(","+idSplit[i]+",")==-1){
				if(newDeptIds.length()>0){
					newDeptIds += ",";
				}
				newDeptIds += idSplit[i];
			}
		}
	}
	if(sendReportIds!=null&&sendReportIds.length()>0){
		String[] idSplit = sendReportIds.split(",");
		String ids = ","+newDeptIds+",";
		for(int i=0;i<idSplit.length;i++){
			if(ids.indexOf(","+idSplit[i]+",")==-1){
				if(newDeptIds.length()>0){
					newDeptIds += ",";
				}
				newDeptIds += idSplit[i];
			}
		}
	}
	if(newDeptIds.length()>0){
		List<Object> deptCodeList = commonService.findByHQL("from DeptCode t where t.removed = '0' and t.newDeptId in ("+newDeptIds+")");
		Map<String,DeptCode> codeMap = new HashMap<String,DeptCode>();
		if(deptCodeList!=null&&deptCodeList.size()>0){
			for(int k=0;k<deptCodeList.size();k++){
				DeptCode deptCode = (DeptCode)deptCodeList.get(k);
				codeMap.put(deptCode.getNewDeptId(), deptCode);
			}
		}

		List<OrganUserBo> listReseivers = externalService.getDeptReceivers(newDeptIds, modelId,pincident,"部门接受人工作分发");
		Map<String,OrganUserBo> receiversMap = new HashMap<String,OrganUserBo>();
		if(listReseivers!=null&&listReseivers.size()>0){
			for(int k=0;k<listReseivers.size();k++){
				OrganUserBo organUserBo = listReseivers.get(k);
				receiversMap.put(organUserBo.pid, organUserBo);
			}
		}

		List<OrganUserBo> listLeaders = externalService.getDeptSingleLeader(newDeptIds);
		Map<String,OrganUserBo> leadersMap = new HashMap<String,OrganUserBo>();
		if(listLeaders!=null&&listLeaders.size()>0){
			for(int k=0;k<listLeaders.size();k++){
				OrganUserBo organUserBo = listLeaders.get(k);
				leadersMap.put(organUserBo.pid, organUserBo);
			}
		}

		String[] newDeptIdsSplit = newDeptIds.split(",");
		for(int i=0;i<newDeptIdsSplit.length;i++){
			DeptCode deptCode = codeMap.get(newDeptIdsSplit[i]);
			OrganUserBo receiverBo = receiversMap.get(newDeptIdsSplit[i]);
			OrganUserBo leaderBo = leadersMap.get(newDeptIdsSplit[i]);

			String url = "";
			String stepName = "发文通知";
			Date date = new Date();
			String id = "";
			if(deptCode!=null){
				id = func.getReceiverId(deptCode.getOldDeptId());
			}
			if(id!=null&&id.length()>0&&!"null".equals(id)&&deptCode!=null&&receiverBo!=null&&leaderBo!=null){
				TMsgUserMassage bo = new TMsgUserMassage();
				bo.setTitle(title);
				bo.setContent(content.replace("listType=2", "listType=2&codeId="+deptCode.getId()));
				bo.setEmpidrec(Long.valueOf(id));
				bo.setEmpidsend(Long.valueOf(senderId));
				commonService.save(bo);

				if("新发文流程".equals(modelId)||"新党委发文流程".equals(modelId)){
					String loginName = "";
					String userName = "";
					String actionName = "";
					if(PublicFunction.judgeDeptInfoForNotice(newDeptIdsSplit[i])){
						loginName = leaderBo.loginName;
						userName = leaderBo.name;
						actionName = "add";
					}else{
						loginName = receiverBo.loginName;
						userName = receiverBo.name;
						actionName = "sign";
					}
					if(PublicFunction.judgeSendPass(newDeptIdsSplit[i])){//processname=send_notice
						actionName = "add";
					}
					try {
						url = contextIpPath+"/dept-passMain/"+actionName+".action?modelName="+
						//url = "http://10.1.41.252:8080/workflow/dept-passMain/"+actionName+".action?modelName="+
							URLEncoder.encode(modelId,"UTF-8")+"&incidentNo="+pincident+"&processName="+
							URLEncoder.encode(modelId,"UTF-8")+"&pinstanceId="+pincident+"&taskUserName="+
							loginName+"&stepName="+URLEncoder.encode(stepName,"UTF-8")+
							"&taskId="+taskId+"&taskuser="+loginName+"&codeId="+deptCode.getId()+
							"&attach="+tDocSend.getContentAttMain()+"&title="+
							URLEncoder.encode(tDocSend.getSendId()+" "+tDocSend.getDocTitle(),"UTF-8")+
							"&mainId="+tDocSend.getId()+"&mainTable=T_DOC_SEND";
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					SendTodoItem todoItemBo = new SendTodoItem();
					todoItemBo.setApp("sendNotice");
					todoItemBo.setOccurTime(df.format(date));
					todoItemBo.setTitle(title);
					todoItemBo.setLoginName(loginName);
					todoItemBo.setStatus(0);
					todoItemBo.setRemoved(0);
					todoItemBo.setTypename(modelId);
					todoItemBo.setUrl(url);
					todoItemBo.setPname(modelId);
					todoItemBo.setPincident(Integer.valueOf(pincident));
					todoItemBo.setCname(modelId);
					todoItemBo.setCincident(Integer.valueOf(pincident));
					todoItemBo.setStepName(stepName);
					todoItemBo.setTaskId(taskId);
					todoItemBo.setType(0);
					todoItemBo.setInitiator("ST/"+Initiator);
					todoItemBo.setData(bo.getSid()+"");
					todoItemBo.setKey(tDocSend.getId());
					commonService.save(todoItemBo);

					todoItemBo.setUrl(todoItemBo.getUrl()+"&todoId="+todoItemBo.getId());
					commonService.update(todoItemBo);


					//关联有效文件
					DocMsgRelation relation = new DocMsgRelation();
					relation.setMainId(mainId);
					relation.setDeptId(deptCode.getNewDeptId());
					relation.setRemoved("0");
					relation.setOperateTime(df.format(date));
					relation.setDeptName(deptCode.getDeptName());
					relation.setLoginName(loginName);
					relation.setUserName(userName);
					commonService.save(relation);
				}

				TFlowfunctionGuanLian tfglIn = new TFlowfunctionGuanLian();
				tfglIn.setProcessName(modelId);
				tfglIn.setIncidentNo(tDocSend.getPinstanceid());
				tfglIn.setType("DocSend_notice");
				tfglIn.setDisplayName(title);
				tfglIn.setYewuId(bo.getSid() + "");
				tfglIn.setOperator(sender);
				//listGl.add(tfglIn);
				commonService.save(tfglIn);

//			

				//				List<String> oldDeptIdList = new ArrayList<String>();
//				oldDeptIdList.add(deptCode.getOldDeptId());
//				Map<String,String> deptMap = PublicFunction.getCatalog(oldDeptIdList);
//				if(deptMap != null && deptMap.size() > 0){
//					String ttUser = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.LOGINNAME)).substring(0,12);
//					TDocsFileBo docsFile=new TDocsFileBo();
//					docsFile.setFile_name(tDocSend.getModelid()+" "+tDocSend.getDocTitle());
//					docsFile.setFile_path(docsFileUrl.replace("listType=2", "listType=2&codeId="+deptCode.getId()));
//					docsFile.setCreate_user(ttUser);
//					docsFile.setUpdate_user(ttUser);
//					docsFile.setFile_assessor(ttUser);
//					docsFile.setLink_flag("2");
//					docsFile.setFile_check_flag("1");
//					docsFile.setParent_sid(deptMap.get(deptCode.getOldDeptId()));
//					commonService.save(docsFile);
				}
			}

		//入部门文件柜 2014-10-18 zhoushun
		String doc_loginName = "";
		String doc_userName = "";
		String doc_deptId = "";
		Map<String,TaskUserVo> doc_userMap = (Map<String, TaskUserVo>) session.getAttribute(LoginConstants.DEPT_USER);
		if(doc_userMap!=null){
			TaskUserVo taskUserVo = doc_userMap.get(assignedtouser);
			if(taskUserVo!=null){
				doc_loginName = taskUserVo.getLoginName();
				doc_deptId = taskUserVo.getDeptId();
				doc_userName = taskUserVo.getUserName();
			}
		}
		deptDocService.addDocs(tDocSend,doc_loginName,doc_userName,doc_deptId);
		//入部门文件柜
		}














//		//List<Object> deptCodeList = commonService.findByHQL("from DeptCode t where t.removed = '0' and t.newDeptId = '"+newDeptIdsSplit[k]+"'");
//		List<Object> deptCodeList = commonService.findByHQL("from DeptCode t where t.removed = '0' and t.newDeptId in ("+newDeptIds+")");
//		if(deptCodeList!=null&&deptCodeList.size()>0){
//			for(int k=0;k<deptCodeList.size();k++){
//			DeptCode deptCode = (DeptCode)deptCodeList.get(k);
//			List<OrganUserBo> listReseivers = externalService.getDeptSingleLeader(deptCode.getNewDeptId());
//			String url = "";
//			String stepName = "发文通知";
//			Date date = new Date();
//			
//			String id = func.getReceiverId(deptCode.getOldDeptId());	
//			if(listReseivers!=null&&listReseivers.size()>0&&id!=null&&id.length()>0&&!"null".equals(id)){
//					TMsgUserMassage bo = new TMsgUserMassage();
//					bo.setTitle(title);
//					bo.setContent(content.replace("listType=2", "listType=2&codeId="+deptCode.getId()));
//					bo.setEmpidrec(Long.valueOf(id));
//					bo.setEmpidsend(Long.valueOf(senderId));
//					//listBo.add(bo);
//					commonService.save(bo);
//					
//					try {
//						url = contextIpPath+"/dept-passMain/add.action?modelName="+
//							URLEncoder.encode(modelId,"UTF-8")+"&incidentNo="+pincident+"&processName="+
//							URLEncoder.encode(modelId,"UTF-8")+"&pinstanceId="+pincident+"&taskUserName="+
//							listReseivers.get(0).loginName+"&stepName="+URLEncoder.encode(stepName,"UTF-8")+
//							"&taskId="+taskId+"&taskuser="+listReseivers.get(0).loginName+"&codeId="+deptCode.getId()+
//							"&attach="+tDocSend.getContentAttMain()+"&title="+
//							URLEncoder.encode(tDocSend.getSendId()+" "+tDocSend.getDocTitle(),"UTF-8")+
//							"&mainId="+tDocSend.getId()+"&mainTable=T_DOC_SEND";
//					} catch (UnsupportedEncodingException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					SendTodoItem todoItemBo = new SendTodoItem();
//					todoItemBo.setApp("sendNotice");
//					todoItemBo.setOccurTime(df.format(date));
//					todoItemBo.setTitle(title);
//					todoItemBo.setLoginName(listReseivers.get(0).loginName);
//					todoItemBo.setStatus(0);
//					todoItemBo.setRemoved(0);
//					todoItemBo.setTypename(modelId);
//					todoItemBo.setUrl(url);
//					todoItemBo.setPname(modelId);
//					todoItemBo.setPincident(Integer.valueOf(pincident));
//					todoItemBo.setCname(modelId);
//					todoItemBo.setCincident(Integer.valueOf(pincident));
//					todoItemBo.setStepName(stepName);
//					todoItemBo.setTaskId(taskId);
//					todoItemBo.setType(0);
//					todoItemBo.setInitiator("ST/"+Initiator);
//					todoItemBo.setData(bo.getSid()+"");
//					//listBo1.add(todoItemBo);
//					commonService.save(todoItemBo);
//					
//					TFlowfunctionGuanLian tfglIn = new TFlowfunctionGuanLian();
//					tfglIn.setProcessName(modelId);
//					tfglIn.setIncidentNo(tDocSend.getPinstanceid());
//					tfglIn.setType("DocSend_notice");
//					tfglIn.setDisplayName(title);
//					tfglIn.setYewuId(bo.getSid() + "");
//					tfglIn.setOperator(sender);
//					//listGl.add(tfglIn);
//					commonService.save(tfglIn);
//					
//					//关联有效文件
//					DocMsgRelation relation = new DocMsgRelation();
//					relation.setMainId(mainId);
//					relation.setDeptId(deptCode.getOldDeptId());
//					relation.setRemoved("0");
//					relation.setOperateTime(df.format(date));
//					commonService.save(relation);
//					
//					//入部门文件柜
//					List<String> oldDeptIdList = new ArrayList<String>();
//					oldDeptIdList.add(deptCode.getOldDeptId());
//					Map<String,String> deptMap = PublicFunction.getCatalog(oldDeptIdList);
//					if(deptMap != null && deptMap.size() > 0){
//						String ttUser = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.LOGINNAME)).substring(0,12);
//						TDocsFileBo docsFile=new TDocsFileBo();
//						docsFile.setFile_name(tDocSend.getModelid()+" "+tDocSend.getDocTitle());
//						docsFile.setFile_path(docsFileUrl.replace("listType=2", "listType=2&codeId="+deptCode.getId()));
//						docsFile.setCreate_user(ttUser);
//						docsFile.setUpdate_user(ttUser);
//						docsFile.setFile_assessor(ttUser);
//						docsFile.setLink_flag("2");
//						docsFile.setFile_check_flag("1");
//						docsFile.setParent_sid(deptMap.get(deptCode.getOldDeptId()));
//						commonService.save(docsFile);
//					}
//				}
//			}
//		}

	}

@Action("transmitNotice")
public String transmitNotice() throws IllegalAccessException, InvocationTargetException{
	String pname = this.request.getParameter("modelName");
	String pincident = this.request.getParameter("incidentNo");
	String stepName = this.request.getParameter("stepName");
	String dealPersonStr = this.request.getParameter("dealPersonStr");
	String dealLeaderStr = this.request.getParameter("dealLeaderStr");
	String persons = "";
	if(dealPersonStr!=null&&dealPersonStr.length()>0){
		persons = dealPersonStr;
	}
	if(dealLeaderStr!=null&&dealLeaderStr.length()>0){
		if(persons.length()>0){
			persons += ",";
		}
		persons += dealLeaderStr;
	}

	 String loginName = "";
	HttpSession session = request.getSession();
	loginName = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.LOGINNAME));

	String assignedtouser = this.request.getParameter("assignedtouser");
	Map<String,TaskUserVo> userMap = (Map<String, TaskUserVo>) session.getAttribute(LoginConstants.DEPT_USER);
	if(userMap!=null){
		TaskUserVo taskUserVo = userMap.get(assignedtouser);
		if(taskUserVo!=null){
			loginName = taskUserVo.getLoginName().replace("ST/","");
		}
	}

	String hql = "from SendTodoItem t where t.app = 'sendNotice' and t.pname = '"+pname+"' and t.pincident = "+pincident+" and t.stepName = '"+stepName+"' and t.removed = 0 and t.loginName = 'ST/"+loginName+"'";
	List<Object> list = commonService.findByHQL(hql);
	List<SendTodoItem> listBo1 = new ArrayList<SendTodoItem>();
	if(list!=null&&list.size()>0){
		SendTodoItem bo = (SendTodoItem)list.get(0);
			if(persons.length()>0){
				String[] personsSplit = persons.split(",");
				Date date = new Date();
				for(int i=0;i<personsSplit.length;i++){
					SendTodoItem bo1 = new SendTodoItem();
					BeanUtils.copyProperties(bo, bo1);
					bo1.setId(null);
					bo1.setData(null);
					bo1.setStatus(0);
					bo1.setOccurTime(df.format(date));
					bo1.setLoginName(personsSplit[i]);
					bo1.setUrl(bo.getUrl().replace("ST/"+loginName, personsSplit[i]));
					listBo1.add(bo1);
				}
				commonService.saveOrUpdateAll(listBo1);
			}
		//bo.setStatus(1);
		//commonService.update(bo);

	}
	if(listBo1!=null&&listBo1.size()>0){
		ActionWriter aw = new ActionWriter(this.response);
		aw.writeAjax("{\"if_success\":\"yes\"}");
	}
	return null;
}


	//规范性文件设置
	/**
	* @Title: normativeManage
	* @Description: TODO(规范性设置)
	* @param @param request
	* @param @param bo
	* @param @param list    设定文件
	* @return void    返回类型
	* @date 2014年10月9日 下午3:03:56
	* @throws
	*/
	public void normativeManage(HttpServletRequest request,TDocSend bo,List<TNormativeDoc> list){
		if("新发文流程".equals(bo.getModelid())){
		String loginName = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.LOGINNAME));
		String assignedtouser = this.request.getParameter("assignedtouser");
		if(assignedtouser!=null){
			Map<String,TaskUserVo> userMap = (Map<String, TaskUserVo>) session.getAttribute(LoginConstants.DEPT_USER);
			if(userMap!=null){
				TaskUserVo taskUserVo = userMap.get(assignedtouser);
				if(taskUserVo!=null){
					loginName = taskUserVo.getLoginName().replace("ST/","");
				}
			}
		}
		String normative = StringUtil.getNotNullValueString(request.getParameter("normative"));
		String override = StringUtil.getNotNullValueString(request.getParameter("override"));
		bo.setNormative(normative);
		if("1".equals(normative) && "1".equals(override)){
			String[] normativeIds = request.getParameterValues("normativeId");
			String[] normativeRemoveds = request.getParameterValues("normativeRemoved");
			String[] normativeTitles = request.getParameterValues("normativeTitle");
			String[] normativeCodes = request.getParameterValues("normativeCode");
			String[] normativeAttachs = request.getParameterValues("normativeAttach");
			String[] normativeStatuss = request.getParameterValues("normativeStatus");
			if(normativeIds != null && normativeIds.length > 0){
				for(int i = 0;i < normativeIds.length; i++){
					TNormativeDoc doc = new TNormativeDoc();
					if(normativeIds[i] != null && normativeIds[i].length() > 0){
						doc.setId(normativeIds[i]);
					}
					doc.setMainId(bo.getId());
					doc.setTitle(normativeTitles[i]);
					doc.setCode(normativeCodes[i]);
					doc.setAttach(normativeAttachs[i]);
					doc.setStatus(normativeStatuss[i]);
					doc.setRemoved(normativeRemoveds[i]);
					doc.setOperatePerson("ST/"+loginName);
					list.add(doc);
				}
			}
		}else{
			this.commonService.UpdateByHql(
					"update TNormativeDoc t set t.removed='1' where t.mainId = '"+bo.getId()+"'");
		}
		}
	}

	//-------------------------------------------------------------------------------------
	
/*     */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.mainProcess.send.action.TDocSendAction
 * JD-Core Version:    0.6.0
 */