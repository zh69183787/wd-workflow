<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%String path = request.getContextPath(); %>

<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table_suggest">
<s:iterator value="resultMap" var="map" status="s">
<tr>
	
	<td style="width:15%;">
		<b>通知类：</b>
	</td>
	<td>
	已发送通知：<s:property value="#map.key"/>至：（<s:property value="#map.value"/>）
	</td>
</tr>
</s:iterator>
</table>