<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="com.wonders.util.StringUtil"%>
<%
	String fileGroup = (String)request.getParameter("fileGroup");
	if(StringUtil.isNull(fileGroup)){
		fileGroup = (String)request.getAttribute("fileGroup");
	}
	String fileGroupId = (String)request.getParameter("fileGroupId");
	String newGroupId = (String)request.getAttribute("newGroupId");
	if(StringUtil.isNull(fileGroupId)){
		fileGroupId = (String)request.getAttribute("fileGroupId");
		if(StringUtil.isNull(fileGroupId)){
			fileGroupId = newGroupId;
		}
	}
	String fileGroupName = (String)request.getParameter("fileGroupName");
	if(StringUtil.isNull(fileGroupName)){
		fileGroupName = (String)request.getAttribute("fileGroupName");
	}
	String procType = (String)request.getParameter("procType");
	if(StringUtil.isNull(procType)){
		procType = (String)request.getAttribute("procType");
	}
	String targetType = (String)request.getParameter("targetType");
	if(StringUtil.isNull(targetType)){
		targetType = (String)request.getAttribute("targetType");
	}
	String attachMemo = StringUtil.getNotNullValueString(request.getParameter("attachMemo1"));
	String fileCntObjId = request.getParameter("fileCntObjId");
	if (fileCntObjId == null) fileCntObjId = "";
	//String url = request.getContextPath()+"/loadFileList.action?fileGroup="+fileGroup+"&fileGroupName="+fileGroupName
	//			+"&fileGroupId="+fileGroupId+"&newGroupId="+newGroupId+"&procType="+procType+"&targetType="+targetType
	//			+ "&fileCntObjId=" + fileCntObjId+"&attachMemo=" + attachMemo;
	//System.out.println("url==="+url);
	//request.getRequestDispatcher(url).forward(request,response);
	//response.sendRedirect(url);
 %>
 
<script language="javascript" type="text/javascript">
 parent.reLoadFL('<%=fileGroupId%>','<%=fileCntObjId%>');
 </script>