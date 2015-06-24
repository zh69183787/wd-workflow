package com.wonders.receive.workflow.external.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.wonders.receive.workflow.common.util.CommonUtil;
import com.wonders.receive.workflow.common.util.SimpleLogger;
import com.wonders.receive.workflow.external.constant.ExternalConstants;
import com.wonders.receive.workflow.external.model.AbstractExternalResult;
import com.wonders.receive.workflow.external.model.ExternalResultMulti;
import com.wonders.receive.workflow.external.model.ExternalResultSingle;

/**调用portal外部接口工具类
 * @author XFZ
 * @version 1.0 2012-6-19
 */
public class PortalUtil {
	private Gson gson = new Gson();
	private String token = "";
	private Logger log = SimpleLogger.getLogger(this.getClass());
	
	private final String CODE_SUCCESS = "100";
	
	
	
	/**调用portal外部接口方法
	 * @param method
	 * @param paramsXml
	 * @return
	 */
	private String portalService(String method, String paramsXml) {
		//String result = "error";
		//if(token==null||token.length()==0) return result;

//log.debug(paramsXml);
		
		Properties properties = new Properties();
		String path = Thread.currentThread().getContextClassLoader().getResource("config.properties").getPath();
		String param = "";
		
		String result = "";
		
		//String urls = "http://10.1.43.32:8088/ca/services/api/dataExchange";
		//String token = "8a81aba038bd6f790138bd72bf4a0001";
		//String appName = "stptm";
		//String param = "";
		try {
			
			FileInputStream fis = new FileInputStream(path);
			properties.load(fis);
			String urlCa = properties.getProperty("urlCa");
			String serverPath=properties.getProperty("serverPath");
			String apiName=properties.getProperty("apiName");
			
			String urls = urlCa + serverPath+"/"+apiName;
			String secret = properties.getProperty("secret");
			String appName = properties.getProperty("appName");
			
			if(token==null||token.length()==0) token = appName+secret;
			
			String dataParams = "<?xml version=\"1.0\" encoding=\"utf-8\"?><params>" + paramsXml + "</params>";
//log.debug(urls + " " + appName + " " + token + " " + method + " " + secret);
			String sign = CommonUtil.getMD5(appName + token + method + secret);
			String dataType = "json";

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
				param = "&appName=" + appName 
						+ "&token=" + token 
						+ "&method=" + method 
						+ "&dataType=" + dataType 
						+ "&dataParams=" + dataParams 
						+ "&sign=" + sign;

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
			} finally {
				if (http != null) http.disconnect();
				if (fis != null) fis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//log.debug("result="+result);
//log.debug("param:"+param+" result.length():"+result.length());

		return result;
	}
		

	
	private String service(String method, String paramsXml){
		String ret = portalService(method, paramsXml);
		
//log.debug("ret="+ret);
		
		AbstractExternalResult result = new ExternalResultSingle();
		
		try{
			result = gson.fromJson(ret, result.getClass());
		}catch(Exception e){
//log.error("single failed");
//e.printStackTrace();
			try{
				result = gson.fromJson(ret, result.getClass());
			}catch(Exception e1){
//log.error("multi failed");
				result = new ExternalResultMulti();
				result = gson.fromJson(ret, result.getClass());
//e1.printStackTrace();
			}
		}
//log.debug("ret="+result.getParams().getString());
		
		if(result.code==null||!CODE_SUCCESS.equals(result.code)){
			log.warn("error("+method+"):"+result.description+" ");
			return "error";
		}
		
		ret = result.getString();
		
//log.debug("ret string="+ret);
		
		return ret;
	}
	
	/**得到指定节点信息
	 * @param method
	 * @param token
	 * @param id
	 * @return
	 */
	public String getNodesInfo(String id){
		
		return service(ExternalConstants.METHOD_GET_NODES_INFO, wrapForTag(id,"id"));
	}
	
	/**得到指定节点相邻节点信息
	 * @param method
	 * @param token
	 * @param id
	 * @return
	 */
	public String getRelatedNodes(String id){
		if(checkNullParams(ExternalConstants.METHOD_GET_RELATED_NODES,id)){
			return "error";
		}
		return service(ExternalConstants.METHOD_GET_RELATED_NODES, wrapForTag(id,"id"));
	}

	/**得到指定节点子节点信息
	 * @param method
	 * @param token
	 * @param id
	 * @return
	 */
	public String getChildNodes(String id){
		if(checkNullParams(ExternalConstants.METHOD_GET_CHILD_NODES,id)){
			return "error";
		}
		return service(ExternalConstants.METHOD_GET_CHILD_NODES, wrapForTag(id,"id"));
	}
	
	/**得到指定部门领导层信息
	 * @param method
	 * @param token
	 * @param id
	 * @return
	 */
	public String getDeptLeaders(String id){
		if(checkNullParams(ExternalConstants.METHOD_GET_DEPT_LEADERS,id)){
			return "error";
		}
		return service(ExternalConstants.METHOD_GET_DEPT_LEADERS, wrapForTag(id,"id"));
	}
	
	/**得到指定部门大领导信息
	 * @param method
	 * @param token
	 * @param id
	 * @return
	 */
	public String getDeptSingleLeader(String id){
		if(checkNullParams(ExternalConstants.METHOD_GET_DEPT_SINGLE_LEADER,id)){
			return "error";
		}
		return service(ExternalConstants.METHOD_GET_DEPT_SINGLE_LEADER, wrapForTag(id,"id"));
	}
	
	/**得到指定部门收发员信息（流程内，包括取消收发员功能时，返回领导）
	 * @param method
	 * @param token
	 * @param id
	 * @return
	 */
	public String getReceivers(String processName, String stepName,String deptIds){
		if(checkNullParams(ExternalConstants.METHOD_GET_RECVS,deptIds)){
			return "error";
		}
		return service(ExternalConstants.METHOD_GET_RECVS, wrapForTag(processName,"processName",stepName,"stepName",deptIds,"deptIds"));
	}
	
	/**得到部门收发员信息（读取配置表）
	 * @param method
	 * @param token
	 * @param id
	 * @return
	 */
	public String getProcessReceivers(String processName, String stepName,String deptIds){
		if(checkNullParams(ExternalConstants.METHOD_GET_PROCESS_RECVS,deptIds)){
			return "error";
		}
		return service(ExternalConstants.METHOD_GET_PROCESS_RECVS, wrapForTag(processName,"processName",stepName,"stepName",deptIds,"deptIds"));
	}
	
	/**得到部门收发员信息（包括配置表）
	 * @param method
	 * @param token
	 * @param id
	 * @return
	 */
	public String getDeptReceivers(String id){
		if(checkNullParams(ExternalConstants.METHOD_GET_DEPT_RECIEVERS,id)){
			return "error";
		}
		return service(ExternalConstants.METHOD_GET_DEPT_RECIEVERS, wrapForTag(id,"id"));
	}
	
	/**得到指定部门人员信息
	 * @param method
	 * @param token
	 * @param id
	 * @return
	 */
	public String getDeptUsers(String id){
		if(checkNullParams(ExternalConstants.METHOD_GET_DEPT_USERS,id)){
			return "error";
		}
		return service(ExternalConstants.METHOD_GET_DEPT_USERS, wrapForTag(id,"id"));
	}

	/**得到指定部门人员信息(非领导)
	 * @param method
	 * @param token
	 * @param id
	 * @return
	 */
	public String getDeptUsersOffLeaders(String id){
		if(checkNullParams(ExternalConstants.METHOD_GET_DEPT_USERS_OFF_LEADERS,id)){
			return "error";
		}
		return service(ExternalConstants.METHOD_GET_DEPT_USERS_OFF_LEADERS, wrapForTag(id,"id"));
	}
	
	/**得到集团领导层信息
	 * @param method
	 * @param token
	 * @param id
	 * @return
	 */
	public String getComLeaderInfo(String processName,String type){
		
		return service(ExternalConstants.METHOD_GET_COM_DEPT_LEADERS, wrapForTag(processName,"processName",type,"type"));
	}
	
	private String wrapForTag(String str,String tag,String str2, String tag2,String str3, String tag3){
		//if(str==null||str.length()==0) return "";
		String ret = "<"+tag+">"+str+"</"+tag+">"+"<"+tag2+">"+str2+"</"+tag2+">"+"<"+tag3+">"+str3+"</"+tag3+">";
//log.debug("ret="+ret);
		return ret;
	}
	
	private String wrapForTag(String str,String tag,String str2, String tag2){
		//if(str==null||str.length()==0) return "";
		String ret = "<"+tag+">"+str+"</"+tag+">"+"<"+tag2+">"+str2+"</"+tag2+">";
//log.debug("ret="+ret);
		return ret;
	}
	
	private String wrapForTag(String str,String tag){
		//if(str==null||str.length()==0) return "";
		String ret = "<"+tag+">"+str+"</"+tag+">";
//log.debug("ret="+ret);
		return ret;
	}
	
	/**检查参数是否为空
	 * @param params
	 * @return
	 */
	private boolean checkNullParams(String method,String params){
		if(params.length()==0){
			log.warn(method+":params is null!");
			return true;
		}
		return false;
	}
	

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
}
