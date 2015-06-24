<%@page import="com.wonders.util.StringUtil"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" import="java.io.*,java.util.*" %>
<%
	String error = (String)request.getAttribute("error");
String listType = request.getParameter("listType");
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
	String attachMemo = (String)request.getParameter("attachMemo1");
	if(StringUtil.isNull(attachMemo)){
		attachMemo = (String)request.getAttribute("attachMemo");
	}
	
	String approve = (String)request.getParameter("approve");
	if(StringUtil.isNull(approve)){
		approve = (String)request.getAttribute("approve");
	}
	
	String fileCntObjId = request.getParameter("fileCntObjId");
	if (fileCntObjId == null) fileCntObjId = "";
	String url = request.getContextPath()+"/attach/loadFileList.action?fileGroup="+fileGroup+"&fileGroupName="+fileGroupName
				+"&fileGroupId="+fileGroupId+"&newGroupId="+newGroupId+"&procType="+procType+"&targetType="+targetType
				+ "&fileCntObjId=" + fileCntObjId+"&attachMemo=" + attachMemo+"&approve="+approve+"&error="+error+"&listType="+listType;
	//System.out.println("url==="+url);
	//request.getRequestDispatcher(url).forward(request,response);
	response.sendRedirect(url);
 %>
正在返回...