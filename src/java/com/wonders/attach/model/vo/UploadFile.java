package com.wonders.attach.model.vo;

import java.io.File;

import org.springframework.stereotype.Repository;

import com.wonders.attach.model.bo.AttachFile;
import com.wonders.attach.util.AttachUtil;
import com.wonders.attach.util.FileUpProperties;
import com.wonders.util.StringUtil;


@Repository("receive-uploadFile")
public class UploadFile {
	
	private AttachFile attachFile;
	
	private File uploadFile;
	
	/**
	 * 文件全名
	 */
	//private String fileAllName;
	
	public UploadFile(){
		this.attachFile = new AttachFile();
		this.attachFile.setAppName(AttachUtil.APP_MODEL_NAME);
	}
	
	public UploadFile(File uploadFile){
		AttachUtil attachInstance = AttachUtil.getInstance();
		this.uploadFile = uploadFile;
		this.attachFile = new AttachFile();
		this.setFileAllName(uploadFile.getName());
		this.setFileSize(uploadFile.length());
		this.attachFile.setSaveFileName(attachInstance.getFileCode()+"."+AttachUtil.UPLOAD_FILE_EXT_NAME);
		this.attachFile.setAppName(AttachUtil.APP_MODEL_NAME);
	}
	
	public UploadFile(AttachFile attachFile){
		this.attachFile = attachFile;
	}
	
	
	public UploadFile(File uploadFile,AttachFile attachFile){
		AttachUtil attachInstance = AttachUtil.getInstance();
		this.uploadFile = uploadFile;
		AttachFile af  = new AttachFile();
		//this.setFileAllName(uploadFile.getName());
		this.attachFile = af;
		this.attachFile.setFileName(attachFile.getFileName());
		this.attachFile.setFileExtName(attachFile.getFileExtName());
		this.setFileSize(uploadFile.length());
		this.attachFile.setSaveFileName(attachInstance.getFileCode()+"."+AttachUtil.UPLOAD_FILE_EXT_NAME);
		this.attachFile.setAppName(AttachUtil.APP_MODEL_NAME);
		this.attachFile.setMemo(af.getMemo());
	}
	//private String downloadUrl;

	public String getFileExtName() {
		return this.attachFile.getFileExtName();
	}

	public void setFileExtName(String fileExtName) {
		this.attachFile.setFileExtName(fileExtName);
	}

	public String getFileName() {
		return this.attachFile.getFileName();
	}

	public void setFileName(String fileName) {
		this.attachFile.setFileName(fileName);
	}

	public long getFileSize() {
		return this.attachFile.getFileSize();
	}

	public void setFileSize(long fileSize) {
		this.attachFile.setFileSize(fileSize);
	}

	public String getGroupId() {
		return this.attachFile.getGroupId();
	}

	public void setGroupId(String groupId) {
		this.attachFile.setGroupId(groupId);
	}

	public long getId() {
		return this.attachFile.getId();
	}

	public void setId(long id) {
		this.attachFile.setId(id);
	}

	public String getPath() {
		return this.attachFile.getPath();
	}

	public void setPath(String path) {
		this.attachFile.setPath(path);
	}

	public String getUploadDate() {
		return this.attachFile.getUploadDate();
	}

	public void setUploadDate(String uploadDate) {
		this.attachFile.setUploadDate(uploadDate);
	}

	public String getUploader() {
		return this.attachFile.getUploader();
	}

	public void setUploader(String uploader) {
		this.attachFile.setUploader(uploader);
	}
	
	public String getUploaderLoginName() {
		return this.attachFile.getUploaderLoginName();
	}

	public void setUploaderLoginName(String uploaderLoginName) {
		this.attachFile.setUploaderLoginName(uploaderLoginName);
	}

	public String getDownloadUrl() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("<a href=\"/"+FileUpProperties.getValueByKey("app_model_name")+"/downloadFile.action?fileId=")
				.append(this.attachFile.getId()).append("\" target=\"_blank\">下载</a>");
		return strBuf.toString();
	}

	public AttachFile getAttachFile() {
		return attachFile;
	}

	public String getFileAllName() {
		return this.attachFile.getFileName()+"."+this.attachFile.getFileExtName();
	}

	public void setFileAllName(String fileAllName) {
		int index = fileAllName.lastIndexOf('.');
		this.attachFile.setFileName(fileAllName.substring(0,index));
		this.attachFile.setFileExtName(fileAllName.substring(index+1,fileAllName.length()));
	}
	
	public String getSaveFileName() {
		return this.attachFile.getSaveFileName();
	}

	public void setSaveFileName(String saveFileName) {
		this.attachFile.setSaveFileName(saveFileName);
	}

	public File getUploadFile() {
		return uploadFile;
	}
	
	public String getVersion(){
		return this.attachFile.getVersion();
	}
	
	public String getStatusStr(){
		String r = "";
		if(this.attachFile!=null&&!StringUtil.isNull(this.attachFile.getStatus())){
			if(this.attachFile.getStatus().equals(AttachUtil.STATUS_UPLOAD)){
				r = "最新";
			}else if(this.attachFile.getStatus().equals(AttachUtil.STATUS_OVERWRITE)){
				r = "历史流程稿(此件已被修改)";
			}else if(this.attachFile.getStatus().equals(AttachUtil.STATUS_DELETE)){
				r = "已删除";
			}
		}
		return r;
	}

}
