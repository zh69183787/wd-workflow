package com.wonders.attach.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.wonders.attach.dao.FjshDao;
import com.wonders.attach.model.bo.AttachFile;
import com.wonders.attach.model.vo.UploadFile;
import com.wonders.attach.service.FjshService;
import com.wonders.attach.util.AttachUploadUtil;
import com.wonders.attach.util.AttachUtil;
import com.wonders.util.DateUtil;
import com.wonders.util.FileUtil;
import com.wonders.util.StringUtil;



@Repository("fjshService")
@Scope("prototype")
public class FjshServiceImpl implements FjshService{

	private FjshDao fjshDao;

	/**
	 * 取得当天文件总数
	 */
	public int getFileNumByDate(String dateStr){
		return this.fjshDao.getFileNumByDate(dateStr);
	}
	

	@SuppressWarnings("rawtypes")
	public List findFilesByGroupId(String groupId){
		return this.fjshDao.findFilesByGroupId(groupId);
	}
	
	public UploadFile loadFileById(String id){
		UploadFile r = null;
		AttachFile bo = (AttachFile)this.fjshDao.getHibernateTemplate().get(AttachFile.class,new Long(id));
		if(bo!=null){
			r = new UploadFile(bo);
		}
		return r;
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List loadFilesByGroupId(String groupId){
		List list = new ArrayList();
		List boList = this.fjshDao.findFilesByGroupId(groupId);
		if(boList!=null&&boList.size()>0){
			for(int i=0;i<boList.size();i++){
				AttachFile attachFile = (AttachFile)boList.get(i);
				UploadFile file = new UploadFile(attachFile);
				list.add(file);
			}
		}
		return list;
	}
	
	/**
	 * 删除上传文件，包括数据库记录和物理文件
	 */

	@SuppressWarnings("rawtypes")
	public void deleteFileById(String id){
		AttachFile bo = (AttachFile)this.fjshDao.getHibernateTemplate().get(AttachFile.class,new Long(id));
		
		if (AttachUtil.STATUS_UPLOAD.equals(bo.getStatus())) {
			// 将最大版本号的overwrite改为upload状态
			String hql = " from AttachFile attach where attach.fileName=? and attach.fileExtName=?" +
			" and attach.groupId=? and attach.status=?" +
			" order by to_number(attach.version) desc";
			
			List fileLst = this.fjshDao.findByHQL(hql,bo.getFileName(),bo.getFileExtName(),bo.getGroupId(),AttachUtil.STATUS_OVERWRITE);
			if (fileLst != null && fileLst.size() > 0) {
				AttachFile afBean = (AttachFile) fileLst.get(0);
				this.fjshDao.versionLocalFileRestore(afBean.getPath(), afBean.getSaveFileName(), afBean.getVersion());
				afBean.setStatus(AttachUtil.STATUS_UPLOAD);
				this.fjshDao.getHibernateTemplate().update(afBean);
			}
		}

		if(bo!=null){
			//String saveFilePath = bo.getPath()+"//"+bo.getSaveFileName();
			//this.fjshDao.deleteLocalFile(saveFilePath);	//删除本地文件
			this.fjshDao.versionLocalFile(bo.getPath(), bo.getSaveFileName(), bo.getVersion());
		}

		//this.fjshDao.deleteById(new Long(id));
		bo.setStatus(AttachUtil.STATUS_DELETE);
		bo.setRemoved(1);
		this.fjshDao.getHibernateTemplate().update(bo);
	}
	
	/**
	 * 首次上传新文件
	 */
	public String uploadNewFiles(String fileGroupCode,File[] files,
								String[] fileNames,
								String uploader,
								String uploaderLoginName,
								String uploadTime,
								String memo) {
		AttachUtil ins = AttachUtil.getInstance();
		String currentDate = DateUtil.getCurrDate("yyyyMMdd");
		String filePathStr = AttachUtil.UPLOAD_FILE_SERVER_PATH+currentDate;
		File filePath = new File(filePathStr);
		if(!filePath.exists()){	//判断以日期为名的文件夹是否存在
			filePath.mkdirs();
		}
		if(fileGroupCode==null
			||fileGroupCode.trim().equals("")
			||fileGroupCode.trim().equalsIgnoreCase("null")
			||fileGroupCode.trim().equalsIgnoreCase("undefined")){
			fileGroupCode = ins.getFileGroupCode();
		}
		filePathStr = filePathStr+"//"+fileGroupCode+"//";
		for(int i=0;i<files.length;i++){
			File file = (File)files[i];
			//System.out.println("len==="+file.length());
			UploadFile uploadFile = new UploadFile(file);
			uploadFile.setUploader(uploader);
			uploadFile.setUploaderLoginName(uploaderLoginName);
			uploadFile.setUploadDate(uploadTime);
			uploadFile.setGroupId(fileGroupCode);
			uploadFile.setPath(filePathStr);
			uploadFile.getAttachFile().setMemo(memo);
			if(fileNames!=null&&fileNames.length>=(i+1)){
				uploadFile.setFileAllName(fileNames[i]);
			}
			uploadFile.getAttachFile().setStatus(AttachUtil.STATUS_UPLOAD);
			int version = 1;
			String versionStr = this.getCurrentFileVersion(fileGroupCode, uploadFile.getFileAllName());
			if(!StringUtil.isNull(versionStr)){
				version = Integer.parseInt(versionStr)+1;
			}
			uploadFile.getAttachFile().setVersion(String.valueOf(version));
			this.fjshDao.saveFileToLocal(uploadFile.getUploadFile(),uploadFile.getPath(),uploadFile.getSaveFileName());	//保存文件至物理路径
			this.fjshDao.getHibernateTemplate().save(uploadFile.getAttachFile());	//文件信息保存至数据库
		}
		return fileGroupCode;
	}
	
	/**
	 * 上传并覆盖原有文件
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void uploadOverrideFiles(String groupId,
									File[] files,
									String[] fileNames,
									String uploader,
									String uploaderLoginName,
									String uploadTime,
									String memo) {
		List boList = this.fjshDao.findFilesByGroupId(groupId);	//取出数据库中的文件记录
		if(boList==null||boList.size()==0){	//数据库中没有记录，情况同首次上传
			this.uploadNewFiles(groupId,files, fileNames, uploader,uploaderLoginName, uploadTime,memo);
		}else{
			boolean flag = true;
			Map boMap = new HashMap();
			String filePathStr = "";
			for(int i=0;i<boList.size();i++){
				AttachFile bo = (AttachFile)boList.get(i);
				filePathStr = bo.getPath();
				String key = bo.getFileName()+"."+bo.getFileExtName();
				boMap.put(key,bo);
				if(flag){
					if(filePathStr !=null && filePathStr.indexOf("http")>=0){
						flag = false;
					}
				}
			}
			
			if(!flag){
				AttachUtil ins = AttachUtil.getInstance();
				String currentDate = DateUtil.getCurrDate("yyyyMMdd");
				String newfilepath = AttachUtil.UPLOAD_FILE_SERVER_PATH+currentDate;
				File filePath = new File(newfilepath);
				if(!filePath.exists()){	//判断以日期为名的文件夹是否存在
					filePath.mkdirs();
				}
				filePathStr = newfilepath+"//"+ins.getFileGroupCode()+"//";
				//System.out.println("zs+++++++++++++"+filePathStr);
			}
			
			for(int i=0;i<files.length;i++){
				File file = (File)files[i];
				//System.out.println("len==="+file.length());
				UploadFile uploadFile = new UploadFile(file);
				uploadFile.setUploader(uploader);
				uploadFile.setUploaderLoginName(uploaderLoginName);
				uploadFile.setUploadDate(uploadTime);
				uploadFile.setGroupId(groupId);
				uploadFile.setPath(filePathStr);
				uploadFile.getAttachFile().setMemo(memo);
				if(fileNames!=null&&fileNames.length>=(i+1)){
					uploadFile.setFileAllName(fileNames[i]);
				}
				int version = 1;
				if(boMap.get(uploadFile.getFileAllName())!=null){	//如果已有文件，因为是覆盖，所以先删除
					AttachFile bo = (AttachFile)boMap.get(uploadFile.getFileAllName());
					//String saveFilePath = bo.getPath()+"//"+bo.getSaveFileName();
					if(!StringUtil.isNull(bo.getVersion())){
						version = Integer.parseInt(bo.getVersion());
					}
					bo.setStatus(AttachUtil.STATUS_OVERWRITE);
					this.fjshDao.versionLocalFile(bo.getPath(), bo.getSaveFileName(), String.valueOf(version));
					version = Integer.parseInt(bo.getVersion())+1;
					this.fjshDao.getHibernateTemplate().update(bo);
					//this.fjshDao.deleteLocalFile(saveFilePath);	//删除本地文件
					//this.fjshDao.delete(bo);	//删除数据库记录
				}
				//if(version==1){	//表示首次上传
					String versionStr = this.getCurrentFileVersion(groupId, uploadFile.getFileAllName());
					if(!StringUtil.isNull(versionStr)){
						version = Integer.parseInt(versionStr)+1;
					}
				//}
				uploadFile.getAttachFile().setVersion(String.valueOf(version));
				uploadFile.getAttachFile().setStatus(AttachUtil.STATUS_UPLOAD);
				this.fjshDao.saveFileToLocal(uploadFile.getUploadFile(),uploadFile.getPath(),uploadFile.getSaveFileName());	//保存文件至物理路径
				this.fjshDao.getHibernateTemplate().save(uploadFile.getAttachFile());	//文件信息保存至数据库
			}
		}
	}
	

	@SuppressWarnings("rawtypes")
	public String flushFileByGroupId(String groupId){
		String r = groupId;
		if(!StringUtil.isNull(groupId)&&groupId.endsWith("-temp")){
			List boList = this.fjshDao.findFilesByGroupId(groupId);	//取出数据库中的文件记录
			if(boList!=null&&boList.size()>0){
				String srcDirStr = "";
				String destDirStr = "";
				for(int i=0;i<boList.size();i++){
					AttachFile bo = (AttachFile)boList.get(i);
					String path = bo.getPath();
					srcDirStr = path;
					if(path.indexOf("-temp")>=0){
						path = path.substring(0,path.indexOf("-temp"));
						destDirStr = path;
						bo.setPath(path);
					}
					String groupCode = bo.getGroupId();
					//System.out.println("groupCode1========"+groupCode);
					if(groupCode.indexOf("-temp")>=0){
						groupCode = groupCode.substring(0,groupCode.indexOf("-temp"));
						//System.out.println("groupCode2========"+groupCode);
						bo.setGroupId(groupCode);
					}
					this.fjshDao.getHibernateTemplate().update(bo);
				}
				FileUtil.moveDirFiles(srcDirStr, destDirStr, true);	//移动物理文件
			}
			r = groupId.substring(0,groupId.indexOf("-temp"));
		}
		return r;
	}
	
	public String getCurrentFileVersion(String groupId,String fileAllName){
		return this.fjshDao.getCurrentFileVersion(groupId, fileAllName);
	}
	

	@SuppressWarnings("rawtypes")
	public List findVersionFilesByGroupId(String groupId,String fileId){
		AttachFile bo = (AttachFile)this.fjshDao.getHibernateTemplate().get(AttachFile.class,new Long(fileId));
		return this.findVersionFilesByGroupId(groupId,bo.getFileName(),bo.getFileExtName());
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List findVersionFilesByGroupId(String groupId,String fileName,String fileExtName){
		List list = new ArrayList();
		String hql = "from AttachFile attach where attach.appName='"+AttachUtil.APP_MODEL_NAME+"' and attach.status!='"+AttachUtil.STATUS_UPLOAD+"' " +
				"and attach.groupId='"+groupId+"' and attach.fileName='"+fileName+"' and attach.fileExtName='"+fileExtName+"' and attach.removed!=1";
		List boList = this.fjshDao.findFilesByHql(hql," order by attach.version desc ");
		if(boList!=null&&boList.size()>0){
			for(int i=0;i<boList.size();i++){
				AttachFile attachFile = (AttachFile)boList.get(i);
				UploadFile file = new UploadFile(attachFile);
				list.add(file);
			}
		}
		return list;
	}
	
	//fileGroupId为文件夹名称 isNewestVerson是否需要最新版本

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<AttachFile> getLocalFiles(String fileGroupId, Boolean isNewestVerson){
		if(StringUtil.isNull(fileGroupId))
			return null;
		String hql ="";
		if(isNewestVerson != null && isNewestVerson){
			List<AttachFile> lists = new ArrayList();
			List listTmp = this.loadFilesByGroupId(fileGroupId);
			UploadFile uploadFile = null;
			if(listTmp != null && !listTmp.isEmpty()){
				Iterator iter = listTmp.iterator();
				while(iter.hasNext()){
					uploadFile = (UploadFile)iter.next();
					lists.add(uploadFile.getAttachFile());
				}
			}
			return lists;
		}else{
			hql = " from AttachFile t where t.groupId=? and t.removed=0";
			return fjshDao.findByHQL(hql, fileGroupId);
		}
	}
	
	//转移文件至另一个文件夹
	public String copyLocalFiles(String fileGroupId, Boolean isNewestVerson, String uploaderLoginName,String uploader){
		AttachUtil ins = AttachUtil.getInstance();
		String uploadTime = DateUtil.getNowTime();//系统当前时间
		List<AttachFile> list = getLocalFiles(fileGroupId, isNewestVerson);//获取原始文件信息
		String fileGroupCode = ins.getFileGroupCode();//生成新的filegroupId
		if(list != null && !list.isEmpty()){
			String currentDate = DateUtil.getCurrDate("yyyyMMdd");
			String filePathStr = AttachUtil.UPLOAD_FILE_SERVER_PATH+currentDate;
			filePathStr = filePathStr+"//"+fileGroupCode+"//";//服务器文件路径
			File filePath = new File(filePathStr);
			if(!filePath.exists()){	//判断以日期为名的文件夹是否存在
				filePath.mkdirs();
			}
			UploadFile uploadFile = null;
			String fileWholePath = null;
			for(AttachFile af : list){
				if(AttachUtil.STATUS_DELETE.equals(af.getStatus())){//如果文件为delete状态则继续循环
					continue;
				}else if(AttachUtil.STATUS_OVERWRITE.equals(af.getStatus())){//如果文件为overwrite属性则文件名称修改为“文件名称_v1.dat”
					fileWholePath = af.getPath()+"//"+af.getSaveFileName();
					fileWholePath = fileWholePath.replace(".dat","_v"+af.getVersion()+".dat");
				}else{
					fileWholePath = af.getPath()+"//"+af.getSaveFileName();
				}
				uploadFile = new UploadFile(new File(fileWholePath),af);
				uploadFile.setUploader(uploader);
				uploadFile.setUploaderLoginName(uploaderLoginName);
				uploadFile.setUploadDate(uploadTime);
				uploadFile.setGroupId(fileGroupCode);
				uploadFile.setPath(filePathStr);
				uploadFile.getAttachFile().setMemo(af.getMemo());
				//uploadFile.getAttachFile().setMemo(memo);
				if(isNewestVerson != null && isNewestVerson){
					uploadFile.getAttachFile().setVersion("1");
					uploadFile.getAttachFile().setStatus(AttachUtil.STATUS_UPLOAD);
				}else{
					uploadFile.getAttachFile().setVersion(af.getVersion());
					uploadFile.getAttachFile().setStatus(af.getStatus());
				}
				
				this.fjshDao.saveFileToLocal(uploadFile.getUploadFile(),uploadFile.getPath(),uploadFile.getSaveFileName());	//保存文件至物理路径

				this.fjshDao.getHibernateTemplate().save(uploadFile.getAttachFile());	//文件信息保存至数据库
			}
		}
		return fileGroupCode;
	}
	
	
	/**
	 * 首次创建文件  移动平台接口 beginUpload

	 */
	public AttachFile beginUpload(String fileName,String fileType,
								String fileSize,
								String uploader,
								String uploaderLoginName,
								String uploadTime,
								String fileGroupCode,
								String memo) {
		AttachUtil ins = AttachUtil.getInstance();
		String currentDate = DateUtil.getCurrDate("yyyyMMdd");
		String filePathStr = AttachUtil.UPLOAD_FILE_SERVER_PATH+currentDate;
		File filePath = new File(filePathStr);
		if(!filePath.exists()){	//判断以日期为名的文件夹是否存在
			filePath.mkdirs();
		}
		if(fileGroupCode==null
			||fileGroupCode.trim().equals("")
			||fileGroupCode.trim().equalsIgnoreCase("null")
			||fileGroupCode.trim().equalsIgnoreCase("undefined")){
			fileGroupCode = ins.getFileGroupCode();
		}
			filePathStr = filePathStr+"//"+fileGroupCode+"//";
			AttachUtil attachInstance = AttachUtil.getInstance();
			AttachFile bo = new AttachFile();
			bo.setGroupId(fileGroupCode);
			bo.setPath(filePathStr);
			bo.setFileName(fileName);
			bo.setFileExtName(fileType);
			bo.setFileSize(0);
			bo.setRemoved(1);
			bo.setUploadDate(uploadTime);
			bo.setUploader(uploader);
			bo.setUploaderLoginName(uploaderLoginName);
			bo.setAppName(AttachUtil.APP_MODEL_NAME);
			bo.setSaveFileName(attachInstance.getFileCode()+"."+AttachUtil.UPLOAD_FILE_EXT_NAME);
			bo.setStatus("upload");
			bo.setMemo(memo);
			bo.setVersion("1");
			AttachUploadUtil.saveFile(filePathStr,bo.getSaveFileName());	//保存文件至物理路径
			this.fjshDao.getHibernateTemplate().save(bo);	//文件信息保存至数据库
		return bo;
	}
	
	public void updateAttach(String uploader,String uploaderLoginName,AttachFile bo,long l){
		bo.setFileSize(l);
		bo.setUploader(uploader);
		bo.setUploaderLoginName(uploaderLoginName);
		bo.setUploadDate(DateUtil.getNowTime());
		this.fjshDao.getHibernateTemplate().update(bo);
	}
	
	//移动接口确认上传文件
	public String commitAttach(String uploader,String uploaderLoginName,AttachFile bo){
		bo.setRemoved(0);
		bo.setUploader(uploader);
		bo.setUploaderLoginName(uploaderLoginName);
		bo.setUploadDate(DateUtil.getNowTime());
		this.fjshDao.getHibernateTemplate().update(bo);
		return bo.getFileSize()+"";
	}
	
	
	
	
	//上传 http 地址的附件 
	public String uploadHttpFiles(List<AttachFile> attachList){

		AttachUtil ins = AttachUtil.getInstance();
		String fileGroupCode = ins.getFileGroupCode();
		if(attachList !=null && attachList.size() > 0){
			for(AttachFile bo : attachList){
				bo.setGroupId(fileGroupCode);
				bo.setAppName(AttachUtil.APP_MODEL_NAME);
			}
			try{
				this.fjshDao.getHibernateTemplate().saveOrUpdateAll(attachList);;	//文件信息保存至数据库
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return fileGroupCode;
	}	
	
	public FjshDao getFjshDao() {
		return fjshDao;
	}
	
	@Autowired(required=false)
	public void setFjshDao(@Qualifier("fjshDao")FjshDao fjshDao) {
		this.fjshDao = fjshDao;
	}
	
	/*
	public static void main(String[] s){
		try{
			//FileUtil.moveFile("E:\\test\\20080915\\2008-09-15-2\\2008-09-15-00007.dat","E:\\test\\20080915\\2008-09-15-2-temp");
			FileUtil.moveDirFiles("E:\\test\\20080915\\2008-09-15-2\\", "E:\\test\\20080915\\2008-09-15-2-temp",true);
			System.out.println("=========end");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	*/
		
}
