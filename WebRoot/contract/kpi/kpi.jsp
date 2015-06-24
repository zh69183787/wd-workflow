<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%String path = request.getContextPath(); %>
<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table_suggest">
<s:iterator value="kpiKey" var="a">
<s:if test='#a != null && #a != ""'>
<tr>	
	<td>
		<div>
			<s:property value="#a"/>：<s:property value="kpiMap.get(#a)"/>
		</div>
	</td>
</tr>
</s:if>
</s:iterator>
</table>