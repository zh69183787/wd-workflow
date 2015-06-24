<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" import="java.io.*,java.util.*" %>
<%@page import="org.springframework.web.context.WebApplicationContext" %>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@page import="java.sql.Date"%>

<%
	//备注下拉列表code
	
	WebApplicationContext context=WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
	
	//下拉列表查找方式
	String attachMemo1 = request.getParameter("attachMemo");
	String procType = request.getParameter("procType");
	String targetType = request.getParameter("targetType");
	if(StringUtil.isNull(targetType)){
		targetType = (String)request.getAttribute("targetType");
	}
	//文件组编号
	String fileGroup = (String)request.getParameter("fileGroup");
	request.setAttribute("fileGroup",fileGroup);
	String fileGroupId = (String)request.getParameter("fileGroupId");
	request.setAttribute("fileGroupId",fileGroupId);
	String fileGroupName = (String)request.getParameter("fileGroupName");
	if(StringUtil.isNull(fileGroupName)){
		fileGroupName = "uploadFile";
	}
	request.setAttribute("fileGroupName",fileGroupName);
	List fileList = (List)request.getAttribute("attachList");
	//用户名验证
	String userName = StringUtil.getNotNullValueString((String)session.getAttribute("user_name"));
	if(StringUtil.isNull(userName)){
		userName = request.getParameter("userName");
		if(!StringUtil.isNull(userName)){
			session.setAttribute("user_name",userName);
		}
	}
	String uploaderLoginName = (String)session.getAttribute("login_name");
	if(StringUtil.isNull(uploaderLoginName)){
		uploaderLoginName = request.getParameter("loginName");
		if(!StringUtil.isNull(uploaderLoginName)){
			session.setAttribute("login_name",uploaderLoginName);
		}
	}
	AttachUtil attachUtil = AttachUtil.getInstance();
	String fileTypes = request.getParameter("fileTypes");
	if(StringUtil.isNull(fileTypes)){
		fileTypes = (String)request.getAttribute("fileTypes");
	}
	fileTypes = "正文,附件";
	String[] fileTypeSel = null;
	if(!StringUtil.isNull(fileTypes)){
		fileTypeSel = fileTypes.split(",");
	}
	String fileType = request.getParameter("fileType");
	if(StringUtil.isNull(fileType)){
		fileType = (String)request.getAttribute("fileType");
	}
	String fileCntObjId = request.getParameter("fileCntObjId"); // 在调用页面上显示附件个数用
	String id = request.getParameter("id");
%>

<%@page import="com.wonders.util.StringUtil"%>
<%@page import="com.wonders.attach.util.AttachUtil"%>
<html>
 
<head>
<meta http-equiv="x-ua-compatible" content="IE=8">
<script src="<%=request.getContextPath()%>/attach/js/jquery-1.3.2.min.js" type="text/javascript"></script>
<base target="_self">
</head>
<body onload="init()">
<script type="text/javascript">
	var parentid="fileUploadDiv<%=id %>";
	/*
		ajax加载已上传文件，刷新已上传文件列表内容。
		@param fileGroupId 附件组编号
		@param fileCntObjId 附件数量显示控件id
	*/
	function reLoadFL(fileGroupId,fileCntObjId){
		try {
			var hidden = parent.document.getElementById("<%=fileGroup%>");
			hidden.value=fileGroupId;
		} catch (e) {}
		document.forms[0].fileGroupId.value=fileGroupId;
		document.forms[0].fileCntObjId.value=fileCntObjId;
		$.ajax({
			type: "GET",
		  	cache: false,
		  	url: "<%=request.getContextPath()%>/loadFiles.action?fileGroupId="+fileGroupId,
		  	dataType: "json",
		  	success: function (data, textStatus) {
		  		var filelisthtm="";
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
		   				filelisthtm+="<span style=\"white-space:nowrap;\"><img src=\"<%=request.getContextPath()%>/images/files/"+data.results[x].fileExtName+".gif\" onerror=\"src='<%=request.getContextPath()%>/images/files/null.gif'\" />&nbsp;"
		   				<%if(request.getParameter("delete").equals("1")){ %>
						filelisthtm+="<a onmousedown=\"fsclick(this,'<%=request.getContextPath()%>/downloadFile.action?fileId="+data.results[x].id+"','<%=request.getContextPath()%>/deleteFile.action?deleteData="+data.results[x].id+"&fileGroup=<%=fileGroup%>&fileGroupId="+fileGroupId+"&fileGroupName=<%=fileGroupName%>&targetType=<%= targetType%>&fileCntObjId="+fileCntObjId+"&attachMemo1=<%=attachMemo1%>','"+data.results[x].uploader+"','"+data.results[x].uploadDate+"','"+daxiao+"','v"+data.results[x].version+"','"+data.results[x].attachFile.memo+"','<%=id%>','1')\" href=\"<%=request.getContextPath()%>/downloadFile.action?fileId="+data.results[x].id+"\">"+data.results[x].fileAllName+"</a>&nbsp;"
						<%}else if(request.getParameter("delete").equals("0")){ %>
						filelisthtm+="<a onmousedown=\"fsclick(this,'<%=request.getContextPath()%>/downloadFile.action?fileId="+data.results[x].id+"','<%=request.getContextPath()%>/deleteFile.action?deleteData="+data.results[x].id+"&fileGroup=<%=fileGroup%>&fileGroupId="+fileGroupId+"&fileGroupName=<%=fileGroupName%>&targetType=<%= targetType%>&fileCntObjId="+fileCntObjId+"&attachMemo1=<%=attachMemo1%>','"+data.results[x].uploader+"','"+data.results[x].uploadDate+"','"+daxiao+"','v"+data.results[x].version+"','"+data.results[x].attachFile.memo+"','<%=id%>','0')\" href=\"<%=request.getContextPath()%>/downloadFile.action?fileId="+data.results[x].id+"\">"+data.results[x].fileAllName+"</a>&nbsp;"
		   				<% }%>
		   				if(data.results[x].version==1){
		   					filelisthtm+="v"+data.results[x].version+"&nbsp;&nbsp;</span>";
		   				}else{
		   					filelisthtm+="<a href=\"#0\" onclick=\"javascript:loadVersionFiles('"+data.results[x].groupId+"','"+data.results[x].id+"')\" >v"+data.results[x].version+"</a>&nbsp;&nbsp;</span>";
		   				}
		   			}
		   			try {
						var fileCntObj = parent.document.getElementById("<%=fileCntObjId%>");
						fileCntObj.innerText=data.results.length;
					} catch (e) {}
  				}
  				parent.document.getElementById(parentid).innerHTML=filelisthtm;
  				var div   =   document.getElementById("fileDiv"); 
  				div.parentNode.removeChild(div);
  				init();	
			}
		});
	}
	
	/*
		ajax删除已上传文件，刷新已上传文件列表内容。
		@param url 删除文件链接
		@param id 文件列表id值
	*/
	function deleteFile(url,id){
		parentid="fileUploadDiv"+id;
		$.ajax({
			type: "GET",
		  	cache: false,
		  	url: url,
		  	dataType: "json",
		  	success: function (data, textStatus) {
  				reLoadFL(document.forms[0].fileGroupId.value,document.forms[0].fileCntObjId.value);
			}
		});
	}
	
	/*上传文件*/
	function upFile(){
		fileInpVal = document.forms[0].<%=fileGroupName%>.value;
		if(fileInpVal==""){
			alert("请选取文件再上传。");
			return;
		}else if(fileInpVal.indexOf('.')<0){
			alert("您上传的文件没有扩展名");
			return;
		}
		
		
		
		//var image=new Image();
　               // image.dynsrc=fileInpVal;
　 		//alert(image.fileSize);
		
		
		form = document.forms[0];
		form.action = "<%=request.getContextPath()%>/upFileJspNew.action";
		form.submit();
	}
	
	
	/*创建IE下的文件选择框*/ 
	function createIeInput(form) 
  	{ 
    	var tip=getIeTip(form); 
        var inputDiv=document.createElement("div"); 
        inputDiv.setAttribute("id","fileDiv");
        var input=document.createElement("input"); 
        input.setAttribute("type","file"); 
        input.setAttribute("name","<%=fileGroupName%>"); 
        input.setAttribute("id","<%=fileGroupName%>"); 
        input.style.cssText="width:0;cursor:hand"; 
        input.onchange=function(){        
        	upFile();
        } 
        inputDiv.appendChild(input);
        var position=getPosition(tip); 
        inputDiv.style.cssText="position:absolute;top:"+position.top+"px;left:"+position.left+"px;filter:alpha(opacity=0);z-index:2"; 
        form.appendChild(inputDiv);         
    } 
    
    /*初始化函数*/  
	function initIE() 
  	{ 
    	var form=document.forms["form_upload"]; 
    	createIeInput(form); 
  	} 
	
	/*获得文件输入控件*/
	function getIeTip(form) 
  	{
        var cssStr="font:12px Arial;color:#00f;text-decoration:underline"; 
        var tipDiv=document.getElementById("addFileDiv"); 
        tipDiv.style.cssText=cssStr; 
        return tipDiv; 
	} 
	
	/* 获取指定元素在页面的位置 */ 
	function getPosition(obj) 
	{ 
    	var top=0,left=0; 
        while(obj.offsetParent) { 
        	top+=obj.offsetTop; 
            left+=obj.offsetLeft; 
            obj=obj.offsetParent; 
        } 
        return {top:top,left:left}; 
    } 
    
    /*初始化函数*/  
	function init() 
    {         
		initIE();       
  	} 
</script>
<form action="<%=request.getContextPath()%>/upFileJspNew.action" enctype="multipart/form-data" method="post" id="form_upload" name="form_upload" target="upload">
<input type="hidden" name="fileGroup" value="<%=fileGroup %>" />
<input type="hidden" name="fileGroupId" value="<%=fileGroupId %>" />
<input type="hidden" name="fileGroupName" value="<%=fileGroupName %>" />
<input type="hidden" name="procType" value="<%=procType %>" />
<input type="hidden" name="targetType" value="<%=targetType %>" />
<input type="hidden" name="fileType" value="<%=fileType %>" />
<input type="hidden" name="fileCntObjId" value="<%=fileCntObjId %>" />
<input type="hidden" name="attachMemo1" value="<%=attachMemo1 %>">
<input type="hidden" name="id" value="<%=request.getParameter("id") %>">
<table>
<tr>
<td>
<span id="addFileDiv" style="cursor:hand">添加附件</span>
<% if(request.getParameter("memo").equals("")){%>
<font style='font-size:9pt'>附件说明:</font><select name="attachMemo">
	<option value="正文">正文</option>
	<option value="正文之附件">正文之附件</option>
	<option value="正文之附件">其他材料</option>
	</select>
<% }else{%>
<input type="hidden" name="attachMemo" value="<%=request.getParameter("memo")%>" />
<% }%>
</td>
</tr>
</table>
</form>
<iframe name="upload" style="display:none"></iframe>
<% if(!StringUtil.isNull(fileGroupId)){%>
<script type="text/javascript">
reLoadFL('<%=fileGroupId %>','<%=fileCntObjId %>');
</script>
<% }%>
</body>
</html>