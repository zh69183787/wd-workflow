<%@ page language="java" import="java.util.*,java.net.*" pageEncoding="utf-8"%>


<%@page import="com.wonders.receive.workflow.common.model.vo.UserInfo"%>
<%@page import="com.wonders.receive.workflow.constants.CommonConstants"%>
<%@page import="com.wonders.receive.workflow.constants.LoginConstants"%>
<%@page import="com.wonders.util.*"%>
<%@page import="com.wonders.receive.workflow.common.util.LoginUtil"%>
<%@page import="com.wonders.receive.workflow.process.util.ProcessUtil"%>
<%
	String path = request.getContextPath();
	String ultimusIp = PWSProperties.getValueByKey("pws_server_ip");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<script src="js/jquery-1.7.1.js"></script>
  	
  	<script>
 
  	
	function openForm(pname,pincident,cname,cincident,steplabel,taskid){
			var url = "<%=path %>/receive-recvMain/forward.action?"
				+"pname="+encodeURI(pname)+"&"
				+"pincident="+pincident+"&"
				+"cname="+encodeURI(cname)+"&"
				+"cincident="+cincident+"&"
				+"steplabel="+encodeURI(steplabel)+"&"
				+"taskid="+taskid+"&"
				+"viewType=1"+"&"
				+"rand="+Math.random();
			window.open(url);
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
	<%
	UserInfo bo = LoginUtil.getUserInfo(session);
	out.println("用户ID"+bo.getUserId());
	out.println("用户工号"+bo.getLoginName());
	out.println("用户姓名"+bo.getUserName());
	out.println("部门ID"+bo.getDeptId());
	out.println("部门名称"+bo.getDeptName());

	%>
	<div>
	<%
		UserInfo userInfo = LoginUtil.getUserInfo(session);
		List<Map<String,Object>> list = ProcessUtil.generateRecvQueryList();
	
		
	//System.out.println(list.size());
		out.println("<br/><div><b>查询列表</b></div>");
		
		out.println("<table class='list-border' align='center' cellspacing='1' cellpadding='3'>");
		out.println("<tr class='list-title'><td>主流程</td><td>主流程ID</td><td>收文序号</td><td>文件日期</td><td>来文机关</td><td>来文字号</td><td>标题</td><td>状态</td><td>查看</td><td>监控</td></tr>");
		
		if(list!=null && list.size()>0){
		for(int i=0;i<list.size();i++){
			Map<String,Object> map = list.get(i);
			String pname = map.get("modelid").toString();
			String pincident = map.get("instanceid").toString();
			String cname = map.get("modelid").toString();
			String cincident = map.get("instanceid").toString();
			String steplabel = "";
			String taskId = map.get("taskId").toString();
			String tmp = "'"+pname+"','"+pincident+"','"+cname+"','"+cincident+"','"+steplabel+"','"+taskId+"'";
			
			out.println("<tr class='list-td2'>");
			out.print("<td>"+pname+"</td>");
			out.print("<td>"+pincident+"</td>");
			out.print("<td>"+StringUtil.getNotNullValueString(map.get("SWID"))+"</td>");
			out.print("<td>"+StringUtil.getNotNullValueString(map.get("swdate"))+"</td>");
			out.print("<td>"+StringUtil.getNotNullValueString(map.get("SWUNIT"))+"</td>");
			out.print("<td>"+StringUtil.getNotNullValueString(map.get("FILEZH"))+"</td>");
			out.print("<td>"+StringUtil.getNotNullValueString(map.get("TITLE"))+"</td>");
			out.print("<td>"+StringUtil.getNotNullValueString(map.get("pstatus"))+"(1:进行中；2:完成；3:归档)</td>");
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

	<div>
	<!--<a href="<%=path %>/test.jsp" target="_blank">test.jsp</a>-->
	<a href="<%=path %>/receive-process/recv/list.jsp" target="_blank">查询列表</a>
	</div>
  </body>
</html>
