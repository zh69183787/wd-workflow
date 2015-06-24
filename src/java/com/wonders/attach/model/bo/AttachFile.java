package com.wonders.attach.model.bo;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "T_ATTACH")
@XmlRootElement(name="AttachFile")
@XmlAccessorType(XmlAccessType.FIELD)
public class AttachFile implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8741960093878300370L;
	private long id;
	private String fileName;
	private String fileExtName;
	private String path;
	private long fileSize;
	private String uploader;
	private String uploaderLoginName;
	private String uploadDate;
	private String groupId;
	private String appName;
	private String saveFileName;
	private String fileType;
	private String memo;
	private String version;
	private String status;
	private String groupName;
	private Long operateTime;
	private String operator;
	private int removed;
	
	public AttachFile(){
		super();
		this.operateTime = new Date().getTime();
	}
	
	/**
	 * @return the id
	 */
	
	@Id  
	@SequenceGenerator(name="t_seq_attach", sequenceName="seq_attach",allocationSize=1,initialValue=1) 
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="t_seq_attach")  
	@Column(name = "ID")
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the fileName
	 */
	
	@Column(name = "FILENAME", nullable = true, length = 200)
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the fileExtName
	 */
	@Column(name = "FILEEXTNAME", nullable = true, length = 10)
	public String getFileExtName() {
		return fileExtName;
	}
	/**
	 * @param fileExtName the fileExtName to set
	 */
	public void setFileExtName(String fileExtName) {
		this.fileExtName = fileExtName;
	}
	/**
	 * @return the path
	 */
	@Column(name = "PATH", nullable = true, length = 2000)
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * @return the fileSize
	 */
	@Column(name = "FILESIZE")
	public long getFileSize() {
		return fileSize;
	}
	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	/**
	 * @return the uploader
	 */
	@Column(name = "UPLOADER", nullable = true, length = 30)
	public String getUploader() {
		return uploader;
	}
	/**
	 * @param uploader the uploader to set
	 */
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	/**
	 * @return the uploaderLoginName
	 */
	@Column(name = "UPLOADER_LOGIN_NAME", nullable = true, length = 50)
	public String getUploaderLoginName() {
		return uploaderLoginName;
	}
	/**
	 * @param uploaderLoginName the uploaderLoginName to set
	 */
	public void setUploaderLoginName(String uploaderLoginName) {
		this.uploaderLoginName = uploaderLoginName;
	}
	/**
	 * @return the uploadDate
	 */
	@Column(name = "UPLOADDATE", nullable = true, length = 19)
	public String getUploadDate() {
		return uploadDate;
	}
	/**
	 * @param uploadDate the uploadDate to set
	 */
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	/**
	 * @return the groupId
	 */
	@Column(name = "GROUPID", nullable = true, length = 100)
	public String getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	/**
	 * @return the appName
	 */
	@Column(name = "APPNAME", nullable = true, length = 50)
	public String getAppName() {
		return appName;
	}
	/**
	 * @param appName the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}
	/**
	 * @return the saveFileName
	 */
	@Column(name = "SAVEFILENAME", nullable = true, length = 50)
	public String getSaveFileName() {
		return saveFileName;
	}
	/**
	 * @param saveFileName the saveFileName to set
	 */
	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}
	/**
	 * @return the fileType
	 */
	@Column(name = "FILETYPE", nullable = true, length = 20)
	public String getFileType() {
		return fileType;
	}
	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	/**
	 * @return the memo
	 */
	@Column(name = "MEMO", nullable = true, length = 200)
	public String getMemo() {
		return memo;
	}
	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	/**
	 * @return the version
	 */
	@Column(name = "VERSION", nullable = true, length = 10)
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * @return the status
	 */
	@Column(name = "STATUS", nullable = true, length = 20)
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the groupName
	 */
	@Column(name = "GROUPNAME", nullable = true, length = 50)
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * @return the operateTime
	 */
	@Column(name = "OPERATE_TIME")
	public Long getOperateTime() {
		return operateTime;
	}
	/**
	 * @param operateTime the operateTime to set
	 */
	public void setOperateTime(Long operateTime) {
		this.operateTime = operateTime;
	}
	/**
	 * @return the operator
	 */
	@Column(name = "OPERATOR", nullable = true, length = 200)
	public String getOperator() {
		return operator;
	}
	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	/**
	 * @return the removed
	 */
	@Column(name = "REMOVED")
	public int getRemoved() {
		return removed;
	}
	/**
	 * @param removed the removed to set
	 */
	public void setRemoved(int removed) {
		this.removed = removed;
	}
	
	

}
