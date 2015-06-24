package com.wonders.util;

import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {

	private static Properties p = null;

	public synchronized static void initP(String propertyName) throws Exception {
		if (p == null) {
			p = new Properties();

			InputStream inputstream = PropertyUtil.class.getClassLoader()
					.getResourceAsStream(propertyName);
			if (inputstream == null) {
				throw new Exception("inputstream " + propertyName
						+ " open null");
			}
			p.load(inputstream);
			inputstream.close();
			inputstream = null;
		}
	}

	public static String getValueByKey(String propertyName, String key) {
		String result = "";
		try {
			initP(propertyName);
			result = p.getProperty(key);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] s) {
		// System.out.println(PWSProperties.getValueByKey("ws_split_chars"));
	}
}