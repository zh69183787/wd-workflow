package com.wonders.attach.util;

import java.io.InputStream;
import java.util.Properties;

public class FileUpProperties {
	
	public static final String FILE_PROPERTIES_NAME = "fileup.properties";

	private static Properties p = null;
	
	public synchronized static void initP() throws Exception {
		if (p == null) {
			p = new Properties();
			InputStream inputstream = FileUpProperties.class
					.getClassLoader().getResourceAsStream(FileUpProperties.FILE_PROPERTIES_NAME);
			if (inputstream == null) {
				throw new Exception("inputstream "+FileUpProperties.FILE_PROPERTIES_NAME+" open null");
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
}
