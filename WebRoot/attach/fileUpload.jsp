<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<!DOCTYPE html>
<%
String path = request.getContextPath();
%>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=8">
<title>项目立项</title>
<base target="_self"/>
<link rel="stylesheet" href="<%=path %>/receive/css/formalize.css" />
<link rel="stylesheet" href="<%=path %>/receive/css/page.css" />
<link rel="stylesheet" href="<%=path %>/receive/css/default/imgs.css" />
<link rel="stylesheet" href="<%=path %>/receive/css/reset.css" />
<link href="<%=path %>/receive/css/organTree.css" rel="stylesheet" type="text/css">
</head>
<body class="Flow" style="width:100%; ">
 <!--Transparent-->
     
    <div id="bt" class=" transparent" ></div>
    <div class="f_window" style="white-space:nowrap;width:98%;" >
   	  <h3 class="clearfix mb10"><span class="fl">Error</span></h3>
        <div class="con">
			出错了，文件大小不能超过20M，请关闭重新上传!
		</div>
	</div>
</body>
</html>