<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" import="java.io.*,java.util.*" %>
<%@page import="com.wonders.attach.model.vo.UploadFile,com.wonders.attach.util.AttachUtil"%>
<%
String path = request.getContextPath();
	AttachUtil attachUtil = AttachUtil.getInstance();
	List fileList = (List)request.getAttribute("fileList");
%>
<head>
<meta http-equiv="x-ua-compatible" content="IE=8">
<base target="_self">
<link href="<%=path %>/receive/css/organTree.css" rel="stylesheet" type="text/css">
</head>
<body>
<table  width="100%" bordercolorlight="#000000" bordercolordark="#FFFFFF" border="1" cellpadding="5" cellspacing="0">
<%
if(fileList!=null&&fileList.size()>0){
 %>
 	<tr>
 		<td colspan="7">历史版本</td>
 	</tr>
	<tr>
		<td nowrap="nowrap" ><font style="font-family: 黑体;"><b>文件名</b></font></td>
		<td nowrap="nowrap" ><font style="font-family: 黑体;"><b>大小</b></font></td>
		<td nowrap="nowrap" ><font style="font-family: 黑体;"><b>上传时间</b></font></td>
		<td nowrap="nowrap" ><font style="font-family: 黑体;"><b>上传者</b></font></td>
		<td nowrap="nowrap" ><font style="font-family: 黑体;"><b>版本</b></font></td>
		<td nowrap="nowrap" ><font style="font-family: 黑体;"><b>状态</b></font></td>
		<td nowrap="nowrap" ><font style="font-family: 黑体;"><b>下载</b></font></td>
	</tr>
<%
	for(int i=0;i<fileList.size();i++){
		UploadFile file = (UploadFile)fileList.get(i);
	%>
	<tr>
		<td style="line-height: 15px"><img src="<%=request.getContextPath()%>/receive/css/default/images/files/<%=attachUtil.getFileImageName(file.getFileExtName()) %>.gif" /><%=file.getFileAllName() %></td>
		<td style="line-height: 15px"><%= file.getFileSize() > 1024*1024 ? file.getFileSize() * 100 / 1024 / 1024 / 100.0 + " M" : (file.getFileSize() > 1024 ? file.getFileSize() * 100 / 1024 / 100.0 + " K" : file.getFileSize() + " B") %></td>
		<td style="line-height: 15px"><%=file.getUploadDate() %></td>
		<td style="line-height: 15px"><%=file.getUploader() %></td>
		<td style="line-height: 15px">v<%=file.getVersion() %></td>
		<td style="line-height: 15px"><%=file.getStatusStr() %></td>
		<td style="line-height: 15px"><a href="<%=request.getContextPath()%>/attach/downloadFile.action?fileId=<%=file.getAttachFile().getId()%>&ver=old" target="_blank">下载</a></td>
	</tr>
	<% 
	}
 %>	
<%
}else{
 %>
 <tr><td>
<font style="font-family: 黑体;"><b>没有历史版本。</b></font>
</td></tr>
 <%
 }
  %>
</table>
</body>