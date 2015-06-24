<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.io.*,java.util.*"%>
<%@page import="com.wonders.util.StringUtil"%>
<%@page import="com.wonders.attach.model.vo.UploadFile,com.wonders.attach.util.AttachUtil"%>
<%--@page import="org.springframework.web.context.WebApplicationContext"--%>
<%--@page import="org.springframework.web.context.support.WebApplicationContextUtils"--%>

<%@page import="java.sql.Date"%>
<%
	//备注下拉列表code
	String defaultListCode = "qita_att_dic";
	String extName = null;
	//WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());

	//下拉列表查找方式
	String attachMemo1 = request.getParameter("attachMemo");

	String procType = request.getParameter("procType");
	String targetType = request.getParameter("targetType");
	if (StringUtil.isNull(targetType)) {
		targetType = (String) request.getAttribute("targetType");
	}
	//System.out.println("jsp targetType============"+targetType);
	//文件组编号

	String fileGroup = (String) request.getParameter("fileGroup");
	request.setAttribute("fileGroup", fileGroup);
	String fileGroupId = (String) request.getParameter("fileGroupId");
	request.setAttribute("fileGroupId", fileGroupId);
	String fileGroupName = (String) request
			.getParameter("fileGroupName");
	if (StringUtil.isNull(fileGroupName)) {
		fileGroupName = "uploadFile";
	}
	request.setAttribute("fileGroupName", fileGroupName);
	List fileList = (List) request.getAttribute("attachList");
	//用户名验证

	String userName = (String) session.getAttribute("user_name");
	if (StringUtil.isNull(userName)) {
		userName = StringUtil.getNotNullValueString(request.getParameter("userName"));

		if (!StringUtil.isNull(userName)) {
			session.setAttribute("user_name", userName);
		}
	}

	String uploaderLoginName = (String) session
			.getAttribute("login_name");
	if (StringUtil.isNull(uploaderLoginName)) {
		uploaderLoginName = request.getParameter("loginName");
		if (!StringUtil.isNull(uploaderLoginName)) {
			session.setAttribute("login_name", uploaderLoginName);
		}
	}
	AttachUtil attachUtil = AttachUtil.getInstance();
	String fileTypes = request.getParameter("fileTypes");
	if (StringUtil.isNull(fileTypes)) {
		fileTypes = (String) request.getAttribute("fileTypes");
	}
	fileTypes = "正文,附件";
	String[] fileTypeSel = null;
	if (!StringUtil.isNull(fileTypes)) {
		fileTypeSel = fileTypes.split(",");
	}
	String fileType = request.getParameter("fileType");
	if (StringUtil.isNull(fileType)) {
		fileType = (String) request.getAttribute("fileType");
	}
	String fileCntObjId = request.getParameter("fileCntObjId"); // 在调用页面上显示附件个数用
%>

<html>
	<head>
		<meta http-equiv="x-ua-compatible" content="IE=8">
		<base target="_self">
		
		<link rel="stylesheet" href="../css/formalize.css" />
		<link rel="stylesheet" href="../css/page.css" />
		<link rel="stylesheet" href="../css/default/imgs.css" />
		<link rel="stylesheet" href="../css/reset.css" />
		<link href="../css/organTree.css" rel="stylesheet" type="text/css">
		<script src="../js/jquery-1.7.1.js"></script>
		<script type="text/javascript">
		
		function btnClose(num) {
		
			var result=new Array;    
	        result[0]=document.getElementById('fileGroup').value;
	        result[1]=document.getElementById('fileGroupId').value;
	        result[2]=document.getElementById('fileGroupName').value;
	        result[3]=document.getElementById('procType').value;
	        result[4]=document.getElementById('targetType').value;
	        result[5]=document.getElementById('fileType').value;
	        result[6]=document.getElementById('fileCntObjId').value;
	        result[7]=document.getElementById('attachMemo1').value;
	        result[8]=document.getElementById('count').value;
	        
	        window.returnValue=result;
	        
	        var ua = navigator.userAgent.toLowerCase(); 
			if(ua.match(/chrome\/([\d.]+)/)){
				window.opener.returnValue=result;
			}
	        window.close();
		}
		function getFileSize(filename){ 
		    var size = null; 
		    var file = null; 
		    if(filename){ 
		    	var fileSizeImg = new Image();
				fileSizeImg.src = filename;
		        var size = fileSizeImg.fileSize;
		    } 
		    return(size); 
		}
		
		
		function upFile(){
			fileInpVal = document.forms[0].<%=fileGroupName%>.value;
			//alert(fileInpVal);
			if(fileInpVal==""){
				alert("请选取文件再上传。");
				return;
			}else if(fileInpVal.indexOf('.')<0){
				alert("您上传的文件没有扩展名");
				return;
			}
			//alert(getFileSize(document.forms[0].<%=fileGroupName%>.value));
			form = document.forms[0];
			form.action = "<%=request.getContextPath()%>/attach/upFileJsp.action";
			form.submit();
			
			
	        //window.close();
		}
		function loadVersionFiles(groupId,fileId){
			url = "<%=request.getContextPath()%>/attach/loadVersionFileList.action?groupId="+groupId+"&fileId="+fileId;
			//alert(url);
			window.open(url);
		}
	
		function delete_confirm(e)  
		{ 
			 if (event.srcElement.alt=="删除附件" ) 
			 	//event.returnValue=confirm("您确认执行删除操作么？");	
			 	if(confirm("您确认执行删除操作么？")){
			 		
			 	}	 
		}  
		
		$(function(){
			//$(window).unload(function(){
			//	btnClose(1);
		//	})
			//window.onbeforeunload = function() { btnClose(1); }
			
		})
		
		</script>

		<style>
		font {font-family: 黑体;font-size:9pt;}
		</style>

	</head>
	<body  style="width: 100%; background-color: transparent; background-color: FFF;">
		<div style="width: 98%;">

			<s:form theme="simple" action="upFileJsp" enctype="multipart/form-data" method="post" namespace="/attach">
				<input type="hidden" name="fileGroup" id="fileGroup" value="<%=fileGroup%>" />
				<input type="hidden" name="fileGroupId" id="fileGroupId" value="<%=fileGroupId%>" />
				<input type="hidden" name="fileGroupName" id="fileGroupName" value="<%=fileGroupName%>" />
				<input type="hidden" name="procType" id="procType" value="<%=procType%>" />
				<input type="hidden" name="targetType" id="targetType" value="<%=targetType%>" />
				<input type="hidden" name="fileType" id="fileType" value="<%=fileType%>" />
				<input type="hidden" name="fileCntObjId" id="fileCntObjId" value="<%=fileCntObjId%>" />
				<input type="hidden" name="attachMemo1" id="attachMemo1" value="<%=attachMemo1%>">
				<input type="hidden" name="count" id="count" value="<%=fileList == null ? 0 : fileList.size()%>">

				<!-- <font size="2">全部</font></br> -->
				<%
					if (StringUtil.isNull(procType) || !procType.equals("view")) { //不可操作模式
				%>
				<input type="file" id="<%=fileGroupName%>" name="<%=fileGroupName%>" ContentEditable="false" />

				<span style='font-size:9pt;display:inline;'>附件说明:</span>

				<select name="attachMemo">
					<option value="正文">
						正文
					</option>
					<option value="正文之附件">
						正文之附件
					</option>
					<option value="其他材料">
						其他材料
					</option>
				</select>&nbsp;<input type="button" name="upBut" value="上传" onclick="javascript:upFile()" class="btn" title="附件最大不允许超过20M" />
				&nbsp;单个附件不允许超过20M
				&nbsp;<input type="button" name="cfBut" value="确认并关闭" onclick="javascript:btnClose(1)" class="btn" />
				<!-- <input type="button" name="upBut" value="关闭" onclick="btnClose(2)" class="btn"  /> -->

				<!-- <input type='text' class="inputLine" id='memo' name='memo' size='11%' maxlength='100'/>-->
				<%
					}
				%>
				<table width="100%" bordercolorlight="#eeeeee" bordercolordark="#eeeeee" border="1" cellpadding="2" cellspacing="0">
					<%
						if (fileList == null || fileList.size() == 0) {
					%>
					<tr>
						<td>
							<b>当前没有附件。</b>
						</td>
					</tr>
					<%
						} else {
					%>
					<tr>
						<td align='center' nowrap="nowrap" width='40%'>
							<font><b>文件名</b> </font>
						</td>
						<td align='center' nowrap="nowrap" width='5%'>
							<font><b>大小</b> </font>
						</td>
						<td align='center' nowrap="nowrap" width='20%'>
							<font><b>上传时间</b> </font>
						</td>
						<td align='center' nowrap="nowrap" width='5%'>
							<font><b>上传人</b> </font>
						</td>
						<td align='center' nowrap="nowrap" width='10%'>
							<font><b>版本</b> </font>
						</td>
						<td align='center' nowrap="nowrap" width='15%'>
							<font><b>备注</b> </font>
						</td>
						<!--<td align='center' nowrap="nowrap" width='10%' ><font ><b>预览</b></font></td>-->
						<!--<td nowrap="nowrap"><font ><b>下载</b></font></td>
	
						<%if (StringUtil.isNull(procType) || !procType.equals("view")) { //不可操作模式%>
						<td nowrap="nowrap" ><font><b>删除</b></font></td>
						<%}%>
						-->
					</tr>
					<%
						for (int i = 0; i < fileList.size(); i++) {
									UploadFile file = (UploadFile) fileList.get(i);
					%>
					<script type="text/javascript">
						function delete_confirm()  
						{ 
						  c=window.confirm("确认删除吗?");   
						  if(c==true)
						  		return true;   
						  	else   
						    	return false;   				 
						}  
					</script>
					<tr>
						<td style="line-height: 15px">
							<%
								String fileExtName = file.getFileExtName();
								if (!StringUtil.isNull(fileExtName)) {
									fileExtName = fileExtName.toLowerCase();
								}
								extName = attachUtil.getFileImageName(fileExtName);
							%>
							<img src="<%=request.getContextPath()%>/images/files/<%=extName != null ? extName.toLowerCase() : "null"%>.gif" style="display: inline;" />
							<a href="<%=request.getContextPath()%>/attach/downloadFile.action?fileId=<%=file.getAttachFile().getId()%>" target="_blank"
								style="display: inline;"><%=file.getFileAllName()%></a>&nbsp;
							<%
								if (StringUtil.isNull(procType)|| !procType.equals("view")) {
									if (userName.equals(file.getUploader())&& (file.getUploadDate().startsWith(new Date(System.currentTimeMillis()).toString()))) { //不可操作模式
							%>
							<a href="<%=request.getContextPath()%>/attach/deleteFilesJsp.action?deleteData=<%=file.getAttachFile().getId()%>&fileGroup=<%=fileGroup%>&fileGroupId=<%=fileGroupId%>&fileGroupName=<%=fileGroupName%>&targetType=<%=targetType%>&fileCntObjId=<%=fileCntObjId%>&attachMemo1=<%=attachMemo1%>"
								onClick="return delete_confirm();" style="display: inline;"><img src='<%=request.getContextPath()%>/receive/css/default/images/delete.gif'
									style='cursor: hand; display: inline;' alt='删除附件' /> </a>
							<%
								}
							%>
							<%
								}
							%>
						</td>
						<td style="line-height: 15px;">
							<%=file.getFileSize() > 1024 * 1024 ? file.getFileSize() * 100 / 1024 / 1024 / 100.0 + " M" : (file
								.getFileSize() > 1024 ? file.getFileSize()* 100 / 1024 / 100.0 + " K" : file.getFileSize()+ " B")%></td>
						<td style="line-height: 15px"><%=file.getUploadDate()%></td>
						<td style="line-height: 15px"><%=file.getUploader() == null ? "" : file.getUploader()%></td>
						<%
							int verion = 1;
							if (!StringUtil.isNull(file.getVersion())) {
								verion = Integer.parseInt(file.getVersion());
							}
						%>
						<%
							if (verion == 1) {
						%>
						<td style="line-height: 15px">
							v<%=file.getVersion()%></td>
						<%
							} else {
						%>
						<td style="line-height: 15px">
							<a href="#" style="text-align: left; float: left; margin: 0px; padding: 0px; display: inline;"
								onclick="javascript:loadVersionFiles('<%=file.getGroupId()%>','<%=file.getAttachFile().getId()%>')">v<%=file.getVersion()%></a>
							<input type="button" style="display: inline;" name="str" value="历"
								onclick="javascript:loadVersionFiles('<%=file.getGroupId()%>','<%=file.getAttachFile().getId()%>')">
						</td>
						<%
							}
						%>
						<!--td style="line-height: 15px"><a href="<%=request.getContextPath()%>/attach/downloadFile.action?fileId=<%=file.getAttachFile().getId()%>" target="_blank">下载</a></td>
						<%if (StringUtil.isNull(procType)|| !procType.equals("view")) { //不可操作模式%>
						<td style="line-height: 15px"><a href="<%=request.getContextPath()%>/attach/deleteFilesJsp.action?deleteData=<%=file.getAttachFile().getId()%>&fileGroup=<%=fileGroup%>&fileGroupId=<%=fileGroupId%>&fileGroupName=<%=fileGroupName%>"><font color=red>删除</font></a></td>
						<%}%>
						-->
						<td style="word-break: break-all">
							<%
								String memo = file.getAttachFile().getMemo();
								memo = (memo == null) ? "" : memo;
								out.println(memo);
							%>&nbsp;
						</td>

						<%-- 在线预览
						<td style="word-break:break-all">
							<%
								if("pdf".equalsIgnoreCase(file.getFileExtName())) {					
							%>
								<a href="<%=request.getContextPath()%>/attach/pdfPreview.action?fileId=<%=file.getAttachFile().getId()%>" target="_blank">
								<img src="<%=request.getContextPath()%>/images/find.gif" />
								</a>
							<%
								}
							%>
							<%
								if("jpg".equalsIgnoreCase(file.getFileExtName()) ||
								   "jpeg".equalsIgnoreCase(file.getFileExtName()) ||	
								   "gif".equalsIgnoreCase(file.getFileExtName()) ||
								   "bmp".equalsIgnoreCase(file.getFileExtName()) ||
								   "tiff".equalsIgnoreCase(file.getFileExtName()) ||
								   "png".equalsIgnoreCase(file.getFileExtName()) ) {					
							%>
								<a href="<%=request.getContextPath()%>/attach/imagePreview.action?fileId=<%=file.getAttachFile().getId()%>" target="_blank">
								<img src="<%=request.getContextPath()%>/images/find.gif" />
								</a>
							<%
								}
							%>
							&nbsp;
						</td>
						 --%>
						<!-- 在线预览 -->
					</tr>
					<%
						}
					}
					%>
				</table>
			</s:form>

			<script type="text/javascript">
				var hidden = null, fileCntObj = null;
				<%if (StringUtil.isNull(targetType)|| targetType.equalsIgnoreCase("open")) { //按弹出页面处理%>
				try {
					hidden = window.opener.document.getElementById("<%=fileGroup%>");
					fileCntObj = window.opener.document.getElementsByTagName('span');
				} catch (e) {}
				<%} else if (targetType.equalsIgnoreCase("frame")) { //按iframe处理%>
				try {
					hidden = parent.document.getElementById("<%=fileGroup%>");
					fileCntObj = parent.document.getElementsByTagName('span');
				} catch (e) {}
				<%} else if (targetType.equalsIgnoreCase("dialog")) {%>
				try {
					var pwin = window.dialogArguments;
					hidden = pwin.document.getElementById("<%=fileGroup%>");
					fileCntObj = pwin.document.getElementsByTagName('span');
				} catch (e) {}
				<%}
					String newGroupId = request.getParameter("newGroupId");
					if (!StringUtil.isNull(newGroupId)) {%>
				if (hidden != null) {
					hidden.value = '<%=newGroupId%>';
				}
			<%}%>
				if (fileCntObj != null) {
					for (var i=0; i<fileCntObj.length; i++) {
						if (fileCntObj[i].id == '<%=fileCntObjId%>') {
							fileCntObj[i].innerText = "<%=fileList == null ? 0 : fileList.size()%>";
						}
					}
				}
			</script>
		</div>
	</body>
</html>
