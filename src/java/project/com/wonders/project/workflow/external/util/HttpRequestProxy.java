package com.wonders.project.workflow.external.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
/**
 * 
 */


/** 
 * @ClassName: Msg 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-6-7 上午9:56:56 
 *  
 */
public class HttpRequestProxy {
	

	/**调用portal外部接口方法
	 * @param method
	 * @param paramsXml
	 * @return
	 */
	public static String doPost(String urls,String param) {
		
		String result = "";
		//String msg  = "您好！机关工会为您办理了“上海市退休职工住院补充医疗互助保障”，费用由集团统筹。具体保障内容等有关说明已通过信函、快递等形式寄给您，请注意查收。另，值此端午佳节来临之际，祝您节目快乐，身体健康，阖家幸福！申通地铁集团机关工会";
		//String msg  = "周四在文三路马腾路东部软件园，创新大厦B座1F，进门左手边，交通物流公共信息平台。杭州东站下车的话出租车或者179路（到站：文三路口）。城站就打车吧。";
		try {
			
			//String urls = "http://211.136.163.68:8000/httpserver?enterpriseid=00323&accountid=666&pswd=4Y3j78z2&mobs="+mobile+"&msg="+msg;
			URL url = null;
			HttpURLConnection http = null;
			
			try {
				url = new URL(urls);
				http = (HttpURLConnection) url.openConnection();
				http.setDoInput(true);
				http.setDoOutput(true);
				http.setUseCaches(false);
				http.setConnectTimeout(50000);
				http.setReadTimeout(50000);
				http.setRequestMethod("POST");
				// http.setRequestProperty("Content-Type",
				// "text/xml; charset=UTF-8");
				http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				http.connect();
				  
				    OutputStreamWriter osw = new OutputStreamWriter(http.getOutputStream(), "utf-8");  
				    osw.write(param);
				    osw.flush();  
				    osw.close();  
				  
				    
				if (http.getResponseCode() == 200) {
					BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream(), "utf-8"));
					String inputLine;
					while ((inputLine = in.readLine()) != null) {
						result += inputLine;
					}
					in.close();
					//result = "["+result+"]";
				}
			} catch (Exception e) {
				System.out.println("err");
				e.printStackTrace();
			} finally {
				if (http != null) http.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public static String doPost(String urls,Map<String,String> paramMap){
		String param = prepareParam(paramMap);  
		return doPost(urls, param);
	}
		
    private static String prepareParam(Map<String,String> paramMap){  
        StringBuffer sb = new StringBuffer();  
        if(paramMap.isEmpty()){  
            return "" ;  
        }else{  
            for(String key: paramMap.keySet()){  
				try {
					String value = URLEncoder.encode(paramMap.get(key), "UTF-8");
	                if(sb.length()<1){  
	                    sb.append(key).append("=").append(value);  
	                }else{  
	                    sb.append("&").append(key).append("=").append(value);  
	                }  
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
            }  
            return sb.toString();  
        }  
    }  
	
	public static void main(String[] args) throws IOException{
		
        Map<String,String> map = new HashMap<String,String>();  
        map.put("projectAppNo","沪地铁投（2012）73号");
        map.put("pageSize","99999");
		//
		System.out.println(doPost("http://10.1.48.16:7001/AssetWeb/api/asset/assetRecords.action", map));
	}
}
