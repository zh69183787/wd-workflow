<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>

<%@taglib prefix="s" uri="/struts-tags" %>

<%String path = request.getContextPath(); %>

<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table_suggest">
<s:iterator value="deptRounds" var="rounds" status="s">
	<tr>

	<td >
	<table width="100%" cellspacing="0" cellpadding="0" border="0">
	<s:iterator value="deptRounds.get(#rounds.key)" var="depts">
	<tr>
	<!-- <td style="width:30%;"><b><s:property value="deptMap.get(#depts)"/></b></td>
	 --><td>
	<img class="showDetail" style="cursor:hand;display:inline;" src="<%=path%>/contract/css/default/images/files/find.gif"/>
	</td>
	<td style="width:95%;">
		<div>
			<s:iterator value="appDeptRounds.get(#rounds.key).get(#depts)" var="suggest">
			
			<div align="left" <s:if test='"部门领导审核"!=#suggest.stepname'>class="noleader" style="display:none;" </s:if><s:else>class="leader"</s:else>>
				<div align="left">
					<s:property value="#suggest.remark" escape="0"/>
					<s:if test="#suggest.fileGroupId!=null">
					<a target="_blank" style="display:inline;" href="<%=path %>/attach/loadFileList.action?fileGroup=attach&fileGroupName=attachGroup&fileGroupId=<s:property value="#suggest.fileGroupId"/>&userName=<s:property value="#session.login_name"/>&loginName=<s:property value="#session.login_name"/>&procType=view&targetType=frame&type=1">
						<img style="cursor:hand;display:inline;" title="附件" src="<%=path%>/contract/css/default/images/fj.gif"/>
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