<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.wonders.util.*"%>
<%@page import="com.wonders.contact.deptContact.constant.*"%>
<%@page import="java.io.*"%>

<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
String steplabel = StringUtil.getNotNullValueString(request.getParameter("steplabel"));
if(steplabel.length()==0) steplabel="detail";
	String urlProcessDone = "";
Properties properties = new Properties();
String configPath = Thread.currentThread().getContextClassLoader().getResource("config.properties").getPath();
try {
	FileInputStream fis = new FileInputStream(configPath);
	properties.load(fis);
	urlProcessDone = properties.getProperty("urlProcessDone");
	fis.close();
} catch (Exception e) {
	urlProcessDone="http://10.1.48.30/portal";
}
%>
<script>
function scan(){
	var form = $("form").get(0);
	var id = $(form).find("input[name='id']:hidden").val();
	var params = "id="+id+"&rand="+Math.random();
	var url = "<%=path%>/contact-ultimus/scanList.action?"+params;
	window.open(url);

	return false;	
}

function print(){
	var form = $("form").get(0);
	var id = $(form).find("input[name='id']:hidden").val();
	var params = "id="+id+"&rand="+Math.random();
	var url = "<%=basePath%>/contact-deptContact/printForward.action?"+params;	
	//var pid = $(form).find("input[name='pincident']:hidden").val();
	var newParams = "deptContactMainId="+id+"&ptype=newJobContact&rand="+Math.random();
	var newUrl = "<%=urlProcessDone%>/printProcessInfo.action?"+newParams;
	window.open(newUrl+"&returnUrl="+url);
}
</script>
<%if(!DeptContactFlowConstants.STEPNAME_BEGIN.equals(steplabel)){ %>
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
						<li>
							<a id="todo_handle" class="ywbl" href="javascript:void(0);">业务办理</a>
						</li>
						<!--  -->
						<li>
							<a id="todo_print" class="print" onclick="return print()" href="javascript:void(0);">打印</a>
						</li>
						
						<li>
							<a id="todo_scan" class="jk" onclick="return scan()" href="javascript:void(0);">业务监控</a>
						</li>
						<!--
						<li>
							<a id="todo_tips" class="tips" href="javascript:void(0);">小提示</a>
						</li>
						-->
						<!--  
		               	<li><a class="imp" href="#">公文导入</a></li>
		               	<li><a class="exp" href="#">公文导出</a></li>
		               	-->
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
<%} %>
