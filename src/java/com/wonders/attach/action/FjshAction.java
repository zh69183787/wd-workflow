package com.wonders.attach.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wonders.attach.model.vo.UploadFile;
import com.wonders.attach.service.FjshService;
import com.wonders.attach.util.FileUpProperties;
import com.wonders.util.DateUtil;
import com.wonders.util.StringUtil;


@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/")
@Component("fjshAction")
@Scope("prototype")
public class FjshAction extends ActionSupport {
	private static final long fileSize = Long.parseLong(FileUpProperties.getValueByKey("fileSize"));
	private FjshService fjshService;
	private String attachMemo1;
	
	String AJAX = null;
	
	ActionContext actionContext = ActionContext.getContext();
	HttpServletRequest request = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
	HttpServletResponse response = (HttpServletResponse) actionContext.get(ServletActionContext.HTTP_RESPONSE);
	
	/*

		<action name="upFile" class="fjshAction" method="upFile">
		
		<action name="upFileJsp" class="fjshAction" method="upFileJsp"><result name="upFileRes" >/attach/upFileRes.jsp?attachMemo1=${attachMemo1}</result>
		
		<action name="upFileJspNew" class="fjshAction" method="upFileJsp"><result name="upFileRes" >/attach/upFileResNew.jsp?attachMemo1=${attachMemo1}</result>
		
		<action name="loadVersionFileList" class="fjshAction" method="loadVersionFileList"><result name="versionFileList" >/attach/versionFileList.jsp</result>
		

		
		<action name="deleteFilesJsp" class="fjshAction" method="deleteFilesJsp"><result name="delFileRes" >/attach/upFileRes.jsp</result>
		
		<action name="copyLocalFiles" class="fjshAction" method="copyLocalFiles"><result name="success" >/attach/testAttach2.jsp</result>
		
		<action name="loadVersionFileListAjax" class="fjshAction" method="loadVersionFileListAjax">
		
		<action name="deleteFile" class="fjshAction" method="deleteFile">
		
	
	*/
	@Action(value="/addFjsh",results={@Result(name="fjsh",location="/attach/fjsh.jsp")})
	public String addFjsh() {
		//System.out.println("==========FjshAction addFjsh==================");
		request = ServletActionContext.getRequest();
		this.procParams(request);
		//HttpServletRequest request = request;
		String fileGroup = StringUtil.getNotNullValueString(request.getParameter("fileGroup"));
		String fileGroupId = StringUtil.getNotNullValueString(request.getParameter("fileGroupId"));
		//System.out.println("fileGroup=========="+fileGroup+"|fileGroupId=========="+fileGroupId);
		request.setAttribute("fileGroup", fileGroup);
		request.setAttribute("fileGroupId", fileGroupId);
		
		return "fjsh";
	}

	public static String encodingFileName(String fileName) { 
        String returnFileName = ""; 
        try { 
            returnFileName = URLEncoder.encode(fileName, "UTF-8"); 
            returnFileName = StringUtils.replace(returnFileName, "+", "%20"); 
            if (returnFileName.length() > 150) { 
                returnFileName = new String(fileName.getBytes("GB2312"), "ISO8859-1"); 
                returnFileName = StringUtils.replace(returnFileName, " ", "%20"); 
            } 
        } catch (UnsupportedEncodingException e) { 
            e.printStackTrace(); 
        } 
        return returnFileName; 
    } 
	/*
	 * 文件下载
	 * 2009-11-27 zzg 修改为直接从action输出文件数据流，不转入jsp
	 */
	@Action(value="/downloadFile",results={@Result(name="downloadFile",location="/attach/downloadFile.jsp")})
	public String downloadFile() throws UnsupportedEncodingException{
		//System.out.println("==========FjshAction downloadFile==================");
		request = ServletActionContext.getRequest();
		this.procParams(request);

		String fileId = StringUtil.getNotNullValueString(request.getParameter("fileId"));
		UploadFile upFile = this.fjshService.loadFileById(fileId);
		String path = upFile.getPath();					// 文件所在磁盘路径

		String fileName = upFile.getFileAllName();		// 真实文件名

		String saveFileName = upFile.getSaveFileName();	// 磁盘上的文件名

		String version = upFile.getVersion();
//		request.setAttribute("path", path);
//		request.setAttribute("fileName", fileName);

		if ("old".equals(StringUtil.getNotNullValueString(request.getParameter("ver")))){
			if (version != null){
				saveFileName = saveFileName.replace(".dat","_v"+version+".dat");
//				request.setAttribute("saveFileName", saveFileName);
			}
		}
		//fileName = StringUtils.trim(fileName); 
		//String formatFileName = encodingFileName(fileName);//在后面定义方法encodingFileName(String fileName); 
		//System.out.println(formatFileName);
		fileName = URLEncoder.encode(fileName, "utf-8");
		fileName = fileName.replace("+", "%20"); // encode后替换  解决空格问题
		response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
		response.setContentType("application/octet-stream");
		try {
			InputStream is = new FileInputStream(path + saveFileName);
			OutputStream os = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int n = 0;
			while ((n = is.read(buffer, 0, 1024)) > 0) {
				os.write(buffer, 0, n);
			}
			os.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes" })
	@Action(value="/loadFiles")
	public String loadFiles() {
		//System.out.println("==========FjshAction loadFiles==================");
		request = ServletActionContext.getRequest();
		this.procParams(request);
		String fileGroupId = StringUtil.getNotNullValueString(request.getParameter("fileGroupId"));
		//System.out.println("fileGroupId=========="+fileGroupId);
		@SuppressWarnings("unused")
		List list = new ArrayList();
		if(!StringUtil.isNull(fileGroupId)){
			list = this.fjshService.loadFilesByGroupId(fileGroupId);
		}
		//System.out.println("file list size==="+list.size());
//createJSonData("{\"success\":true, \"results\": " + VOUtils.getJsonDataFromCollection(list) + "}");
		return AJAX;
	}
	
	/**
	 * 适用jsp
	 * @return
	 */
	
	@SuppressWarnings("rawtypes")
	@Action(value="/loadFileList",results={
			@Result(name="attachList",location="/attach/attachList.jsp",params={"attachMemo1","${attachMemo1}"}),
			@Result(name="sendAttachList",location="/attach/sendAttachList.jsp"),
			@Result(name="sendReceiverAttachList",location="/attach/sendReceiverAttachList.jsp"),
			@Result(name="attachListHT",location="/attach/attachListHT.jsp"),
			@Result(name="normative",location="/attach/attachListNormative.jsp")})
	public String loadFileList(){
		//System.out.println("===================loadFileList===========================");
		request = ServletActionContext.getRequest();
		this.procParams(request);

		String fileGroupId = StringUtil.getNotNullValueString(request.getParameter("fileGroupId"));
		String listType = StringUtil.getNotNullValueString(request.getParameter("listType"));

		//System.out.println("fileGroupId=========="+fileGroupId);
		List list = new ArrayList();
		if(!StringUtil.isNull(fileGroupId)){
			list = this.fjshService.loadFilesByGroupId(fileGroupId);
		}
		//setAttachMemo1("12321321321321");
		request.setAttribute("attachList", list);
		if(listType!=null&&"1".equals(listType)){
			return "sendAttachList";
		}else if("HT".equals(listType)){
			return "attachListHT";
		}else if("2".equals(listType)){
			return "sendReceiverAttachList";
		}else if("normative".equals(listType)){
			return "normative";
		}

		return "attachList";
	}
	

	@SuppressWarnings("rawtypes")
	@Action(value="/loadVersionFileList",results={@Result(name="versionFileList",location="/attach/versionFileList.jsp")})
	public String loadVersionFileList(){
		String groupId = StringUtil.getNotNullValueString(request.getParameter("groupId"));
		String fileId = StringUtil.getNotNullValueString(request.getParameter("fileId"));
		//System.out.println("loadVersionFileList groupId===="+groupId);
		//System.out.println("loadVersionFileList fileId===="+fileId);
		List fileList = this.fjshService.findVersionFilesByGroupId(groupId, fileId);
		request.setAttribute("fileList", fileList);
		return "versionFileList";
	}
	
	@Action(value="/deleteFiles")
	public String deleteFiles(){
		//System.out.println("==========FjshAction deleteFiles==================");
		request = ServletActionContext.getRequest();
		this.procParams(request);
		String[] deleteData = (String[]) request.getParameterValues("deleteData");
		if (deleteData != null) {
			JSONArray array = JSONArray.fromObject("[" + deleteData[0] + "]");
			for (int i = 0; i < array.size(); i++) {
				Object obj = array.get(i);
				try{
					Integer id = (Integer) PropertyUtils.getProperty(obj, "id");
					this.fjshService.deleteFileById(String.valueOf(id));
				}catch(Exception e){
					e.printStackTrace();
					createJSonData("{\"success\":false}");
				}
			}
			createJSonData("{\"success\":true}");
		}else {
			createJSonData("{\"success\":false}");
		}
		return AJAX;
	}
	
	@Action(value="/deleteFilesJsp",results={@Result(name="delFileRes",location="/attach/upFileRes.jsp")})
	public String deleteFilesJsp(){
		//System.out.println("==========FjshAction deleteFiles==================");
		String attachMemo = StringUtil.getNotNullValueString(request.getParameter("attachMemo1"));
		request = ServletActionContext.getRequest();
		this.procParams(request);
		String[] deleteData = request.getParameterValues("deleteData");
		if(deleteData==null){
			String delId = request.getParameter("deleteData");
			if(!StringUtil.isNull(delId)){
				deleteData = new String[1];
				deleteData[0] = delId;
			}
		}
		if (deleteData != null) {
			for (int i = 0; i < deleteData.length; i++) {
				try{
					this.fjshService.deleteFileById(deleteData[i]);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		request.setAttribute("attachMemo1", attachMemo);
		return "delFileRes";
	}

	@Action(value="/upFile")
	public String upFile() {
		//System.out.println("==========FjshAction upFile==================");
		request = ServletActionContext.getRequest();
		this.procParams(request);
		MultiPartRequestWrapper multi = (MultiPartRequestWrapper) request;
		
		String fileGroupId = StringUtil.getNotNullValueString(request.getParameter("fileGroupId"));
		String fileGroupName = StringUtil.getNotNullValueString(request.getParameter("fileGroupName"));
		String uploader = StringUtil.getNotNullValueString(request.getSession().getAttribute("user_name"));
		String uploaderLoginName = StringUtil.getNotNullValueString(request.getSession().getAttribute("login_name"));
		String memo = StringUtil.getNotNullValueString(request.getParameter("attachMemo"));
		//System.out.println("fileGroupId========"+fileGroupId+"|fileGroupName==="+fileGroupName+"|uploader==="+uploader);
		
		//String uploader = "attila";
		String uploadTime = DateUtil.getNowTime();
		String newGroupId = "";
		if (multi.getFiles(fileGroupName) != null&& multi.getFiles(fileGroupName).length > 0) {
			if(StringUtil.isNull(fileGroupId)||fileGroupId.trim().equalsIgnoreCase("undefined")){	//如果取不到文件组编号，说明是首次上传
				System.out.println("fileGroupId is null");
				newGroupId = this.fjshService.uploadNewFiles(null,multi.getFiles(fileGroupName),multi.getFileNames(fileGroupName),uploader,uploaderLoginName,uploadTime,memo);
			}else{	//上传文件至原有目录，并且同名覆盖
				System.out.println("fileGroupId is not null");
				this.fjshService.uploadOverrideFiles(fileGroupId,multi.getFiles(fileGroupName),multi.getFileNames(fileGroupName),uploader,uploaderLoginName,uploadTime,memo);
				newGroupId = fileGroupId;
			}
		}else{
			//System.out.println("===no file======");
		}
		//System.out.println("newGroupId========="+newGroupId);
		try {
			response.getWriter().print("{success:true, msg:true,newGroupId:'"+newGroupId+"'}");// Possible!
			//createJSonData("{\"success\":true,\"msg\":true,\"newGroupId\":" + newGroupId + "}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AJAX;
	}
	
//	@Action(value="/upFileJsp",
//			results={
//			@Result(name = "upFileRes", location="/attach/upFileRes.jsp",
//					params={}),
//			@Result(name = "input", location="/attach/fileUpload.jsp")
//			}
//			)
	@Action(value="/upFileJsp")
	public String upFileJsp(){
		//System.out.println("===================upFileJsp===========================");
		
		//HttpServletResponse response = response;
		request = ServletActionContext.getRequest();
		this.procParams(request);
		MultiPartRequestWrapper multi = (MultiPartRequestWrapper) request;
		
		String fileGroupId = StringUtil.getNotNullValueString(request.getParameter("fileGroupId"));
		String fileGroupName = StringUtil.getNotNullValueString(request.getParameter("fileGroupName"));
		String uploader = StringUtil.getNotNullValueString(request.getSession().getAttribute("user_name"));
		String uploaderLoginName = StringUtil.getNotNullValueString(request.getSession().getAttribute("login_name"));
		
		String memo = StringUtil.getNotNullValueString(request.getParameter("attachMemo"));
		String attachMemo1 = StringUtil.getNotNullValueString(request.getParameter("attachMemo1"));
		
		if(multi.getFiles(fileGroupName) != null && multi.getFiles(fileGroupName).length > 0){
			File src = multi.getFiles(fileGroupName)[0];
			long size = src.length();
			if(size > fileSize){
				request.setAttribute("error", "1");
				return "upFileRes";
			}
		}

		//String attachMemo = request.getParameter("attachMemo");
//System.out.println("fileGroupId========"+fileGroupId+"|fileGroupName==="+fileGroupName+"|uploader==="+uploader);
//System.out.println("attachMemo1:" + attachMemo1);
		//String uploader = "attila";
		
		String uploadTime = DateUtil.getNowTime();
		String newGroupId = "";
		if (multi.getFiles(fileGroupName) != null&& multi.getFiles(fileGroupName).length > 0) {
			if(StringUtil.isNull(fileGroupId)||fileGroupId.trim().equalsIgnoreCase("undefined")){	//如果取不到文件组编号，说明是首次上传
				newGroupId = this.fjshService.uploadNewFiles(null,multi.getFiles(fileGroupName),multi.getFileNames(fileGroupName),uploader,uploaderLoginName,uploadTime,memo);
			}else{	//上传文件至原有目录，并且同名覆盖
				//System.out.println("fileGroupId is not null");
				this.fjshService.uploadOverrideFiles(fileGroupId,multi.getFiles(fileGroupName),multi.getFileNames(fileGroupName),uploader,uploaderLoginName,uploadTime,memo);
				newGroupId = fileGroupId;
			}
		}else{
			//System.out.println("===no file======");
		}
		setAttachMemo1(attachMemo1);
		
		//String error = (String)request.getAttribute("error");

		String fileGroup = (String)request.getParameter("fileGroup");
		if(StringUtil.isNull(fileGroup)){
			fileGroup = (String)request.getAttribute("fileGroup");
		}
		fileGroupId = (String)request.getParameter("fileGroupId");
		
		if(StringUtil.isNull(fileGroupId)){
			fileGroupId = (String)request.getAttribute("fileGroupId");
			if(StringUtil.isNull(fileGroupId)){
				fileGroupId = newGroupId;
			}
		}
		fileGroupName = (String)request.getParameter("fileGroupName");
		if(StringUtil.isNull(fileGroupName)){
			fileGroupName = (String)request.getAttribute("fileGroupName");
		}
		String procType = (String)request.getParameter("procType");
		if(StringUtil.isNull(procType)){
			procType = (String)request.getAttribute("procType");
		}
		String targetType = (String)request.getParameter("targetType");
		if(StringUtil.isNull(targetType)){
			targetType = (String)request.getAttribute("targetType");
		}
		String attachMemo = attachMemo1;
		if(StringUtil.isNull(attachMemo)){
			attachMemo = (String)request.getAttribute("attachMemo");
		}
		
		String approve = (String)request.getParameter("approve");
		if(StringUtil.isNull(approve)){
			approve = (String)request.getAttribute("approve");
		}
		String fileCntObjId = request.getParameter("fileCntObjId");
		
		createJSonData(
				"{" +
				"\"fileGroup\":\""+fileGroup+"\"," +
				"\"fileGroupName\":\""+fileGroupName+"\"," +
				"\"fileGroupId\":\""+fileGroupId+"\"," +
				"\"newGroupId\":\""+newGroupId+"\"," +
				"\"procType\":\""+procType+"\"," +
				"\"targetType\":\""+targetType+"\"," +
				"\"fileCntObjId\":\""+fileCntObjId+"\"," +
				"\"attachMemo\":\""+attachMemo+"\"," +
				"\"approve\":\""+approve+"\""+
						"}");
		
		return null;
	}
	
	public String copyLocalFiles(){
		String type = request.getParameter("type");
		Boolean isNewestVerson = null;
		if(type != null && "1".equals(type)){
			isNewestVerson = true;
		}else{
			isNewestVerson = false;
		}
		String fileGroupId = StringUtil.getNotNullValueString(request.getParameter("fileGroupId"));
		String uploader = StringUtil.getNotNullValueString(request.getSession().getAttribute("user_name"));
		String uploaderLoginName = StringUtil.getNotNullValueString(request.getSession().getAttribute("login_name"));
		String filegroupId = fjshService.copyLocalFiles(fileGroupId, isNewestVerson, uploaderLoginName, uploader);
		request.setAttribute("filegroupId", filegroupId);
		return FjshAction.SUCCESS;
	}
	
	private void procParams(HttpServletRequest request){
		String procType = StringUtil.getNotNullValueString(request.getParameter("procType"));
		if(!StringUtil.isNull(procType)){
			request.setAttribute("procType", procType);
		}
		//System.out.println("fj procParams procType===="+procType);
		String targetType = StringUtil.getNotNullValueString(request.getParameter("targetType"));
		if(!StringUtil.isNull(targetType)){
			request.setAttribute("targetType", targetType);
		}
		//System.out.println("fj procParams targetType===="+targetType);
	}
	
	/*
	public String loadVersionFileListAjax(){
		String groupId = StringUtil.getNotNullValueString(request.getParameter("groupId"));
		String fileId = StringUtil.getNotNullValueString(request.getParameter("fileId"));
		List fileList = this.fjshService.findVersionFilesByGroupId(groupId, fileId);
//createJSonData("{\"success\":true, \"results\": " + VOUtils.getJsonDataFromCollection(fileList) + "}");
		return AJAX;
	}
	*/
	
	/*
	public String deleteFile(){
		//System.out.println("==========FjshAction deleteFiles==================");
		this.procParams(request);
		String[] deleteData = (String[]) request.getParameterValues("deleteData");
		if(deleteData==null){
			String delId = StringUtil.getNotNullValueString(request.getParameter("deleteData"));
			if(!StringUtil.isNull(delId)){
				deleteData = new String[1];
				deleteData[0] = delId;
			}
		}
		if (deleteData != null) {
			for (int i = 0; i < deleteData.length; i++) {
				try{
					this.fjshService.deleteFileById(deleteData[i]);
					createJSonData("{\"success\":true}");
				}catch(Exception e){
					e.printStackTrace();
					createJSonData("{\"success\":false}");
				}
			}
		}
		return AJAX;
	}
	*/
	
	private void createJSonData(String json){
		Writer w = null;
		try{
			w = response.getWriter();
			w.write(json);
		}catch(Exception e){
			
		}finally{
			try {
				w.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

	public String getAttachMemo1() {
		return attachMemo1;
	}

	public void setAttachMemo1(String attachMemo1) {
		this.attachMemo1 = attachMemo1;
	}
	
	public FjshService getFjshService() {
		return fjshService;
	}
	
	@Autowired(required=false)
	public void setFjshService(@Qualifier("fjshService")FjshService fjshService) {
		this.fjshService = fjshService;
	}

}
