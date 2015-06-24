<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.wonders.util.*"%>

<%@taglib prefix="s" uri="/struts-tags" %>

<% 
String path = request.getContextPath();
String steplabel = StringUtil.getNotNullValueString(request.getParameter("steplabel"));
String id = StringUtil.getNotNullValueString(request.getParameter("id"));

if(steplabel.length()==0) steplabel="detail";
String ultimusIp = PWSProperties.getValueByKey("pws_server_ip");
%>
<script>
function scan(){
	var id = $("input[name='taskid']:hidden").val();
	var url = "http://<%=ultimusIp%>/sLogin/workflow/TaskStatus.aspx?TaskID="+id;
	window.open(url);

	return false;	
}

function print(){
	var pname = $("input[name='pname']:hidden").val();
	var pincident = $("input[name='pincident']:hidden").val();
	var url =  "";
	if(pname=="合同后审流程"){
		url = "<%=path %>/contract-reviewMain/print.action?"
			+"pname="+encodeURI(pname)+"&"
			+"pincident="+pincident+"&"
			+"rand="+Math.random();
	}
	window.open(url);
	return false;
}


</script>

<div class="Divab1">
	<!--1st-->
	<div class="panel_6">
		<div class="divT">
			<div class="mb10 icon icon_1"></div>
			<div class="more_4">
				<a title="更多" href="#">更多</a>
			</div>
		</div>
		<div class="divH">
			<div class="divB">
				<h5 class="clearfix">
					业务办理
				</h5>
				<div class="con">
					<ul class="button clearfix">
				
						<s:if test='"view"!=params.processParam["operateType"]'>
							<li>
								<a id="todo_handle" class="ywbl" href="javascript:void(0);">业务办理</a>
							</li>
						</s:if>
						<!--  -->
						<li>
							<a id="todo_print" class="print" onclick="return print();" href="javascript:void(0);">打印</a>
						</li>
						<li>
							<a id="todo_scan" class="jk" onclick="return scan()" href="javascript:void(0);">业务监控</a>
						</li>

						<s:if test='"办结"==params.processParam["steplabel"] && "view"!=params.processParam["operateType"]'>
							<li>
								<s:if test='"0"==vo.flag'>
									<a id="todo_prefill" class="prefill" onclick="return prefill(1);" href="javascript:void(0);">预归档</a>
								</s:if>
								<s:else>
									<a id="todo_prefill" class="prefill" onclick="return prefill(0);" href="javascript:void(0);">取消归档</a>
								</s:else>
							</li>
						</s:if>
					</ul>
				</div>
			</div>
			<div class="divF"></div>
		</div>
	</div>
	<!--1st End-->
	<!--2nd-->
	<!--  
   <div class="panel_6">
   	<div class="divT">
      	  <div class="mb10 icon icon_1"></div>
      	  <div class="more_4"><a title="更多" href="#">更多</a></div>
     </div>
   	<div class="divH">
      	  <div class="divB">
          	<h5 class="clearfix">
          	  <span class="fl">参考信息</span>
                   <select class="input_small fr" id="select3" name="select3">
                   	<option>收文</option>
                   </select>
           </h5>
               <div class="con">
               	<ul class="list">
                   	<li><a href="#">123</a></li>
                   </ul>
               </div>
         </div>
       	<div class="divF">
           </div>
       </div>
   </div>
   -->
	<!--2nd End-->
</div>

