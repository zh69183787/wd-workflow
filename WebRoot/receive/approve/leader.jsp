<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%String path = request.getContextPath(); %>

<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table_suggest">
<s:iterator value="appRounds" var="rounds" status="s">
	<tr>
	<td>
		<div>
			<s:iterator value="#rounds.value" var="suggest">
			<div align="left">
				<div align="left">
					<s:if 
						test='#suggest.optionCode=="LEADER_ASSISTANT_TREAT_OTHER" ||
						#suggest.optionCode=="LEADER_TREAT_OTHER" || 
						#suggest.optionCode=="LEADER_ASSISTANT_DEAL_OTHER_NOTION" ||
						#suggest.optionCode=="LEADER_DEAL_OTHER_NOTION" ||
						#suggest.optionCode=="LEADER_CHECK_OTHER_NOTION"'>
					<img style="vertical-align:baseline;cursor:hand;display:inline;" title="其他意见" src="<%=path%>/receive/css/default/images/qtyj.gif"/>
					&nbsp;
					</s:if>	
					<s:if 
						test='#suggest.optionCode=="LEADER_ASSISTANT_TREAT_OTHER" ||
						#suggest.optionCode=="LEADER_TREAT_OTHER" || 
						#suggest.optionCode=="LEADER_ASSISTANT_DEAL_OTHER_NOTION" ||
						#suggest.optionCode=="LEADER_DEAL_OTHER_NOTION" ||
						#suggest.optionCode=="LEADER_CHECK_OTHER_NOTION"'>
					<s:property value="#suggest.remark.substring(5)" escape="0"/>
					</s:if>	
					<s:else>
						<s:property value="#suggest.remark" escape="0"/>
					</s:else>
					
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