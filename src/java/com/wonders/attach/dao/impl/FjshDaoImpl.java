package com.wonders.attach.dao.impl;

import com.wonders.attach.dao.FjshDao;
import com.wonders.attach.model.bo.AttachFile;
import com.wonders.attach.util.AttachUtil;
import com.wonders.util.DbUtil;
import com.wonders.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Repository("fjshDao")
public class FjshDaoImpl implements FjshDao {
	
	private HibernateTemplate hibernateTemplate;
	private DbUtil dbUtil;
	
	@SuppressWarnings({ "rawtypes" })
	public List findByHQL(String hql, Object... params) {
		List list = new ArrayList();
		if(params!=null&&params.length>0){
			list = this.getHibernateTemplate().find(hql,params);
		}else{
			list = this.getHibernateTemplate().find(hql);
		}
		return list;
	}
	
	
	public String getCurrentFileVersion(String groupId,String fileAllName){
		String r = null;
		try{
			if(!StringUtil.isNull(fileAllName)&&fileAllName.indexOf(".")>=0){
				String fileName = fileAllName.substring(0,fileAllName.indexOf("."));
				String fileExtName = fileAllName.substring(fileAllName.indexOf(".")+1,fileAllName.length());
				
				String sql = "select max(to_number(t.version)) from t_attach t where t.appname=? and t.filename=? and t.fileextname=? and t.groupid=? and t.status != ?";
				
				int count = dbUtil.getJdbcTemplate().queryForInt(sql, new Object[]{AttachUtil.APP_MODEL_NAME,fileName,fileExtName,groupId,AttachUtil.STATUS_DELETE});
				r = String.valueOf(count);
				/*
				String hql = "select max(to_number(attach.version)) from AttachFile attach where " +
							"attach.appName= ? and attach.fileName= ? and attach.fileExtName= ? and attach.groupId= ?";
				
				List list = this.findByHQL(hql,AttachUtil.APP_MODEL_NAME,fileName,fileExtName,groupId);
				*/
				/*
				final String hql = "select max(attach.version) from AttachFile attach where attach.appName='"+AttachUtil.APP_MODEL_NAME+"' and attach.fileName='"+fileName+"' and attach.fileExtName='"+fileExtName+"' and attach.groupId='"+groupId+"'";	
				
				List list = this.hibernateTemplate.find(hql);
				
				if(list!=null&&list.size()>0){
					Object obj = list.get(0);
					r = String.valueOf(obj);
				}
				*/
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return r;
	}
	
	public void versionLocalFile(String filePathStr,String fileAllName,String version){
		File filePath = new File(filePathStr);
		if(filePath.isDirectory()&&filePath.exists()){
			File file = new File(filePathStr+"//"+fileAllName);
			if(file.exists()){
				String fileName = fileAllName.substring(0,fileAllName.indexOf("."));
				String fileExtName = fileAllName.substring(fileAllName.indexOf(".")+1,fileAllName.length());
				String newFileName = fileName;
				if(fileName.indexOf("_")>=0){
					newFileName = fileName.substring(0,fileName.indexOf("_"));
				}
				newFileName = newFileName+"_v"+version;
				File destFile = new File(filePathStr+"\\"+newFileName+"."+fileExtName);
				file.renameTo(destFile);
			}
		}
	}
	
	/*
	 * versionLocalFile的反向功能

	 */
	public void versionLocalFileRestore(String filePathStr,String fileAllName,String version){
		File filePath = new File(filePathStr);
		if(filePath.isDirectory() && filePath.exists()) {
			String fileName = fileAllName.substring(0,fileAllName.indexOf("."));
			String fileExtName = fileAllName.substring(fileAllName.indexOf(".")+1,fileAllName.length());
			if(fileName.indexOf("_") < 0){
				fileName = fileName + "_v" + version;
			}
			fileAllName = fileName + "." + fileExtName;
			File file = new File(filePathStr + "\\" + fileAllName);
			if(file.exists()){
				fileName = fileName.substring(0,fileName.indexOf("_"));
				File destFile = new File(filePathStr+"\\"+fileName+"."+fileExtName);
				file.renameTo(destFile);
			}
		}
	}

	public void saveFileToLocal(File sourceFile,String destFilePathStr,String destFileName){
		try {
			File destFilePath = new File(destFilePathStr);
			if(!destFilePath.exists()){
				destFilePath.mkdirs();
				destFilePath = null;
			}
			File destFile = new File(destFilePathStr+"//"+destFileName);
			if(!destFile.exists()){
				destFile.createNewFile();
			}
			DataInputStream in = new DataInputStream(new FileInputStream(sourceFile));
			DataOutputStream out = new DataOutputStream(new FileOutputStream(destFile));
			byte[] c = new byte[(int) sourceFile.length()];
			in.read(c);
			out.write(c);
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据文件名路径删除本地文件

	 */
	public void deleteLocalFile(String filePath){
		File file = new File(filePath);
		if(file.exists()){
			file.delete();
		}
	}
	
	/**
	 * 取得当天文件总数
	 */
	@SuppressWarnings("unchecked")
	public int getFileNumByDate(String dateStr){
		int count = 0;
		String hql = "select count(*) from AttachFile attach where substr(attach.uploadDate,1,10) = ?";
		
		try{
			List<Long> list = this.findByHQL(hql, dateStr);
	//System.out.println(list.size());
			if(list!=null&&list.size()>0){
	//System.out.println(list.get(0).getClass());
				count = list.get(0).intValue();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public List<AttachFile> findFilesByGroupId(String groupId){
//		String hql = "from AttachFile attach where " +
//				"attach.appName=? and attach.status=? and attach.groupId=? and attach.removed=0 order by attach.uploadDate desc";
//		return this.findByHQL(hql,AttachUtil.APP_MODEL_NAME,AttachUtil.STATUS_UPLOAD,groupId);

        String hql = "from AttachFile attach where " +
                " attach.status=? and attach.groupId=? and attach.removed=0 order by attach.uploadDate desc";
        return this.findByHQL(hql,AttachUtil.STATUS_UPLOAD,groupId);
	}
	
	@SuppressWarnings("rawtypes")
	public List findFilesByHql(String hql){
		return this.findByHQL(hql);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List findFilesByHql(String hql,String orderByHql){
		hql = hql+" "+orderByHql;
		return this.findFilesByHql(hql);
	}
	/*
	public static void main(String[] s){
		FjshDaoImpl dao = new FjshDaoImpl();
		dao.versionLocalFile("E://temp//", "2009-04-29-00004_v2.dat", "3");
	}
	*/
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	//注入hibernateTemplate
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public DbUtil getDbUtil() {
		return dbUtil;
	}
	
	@Autowired(required=false)
	public void setDbUtil(@Qualifier("dbUtil")DbUtil dbUtil) {
		this.dbUtil = dbUtil;
	}
}
