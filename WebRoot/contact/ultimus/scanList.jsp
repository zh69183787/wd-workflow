<%@ page language="java" import="java.util.*,java.net.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<script src="<%=path %>/contact/js/jquery-1.7.1.js"></script>
		<script>
		function scanList(params){
			var url = "<%=path%>/contact-ultimus/scanList.action?"+params+"&rand="+Math.random();
			//console.log(url);
			window.location = url;
			return false;
		}

		$(document).ready(function(){
			$("#gv_TaskList").find("tr:odd").addClass("dg_itemstyle");
			$("#gv_TaskList").find("tr:even").addClass("dg_alternatingitemstyle");
		});
		</script>
		<link href="<%=path %>/contact/css/scan.css" rel="stylesheet">
		<style>
			th{border:1px solid black;}
			td{border:1px solid black;}
		</style>
	</head>
	
	<body>
		<s:set var="list1" value="#request.list1"></s:set>
	
		<s:set var="list2" value="#request.list2"></s:set>
		
		<div id="mainareacontent">
            <div class="mainarea">
                <fieldset style="text-align: center">
                    <legend>监控列表
                    <s:if test="pid!=null">
						<a href="" onclick="return scanList('id=<s:property value="pid"/>');">
							[<font color="#0000FF">返回上级监控</font>]
						</a>
						<div style="text-align:left;"></div>
					</s:if>
					</legend>
                    <div>
	                    
						<table cellspacing="1" cellpadding="0" border="0" style="border:1px solid black;" id="gv_TaskList" rules="all" class="dg_borderstyle">
							<tbody>
							
							<tr>
								<th scope="col">步骤名称</th>
								<th scope="col">处理用户</th>
								<th scope="col">开始时间</th>
								<th scope="col">结束时间</th>
								<th scope="col">任务状态</th>
							</tr>						
							
							<s:if test="#list1.size>0">
								<s:iterator value="#list1" var="item">
								<tr>
									<td><span id="gv_TaskList__ctl2_Label1"><s:property value="#item['STEPLABEL']"/></span></td>
									<td><s:property value="#item['NAME']"/></td>
									<td><s:property value="#item['STARTTIME']"/></td>
									<td><s:property value="#item['ENDTIME']"/></td>
									<td><s:property value="#item['statusText']"/></td>
								</tr>
								</s:iterator>
							</s:if>
							
							<s:if test="#list2.size>0">
								<s:iterator value="#list2" var="item">
								<tr>
									<td><s:property value="#item['flowType']"/>[<a href="" onclick="return scanList('<s:property value="#item['params']"/>');"><font color="#0000FF">监控</font></a>]&nbsp;</td>
									<td><s:property value="#item['NAME']"/>&nbsp;</td>
									<td><s:property value="#item['STARTTIME']"/>&nbsp;</td>
									<td><s:property value="#item['ENDTIME']"/>&nbsp;</td>
									<td><s:property value="#item['statusText']"/>&nbsp;</td>
								</tr>
								</s:iterator>
							</s:if>
							<!--  
							<tr class="dg_alternatingitemstyle">
								<td>
                                    <span id="gv_TaskList__ctl3_Label1">Step 2</span>
                                </td><td>WONDERS-TEST_WF</td><td>2022-7-4 9:23:29</td><td>2022-7-4 9:23:30</td><td>已完成</td>
							</tr>
							-->
						</tbody>
						
						</table>
					</div>
                   <!-- <input type="button" value="关闭" style="width: 77px; height: 21px" class="textbox" id="btn_Cancel" name="btn_Cancel" onclick="window.close();" language="javascript">
                     --></fieldset>
                
                <fieldset style="height:auto;width:auto;">
                    <legend>图形化显示</legend>
                    <img src="<%=path%>/contact-ultimus/scanPicture.action?<s:property value="imageUrl"/>"/>
                    <!--  <iframe width="100%" height="100%" frameborder="0" src="GraphicalView.aspx?TaskID=070409d743dfd6be607a6d3aa33ec9" vspace="0" hspace="0" name="rightframe" id="rightframe"></iframe>-->
                </fieldset>
            </div>
        </div>
	</body>
</html>
