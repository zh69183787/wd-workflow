<%@ page contentType="text/html; charset=UTF-8" %> 
<%
/**
* fileCntObjId   在调用页面上显示附件个数用（span编号）
* fileGroupId    文件组编号（对应数据库GROUPID）
* memo  附件备注（对应数据库MEMO）
* id 文件上传组件编号
* delete  是否添加右键删除附件功能（1添加删除功能，0不添加删除功能）userName  用户名（用户名）
* loginName  用户登录名（用户登录名，用户工号）
* attachMemo  附件类型字典项分类代码（对应T_LIST_STATUS_TYPE的CODE）
* fileGroup  接收回传的文件组编号的页面空间名称
* fileGroupName 文件上传控件的名称和ID值
* procType 是否可以上传文件（为空可以上传，“view”为不可上传，只能查看）
 */
String procType = request.getParameter("procType");
if(request.getAttribute("needUplaodFileJs")==null){ 
request.setAttribute("needUplaodFileJs","need");
	
%>

<%@page import="com.wonders.util.StringUtil"%>
<script src="<%=request.getContextPath()%>/attach/js/uploadFile.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/attach/js/jquery-1.3.2.min.js" type="text/javascript"></script>

<script type="text/javascript">

	/*
		ajax加载上传文件历史版本，刷新历史版本列表内容。
		@param fileGroupId 附件组编号
		@param fileCntObjId 附件数量显示控件id
	*/
	function loadVersionFiles(groupId,fileId){
		var xp=document.body.scrollLeft+window.event.clientX;
		var yp=document.body.scrollTop+window.event.clientY;
		url = "<%=request.getContextPath()%>/loadVersionFileListAjax.action?groupId="+groupId+"&fileId="+fileId;
		$.ajax({
	  		type: "GET",
	  		cache: false,
	  		url: url,
	  		dataType: "json",
	  		success: function (data, textStatus) {
	  			var tv="";
	  			if(data.results.length>0){
		   			for (x=0;x<data.results.length;x++){
		   				var daxiao;
		   				if(data.results[x].fileSize> 1024*1024){
		   					daxiao = data.results[x].fileSize * 100 / 1024 / 1024 / 100.0; 
		   					daxiao = daxiao.toFixed(0)+ " M";
		   				}else if( data.results[x].fileSize> 1024){
		   					daxiao = data.results[x].fileSize * 100 / 1024 / 100.0
		   					daxiao = daxiao.toFixed(0) + " K";
		   				}else{
		   					daxiao = data.results[x].fileSize;
		   					daxiao = daxiao.toFixed(0)+ "B";
		   				}
		   				
		   				tv+="<span>&nbsp;<img src=\"<%=request.getContextPath()%>/images/files/"+data.results[x].fileExtName+".gif\"/>&nbsp;<a onmousedown=\"fsclick1(this,'<%=request.getContextPath()%>/downloadFile.action?fileId="+data.results[x].id+"&ver=old','"+data.results[x].uploader+"','"+data.results[x].uploadDate+"','"+daxiao+"','v"+data.results[x].version+"','"+data.results[x].attachFile.memo+"')\" href=\"<%=request.getContextPath()%>/downloadFile.action?fileId="+data.results[x].id+"&ver=old\">"+data.results[x].fileAllName+"</a>&nbsp;v"+data.results[x].version+"&nbsp;</span></br>";
		   			}
		   			tv+="<div align=\"right\"><span style=\"cursor:hand\" onclick=\"document.getElementById('versionDiv').style.display='none';\">关 闭</span>&nbsp;&nbsp;</div>";
		  			document.getElementById("versionDiv").innerHTML=tv;
		  			var DIV = document.getElementById('versionDiv');	
					DIV.style.left=xp;
					DIV.style.top=yp;
					DIV.style.display = "block";
				}
			}
		});
	}	
</script>
<% }%>
<%if(StringUtil.isNull(procType)||!procType.equals("view")){ %>
<iframe id="contentAtt2<%=request.getParameter("id") %>" name="contentAtt2<%=request.getParameter("id") %>" src="<%=request.getContextPath()%>/attach/addFiles.jsp?attachMemo=<%=request.getParameter("attachMemo")%>&fileGroup=<%=request.getParameter("fileGroup")%>&fileGroupName=<%=request.getParameter("fileGroupName")%>&userName=<%=request.getParameter("userName")%>&loginName=<%=request.getParameter("loginName")%>&fileGroupId=<%=request.getParameter("fileGroupId")%>&fileCntObjId=<%=request.getParameter("fileCntObjId")%>&procType=<%=request.getParameter("procType")%>&targetType=<%=request.getParameter("targetType")%>&id=<%=request.getParameter("id")%>&delete=<%=request.getParameter("delete")%>&memo=<%=request.getParameter("memo")%>"  scrolling="no" height="30" frameborder="0" marginheight="0" marginwidth="0" width="100%" ></iframe>
<%}else if(procType.equals("view")){ %>
<iframe id="contentAtt2<%=request.getParameter("id") %>" name="contentAtt2<%=request.getParameter("id") %>" src="<%=request.getContextPath()%>/attach/listFiles.jsp?attachMemo=<%=request.getParameter("attachMemo")%>&fileGroup=<%=request.getParameter("fileGroup")%>&fileGroupName=<%=request.getParameter("fileGroupName")%>&userName=<%=request.getParameter("userName")%>&loginName=<%=request.getParameter("loginName")%>&fileGroupId=<%=request.getParameter("fileGroupId")%>&fileCntObjId=<%=request.getParameter("fileCntObjId")%>&procType=<%=request.getParameter("procType")%>&targetType=<%=request.getParameter("targetType")%>&id=<%=request.getParameter("id")%>&delete=<%=request.getParameter("delete")%>&memo=<%=request.getParameter("memo")%>"  scrolling="no" height="0" frameborder="0" marginheight="0" marginwidth="0" width="100%" ></iframe>
<% }%>
<div id="fileUploadDiv<%=request.getParameter("id") %>" style="word-break:break-all;"></div>