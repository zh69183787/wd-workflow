package com.wonders.util;

import java.io.InputStream;
import java.util.Properties;


public class PWSProperties {
	public static final String FILE_PROPERTIES_NAME = "pws.properties";

	private static Properties p = null;
	
	public synchronized static void initP() throws Exception {
		if (p == null) {
			p = new Properties();
			
			InputStream inputstream =  PWSProperties.class
			.getClassLoader().getResourceAsStream(PWSProperties.FILE_PROPERTIES_NAME);
			if (inputstream == null) {
				throw new Exception("inputstream "+PWSProperties.FILE_PROPERTIES_NAME+" open null");
			}
			p.load(inputstream);
			inputstream.close();
			inputstream = null;
		}
	}
	
	public static String getValueByKey(String key) {
		String result = "";
		try {
			initP();
			result = p.getProperty(key);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static void main(String[] s){
		System.out.println(PWSProperties.getValueByKey("ws_split_chars"));
	}
}
