<%@ page language="java" import="java.util.*,java.net.*" pageEncoding="utf-8"%>

<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="com.wonders.dept.workflow.common.model.vo.UserInfo"%>
<%@page import="com.wonders.common.model.vo.TaskUserVo"%>
<%@page import="com.wonders.dept.workflow.constants.CommonConstants"%>
<%@page import="com.wonders.dept.workflow.constants.LoginConstants"%>
<%@page import="com.wonders.util.*"%>
<%@page import="com.wonders.dept.workflow.common.util.LoginUtil"%>
<%@page import="com.wonders.dept.workflow.process.util.ProcessUtil"%>
<%
	String path = request.getContextPath();
	String ultimusIp = PWSProperties.getValueByKey("pws_server_ip");
	String type = StringUtil.getNotNullValueString(request.getParameter("type"));
	out.println(path);
	if("logout".equals(type)){
		session.invalidate();
		String t = "http://10.1.14.20:7001/ca/login.jsp?appName=workflowtest%26returnUrl=http%3A%252F%252F10.1.41.252%3A8080%252Fworkflow%252Fdept%252Ftodo%252FtodoItem.jsp";
		response.sendRedirect("http://10.1.14.20:7001/ca/logout2.jsp?appName=workflowtest&returnUrl="+t);
		
		return;
	}
	
	String loginUserId = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.USERID));
	String loginUserLoginName = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.LOGINNAME));
	String loginUserName = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.USERNAME));
	String loginUserDeptId = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.DEPTID));
	String loginUserDeptName = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.DEPTNAME));
	
	//System.out.println("loginUserId:"+loginUserId);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<script src="/workflow/dept/js/jquery-1.7.1.js"></script>
  	
  	<script>
  	$(document).ready(function(){
		
  	  	
  		//$("#userId").attr("value","");
		//$("#deptId").attr("value","");
		$("#type").attr("value","");

		$(".choice").click(function(){
			var url = "http://10.1.48.16:8080/flow/login.jsp";
			var loginName = $(this).attr("loginName");
			var deptId = $(this).parent("div.role").parent("div.zone").attr("id");

			var url = "http://10.1.14.20:7001/ca/login.action?loginName="+loginName
					+"&password=wonder&validate=test&deptId="+deptId+"&appName=flowtest&returnUrl="+url+"?rand="+Math.random();

			//console.log(url);
			window.location = url;

			return false;
		})
  	  	

		$("#logout").click(function(){
			alert(1);
			$("#type").attr("value","logout");
			$("#loginForm").submit();
			return false;
		})
  	})
  	
	function openForm(pname,pincident,cname,cincident,steplabel,taskid,taskuser){
  		if(pname=="部门内部审阅"){
			var url = "<%=path%>/dept-passMain/forward.action?"
				+"pname="+encodeURI(pname)+"&"
				+"pincident="+pincident+"&"
				+"cname="+encodeURI(cname)+"&"
				+"cincident="+cincident+"&"
				+"steplabel="+encodeURI(steplabel)+"&"
				+"taskid="+taskid+"&"
				+"taskuser="+taskuser+"&"
				+"rand="+Math.random();
			window.open(url);
  		}
			return false;
	  	}
  	
  	function scan(taskid){
		var url = "http://<%=ultimusIp%>/sLogin/workflow/TaskStatus.aspx?TaskID="+taskid;
		window.open(url);
		return false;
  	}
  	
  	</script>
  	
  	<style>
  	div{font-size:12px};
  	div.choice{cursor:pointer;}
  	.choice{border:1px solid black;}
  	.table{border:1px solid black;width:100%;}
  	
  	.role{
  		background-color: #FFFFFF;
	    color: #000000;
	    font-family: 宋体;
	    font-size: 12px;
	    height: 24px;
	    text-align: left;
  	}
  	
  	.zone{
	  	width:22%;
	  	height:100px;
	  	border:1px solid black;
	  	float:left;
	  	margin-left:10px;
	  	margin-top:10px;
	    border-color: #6A85A0;
  	}
  	
  	.list-border {
	  	width:100%;
	    background-color: #6A85A0;
	    border-color: #6A85A0;
	}
	
	.list-title {
	    background: url("images/title_fill.jpg") repeat scroll 0 0 transparent;
	    color: #2C4353;
	    font-size: 12px;
	    font-weight: bold;
	    height: 24px;
	    text-align: center;
	    vertical-align:base;
	}
	
	.list-td2 {
	    background-color: #FFFFFF;
	    color: #000000;
	    font-family: 宋体;
	    font-size: 12px;
	    height: 24px;
	    text-align:left;
	}
  	</style>
  	
  </head>
  <body>
  	<form id="loginForm" method="post">
		<input type="hidden" name="type" id="type" value=""/>
	</form>
  	
  	<%
  	  		if(loginUserId.length()==0){
  	  	%>
  	还未登入
	<a href="http://10.1.14.20:7001/ca/">重新登录</a>
	
	<!-- -->
	<div>
	<a href="refreshIp.jsp" target="_blank">refresh Ip</a>
	</div>
	
	<%
			}else{
		%>
	
	
	
	<%
					UserInfo bo = LoginUtil.getUserInfo(session);
					out.println("TuserName"+bo.gettLoginName());
					out.println("用户ID"+bo.getUserId());
					out.println("用户工号"+bo.getLoginName());
					out.println("用户姓名"+bo.getUserName());
					out.println("部门ID"+bo.getDeptId());
					out.println("部门名称"+bo.getDeptName()+"<br>");
					
					
					Map<String,TaskUserVo> userMap = bo.getMap();
					for(Map.Entry<String , TaskUserVo> entry : userMap.entrySet()){
						out.println(entry.getKey()+"<br>");
						out.println(entry.getValue().getLoginName()+"<br>");
						out.println(entry.getValue().getUserName()+"<br>");
						out.println(entry.getValue().getDeptId()+"<br>");
						out.println(entry.getValue().getDeptName()+"<br>");
					}
				%>
	<div>
	<%
		UserInfo userInfo = LoginUtil.getUserInfo(session);
	List<Map<String,Object>> list = ProcessUtil.gengerateTodoItemsNewSql(bo.gettLoginName());
	
		
	//System.out.println(list.size());
		out.println("<div style='text:align:right;'><b>"+loginUserName+" "+loginUserDeptName+"&nbsp;<input type='button' name='logout' id='logout' value='注销'/></div></b></div>");
		out.println("<br/><div><b>待办事项</b></div>");
		
		out.println("<table class='list-border' align='center' cellspacing='1' cellpadding='3'>");
		out.println("<tr class='list-title'><td>主流程</td><td>主流程ID</td><td>子流程</td><td>子流程ID</td><td>步骤</td><td>summary</td><td>处理人</td><td>办理</td><td>监控</td></tr>");
		
		if(list!=null && list.size()>0){
		for(int i=0;i<list.size();i++){
			Map<String,Object> map = list.get(i);
			String pname = map.get("pname").toString();
			String pincident = map.get("pincident").toString();
			String cname = map.get("cname").toString();
			String cincident = map.get("cincident").toString();
			String taskuser = map.get("loginName").toString();
			String steplabel = map.get("stepName").toString();
			String taskId = map.get("taskId").toString();
			String tmp = "'"+pname+"','"+pincident+"','"+cname+"','"+cincident+"','"+steplabel+"','"+taskId+"','"+taskuser+"'";
			
			out.println("<tr class='list-td2'>");
			out.print("<td>"+pname+"</td>");
			out.print("<td>"+pincident+"</td>");
			out.print("<td>"+cname+"</td>");
			out.print("<td>"+cincident+"</td>");
			out.print("<td>"+steplabel+"</td>");
			out.print("<td>"+map.get("title").toString()+"</td>");
			out.print("<td>"+map.get("loginName").toString()+"</td>");
			

			out.print("<td><a href='#' onclick=\"return openForm("+tmp+")\">操作</a></td>");
			out.print("<td><a href='#' onclick=\"return scan('"+taskId+"')\">监控</a></td>");
			
			//out.print("<td>"+map.get("theme")+"</td>");
			//out.print("<td>"+map.get("theme")+"</td>");
			out.println("</tr>");
		}
		}
		out.println("</table>");
	%>
	
	</div>
	<br/>
	
	<br/>
	<div>
	<input type="button" name="add" id="add" value="添加部门内部审阅流程" onclick="window.open('http://10.1.41.252:8080/workflow/dept-passMain/add.action?yrtyrtyr')"/>
	</div>
	
	<br/>
	<div>
	<!--<a href="<%=path %>/test.jsp" target="_blank">test.jsp</a>-->
	<a href="<%=path %>/dept/process/pass/list.jsp" target="_blank">查询列表（部门内部审阅）</a>
	</div>
	<%} %>
  </body>
</html>
