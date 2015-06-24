<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<s:if test="params.processParam['steplabel']!='Begin'">

<script>
$(document).ready(function(){
	/**	初始化下级流程点击事件*/
	//
	$("#suggest_sub_zone").find("div[name='approvedInfo_item'][type='1']").each(function(i,n){
		//console.log(n);
		var item = $(n).find("i");
		$(item).css("cursor","pointer");
		$(item).attr("title","点击查看下级流程表单");
		
		var div = $(n);
		//console.log($(div));
		//var pname = $(div).find("input[name='pname']:hidden").val();
		//var pincident = $(div).find("input[name='pincident']:hidden").val();
		//var cname = $(div).find("input[name='cname']:hidden").val();
		//var cincident = $(div).find("input[name='cincident']:hidden").val();

		var cId = $(div).find("input[name='cId']:hidden").val();
		
		$(item).click(function(){
			//if(cId!=""){
				openLowerForm(cId);
			//}
			return false;
		});
	});

	$("#suggest_apply_zone").find("div[name='approvedInfo_item']:last").removeClass("b_dash");
	$("#suggest_sub_zone").find("div[name='approvedInfo_item']:last").removeClass("b_dash");
	$("#suggest_backApply_zone").find("div[name='approvedInfo_item']:last").removeClass("b_dash");


	
	/**初始化折叠按钮及需要展开区域*/
	var arrayZone = new Array("form_detail_zone","form_suggest_zone");
	var arrayZoneToOpen = new Array("form_detail_zone","form_suggest_zone");

	$.each(arrayZone,function(i,n){
		var id = n;
		var target = $("#"+n);
		//console.log(target);
		var clickTarget = $(target).find("a[name='zone_expand']");
		$(clickTarget).unbind("click").click(function(){
			var a = $(this);
			
			if($(a).hasClass("open")){
				$(target).find("tbody").hide();
				$(a).removeClass("open");
			}else{
				$(target).find("tbody").show();
				$(a).addClass("open");
			}
			return false;
		});
		//$(clickTarget).click();
	});

	$.each(arrayZoneToOpen,function(i,n){
		var id = n;
		var target = $("#"+n);
		//console.log(target);
		var clickTarget = $(target).find("a[name='zone_expand']");
		$(clickTarget).click();
	});
});

/**打开下级流程表单方法*/
//function openLowerForm(pname,pincident,cname,cincident,steplabel){
function openLowerForm(cid){
	//var url = "/deptContact/forward.action?"
		//+"pname="+encodeURI(pname)+"&"
		//+"pincident="+pincident+"&"
		//+"cname="+encodeURI(cname)+"&"
		//+"cincident="+cincident+"&"
		//+"steplabel="+encodeURI(steplabel)+"&"
		//+"rand="+Math.random();

	var url = "<%=path%>/contact-deptContact/viewForward.action?"
			+"id="+cid+"&"
			//+"pname="+encodeURI(pname)+"&"
			//+"pincident="+pincident+"&"
			+"rand="+Math.random();
	
	window.open(url);
	
	return false;
}
</script>

<s:set var="info_mainDeptNameMap" value="deptContactApprovedInfoVo.mainDeptNameMap"></s:set>

<table name="approvedInfo_zone" id="form_suggest_zone" width="100%" cellspacing="0" cellpadding="0" border="0" class="table_2">
	<thead>
		<tr>
			<th>
				<h5 class="fl">工作联系单流转意见栏</h5>
				<span class="fr pt5 mr5"><a name="zone_expand" href="#">收起</a></span>
			</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>
				<div name="approvedInfo_node" id="suggest_apply_zone" class="node">
					<b class="fl">部门内部意见</b>
					<div class="clear"></div>
					<s:set var="info_applyApprovedInfo" value="deptContactApprovedInfoVo.applyApprovedInfo"></s:set>
					<s:if test="#info_applyApprovedInfo.size>0">
					<s:iterator value="info_applyApprovedInfo" var="item">
					<div name="approvedInfo_item" class="con b_dash"><i><s:property value="#item['DEPT']"/>：</i>
						<div>
						<input type="hidden" name="suggest_attachId" value="<s:property value="#item['FILE_GROUP_ID']"/>"/>
						<p><s:property value="#item['REMARK']"/>
						</p></div>
						<div class="t_r"><s:property value="#item['USERFULLNAME']"/><span class="date_02 mr5"><s:property value="#item['DAY']"/></span></div>
						<div class="clearfix">
							<div class="fl"><s:property value="#item['TIME']"/></div>
							<div class="fr"><!--<a class="mr5 dis" href="#">回复(1234)</a><a class="mr5 fw" href="#">引用(0)</a>--></div>
						</div>
					</div>
					</s:iterator>
					</s:if>
				</div>
			</td>
		</tr>

		
		<tr>
			<td>
				<div name="approvedInfo_node" id="suggest_sub_zone" class="node">
					<b class="fl">主送部门内部意见</b>
					<div class="clear"></div>
					<s:set var="info_subApprovedInfo" value="deptContactApprovedInfoVo.subApprovedInfo"></s:set>
					<s:if test="deptContactApprovedInfoVo.subApprovedInfo.size>0">
					<s:iterator value="info_subApprovedInfo" var="item">
					<div name="approvedInfo_item" class="con b_dash" type="<s:property value="#item['lowerFlag']"/>"><i><s:property value="#info_mainDeptNameMap.get(#item['dept_id'])"/><s:property value="#item['lowerText']"/>：</i>
						
						<s:if test="#item['lowerFlag']==1">
							<s:if test="#item['C_ID']==null">
								<input type="hidden" name="cId" value="<s:property value="#item['P_ID']"/>"/>
							</s:if>
							<s:else>
								<input type="hidden" name="cId" value="<s:property value="#item['C_ID']"/>"/>
							</s:else>
						</s:if>
						
						<div>
						<s:if test="#item['FILE_GROUP_ID']!=''">
						<input type="hidden" name="suggest_attachId" value="<s:property value="#item['FILE_GROUP_ID']"/>"/>
						</s:if>
						<p><s:property value="#item['REMARK']"/></p>
						</div>
						
						<div class="t_r"><s:property value="#item['USERFULLNAME']"/><span class="date_02 mr5"><s:property value="#item['DAY']"/></span></div>
						<div class="clearfix">
							<div class="fl"><s:property value="#item['TIME']"/></div>
							<div class="fr"><!--<a class="mr5 dis" href="#">回复(1234)</a><a class="mr5 fw" href="#">引用(0)</a>--></div>
						</div>
					</div>
					</s:iterator>
					</s:if>
				</div>
			</td>
		</tr>
		
		
		<tr>
			<td>
				<div name="approvedInfo_node" id="suggest_backApply_zone" class="node">
					<b class="fl">返回发起部门内部意见</b>
					<div class="clear"></div>
					<s:set var="info_backApplyApprovedInfo" value="deptContactApprovedInfoVo.backApplyApprovedInfo"></s:set>
					<s:if test="deptContactApprovedInfoVo.backApplyApprovedInfo.size>0">
					<s:iterator value="info_backApplyApprovedInfo" var="item">
					<div name="approvedInfo_item" class="con b_dash"><i><s:property value="#item['DEPT']"/>：</i>
						<div>
						<input type="hidden" name="suggest_attachId" value="<s:property value="#item['FILE_GROUP_ID']"/>"/>
						<p><s:property value="#item['REMARK']"/></p></div>
						<div class="t_r"><s:property value="#item['USERFULLNAME']"/><span class="date_02 mr5"><s:property value="#item['DAY']"/></span></div>
						<div class="clearfix">
							<div class="fl"><s:property value="#item['TIME']"/></div>
							<div class="fr"><!--<a class="mr5 dis" href="#">回复(1234)</a><a class="mr5 fw" href="#">引用(0)</a>--></div>
						</div>
					</div>
					</s:iterator>
					</s:if>
				</div>
			</td>
		</tr>
	</tbody>
</table>
</s:if>
