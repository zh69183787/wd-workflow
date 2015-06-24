<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@	page import="com.wonders.util.StringUtil" %>
<% 
String steplabel = StringUtil.getNotNullValueString(request.getParameter("steplabel"));
String path = request.getContextPath();
%>

<div id="swSerial_zone" style="display:none;" title="请选择预存号码">
	<div class="con">
		<table class="table_2" id="swSerialTab" width="100%" border="0" cellspacing="0" cellpadding="0">
	       
		</table>
	</div>
	<br>
	<div class="button t_c">
		<input id="swSerialSelSubmit" name="swSerialSelSubmit" type="button" value="提交">
		
		<div id="swSerial_zone_loading" style="width:100%;align:center;line-height:20px;display:none;">
	      <p style="width:auto;display:inline;"><img src="<%=path%>/receive/css/default/images/loading.gif" style="display:inline;"/>
	      <b style="color:green;display:inline;">&nbsp;正在提交</b></p>
	    </div>
	</div>
	<br>
</div>


    