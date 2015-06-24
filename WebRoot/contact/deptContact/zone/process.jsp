<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.wonders.util.*"%>

<%
	String path = request.getContextPath();
	String id = StringUtil.getNotNullValueString(request
			.getParameter("id"));
	if (id.length() == 0)
		id = "-1";
%>
<script>
$(function(){
	function showProcess(){
		$.post(
			contextpath+'/contact-ultimus/statusList.action?random='+Math.random(),
			{
				id: 	"<%=id%>"
			},
			function(data, textStatus, jqXHR){
			//alert(data);
				if(data=="-1"){
					$("#loadDiv").show();
					$("#showUl").hide();
				}else if(data=="4"){
					clearInterval(r); 
					$("#loadDiv").hide();
					$("#showUl").show();
					$(".Step li").addClass("pass");
				}else{
					clearInterval(r); 
					$("#loadDiv").hide();
					$("#showUl").show();
					$(".Step li:lt("+data+")").addClass("pass");
					$(".Step li:eq("+data+")").addClass("on");
				}
				
			},
			"html"
		)
	}
	var r = setInterval(showProcess,3000);
})
</script>
<div class="mb10 Step clearfix">
<div id="loadDiv" class="t_c" style="padding: 30px 0 30px 0;">
<img src="<%=path %>/contact/images/flowLoad.gif" style="margin:0 auto"/>
<span>流程状态获取中...</span></div>
<ul id="showUl" class="clearfix" style="display:none;">
	<li id="first" class="fst" style="padding-right:140px;">
	<dl style="height:80px;">
		<dt></dt>
		<dd>申请</dd>
	</dl>
	</li>
	<li id="second" class="" style="padding-right:140px;">
	<dl style="height:80px;">
		<dt></dt>
		<dd>部门签发</dd>
	</dl>
	</li>
	<li id="third" style="padding-right:140px;">
	<dl style="height:80px;">
		<dt></dt>
		<dd>主送部门</dd>
	</dl>
	</li>
	<li id="fourth" style="padding-right:140px;">
	<dl style="height:80px;">
		<dt></dt>
		<dd>返回发起部门</dd>
	</dl>
	</li>
	
	<li id="fifth" class="fin" >
	<dl style="height:80px;">
		<dt></dt>
		<dd>完成</dd>
	</dl>
	</li>
</ul> 
</div>
