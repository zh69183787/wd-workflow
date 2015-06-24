package com.wonders.attach.service;

import java.io.File;
import java.util.List;

import com.wonders.attach.model.bo.AttachFile;
import com.wonders.attach.model.vo.UploadFile;


public interface FjshService {
	
	public String uploadNewFiles(String fileGroupCode,File[] files,String[] fileNames,String uploader,String uploaderLoginName,String uploadTime,String memo);
	
	public void uploadOverrideFiles(String groupId,File[] files,String[] fileNames,String uploader,String uploaderLoginName,String uploadTime,String memo);
	
	public int getFileNumByDate(String dateStr);
	

	@SuppressWarnings("rawtypes")
	public List findFilesByGroupId(String groupId);
	
	@SuppressWarnings(value = "rawtypes")
	public List loadFilesByGroupId(String groupId);
	
	public void deleteFileById(String id);
	
	public UploadFile loadFileById(String id);
	
	public String flushFileByGroupId(String groupId);
	
	public String getCurrentFileVersion(String groupId,String fileAllName);
	
	@SuppressWarnings({  "rawtypes" })
	public List findVersionFilesByGroupId(String groupId,String fileId); 
	
	String copyLocalFiles(String fileGroupId, Boolean isNewestVerson, String uploaderLoginName,String uploader);
	List<AttachFile> getLocalFiles(String fileGroupId, Boolean isNewestVerson);

	//移动接口第一次上传
	public AttachFile beginUpload(String fileName,String fileType,
			String fileSize,
			String uploader,
			String uploaderLoginName,
			String uploadTime,
			String fileGroupCode,
			String memo);
	
	//移动接口上传文件
	public void updateAttach(String uploader,
			String uploaderLoginName,
			AttachFile bo,
			long l);
	
	//移动接口确认上传文件
	public String commitAttach(String uploader,
			String uploaderLoginName,
			AttachFile bo);
	
	
	//http附件上传
	public String uploadHttpFiles(List<AttachFile> attachList);
}
