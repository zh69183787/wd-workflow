<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="com.wonders.util.*"%>
<%@page import="com.wonders.contract.workflow.process.review.dao.ReviewMainDao" %>
<%@page import="com.wonders.contract.workflow.process.review.constants.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<% 
String steplabel = StringUtil.getNotNullValueString(request.getParameter("steplabel"));
String purchaseType = StringUtil.getNotNullValueString(request.getParameter("purchaseType"));
WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(this.getServletConfig().getServletContext());   
ReviewMainDao service = (ReviewMainDao)wac.getBean("contract-reviewMainDao");
pageContext.setAttribute("list", service.findQuestion(purchaseType.indexOf("-")>0? purchaseType.substring(0,purchaseType.indexOf("-")):purchaseType));
if(steplabel.length()==0) steplabel="detail";
String path = request.getContextPath();
%>
<script>
function su(q,o){ 
    this.q=q;
    this.o=o;
}

$(function(){
	var result = [];
	var suggests = "";
	var count = $("td[name='question']").length;
	$(document).on("click",":radio[id^='q_']",function(){
		suggests = "";
		result.length = 0;//清空数组 
		$(":radio[id^='q_']:checked").each(function(){
			result.push(new su($(this).attr("qId"),$(this).val()));	
			suggests += $(this).attr("qValue")+"："+$(this).attr("oValue")+"。\r\n";
		})
		$("#suggest").val(suggests);
	});
	
	$("#handleSubmit").click(function(){
		if(confirm("确认提交吗？")){
			$("#count").val($("td[name='question']").length);
			$("#checkCount").val($(":radio[id^='q_']:checked").length);
			//alert(JSON.stringify(result));
			$("#structuredSuggest").val(JSON.stringify(result));
			//return false;
			$(this).attr("disabled",true);
			options.formId = "formUpdate";
			options.url = "<%=path %>/contract-reviewMain/contractDealPerson.action";
			options.submitZone = "handle_zone";
			options.handleZone = "handle_submit_zone";
			handleZoneControl(options.handleZone,true);
			$("#"+options.submitZone+"_loading").show();
			clearError();
		
			$("#formUpdate").ajaxSubmit(options);
			return false;
		}
		//$(this).removeAttr("disabled");
		
	});
});
</script>
<div id="handle_zone" style="display:none;" title="<%=steplabel%>">
    <input type="hidden" name="structuredSuggest" id="structuredSuggest"/>
	<input type="hidden" name="count" id="count"/>
	<input type="hidden" name="checkCount" id="checkCount"/>
	<div class="con">
     	
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
	  			<td colspan=2>
			  		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			  		<c:forEach items="${list}" var="items" varStatus="s">
			  		    <tr>
                            <td name="question" class="td_1" colspan="2" style="font-weight:bold;">${items.name}</td>
                        </tr>
                        <c:forEach items="${items.options}" var="options">
				  		<tr>
				  			<td class="td_1" colspan="2" >
				  			<input type="radio" id="q_${items.id}" 
				  			name="q_${items.id}" value="${options.id}" 
				  			qId="${items.id}" qValue="${items.name}" oValue="${options.name}">
				  			${options.name}
				  			</td>
				  		</tr>
				  		</c:forEach>
                        <tr><td colspan="2" style="border-bottom:1px dotted #000;"></td></tr>
                     </c:forEach>   
				  		<tr>	
				  		    <td class="td_1" style="text-align:right;">&nbsp;</td>
		                	<td textareaId="suggest">
		                	<span class="fl">
		                	意见：<font style="color:red;display:inline;">*</font>
		                	</span>
		                	<span class="fr" style="display:inline;">
		                	<a name="suggest_attach" class="suggest_attach" target="#">
			  				<input type="hidden" name="attachId" id="attachId" value=""/>
			  				上传意见附件(<span style="display:inline;" id="fjcount" class="fjcount">0</span>)
			  				</a>
		                	</span>
		                	<!--  
		                	<span class="fr" style="display:inline;"><a class="previewSuggest">意见预览</a></span>
		                	-->
		                	</td>
		              	</tr>
		              	<tr>
		              		<td class="td_1">&nbsp;</td>
		                	<td><textarea id="suggest" name="suggest" rows="5"></textarea></td>
		              	</tr>
	              	</table>
           		</td>
           	</tr>
		</table>
	</div>
	<br>
	<div id="handle_submit_zone" class="button t_c">
		<input id="handleSubmit" type="button" value="提交">&nbsp;
		<input id="handleClose" type="button" value="关闭">
		
		<div id="handle_zone_loading" style="width:100%;align:center;line-height:20px;display:none;" class="">
	      <p style="width:auto;display:inline;"><img src="<%=path%>/contract/css/default/images/loading.gif" style="display:inline;"/>
	      <b style="color:green;display:inline;">&nbsp;正在提交</b></p>
	    </div>
	</div>
</div>


    