<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page import="com.wonders.send.approve.model.bo.SendApprovedinfo"%>
<%@page import="com.wonders.send.util.Donull"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.wonders.util.DateUtil"%>
<%@page import="com.wonders.util.StringUtil"%>
<%@page import="com.wonders.send.mainProcess.send.model.bo.TDocSend"%>
<%@page import="org.springframework.web.context.WebApplicationContext" %>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@page import="com.wonders.send.approve.service.TApprovedinfoService"%>
<%@ page import="com.wonders.common.model.vo.TaskUserVo" %>
<%@ page import="com.wonders.constants.LoginConstants" %>
<%
String path = request.getContextPath();

Donull donull = new Donull();
TDocSend model =(TDocSend)request.getAttribute("model");
WebApplicationContext context=WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());	
TApprovedinfoService tapprovedinfoService=(TApprovedinfoService)context.getBean("send-tApprovedInfoService");


String loginName = (String)session.getAttribute("login_name");
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
 %>
<script>

</script>
<tr class="content6">
	<td class="lableTd t_r">办理情况</td>
	<td class="pl18" colspan="3">
	<%
	List msList = tapprovedinfoService.queryApprovedinfo(model.getModelid(), model.getPinstanceid(), new String[]{ "秘书","签发秘书"},null);
	if (msList != null && msList.size() > 0) {
 		for (int i = 0; i < msList.size(); i++) {
 		//System.out.println("---------------------------------" + list.size());
 		SendApprovedinfo approvedinfo = (SendApprovedinfo) msList.get(i);
	 			//out.println(img);
 				out.println("<div align=left >");
			if(approvedinfo.getFileGroupId() != null && !"".equals(approvedinfo.getFileGroupId())){
				out.print("<div align=left>"+StringUtil.getNotNullValueString(approvedinfo.getRemark()) 
				+"<a href='"+request.getContextPath()+"/loadFileList.action?attachMemo=shouwen_att_dic&fileGroup=docReYjFile&fileGroupName=docReYjFileGroup&userName="
				+session.getAttribute("login_name")+"&loginName="+session.getAttribute("login_name")+"&fileGroupId="
				+approvedinfo.getFileGroupId()+"&procType=view&targetType=frame' target='_blank'><img alt='附件' style='cursor:hand' src='/images/new_images/fj.gif'/></a>"+"[" + StringUtil.getNotNullValueString(approvedinfo.getUserfullname()) + "&nbsp;&nbsp;"	+ StringUtil.getNotNullValueString(new SimpleDateFormat("yyyy-MM-dd").format(approvedinfo.getUpddate())) + "]"+"</div>");
			}else{
				out.print("<div align=left>"+StringUtil.getNotNullValueString(approvedinfo.getRemark()) +"[" + StringUtil.getNotNullValueString(approvedinfo.getUserfullname()) + "&nbsp;&nbsp;"	+ StringUtil.getNotNullValueString(new SimpleDateFormat("yyyy-MM-dd").format(approvedinfo.getUpddate())) + "]"+ "</div>");
			}
			//out.println("[" + StringUtil.getNotNullValueString(approvedinfo.getUserfullname()) + "&nbsp;&nbsp;"	+ StringUtil.getNotNullValueString(new SimpleDateFormat("yyyy-MM-dd").format(approvedinfo.getUpddate())) + "]");
			out.println("</div>");
		}
	}else{
		out.println("&nbsp;");
	}
	%>
	</td>
 </tr>
 <s:if test="#request.print!=1">
 <tr class="content6">
	<td class="lableTd t_r">套头意见</td>
	<td class="pl18" colspan="3">
		<%															
			List list =tapprovedinfoService.queryApprovedinfoLike(model.getModelid(), model.getPinstanceid(), new String[]{"套头"},null);
			
			if (list != null && list.size() > 0) {
		 		for (int i = 0; i < list.size(); i++) {
		 		//System.out.println("---------------------------------" + list.size());
		 			if(i%2 == 0){
		 				out.println("<div align=left style='background-color:#F2F2F2;'>");
		 			}else{
		 				out.println("<div align=left >");
		 			}
		 			SendApprovedinfo approvedinfo = (SendApprovedinfo) list.get(i);
		 			if(approvedinfo.getFileGroupId() != null && !"".equals(approvedinfo.getFileGroupId())){
						out.print("<div align=left>"+StringUtil.getNotNullValueString(approvedinfo.getRemark()) 
						+"<a href='"+request.getContextPath()+"/attach/loadFileList.action?attachMemo=fawen_att_dic&fileGroup=docSeYjFile&fileGroupName=docSeYjFileGroup&userName="
						+loginName+"&loginName="+loginName+"&fileGroupId="
						+approvedinfo.getFileGroupId()+"&procType=view&targetType=frame' target='_blank'><img alt='附件' style='cursor:hand' src='"+path+"/send/images/fj.gif'/></a>"+"[" + StringUtil.getNotNullValueString(approvedinfo.getUserfullname()) + "&nbsp;&nbsp;"	+ StringUtil.getNotNullValueString(new SimpleDateFormat("yyyy-MM-dd").format(approvedinfo.getUpddate())) + "]"+"</div>");
					}else{
						out.print("<div align=left>"+StringUtil.getNotNullValueString(approvedinfo.getRemark()) +"[" + StringUtil.getNotNullValueString(approvedinfo.getUserfullname()) + "&nbsp;&nbsp;"	+ StringUtil.getNotNullValueString(new SimpleDateFormat("yyyy-MM-dd").format(approvedinfo.getUpddate())) + "]"+ "</div>");
					}
					//out.println("<div align=left>"+StringUtil.getNotNullValueString(approvedinfo.getRemark()) + "</div>");
					//out.println("<div align=right>" + StringUtil.getNotNullValueString(approvedinfo.getUserfullname()) + "&nbsp;&nbsp;"	+ StringUtil.getNotNullValueString(new SimpleDateFormat("yyyy-MM-dd").format(approvedinfo.getUpddate())) + "</div>");
					out.println("</div>");
				}
			}
		%>
	</td>
</tr>
</s:if>
 <tr class="content6">
	<td class="lableTd t_r">办结人意见</td>
	<td class="pl18" colspan="3">
	<%
	List bjlist =tapprovedinfoService.queryApprovedinfoLike(model.getModelid(), model.getPinstanceid(), new String[]{"办结"},null);
	if (bjlist != null && bjlist.size() > 0) {
 		for (int i = 0; i < bjlist.size(); i++) {
 		//System.out.println("---------------------------------" + list.size());
 			if(i%2 == 0){
 				out.println("<div align=left style='background-color:#F2F2F2;display:inline;'>");
 			}else{
 				out.println("<div align=left style='display:inline;'>");
 			}
 			SendApprovedinfo approvedinfo = (SendApprovedinfo) bjlist.get(i);
 			//  王立俊 2010-09-16 
 			//新增办结节点意见附件显示
 			if(approvedinfo.getFileGroupId() != null && !"".equals(approvedinfo.getFileGroupId())){
				out.print("<div align=left style='display:inline;'>"+StringUtil.getNotNullValueString(approvedinfo.getRemark()) 
				+"<a href='"+request.getContextPath()+"/loadFileList.action?attachMemo=fawen_att_dic&fileGroup=docSeYjFile&fileGroupName=docSeYjFileGroup&userName="
				+loginName+"&loginName="+loginName+"&fileGroupId="
				+approvedinfo.getFileGroupId()+"&procType=view&targetType=frame' target='_blank'><img alt='附件' style='cursor:hand;display:inline' src='"+path+"/send/images/fj.gif'/></a>"+"[" + StringUtil.getNotNullValueString(approvedinfo.getUserfullname()) + "&nbsp;&nbsp;"	+ StringUtil.getNotNullValueString(new SimpleDateFormat("yyyy-MM-dd").format(approvedinfo.getUpddate())) + "]"+"</div>");
			}else{
				out.print("<div align=left style='display:inline;'>"+StringUtil.getNotNullValueString(approvedinfo.getRemark()) +"[" + StringUtil.getNotNullValueString(approvedinfo.getUserfullname()) + "&nbsp;&nbsp;"	+ StringUtil.getNotNullValueString(new SimpleDateFormat("yyyy-MM-dd").format(approvedinfo.getUpddate())) + "]"+ "</div>");
			}
			//out.println("<div align=left>"+StringUtil.getNotNullValueString(approvedinfo.getRemark()) + "</div>");
			//out.println("<div align=right>" + StringUtil.getNotNullValueString(approvedinfo.getUserfullname()) + "&nbsp;&nbsp;"	+ StringUtil.getNotNullValueString(new SimpleDateFormat("yyyy-MM-dd").format(approvedinfo.getUpddate())) + "</div>");
			out.println("</div>");
		}
	}
	%>
	</td>
</tr>
