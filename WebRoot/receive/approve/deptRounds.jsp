<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>

<%@taglib prefix="s" uri="/struts-tags" %>

<%String path = request.getContextPath(); %>

<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table_suggest">
<s:iterator value="deptRounds" var="rounds" status="s">
<s:if test="#s.index != deptRounds.size-1">
	<tr class="latestSuggest">
</s:if>
<s:else>
	<tr>
</s:else>
	<td style="width:20%;">
		<s:if test='#rounds.key == 0'>
			<b>最新意见：</b>
		</s:if>
		<s:else>
			<b>第<s:property value="#rounds.key"/>轮意见：</b>
		</s:else>
		
	</td>
	<td >
	<table width="100%" cellspacing="0" cellpadding="0" border="0">
	<s:iterator value="deptRounds.get(#rounds.key)" var="depts">
	<tr>
	<td style="width:30%;"><b><s:property value="deptMap.get(#depts)"/></b></td>
	<td>
	<s:if test='#rounds.key == 0'>
	&nbsp;
	</s:if>
	<s:else>
	<img class="showDetail" style="cursor:hand;display:inline;" src="<%=path%>/receive/css/default/images/files/find.gif"/>
	</s:else>
	</td>
	<td style="width:90%;">
		<div>
			<s:iterator value="appDeptRounds.get(#rounds.key).get(#depts)" var="suggest">
			
			<div align="left" <s:if test='"部门领导审核"!=#suggest.stepname && #rounds.key != 0'>class="noleader" style="display:none;" </s:if><s:else>class="leader"</s:else>>
				<div align="left">
					<s:property value="#suggest.remark" escape="0"/>
					<s:if test="#suggest.fileGroupId!=null">
					<a target="_blank" style="display:inline;" href="<%=path %>/attach/loadFileList.action?fileGroup=attach&fileGroupName=attachGroup&fileGroupId=<s:property value="#suggest.fileGroupId"/>&userName=<s:property value="#session.login_name"/>&loginName=<s:property value="#session.login_name"/>&procType=view&targetType=frame&type=1">
						<img style="cursor:hand;display:inline;" title="附件" src="<%=path%>/receive/css/default/images/fj.gif"/>
					</a>
					</s:if>
				</div>
				<div align="right">
					<s:property value="#suggest.userfullname"/>
					&nbsp;
					<s:date name="#suggest.upddate" format="yyyy-MM-dd" />
				</div>
			</div>
			</s:iterator>
		</div>
	</td>
	</tr>
	</s:iterator>
	</table>
	</td>
	</tr>
</s:iterator>
</table>