<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.io.*,java.util.*"%>
<%@page import="com.wonders.util.StringUtil"%>
<%@page import="com.wonders.attach.model.vo.UploadFile,com.wonders.attach.util.AttachUtil"%>
<%--@page import="org.springframework.web.context.WebApplicationContext"--%>
<%--@page import="org.springframework.web.context.support.WebApplicationContextUtils"--%>

<%@page import="java.sql.Date"%>
<%
	if("1".equals(request.getParameter("error"))){
		%>
		<script>
			alert("您上传的文件大小超过了20M限制，请分卷压缩后重新上传！");
		</script>
		<%
	}
%>

<%
String path = request.getContextPath();
	//备注下拉列表code
	String defaultListCode = "qita_att_dic";
	String extName = null;
	//WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
	
	//附件类型
	String listType = request.getParameter("listType");

	
	//下拉列表查找方式
	String attachMemo1 = request.getParameter("attachMemo");

	String procType = request.getParameter("procType");
	String targetType = request.getParameter("targetType");
	if (StringUtil.isNull(targetType)) {
		targetType = (String) request.getAttribute("targetType");
	}
	//System.out.println("jsp targetType============"+targetType);
	//文件组编号
	String approve = (String) request.getParameter("approve");
	request.setAttribute("approve", approve);
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
		
		<link rel="stylesheet" href="<%=path %>/receive/css/formalize.css" />
		<link rel="stylesheet" href="<%=path %>/receive/css/page.css" />
		<link rel="stylesheet" href="<%=path %>/receive/css/default/imgs.css" />
		<link rel="stylesheet" href="<%=path %>/receive/css/reset.css" />
		<link rel="stylesheet" href="<%=path %>/attach/js/uploadify.css" />
		
		<link href="<%=path %>/receive/css/organTree.css" rel="stylesheet" type="text/css">
		<script src="<%=path %>/receive/js/jquery-1.7.1.js"></script>
		<script src="<%=path %>/attach/js/jquery.uploadify.3.2.edited.js"></script>
		
		<script type="text/javascript">
		//文件上传  
		$(function() {  
			
			 $("#<%=fileGroupName%>").uploadify({ 
				 //附带值
		        'formData':{
		        	'fileGroup':"<%=fileGroup%>",
		            'fileGroupName':"<%=fileGroupName%>",
		            'fileGroupId':"<%=fileGroupId%>",
		            'attachMemo1':"<%=attachMemo1%>",
		            'attachMemo':$("#attachMemo").val(),
		            'procType': "<%=procType%>",
		            'targetType':"<%=targetType%>",
		            'approve':"<%=approve%>",
		         	'fileCntObjId':"<%=fileCntObjId%>"
		        },
             	//开启调试
       		 	'debug' : false,
        		//是否自动上传
        		'auto':false,
                /*注意前面需要书写path的代码*/ 
                //浏览按钮的宽度
        		'width':'100',
        		//浏览按钮的高度
        		'height':'15',
               	'method' : "post",  
                'swf'       : '<%=path %>/attach/js/uploadify.swf', 
                'uploader'         : '<%=path %>/attach/upFileJsp.action;jsessionid=<%=session.getId()%>', 
                'cancelImg'      : '<%=path %>/attach/js/cancel.png', 
		       	'buttonText' : '选择文件',           //按钮名称  
		       	'fileObjName'    :'<%=fileGroupName%>',  
      
                'simUploadLimit' : 1, //一次同步上传的文件数目 
                //'sizeLimit'      : 10000000, //设置单个文件大小限制  单位byte
                'queueSizeLimit' : 1, //队列中同时存在的文件个数限制 
                'fileTypeDesc'       : '支持格式:*.', //如果配置了以下的'fileExt'属性，那么这个属性是必须的 
                'fileTypeExts'        : '*.*',//允许的格式    '*.jpg;*.bmp;*.png;*.gif'
                'fileSizeLimit' : '20000KB' ,    //文件大小   置单个文件大小限制  默认单位byte
        		'queueSizeLimit' : 1,//上传数量
        		'removeTimeout' : '1',//成功后移除 处理框
       			'removeCompleted' : true, 
       			'requeueErrors' : false,
		       'onUploadSuccess' : function(file, data, response){
		    	   //alert(data);
		    	   //window.name = "dialogPage"; 
		    	  
		    	   var attach = eval('(' + data + ')'); 
		    	   $("#fileGroup").val(attach.fileGroup);
		    	   $("#fileGroupName").val(attach.fileGroupName);
		    	   $("#fileGroupId").val(attach.fileGroupId);
		    	   $("#newGroupId").val(attach.newGroupId);
		    	   $("#procType").val(attach.procType);
		    	   $("#targetType").val(attach.targetType);
		    	   $("#fileCntObjId").val(attach.fileCntObjId);
		    	   $("#attachMemo").val(attach.attachMemo);
		    	   $("#approve").val(attach.approve);
		    	   //alert(1);
		    	   $("form").attr("enctype","application/x-www-form-urlencoded").attr("action","<%=path%>/attach/loadFileList.action");
		    	   $("form").submit();
		           // var url = "<%=path%>/attach/loadFileList.action?"+
		            //		"fileGroup="+attach.fileGroup +"&"+
		            	//	"fileGroupName="+attach.fileGroupName +"&"+
		            		///"fileGroupId="+attach.fileGroupId +"&"+
		            		//"newGroupId="+attach.newGroupId +"&"+
		            		//"procType="+attach.prcssocType +"&"+
		            		//"targetType="+attach.targetType +"&"+
		            		//"fileCntObjId="+attach.fileCntObjId +"&"+
		            		//"attachMemo="+attach.attachMemo +"&"+
		            		//"approve="+attach.approve;
		            //window.open(url,"dialogPage");
		            //console.log(url);
		        } ,
		      //选择上传文件后调用
		  		'onSelect' : function(file) {
		  			$(".uploadify").css("float","");
		  			//$(".uploadify").css("display","inline");
					// $(".uploadify-button ").css("display","inline");
					// $(".uploadify-queue").css("display","inline");
		     },
	         //返回一个错误，选择文件的时候触发
	        'onSelectError':function(file, errorCode, errorMsg){
	            switch(errorCode) {
	                case -100:
	                    this.queueData.errorMsg = "上传的文件数量已经超出系统限制的"+$('#<%=fileGroupName%>').uploadify('settings','queueSizeLimit')+"个文件！";
	                    break;
	                case -110:
	                    //alert("文件 ["+file.name+"] 大小超出系统限制的"+$('#<%=fileGroupName%>').uploadify('settings','fileSizeLimit')+"大小！");
	                    this.queueData.errorMsg = "文件 ["+file.name+"] 大小超出系统限制的"+$('#<%=fileGroupName%>').uploadify('settings','fileSizeLimit')+"大小！";
	                    break;
	                case -120:
	                    //alert("文件 ["+file.name+"] 大小异常！");
	                    this.queueData.errorMsg = "文件 ["+file.name+"] 大小异常！";
	                   break;
	                case -130:
	                    //alert("文件 ["+file.name+"] 类型不正确！");
	                    this.queueData.errorMsg = "文件 ["+file.name+"] 类型不正确！";
	                    break;
	            }
	           
	        }
				 }); 
			 $(".uploadify").css("float","left");
		 }); 
 
		
		
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
	        
	        //window.returnValue=result;
	        if(window.opener){
		        $("#<%=fileGroup%>",window.opener.document).val($("#fileGroupId").val());
				$("#<%=fileCntObjId%>",window.opener.document).val($("#fileCntObjId").val());
		        var ua = navigator.userAgent.toLowerCase(); 
				if(ua.match(/chrome\/([\d.]+)/)){
					//window.opener.returnValue=result;
					$("#<%=fileGroup%>",window.opener.document).val($("#fileGroupId").val());
			     	$("#<%=fileCntObjId%>",window.opener.document).val($("#fileCntObjId").val());
				}
	        }
			if(num==1){
	        	window.close();
	        }
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
			$(window).unload(function(){
				var ua = navigator.userAgent.toLowerCase(); 
				if(ua.match(/chrome\/([\d.]+)/)){
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
			       // window.opener.returnValue=result;
			       	if(window.opener){
				     	$("#<%=fileGroup%>",window.opener.document).val($("#fileGroupId").val());
				     	$("#<%=fileCntObjId%>",window.opener.document).val($("#fileCntObjId").val());
			       	}
				}else{
					btnClose(2);
				}
				
			})
			window.onbeforeunload = function() { 
				var ua = navigator.userAgent.toLowerCase(); 
				if(ua.match(/chrome\/([\d.]+)/)){
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
			        //window.opener.returnValue=result;
			        if(window.opener){
				        $("#<%=fileGroup%>",window.opener.document).val($("#fileGroupId").val());
				     	$("#<%=fileCntObjId%>",window.opener.document).val($("#fileCntObjId").val());
			        }
				}else{
					btnClose(2);
				}
			}
			
		})
		
		</script>

		<style>
		font {font-family: 黑体;font-size:9pt;}
		table, td, th  
		  {  
		  border:1px solid #eeeeee;  
		  }
		</style>

	</head>
	<body  style="width: 100%; background-color: transparent; background-color: FFF;">
		<div style="width: 98%;">

			<s:form theme="simple" action="upFileJsp" enctype="multipart/form-data" method="post" namespace="/attach">
				<input type="hidden" name="fileGroup" id="fileGroup" value="<%=fileGroup%>" />
				<input type="hidden" name="approve" id="approve" value="<%=approve%>" />
				<input type="hidden" name="fileGroupId" id="fileGroupId" value="<%=fileGroupId%>" />
				<input type="hidden" name="newGroupId" id="newGroupId"/>
				<input type="hidden" name="listType" id="listType" value="<%=listType%>"/>
				
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

				<span style='margin-left:5px;font-size:9pt;display:inline;'>附件说明:</span>

				<select name="attachMemo" id="attachMemo">
					<option value="正文">
						正文
					</option>
					<option value="正文之附件">
						正文之附件
					</option>
					
					<option value="正文草稿">正文草稿</option>
					<option value="正文核稿">正文核稿</option>
					<option value="正文清样">正文清样</option>
					<option value="附件草稿">附件草稿</option>
					<option value="附件核稿">附件核稿</option>
					<option value="附件清样">附件清样</option>
					<option value="其他材料">其他材料</option>
				</select>&nbsp;<input type="button" onclick="$('#<%=fileGroupName%>').uploadify('settings', 'formData', { 'attachMemo' : $('#attachMemo').val() } ); $('#<%=fileGroupName%>').uploadify('upload','*');" name="upBut" value="上传" class="btn" title="附件最大不允许超过20M" />
				&nbsp;单个附件不允许超过20M
				<%if("1".equals(approve)&&false){ %>
				&nbsp;<input type="button" name="cfBut" value="确认并关闭" onclick="javascript:btnClose(1)" class="btn" />
				<%} %>
				<!-- <input type="button" name="upBut" value="关闭" onclick="btnClose(2)" class="btn"  /> -->

				<!-- <input type='text' class="inputLine" id='memo' name='memo' size='11%' maxlength='100'/>-->
				<%
					}
				%>
				<table width="100%" cellpadding="2" cellspacing="0">
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
							<img src="<%=request.getContextPath()%>/receive/css/default/images/files/<%=extName != null ? extName.toLowerCase() : "null"%>.gif" style="display: inline;" />
							
							<%
							if(file.getPath().contains("http://")){
							%>
							<a href="<%=file.getPath()%>" target="_blank" style="display:inline;"><%=file.getFileAllName() %></a>&nbsp;
							<%
							}else{
							%>
							<a href="<%=request.getContextPath()%>/attach/downloadFile.action?fileId=<%=file.getAttachFile().getId()%>" target="_blank"
								style="display: inline;"><%=file.getFileAllName()%></a>&nbsp;
							<%
							}
								if (StringUtil.isNull(procType)|| !procType.equals("view")) {
									if (userName.equals(file.getUploader())&& (file.getUploadDate().startsWith(new Date(System.currentTimeMillis()).toString()))) { //不可操作模式
							%>
							<a href="<%=request.getContextPath()%>/attach/deleteFilesJsp.action?deleteData=<%=file.getAttachFile().getId()%>&fileGroup=<%=fileGroup%>&fileGroupId=<%=fileGroupId%>&fileGroupName=<%=fileGroupName%>&targetType=<%=targetType%>&fileCntObjId=<%=fileCntObjId%>&attachMemo1=<%=attachMemo1%>&approve=<%=approve%>"
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
					//fileCntObj = parent.document.getElementsByTagName('span');
					fileCntObj = window.opener.document.getElementsByTagName('span');
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
