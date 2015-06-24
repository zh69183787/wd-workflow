<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" import="java.io.*" %>
<%
/*
 * 不再使用，直接在action中输出

 */
	// 得到文件名字和路径 
	String filename = (String)request.getAttribute("fileName"); 
	String filepath = (String)request.getAttribute("path"); 
	String saveFileName = (String)request.getAttribute("saveFileName");
	
	// 设置响应头和下载保存的文件名 
	response.setContentType("APPLICATION/OCTET-STREAM"); 
	//filename = new String(filename.getBytes("GBK"),"ISO-8859-1");	//解决中文文件名的问题
	//System.out.println("filename2============"+filename);
	response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\""); 
	if(!filepath.endsWith("//")){
		filepath = filepath+"///";
	}
	//System.out.println("down file path=========="+(filepath + saveFileName));
	File sourceFile = new File(filepath + saveFileName);
	//System.out.println("---------file:"+sourceFile.toURL()+".exist="+sourceFile.exists());
	try {
	DataInputStream in = new DataInputStream(new FileInputStream(sourceFile));

	byte[] c = new byte[(int) sourceFile.length()];
	in.read(c);
	OutputStream outputStream = response.getOutputStream();
	outputStream.write(c);
	in.close();
	
	//此2句代码防止tomcat下抛错：java.lang.IllegalStateException: getOutputStream() has already been called for this response
	out.clear();
	out = pageContext.pushBody();
	
	outputStream.close();
	}catch(Exception e) {
		e.printStackTrace();
	}
	
%> 
