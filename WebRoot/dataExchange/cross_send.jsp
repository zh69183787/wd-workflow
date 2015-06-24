<%@ page language="java" import="java.util.*,java.net.*" pageEncoding="utf-8"%>

<%@page import="com.wonders.contract.workflow.common.model.vo.UserInfo"%>
<%@page import="com.wonders.common.model.vo.TaskUserVo"%>
<%@page import="com.wonders.contract.workflow.constants.CommonConstants"%>
<%@page import="com.wonders.contract.workflow.constants.LoginConstants"%>
<%@page import="com.wonders.dataExchange.util.*"%>
<%@page import="com.wonders.util.*"%>
<%@page import="com.wonders.contract.workflow.common.util.LoginUtil"%>
<%@page import="java.sql.Clob"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="org.springframework.web.util.HtmlUtils;"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!--

E80345F921AE41D1AF0B0287CAF5A39F	weibao	weibao2013!	docSend2925	维保公司
E87607F921AE41D1AF0B0287CAF5A39F	yunguan	yunguan2013!	docSend2920	运管中心
111807F921AE41D1AF0B0287CAF5A39F	yunsi	yunsi2013!	docSend2924	运四公司
E80807F9111E41D1AF0B0287CAF5A39F	yunsan	yunsan2013!	docSend2923	运三公司
E80807F9288841D1AF0B0287CAF5A39F	yunyi	yunyi2013!	docSend2921	运一公司
111807EWQEWE41D1AF0B0287CAF5A39F	daqiao	daqiao2013!	docSend2959	大桥
111807F921AE4QWEEWQB0287CAF5A39F	gfgs	gfgs2013!	docSend2945	股份公司
111807F92QWE41D1AF0B0287CAF5A39F	sdy	sdy2013!	docSend2943	隧道院
11QWERRR21AE41D1AF0B0287CAF5A39F	jszx	jszx2013!	docSend2941	技术中心
111807F921AQWER1AF0B0287CAF5A39F	yuner	yuner2013!	docSend2922	运二公司
111807F922AQWER1AF0B0287CAF5A39F	zc	zc2013!	docSend2946  资产公司

  -->

<%!

	public static String getAddUrl(String loginName,String deptId,
				String appName,String urlCa,String returnUrl)  throws UnsupportedEncodingException {
	String url = urlCa+"/login.action?"+
	"loginName="+loginName+"&password=wonder&validate=test"+
	"&deptId="+deptId+"&appName="+appName+"&returnUrl="+URLEncoder.encode(returnUrl,"UTF-8");
	return url;

	}
	
%>  

<%
	String deptIdParam = "";    
	String sender = "";//收发员
	String key = request.getParameter("key");
	if("E80807F9288841D1AF0B0287CAF5A39F".equals(key)){
		deptIdParam = "2921";
		sender = "G01001000018";
	}else if("E80807F9111E41D1AF0B0287CAF5A39F".equals(key)){
		deptIdParam = "2923";
		sender = "G01003000019";
	}else if("111807F921AE41D1AF0B0287CAF5A39F".equals(key)){
		deptIdParam = "2924";
		sender = "G04000000755";
	}else if("E87607F921AE41D1AF0B0287CAF5A39F".equals(key)){
		deptIdParam = "2920";
		sender = "G01001000851";
	}else if("E80345F921AE41D1AF0B0287CAF5A39F".equals(key)){
		deptIdParam = "2925";
		sender = "G01013800002";
	}else if("111807EWQEWE41D1AF0B0287CAF5A39F".equals(key)){
		deptIdParam = "2959";
		sender = "G06000000151";
	}else if("111807F921AE4QWEEWQB0287CAF5A39F".equals(key)){
		deptIdParam = "2945";
		sender = "G05000000202";
	}else if("111807F92QWE41D1AF0B0287CAF5A39F".equals(key)){
		deptIdParam = "2943";
		sender = "G09000000046";
	}else if("11QWERRR21AE41D1AF0B0287CAF5A39F".equals(key)){
		deptIdParam = "2941";
		sender = "T07000000016";
	}else if("111807F921AQWER1AF0B0287CAF5A39F".equals(key)){
		deptIdParam = "2922";
		sender = "G01002000566";
	}else if("111807F922AQWER1AF0B0287CAF5A39F".equals(key)){
		deptIdParam = "2946";
		sender = "T02010100134";
	}else if("000807F921AQWER1AF0B0287CAF5A39F".equals(key)){
		deptIdParam = "2944";
		sender = "G01011000025";
	}else if("232137F921AQWER1AF0B0287CAF5A39F".equals(key)){
		deptIdParam = "2962";
		sender = "G10000000019";
	}
	
	
	String appName = "";
	String urlCa = "";
	Properties properties = new Properties();
	String configPath = Thread.currentThread().getContextClassLoader().getResource("config.properties").getPath();
	try {
		FileInputStream fis = new FileInputStream(configPath);
		properties.load(fis);
		appName = properties.getProperty("appName");
		urlCa = properties.getProperty("urlCa");
		fis.close();
	} catch (Exception e) {
	}
	
	String path = request.getContextPath();
	String returnUrl=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ path+"/dataExchange/view.action?id=";
	if(request.getQueryString()!=null){   
		//returnUrl+="?"+req.getQueryString();
	}
	//appName = "workflow48101";
	
	
%>

<!DOCTYPE html>
<html>
  <head>
 	<script src="<%=path %>/dataExchange/js/html5.js"></script>
    <script src="<%=path %>/dataExchange/js/jquery-1.7.1.js"></script>
	<script src="<%=path %>/dataExchange/js/jquery.formalize.js"></script>
	<script src="<%=path %>/dataExchange/js/jquery.reveal.js"></script>
  	<link rel="stylesheet" href="<%=path %>/dataExchange/js/reveal.css" />
	
  	<link rel="stylesheet" href="<%=path %>/dataExchange/css/formalize.css" />
	<link rel="stylesheet" href="<%=path %>/dataExchange/css/page.css" />
	<link rel="stylesheet" href="<%=path %>/dataExchange/css/default/imgs.css" />
	<link rel="stylesheet" href="<%=path %>/dataExchange/css/reset.css" />
  	<script>
  	function showAdd(){
			window.location.href = '<%=getAddUrl(sender,deptIdParam,appName,urlCa,"/workflow/receive/process/recv/add_cross.jsp")%>';  			
	}
  	</script>

  	
  </head>
 
  <body>
      <iframe style="position:absolute;top:1px;right:9px;" src="<%=urlCa%>/logout.jsp" onload="showAdd()" frameborder="no" border="0" framespacing="0" name="clearSession" scrolling="No" height="0px" width="0px" noresize="noresize" id="clearSession" title="clearSession"></iframe>
  </body>
</html>
