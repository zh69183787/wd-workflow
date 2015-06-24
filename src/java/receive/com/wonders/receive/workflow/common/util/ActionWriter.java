package com.wonders.receive.workflow.common.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ActionWriter {
	private HttpServletResponse response;
	private Gson gsonAnnotation = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	private Gson gson = new Gson();
	SimpleLogger log = new SimpleLogger(this.getClass());
	
	public ActionWriter(HttpServletResponse response){
		this.response = response;
	}
	
	/**JSON输出(ajax)
	 * @param response
	 * @param obj
	 */
	public void writeJsonWithAnnotation(Object obj){
		if(response==null) return;
		Writer w = null;
		
		try {
//log.debug("writeJson:start");
			response.setContentType("text/html");
			//response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			//response.setHeader("Charset","UTF-8'");
			w = response.getWriter();
			
			String ret = gsonAnnotation.toJson(obj);
			
//log.debug("json:"+ret);
			
			w.write(ret);
			
			w.flush();
//log.debug("writeJson:end");
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(w!=null){
				try {
					w.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void writeJson(Object obj){
		if(response==null) return;
		Writer w = null;
		
		try {
//log.debug("writeJson:start");
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			
			w = response.getWriter();
			String ret = gson.toJson(obj);
//log.debug("json:"+ret);
			
			w.write(ret);
			
			w.flush();
//log.debug("writeJson:end");
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(w!=null){
				try {
					w.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**字符串输出(ajax)
	 * @param response
	 * @param str
	 */
	public void writeAjax(String str){
		if(response==null) return;
		Writer w = null;
		
		try {
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			
			w = response.getWriter();
			
			w.write(str);
			
			w.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(w!=null){
				try {
					w.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void writeJpeg(BufferedInputStream bis){
		if(response==null) return;
		OutputStream output = null;
		
		try {
			response.setContentType("image/jpeg");
	        response.setHeader("Pragma","No-cache");
	        response.setHeader("Cache-Control","no-cache");
	        response.setDateHeader("Expires", 0);
			
	        output = response.getOutputStream();
	        byte[] bytes = new byte[100];
	        int len;
	        while ((len = bis.read(bytes)) > 0) {
	        	output.write(bytes, 0, len);
	        }
	        bis.close();
	        output.flush();
	        output.close();
	        
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(output!=null){
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
