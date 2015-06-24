<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page import="com.wonders.send.approve.model.bo.SendApprovedinfo"%>
<%@page import="com.wonders.send.approve.model.vo.TApprovedinfoVo"%>
<%@page import="com.wonders.send.util.Donull"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.wonders.send.approve.service.TApprovedinfoService"%>
<%@page import="com.wonders.util.DateUtil"%>
<%@page import="com.wonders.send.model.bo.SendTasks"%>
<%@page import="org.springframework.web.context.WebApplicationContext" %>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@page import="com.wonders.send.mainProcess.send.model.bo.TDocSend"%>
<%@page import="com.wonders.util.StringUtil"%>
<%@page import="com.wonders.send.util.InProgress"%>
<%@page import="com.wonders.send.external.service.ExternalService"%>
<%@page import="com.wonders.send.external.model.UserInfo"%>
<%@ page import="com.wonders.common.model.vo.TaskUserVo" %>
<%@ page import="com.wonders.constants.LoginConstants" %>
<%
	String path = request.getContextPath();

String loginName = (String)session.getAttribute("login_name");
//System.out.println("====="+deptId+deptName);
String assignedtouser = request.getParameter("assignedtouser");
if(assignedtouser==null){
	assignedtouser = request.getParameter("taskuser");
}
Map<String,TaskUserVo> userMap = (Map<String, TaskUserVo>) session.getAttribute(LoginConstants.DEPT_USER);
if(userMap!=null){
	TaskUserVo taskUserVo = userMap.get(assignedtouser);
	if(taskUserVo!=null){
		loginName = taskUserVo.getLoginName().replace("ST/","");
	}
}

Donull donull = new Donull();
TDocSend model =(TDocSend)request.getAttribute("model");
WebApplicationContext context=WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());	
TApprovedinfoService tapprovedinfoService=(TApprovedinfoService)context.getBean("send-tApprovedInfoService");
ExternalService externalService=(ExternalService)context.getBean("send-externalService");
externalService.setToken((String)session.getAttribute(LoginConstants.TOKEN));
SendTasks tasks =(SendTasks)request.getAttribute("tasks");
List qcApprovedinfoList=(List)request.getAttribute("qcApprovedinfoList");
List sgApprovedinfoList=(List)request.getAttribute("sgApprovedinfoList");
List pbApprovedinfoList=(List)request.getAttribute("pbApprovedinfoList");
List hqApprovedinfoList=(List)request.getAttribute("hqApprovedinfoList");
List xgApprovedinfoList=(List)request.getAttribute("xgApprovedinfoList");

//System.out.println(qcApprovedinfoList.size());


InProgress inProgress = new InProgress();
List<Map<String,Object>> inlist = inProgress.findUserInProgress(model.getModelid(),model.getPinstanceid());
String all_loginname = "";
if(inlist!=null&&inlist.size()>0){
	for(int i=0;i<inlist.size();i++){
		if(i>0){
	all_loginname += ",";
		}
		all_loginname += "'"+StringUtil.getNotNullValueString(inlist.get(i).get("login_name")).replace("ST/", "")+"'";
	}
}
//all_loginname = "'G001000001612549','G001000001362510'";
List<UserInfo> fulllist = externalService.getUserInfoByLoginName(all_loginname);
if(fulllist!=null&&fulllist.size()>0){
	for(int i=0;i<fulllist.size();i++){
		if(inlist.get(i)!=null){
	inlist.get(i).put("name",fulllist.get(i).userName);
	inlist.get(i).put("deptname",fulllist.get(i).deptName);
		}
	}
}
//System.out.println(inlist);
String inprogress_hegao = "";
String inprogress_bumen = "";
String inprogress_huiqian = "";
String inprogress_qianfa = "";
String inprogress_img = "";//显示流程进度条
if(inlist!=null&&inlist.size()>0){
	Map<String,Object> inmap = inlist.get(0);
	if("核稿".equals(StringUtil.getNotNullValueString(inmap.get("steplabel")))){
		inprogress_hegao = StringUtil.getNotNullValueString(inmap.get("name"))+"正在处理...";
	}else if("部门内部子流程".equals(StringUtil.getNotNullValueString(inmap.get("processname")))){
		for(int i=0;i<inlist.size();i++){
	if(i>0){
		inprogress_bumen += "，";
	}
	inprogress_bumen += StringUtil.getNotNullValueString(inlist.get(i).get("deptname"));
		}
		inprogress_bumen += "正在处理...";
	}else if("会签领导子流程".equals(StringUtil.getNotNullValueString(inmap.get("processname")))){
		int j = 0;
		for(int i=0;i<inlist.size();i++){
	if("领导".equals(StringUtil.getNotNullValueString(inlist.get(i).get("deptname")))||"集团领导".equals(StringUtil.getNotNullValueString(inlist.get(i).get("deptname")))){
		if(j>0){
			inprogress_huiqian += "，";
		}
		inprogress_huiqian += StringUtil.getNotNullValueString(inlist.get(i).get("name"));
		j++;
	}
		}
		inprogress_huiqian += "正在处理...";
	}else if("签发领导子流程".equals(StringUtil.getNotNullValueString(inmap.get("processname")))){
		if("领导".equals(StringUtil.getNotNullValueString(inmap.get("deptname")))||"集团领导".equals(StringUtil.getNotNullValueString(inmap.get("deptname")))){
	inprogress_qianfa = StringUtil.getNotNullValueString(inmap.get("name"))+"正在处理...";
		}
	}
	
	if("部门内部子流程".equals(StringUtil.getNotNullValueString(inmap.get("processname")))||"会签领导子流程".equals(StringUtil.getNotNullValueString(inmap.get("processname")))||"签发领导子流程".equals(StringUtil.getNotNullValueString(inmap.get("processname")))){
		inprogress_img = "3";
	}else if("起草子流程".equals(StringUtil.getNotNullValueString(inmap.get("processname")))){
		inprogress_img = "2";
	}else{
		if("拟稿人修改".equals(StringUtil.getNotNullValueString(inmap.get("steplabel")))){
	inprogress_img = "2";
		}else if("核稿".equals(StringUtil.getNotNullValueString(inmap.get("steplabel")))){
	inprogress_img = "3";
		}else{
	inprogress_img = "5";
		}
	}
}else{
	inprogress_img = "6";
}
//王立俊 2010-11-12
//发文审稿时添加办公室审核时的处理人及其秘书信息 
String deptshldId1="ST/G001000001442510";
String deptshldName1="金嘉模";
String deptshldDeptId1="2111";
String deptshldId2="ST/G001000001362510";
String deptshldName2="蔡伟东";
String deptshldDeptId2="2111";
String deptshldId3="ST/G001000000152502";
String deptshldName3="吴昕毅";
String deptshldDeptId3="2102";
%>
<script>
<%if(!"".equals(inprogress_qianfa)){%>
$(function(){
	$("#ldResult").html("<b><%=inprogress_qianfa%></b>");
});
<%}%>

<%if(!"".equals(inprogress_huiqian)){%>
$(function(){
	$("#ldResult2").html("<b><%=inprogress_huiqian%></b>");
});
<%}%>

<%if(!"".equals(inprogress_bumen)){%>
$(function(){
	$("#deptResult").html("<b><%=inprogress_bumen%></b>");
});
<%}%>


function showDescription(flow_no, obj_icon) {
	var div_appinfo_a = document.getElementById('appinfo_a_' + flow_no);
	var div_appinfo_b = document.getElementById('appinfo_b_' + flow_no); // description
	if (div_appinfo_a == undefined || div_appinfo_b == undefined ||
		div_appinfo_a.innerText == div_appinfo_b.innerText) {
		alert('没有更多详细信息!');
		return ;
	}
	
	if (div_appinfo_b.style.display == 'none') {
		div_appinfo_a.style.display = 'none';
		div_appinfo_b.style.display = 'block';
		obj_icon.alt = '关闭详细';
	} else {
		div_appinfo_a.style.display = 'block';
		div_appinfo_b.style.display = 'none';
		obj_icon.alt = '详细';
	}
}

function showDescription2(flow_no, obj_icon) {
	var div_appinfo_a = document.getElementById('appinfo4_a_' + flow_no);
	var div_appinfo_b = document.getElementById('appinfo4_b_' + flow_no); // description
	if (div_appinfo_a == undefined || div_appinfo_b == undefined ||
		div_appinfo_a.innerText == div_appinfo_b.innerText) {
		alert('没有更多详细信息!');
		return ;
	}
	
	if (div_appinfo_b.style.display == 'none') {
		div_appinfo_a.style.display = 'none';
		div_appinfo_b.style.display = 'block';
		obj_icon.alt = '关闭详细';
	} else {
		div_appinfo_a.style.display = 'block';
		div_appinfo_b.style.display = 'none';
		obj_icon.alt = '详细';
	}
}
</script>
<input type="hidden" id="inprogress_img" value="<%=inprogress_img %>">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_2">
                                <tr>
                                  <td width="33%" class="r_bor">
                                    <table border=0 width="100%">
																		<tr class="nprint">
									<td nowrap align="right" class="nprint" colspan="2" style="border:0px">
							<div id="ldResult"></div>
							 </td>
						</tr>
																		<tr>
																			<td width="25%" align="left" style="border:0px">
																				签&nbsp;&nbsp;发:
																			</td>
																			<td class="nprint" width="25%" align="right" style="border:0px">
																				<%
																				if(pbApprovedinfoList!=null && pbApprovedinfoList.size()>1){
																				%>
																				<img alt="详细" onClick="showDescription(99,this)"
																					style="cursor: hand"
																					src="<%=path %>/send/images/118.gif" />
																				<%} %>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="3"  style="border:0px">
																				<div id="appinfo_a_99">
																					<%
										if(pbApprovedinfoList!=null && pbApprovedinfoList.size()>0){
												SendApprovedinfo approvedinfo=(SendApprovedinfo)pbApprovedinfoList.get(0);
												String operatTypeTemp=approvedinfo.getOptionCode();
												if("LEADER_ASSISTANT_DEAL_OTHER_NOTION".equals(operatTypeTemp)){
													out.print("<img alt='其他意见' src='"+path+"/send/images/h_ar1.gif' />");
												}
												out.print(donull.dealNull(approvedinfo.getRemark())+"<br>");
												
												//zhoushun
												if(approvedinfo.getFileGroupId() != null && !"".equals(approvedinfo.getFileGroupId())){
													out.print("<a href='"+request.getContextPath()+
													"/attach/loadFileList.action?attachMemo=fawen_att_dic&fileGroup=docSeYjFile&fileGroupName=docSeYjFileGroup&userName="+
													loginName+"&loginName="+loginName+"&fileGroupId="+
													approvedinfo.getFileGroupId()+"&procType=view&targetType=frame' target='_blank'><img alt='附件' style='cursor:hand' src='"+path+"/send/images/fj.gif'/></a>");
												}
												//王立俊 2010-11-12
												//发文审稿时添加办公室审核时的处理人及其秘书信息 
												if(deptshldId1.equals(approvedinfo.getUsername())||deptshldId2.equals(approvedinfo.getUsername())){
													out.println("<div align=right>行政办公室&nbsp;<br>" + DateUtil.getDateStr(approvedinfo.getUpddate(),"yyyy-MM-dd") +"</div>");
												}else if(deptshldId3.equals(approvedinfo.getUsername())){
													out.println("<div align=right>党委办公室&nbsp;<br>" + DateUtil.getDateStr(approvedinfo.getUpddate(),"yyyy-MM-dd") +"</div>");
												}else{
													out.println("<div align=right>"+approvedinfo.getUserfullname() + "&nbsp;<br>" + DateUtil.getDateStr(approvedinfo.getUpddate(),"yyyy-MM-dd") +"</div>");
												}
										}else{
											out.println("<br><br><br>");
										}
									%>
																				</div>
																				<div id="appinfo_b_99" style="display: none;">
																					<%
										if(pbApprovedinfoList!=null && pbApprovedinfoList.size()>0){
											for(int i=0;i<pbApprovedinfoList.size();i++){
												SendApprovedinfo approvedinfo=(SendApprovedinfo)pbApprovedinfoList.get(i);
												String operatTypeTemp=approvedinfo.getOptionCode();
												if("LEADER_ASSISTANT_DEAL_OTHER_NOTION".equals(operatTypeTemp)){
													out.print("<img alt='其他意见' src='"+path+"/send/images/h_ar1.gif' />");
												}
												out.print(donull.dealNull(approvedinfo.getRemark())+"<br>");
												
												//zhoushun
												if(approvedinfo.getFileGroupId() != null && !"".equals(approvedinfo.getFileGroupId())){
													out.print("<a href='"+request.getContextPath()+
													"/attach/loadFileList.action?attachMemo=fawen_att_dic&fileGroup=docSeYjFile&fileGroupName=docSeYjFileGroup&userName="+
													loginName+"&loginName="+loginName+"&fileGroupId="+
													approvedinfo.getFileGroupId()+"&procType=view&targetType=frame' target='_blank'><img alt='附件' style='cursor:hand' src='"+path+"/send/images/fj.gif'/></a>");
												}
												
												//王立俊 2010-11-12
												//发文审稿时添加办公室审核时的处理人及其秘书信息 
												if(deptshldId1.equals(approvedinfo.getUsername())||deptshldId2.equals(approvedinfo.getUsername())){
													out.println("<div align=right>行政办公室&nbsp;<br>" + DateUtil.getDateStr(approvedinfo.getUpddate(),"yyyy-MM-dd") +"</div>");
												}else if(deptshldId3.equals(approvedinfo.getUsername())){
													out.println("<div align=right>党委办公室&nbsp;<br>" + DateUtil.getDateStr(approvedinfo.getUpddate(),"yyyy-MM-dd") +"</div>");
												}else{
													out.println("<div align=right>"+approvedinfo.getUserfullname() + "&nbsp;<br>" + DateUtil.getDateStr(approvedinfo.getUpddate(),"yyyy-MM-dd") +"</div>");
												}
											}
										}
									%>
																				</div>
																			</td>
																		</tr>
																	</table>
                                    
                                    
                                  </td>
                                  <td width="33%" class="r_bor">
                                    <div>审 阅：</div>
                                    <div class="clearfix">
                                      <div></div>
                                      <div class="fr"></div>
                                      <div class="clear"></div>
                                      <div class="fr"></div>
                                    </div>
                                  </td>
                                  <td>
                                    <table border=0 width="100%">
																		<tr class="nprint">
																			<td nowrap valign="top" align="left" class="nprint"  style="border:0px"></td>
																			<td nowrap align="right" class="nprint"  style="border:0px">
																			<div id="ldResult2"></div>
																			 </td>
																		</tr>
																		<tr>
																			<td width="25%" align="left" style="border:0px">
																				会&nbsp;&nbsp;签:
																			</td>
																			<td class="nprint" width="75%" align="right" style="border:0px">
																				<%
																				if(hqApprovedinfoList!=null && hqApprovedinfoList.size()>1){
																				%>
																				<img alt="详细" onClick="showDescription(2,this)"
																					style="cursor: hand"
																					src="<%=path %>/send/images/118.gif" />
																				<%} %>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="3" style="border:0px">
																				<div id="appinfo_a_2" style="display: none;">
																					<%
								if(hqApprovedinfoList!=null && hqApprovedinfoList.size()>0){
										SendApprovedinfo approvedinfo=(SendApprovedinfo)hqApprovedinfoList.get(0);
										String operatTypeTemp=approvedinfo.getOptionCode();
										if("LEADER_DEAL_OTHER_NOTION".equals(operatTypeTemp)){
											out.print("<img alt='其他意见' src='"+path+"/send/images/h_ar1.gif' />");
										}
										out.print(donull.dealNull(approvedinfo.getRemark())+"<br>");
										out.println("<div align=right>"+approvedinfo.getUserfullname() + "&nbsp;<br>" + DateUtil.getDateStr(approvedinfo.getUpddate(),"yyyy-MM-dd") +"</div>");
								}
							%>
																				</div>
																				<div id="appinfo_b_2">
																					<%
								if(hqApprovedinfoList!=null && hqApprovedinfoList.size()>0){
									for(int i=0;i<hqApprovedinfoList.size();i++){
										SendApprovedinfo approvedinfo=(SendApprovedinfo)hqApprovedinfoList.get(i);
										String operatTypeTemp=approvedinfo.getOptionCode();
										if("LEADER_DEAL_OTHER_NOTION".equals(operatTypeTemp)){
											out.print("<img alt='其他意见' src='"+path+"/send/images/h_ar1.gif' />");
										}
										out.print(donull.dealNull(approvedinfo.getRemark())+"<br>");
										out.println("<div align=right>"+approvedinfo.getUserfullname() + "&nbsp;<br>" + DateUtil.getDateStr(approvedinfo.getUpddate(),"yyyy-MM-dd") +"</div>");
									}
								}
							%>
																				</div>
																			</td>
																		</tr>
																	</table>
                                  </td>
                                </tr>
                                <tr>
                                  <td width="33%" class="lableTd t_c r_bor">拟稿部门</td>
                                  <td width="33%" class="lableTd t_c r_bor">会签部门</td>
                                  <td class="lableTd t_c">核稿部门</td>
                                </tr>
                                <tr>
                                  <td width="33%" class="r_bor">
                                    <table border=0 width="100%">
																		<tr>
																			<td width="25%" align="left" style="border:0px">
																				拟稿人:&nbsp;
																			</td>
																			<td width="25%" align="right" style="border:0px">
																				<%if(qcApprovedinfoList!=null && qcApprovedinfoList.size()>1){ %>
																				<img alt="详细" onClick="showDescription(98,this)"
																					style="cursor: hand"
																					src="<%=path %>/send/images/118.gif" />
																				<%} %>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="3" style="border:0px">
																	<div align="left" id="appinfo_a_98">
																	<%
								if(qcApprovedinfoList!=null && qcApprovedinfoList.size()>0){
									SendApprovedinfo approvedinfo=(SendApprovedinfo)qcApprovedinfoList.get(0);
									out.println(donull.dealNull(approvedinfo.getRemark())+"<br>");
									out.println("<div align=right>"+approvedinfo.getUserfullname() + "&nbsp;" + DateUtil.getDateStr(approvedinfo.getUpddate(),"yyyy-MM-dd") +"</div>");
								}else{
									if(donull.dealNull(model.getSendUser())!=null&&donull.dealNull(model.getSendUser()).length()>0){
										out.println("<div align=right>"+donull.dealNull(model.getSendUser()) + "&nbsp;" + DateUtil.getDateStr(tasks.getStartTime(),"yyyy-MM-dd") +"</div>");
									}
								}
							%></div>
																	<div align="left"  id="appinfo_b_98" style="display: none;">
																					<%
								if(qcApprovedinfoList!=null && qcApprovedinfoList.size()>0){
									for(int i=0;i<qcApprovedinfoList.size();i++){
										SendApprovedinfo approvedinfo=(SendApprovedinfo)qcApprovedinfoList.get(i);
										out.println(donull.dealNull(approvedinfo.getRemark())+"<br>");
										out.println("<div align=right>"+approvedinfo.getUserfullname() + "&nbsp;" + DateUtil.getDateStr(approvedinfo.getUpddate(),"yyyy-MM-dd") +"</div>");
									}
								}else{
									if(donull.dealNull(model.getSendUser())!=null&&donull.dealNull(model.getSendUser()).length()>0){

										out.println("<div align=right>"+donull.dealNull(model.getSendUser()) + "&nbsp;" + DateUtil.getDateStr(tasks.getStartTime(),"yyyy-MM-dd") +"</div>");
									}
								}
							%></div></td></tr>
																	<tr>
																		<td width="28%" align="left" style="border:0px" style="border:0px">
																			部门领导:
																		</td>
																		<td class="nprint" width="25%" align="right" style="border:0px">
																		<%if(sgApprovedinfoList!=null && sgApprovedinfoList.size()>1){ %>
																			<img alt="详细" onClick="showDescription(97,this)"
																				style="cursor: hand"
																				src="<%=path %>/send/images/118.gif" />
																		<%} %>
																		</td>
																	</tr>
																	<tr>
																			<td colspan="3" style="border:0px" style="border:0px">
																	<div align="left" id="appinfo_a_97">
																	<%
								if(sgApprovedinfoList!=null && sgApprovedinfoList.size()>0){
										SendApprovedinfo approvedinfo=(SendApprovedinfo)sgApprovedinfoList.get(0);
										out.println(donull.dealNull(approvedinfo.getRemark())+"<br>");
										out.println("<div align=right>"+approvedinfo.getUserfullname() + "&nbsp;" + DateUtil.getDateStr(approvedinfo.getUpddate(),"yyyy-MM-dd") +"</div>");
								}else{
									out.println("<div align=right>&nbsp;</div>");
								}
							%></div>
																	<div align="left"  id="appinfo_b_97" style="display: none;">
																					<%
								if(sgApprovedinfoList!=null && sgApprovedinfoList.size()>0){
									for(int i=0;i<sgApprovedinfoList.size();i++){
										SendApprovedinfo approvedinfo=(SendApprovedinfo)sgApprovedinfoList.get(i);
										out.println(donull.dealNull(approvedinfo.getRemark())+"<br>");
										out.println("<div align=right>"+approvedinfo.getUserfullname() + "&nbsp;" + DateUtil.getDateStr(approvedinfo.getUpddate(),"yyyy-MM-dd") +"</div>");
									}
								}else{
									out.println("<div align=right>&nbsp;</div>");
								}
							%></div>
							</td></tr></table>
                                    
                                    
                                    <!-- 
                                    <div>拟稿人：</div>
                                    <div class="clearfix">
                                      <div>&nbsp;</div>
                                      <div class="fr">&nbsp;</div>
                                      <div class="fr mr8">&nbsp;</div>
                                    </div>
                                    <div>部门领导：</div>
                                    <div class="clearfix">
                                      <div>&nbsp;</div>
                                      <div class="fr">&nbsp;</div>
                                      <div class="fr mr8">&nbsp;</div>
                                    </div>
                                    -->
                                  </td>
                                  <td width="33%" class="r_bor">
                                  	<table border=0 width="100%">
										<tr>
											<td class="nprint" width="35%" align="right" style="border:0px">
											</td>
										</tr>
										<tr>
											<td colspan="2" style="border:0px">
												<table width='100%' height="100%" bordercolorlight='#000000' bordercolordark='#FFFFFF' border=0 cellpadding='0' cellspacing='0' align="left" >
													<tr class="nprint">
														<td nowrap valign="top" align="left" class="nprint"  style="border:0px"></td>
														<td colspan='7' align="right" class="nprint"  style="border:0px">
														<div id = 'deptResult'></div>
														<%
														int k = 1;
														 %>
														 <br> 
														 </td>
													</tr>	
													<%
															List list = null;
															list = tapprovedinfoService.quereyApprovedInfoByDeptIdWithLeaderSign(model.getModelid(), model.getPinstanceid(), new String[]{ "部门领导审核"},null,"bumenlingdao");
															List list1 = tapprovedinfoService.quereyApprovedInfoByDeptIdWithLeaderSign(model.getModelid(), model.getPinstanceid(), new String[]{ "部门接受人工作分发","部门业务人员处理","部门领导审核"},null,"bumenlingdao");
															List deptList = tapprovedinfoService.queryApprovedinfoLikeByDeptId(model.getModelid(), model.getPinstanceid(), new String[]{ "部门接受人工作分发","部门业务人员处理","部门领导审核"});
															
															if(deptList != null){
																Iterator iter = deptList.iterator();
																for(int j = 0 ; iter.hasNext(); j ++){
																	String o = (String)iter.next();
																%>				
																	<tr>
																		
																		<TD valign="top" align="center" width="10%" class="nprint" style="border:0px"><img alt='详细' onClick='showDescription2(<%=j%>,this)' style='cursor:hand' src='<%=path%>/send/images/118.gif'/>
																		&nbsp;</TD>
																		<td colspan='7' width="90%" style="border:0px">
																			<div id='appinfo4_a_<%=j%>' >
																			<%
																				if (list != null && list.size() > 0) {	
																					for (int i = 0; i<list.size() ; i++) {				
																						TApprovedinfoVo approvedinfo = (TApprovedinfoVo) list.get(i);
																						
																						if(o.equals(approvedinfo.getDeptId())){
																							String userName = approvedinfo.getUsername();
																							if(userName!= null){
																								if(userName.startsWith("ST/")){
																									userName = userName.substring(3,userName.length());
																								}
																								//if(!"0".equals(approvedinfo.getIsLeader())){
																								
																									if(approvedinfo.getFileGroupId() != null && !"".equals(approvedinfo.getFileGroupId())){
																										out.println("<div align=left>"+StringUtil.getNotNullValueString(approvedinfo.getRemark()) + "<a href='"+request.getContextPath()+"/attach/loadFileList.action?fileGroup=docReYjFile&fileGroupName=docReYjFileGroup&userName="+loginName+"&loginName="+loginName+"&fileGroupId="+approvedinfo.getFileGroupId()+"&procType=view&targetType=frame' target='_blank'><img alt='附件' style='cursor:hand' src='"+path+"/send/images/fj.gif'/></a></div>");
																									}else{
																										out.println("<div align=left>"+StringUtil.getNotNullValueString(approvedinfo.getRemark()) + "</div>");
																									}
																									out.println("<div align=right>" + StringUtil.getNotNullValueString(approvedinfo.getUserfullname()) + "&nbsp;&nbsp;"	+ StringUtil.getNotNullValueString(new SimpleDateFormat("yyyy-MM-dd").format(approvedinfo.getUpddate())) + "</div>");
																								//}
																							}
																						}
																					}
																				}else{
																					//out.println("<div align=center>"+organNode.getName()+":暂无。</div>");
																				}
																			%>					
																			</div>
																			<div id='appinfo4_b_<%=j%>' style='display: none;'>
																			<%
																				if (list1 != null && list1.size() > 0) {
																			 		for (int i = 0; i <list1.size() ; i++) {
																			 			
																			 			TApprovedinfoVo approvedinfo = (TApprovedinfoVo) list1.get(i);
																			 			if(o.equals(approvedinfo.getDeptId())){
																			 				if(k%2 == 0){
																			 					out.println("<div align=left style='background-color:#F2F2F2;'>");
																				 			}else{
																				 				out.println("<div align=left >");
																				 			}
																				 			k++;
																							if(approvedinfo.getFileGroupId() != null && !"".equals(approvedinfo.getFileGroupId())){
																								out.println("<div align=left>"+StringUtil.getNotNullValueString(approvedinfo.getRemark()) + "<a href='"+request.getContextPath()+"/attach/loadFileList.action?fileGroup=docReYjFile&fileGroupName=docReYjFileGroup&userName="+loginName+"&loginName="+loginName+"&fileGroupId="+approvedinfo.getFileGroupId()+"&procType=view&targetType=frame' target='_blank'><img alt='附件' style='cursor:hand' src='"+path+"/send/images/fj.gif'/></a></div>");
																							}else{
																								out.println("<div align=left>"+StringUtil.getNotNullValueString(approvedinfo.getRemark()) + "</div>");
																							}
																							out.println("<div align=right>" + StringUtil.getNotNullValueString(approvedinfo.getUserfullname()) + "&nbsp;&nbsp;"	+ StringUtil.getNotNullValueString(new SimpleDateFormat("yyyy-MM-dd").format(approvedinfo.getUpddate())) + "</div><br>");
																							out.println("</div>");
																						}
																					}
																				}else{
																					//out.println("<div align=center>"+organNode.getName()+":暂无。</div>");
																				}
																				
																			%>
																			</div>
																	</td>
																</tr>	
																<%}
														} 
														%>
												</table>
											</td>
										</tr>
									</table>
                                  </td>
                                  <td>
                                  <table border=0 width="100%">
																		<tr>
																			<td class="nprint" width="35%" align="right" style="border:0px">
																			<%if(xgApprovedinfoList!=null && xgApprovedinfoList.size()>1){ %>
																				<img alt="详细" onClick="showDescription(100,this)"
																					style="cursor: hand"
																					src="<%=path %>/send/images/118.gif" />
																			<%} %>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2" style="border:0px">
																				<div id="appinfo_a_100">
																					<%
								if(xgApprovedinfoList!=null && xgApprovedinfoList.size()>0){
										SendApprovedinfo approvedinfo=(SendApprovedinfo)xgApprovedinfoList.get(0);
										out.println(donull.dealNull(approvedinfo.getRemark())+"<br>");
										if(approvedinfo.getFileGroupId() != null && !"".equals(approvedinfo.getFileGroupId())){
											out.print("<a href='"+request.getContextPath()+
											"/attach/loadFileList.action?attachMemo=fawen_att_dic&fileGroup=docSeYjFile&fileGroupName=docSeYjFileGroup&userName="+
											loginName+"&loginName="+loginName+"&fileGroupId="+
											approvedinfo.getFileGroupId()+"&procType=view&targetType=frame' target='_blank'><img alt='附件' style='cursor:hand' src='"+path+"/send/images/fj.gif'/></a>");
										}
										out.println("<div align=right>"+approvedinfo.getUserfullname() + "&nbsp;" + DateUtil.getDateStr(approvedinfo.getUpddate(),"yyyy-MM-dd") +"</div>");
								}
							%>
																				</div>
																				<div id="appinfo_b_100" style="display: none;">
																					<%
								if(xgApprovedinfoList!=null && xgApprovedinfoList.size()>0){
									for(int i=0;i<xgApprovedinfoList.size();i++){
										SendApprovedinfo approvedinfo=(SendApprovedinfo)xgApprovedinfoList.get(i);
										out.println(donull.dealNull(approvedinfo.getRemark())+"<br>");
										if(approvedinfo.getFileGroupId() != null && !"".equals(approvedinfo.getFileGroupId())){
											out.print("<a href='"+request.getContextPath()+
											"/attach/loadFileList.action?attachMemo=fawen_att_dic&fileGroup=docSeYjFile&fileGroupName=docSeYjFileGroup&userName="+
											loginName+"&loginName="+loginName+"&fileGroupId="+
											approvedinfo.getFileGroupId()+"&procType=view&targetType=frame' target='_blank'><img alt='附件' style='cursor:hand' src='"+path+"/send/images/fj.gif'/></a>");
										}
										out.println("<div align=right>"+approvedinfo.getUserfullname() + "&nbsp;" + DateUtil.getDateStr(approvedinfo.getUpddate(),"yyyy-MM-dd") +"</div>");
									}
								}
							%>
																				</div>
																			</td>
																		</tr>
																	</table>
                                  </td>
                                </tr>
                              </table>

