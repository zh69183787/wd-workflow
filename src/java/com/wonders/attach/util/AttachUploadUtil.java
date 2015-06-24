package com.wonders.attach.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class AttachUploadUtil {
	public static void saveFile(String destFilePathStr,String destFileName){
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
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static long uploadFile(String offset,String destFileName,BufferedInputStream bis) 
	throws IOException{
		File uploadFile = new File(destFileName);
		int len = 0;
		byte[] bt = new byte[1024];
		RandomAccessFile raFile = new RandomAccessFile(uploadFile.getAbsolutePath(), "rw");
		raFile.seek(uploadFile.length());
	    while ((len = bis.read(bt)) > 0){
	    	raFile.write(bt, 0, len);
    	}
	    long l = raFile.length();
	    try {
		     if(bis != null)
		    	 bis.close();
		     if (raFile != null)
		    	 raFile.close();
		     
		} catch (IOException e) {
			l = 0;
		    e.printStackTrace();
		}
		return l ;
	}
	
	public static long getFileSize(String destFileName){
		File uploadFile = new File(destFileName);
		return uploadFile.length();
		
	}
}
