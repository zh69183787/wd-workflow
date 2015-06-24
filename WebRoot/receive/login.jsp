<%@ page language="java" import="java.util.*,java.net.*" pageEncoding="utf-8"%>
<%@page import="
com.wonders.util.*,
com.wonders.constants.*,
com.wonders.send.common.util.*,
com.wonders.send.common.model.vo.*,
com.wonders.receive.workflow.util.*
"%>


<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>

<%
	String path = request.getContextPath();
	String ipPath = "http://localhost:8080";
	String type = StringUtil.getNotNullValueString(request.getParameter("type"));
	out.println(path);
	if("logout".equals(type)){
		session.invalidate();
		String t = ipPath+path+"/receive/login.jsp";
		response.sendRedirect("http://10.1.14.20:7001/ca/logout2.jsp?returnUrl="+t);
		//response.sendRedirect("http://10.1.40.202:8088/ca/logout2.jsp?returnUrl="+t);
		
		return;
	}
	
	String loginUserId = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.USERID));
	String loginUserLoginName = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.LOGINNAME));
	String loginUserName = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.USERNAME));
	String loginUserDeptId = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.DEPTID));
	String loginUserDeptName = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.DEPTNAME));
	
	out.println("loginUserId:"+loginUserId);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<script src="js/jquery-1.7.1.js"></script>
  	
  	<script>
  	$(document).ready(function(){
		
  	  	
  		//$("#userId").attr("value","");
		//$("#deptId").attr("value","");
		$("#type").attr("value","");

		$(".choice").click(function(){
			var url = "<%=ipPath%>/workflow/receive/login.jsp";
			var loginName = $(this).attr("loginName");
			var deptId = $(this).parent("div.role").parent("div.zone").attr("id");

			var url = "http://10.1.14.20:7001/ca/login.action?loginName="+loginName
			//var url = "http://10.1.40.202:8088/ca/login.action?loginName="+loginName
					+"&password=wonder&validate=test&deptId="+deptId+"&appName=workflowtest2&returnUrl="+url+"?rand="+Math.random();

			//console.log(url);
			window.location = url;

			return false;
		})
  	  	

		$("#logout").click(function(){
			$("#type").attr("value","logout");
			$("#loginForm").submit();
			return false;
		})
  	})
  	
  	function gotoUrl(url){
		window.open(url);
  	}

  	function openForm(stepName,taskId,pinstanceId,incidentNo,processName,modelName,taskUserName,assignedtouser){
		var url = "<%=ipPath%>/workflow/receive-recvMain/forward.action?"
			+"steplabel="+encodeURI(stepName)+"&"
			+"taskid="+taskId+"&"
			+"cincident="+pinstanceId+"&"//子流程实例号
			+"pincident="+incidentNo+"&"//主流程实例号
			+"cname="+encodeURI(processName)+"&"//子流程类型
			+"pname="+encodeURI(modelName)+"&"//主流程类型
			+"taskuser="+assignedtouser+"&"//流程操作人工号
			+"rand="+Math.random();
			//console.log(url);
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
  	
  	<%if(loginUserId.length()==0){ %>
  	<table><tr><td>
  		<div style="display:none" name="template">
  			<div class="choice"><font class="info" userId="" deptId="">（）</font></div>
  		</div>
  		
  		
  		<div class="zone" id="2549">
	  		<div class="list-title">信息管理中心（发起部门）</div>
	  		<div class="role">发起人：
	  		<input type="button" class="choice" loginName="G00100000161" value="李名敏"/>
	  		<input type="button" class="choice" loginName="G00100000226" value="孔琰"/>
			</div>
			<div class="role">签发领导：
			<input type="button" class="choice" loginName="G02000100049" value="金涛"/>
			</div>
			<!--
			<div class="role">其他人员：
			
			</div>
			-->
		</div>
		
		
		<div class="zone" id="2510">
			<div class="list-title">行政办公室（子流程）</div>
			<div class="role">部门接收员：
			<input type="button" class="choice" loginName="G00100000038" value="朱英"/>
			</div>
			<div class="role">部门处理人员：
			<input type="button" class="choice" loginName="G00100000037" value="阮瑶"/>
			<input type="button" class="choice" loginName="G01006000513" value="忻然"/>
			</div>
			<div class="role">部门领导：
			<input type="button" class="choice" loginName="G00100000136" value="蔡伟东"/>
			<input type="button" class="choice" loginName="G01008000031" value="俞楝"/>
			
			</div>
		</div>
		
		<div class="zone" id="2502">
			<div class="list-title">党委办公室（子流程）</div>
			<div class="role">部门处理人员：
			<input type="button" class="choice" loginName="G00100000046" value="周瑾"/>
			<input type="button" class="choice" loginName="G00100000015" value="吴昕毅"/>
			</div>
		</div>
		
		<div class="zone" id="2505">
			<div class="list-title">纪委办公室（子流程）</div>
			<div class="role">部门处理人员：
			<input type="button" class="choice" loginName="G00100000036" value="宗艳玲"/>
			<input type="button" class="choice" loginName="G00100000094" value="徐卫平"/>
			</div>
		</div>
		
		<div class="zone" id="2551">
			<div class="list-title">建设事业部（子流程）</div>
			<div class="role">部门接收员：
			<input type="button" class="choice" loginName="G00100000201" value="翁露波"/>
			</div>
			<!--  
			<div class="role">部门处理人员：
			</div>
			-->
			<div class="role">部门领导：
			<input type="button" class="choice" loginName="G00200000166" value="张川"/>
			</div>
		</div>
		
		
		<div class="zone" id="2517">
			<div class="list-title">维护保障中心</div>
			<div class="role">人员：
			<input type="button" class="choice" loginName="G01013800002" value="李朕融"/>
			</div>
		</div>
			
		<div class="zone" id="2590">
			<div class="list-title">维护保障中心_车辆分公司（下级流程）</div>
			<div class="role">接收人：
			<input type="button" class="choice" loginName="G01005000221" value="王方程"/>
			</div>
	
			<div class="role">签发领导：
			<input type="button" class="choice" loginName="G01005000001" value="陈鞍龙"/>
			</div>
		</div>
		
		 
		<div class="zone" id="2600">
			<div class="list-title">维护保障中心_车辆分公司_新车部（子流程）</div>
			<div class="role">接收人：
			<input type="button" class="choice" loginName="G01005000782" value="李轶斌"/>
			</div>
			
			<div class="role">签发领导：
			<input type="button" class="choice" loginName="G01005000796" value="司琦"/>
			</div>
		</div>
		
		
		<div class="zone" id="2566">
			<div class="list-title">运营管理中心（下级流程）</div>
			<div class="role">接收人：
			<input type="button" class="choice" loginName="G01001000538" value="高峻"/>
			</div>
			
			<div class="role">签发领导：
			<input type="button" class="choice" loginName="G04000000748" value="陈悦勤"/>
			</div>
		</div>
			
			
		<div class="zone" id="2558">
			<div class="list-title">运营管理中心_综合办公室（下级流程）</div>
			<div class="role">接收人：
			<input type="button" class="choice" loginName="G01001000851" value="朱静叶"/>
			</div>
			
			<div class="role">签发领导：
			<input type="button" class="choice" loginName="G01009000115" value="宋卫平"/>
			</div>
		</div>
		
		
		<div class="zone" id="2560">
			<div class="list-title">运营管理中心_计划财务部（子流程）</div>
			<div class="role">接收人：
			<input type="button" class="choice" loginName="G01000000067" value="陈兆"/>
			</div>
			
			<div class="role">签发领导：
			<input type="button" class="choice" loginName="G01005000038" value="王彦冰"/>
			</div>
		</div>
		
		<div class="zone" id="2501">
			<div class="list-title">领导</div>
			<div class="role">领导：
			<input type="button" class="choice" loginName="G00100000007" value="白廷辉副总裁"/>
			<input type="button" class="choice" loginName="G00100000001" value="应名洪董事长"/>
			<input type="button" class="choice" loginName="G01000000005" value="邵伟中副总裁"/>
			</div>
		</div>
		<div class="zone" id="2510">
			<div class="list-title">秘书</div>
			<div class="role">秘书：
			<input type="button" class="choice" loginName="G01007000490" value="高旭卿"/>
			<input type="button" class="choice" loginName="G01008000031" value="俞楝"/>
			<input type="button" class="choice" loginName="G01003000637" value="应明程"/>
			</div>
		</div>

	</td></tr></table>
	
	
	<!-- -->
	<div>
	<a href="refreshIp.jsp" target="_blank">refresh Ip</a>
	</div>
	
	<%}else{ %>
	
	
	
	<div>
	<%
		UserInfo userInfo = LoginUtil.getUserInfo(session);
		
		List<Map<String,Object>> list = FlowUtil.getActiveProcess2(userInfo);
		
		
	//System.out.println(list.size());
		out.println("<div style='text:align:right;'><b>"+loginUserName+" "+loginUserDeptName+"&nbsp;<input type='button' name='logout' id='logout' value='注销'/></div></b></div>");
		out.println("<br/><div><b>待办事项</b></div>");
		
		out.println("<table class='list-border' align='center' cellspacing='1' cellpadding='3'>");
		out.println("<tr class='list-title'><td>流程名称</td><td>步骤</td><td>办理</td></tr>");
		
		for(int i=0;i<list.size();i++){
			Map<String,Object> map = list.get(i);
			out.println("<tr class='list-td2'>");
			
			out.println("<td>"+map.get("summary")+"</td>");
			out.println("<td>"+map.get("steplabel")+"</td>");
			out.println("<td><a href='javascript:void(0)' onclick=\"openForm('"+map.get("steplabel")+"','"+map.get("taskid")+"','"+map.get("incident")+"','"+map.get("pincident")+"','"+map.get("processname")+"','"+map.get("pname")+"','"+map.get("username")+"','"+map.get("assignedtouser")+"');\">表单</a></td>");
			out.println("</tr>");
		}
		out.println("</table>");
	%>
	
	</div>
	<br/>
	
	<br/>
	<div>
		<input type="button" name="add" id="add" value="添加新收文流程" onclick="gotoUrl('<%=path %>/receive/process/recv/add.jsp')"/>
	</div>
	
	<br/>
	<div>
	</div>
	
	<%} %>
	
  </body>
</html>
