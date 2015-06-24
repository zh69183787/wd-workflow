<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@	page import="com.wonders.util.StringUtil" %>
<% 
String steplabel = StringUtil.getNotNullValueString(request.getParameter("steplabel"));
String path = request.getContextPath();
%>

<div id="swUnit_zone" style="display:none;" title="请选择来文单位">
	<div class="con">
		<table  width="100%" border="0" cellspacing="0" cellpadding="0">
	        <tr height="30">
           		<td>
           			<input type="text" id="swUnitDig" name="swUnitDig" class="inputLine" style="width:250px;height:22px"/>
           		</td>
	  			<td>
	  				<input type="button" name="swUnitQuy" id="swUnitQuy" value="查询" class="btn">
	  				<input type="button" name="swUnitClr" id="swUnitClr" value="清除" class="btn"/>
	  			</td>
			</tr>
	  		
		</table>
		
		<table class="table_2" id="swUnitTab" width="100%" border="0" cellspacing="0" cellpadding="0">
	       
		</table>
	</div>
	<br>
	<div class="button t_c">
		<input id="swUnitSubmit" name="swUnitSubmit" type="button" value="提交">
		
		<div id="swUnit_zone_loading" style="width:100%;align:center;line-height:20px;display:none;">
	      <p style="width:auto;display:inline;"><img src="<%=path%>/receive/css/default/images/loading.gif" style="display:inline;"/>
	      <b style="color:green;display:inline;">&nbsp;正在提交</b></p>
	    </div>
	</div>
	<br>
</div>


    