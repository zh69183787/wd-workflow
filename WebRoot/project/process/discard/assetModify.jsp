<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@	page import="com.wonders.util.StringUtil" %>
<% 
String steplabel = StringUtil.getNotNullValueString(request.getParameter("steplabel"));
String path = request.getContextPath();
%>

<div id="asset_content_zone" style="display:none;" title="销项资产履历信息">
	<div class="con">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr><td colspan="2"><b class="fl">大修详细信息</b></td></tr>
	        <tr height="100">
	        	<td style="vertical-align:middle;width:20%;">
	        		维修内容
	        	</td>
           		<td>
           			<textarea id="assetContent" name="assetContent" rows="5" style="width:90%"></textarea>
           		</td>
			</tr>
		</table>
	</div>
	<br>
	<div class="button t_c">
		<input id="assetContentSubmit" name="assetContentSubmit" type="button" value="确定">
	</div>
	<br>
</div>


    