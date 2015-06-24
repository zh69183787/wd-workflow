/**
 * 
 */
package com.wonders.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.wonders.util.DateUtil;
import com.wonders.util.PropertyUtil;

import javax.ws.rs.core.MultivaluedMap;
import java.util.HashMap;
import java.util.Map;

/** 
 * @ClassName: RestClient 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-9-4 下午8:49:22 
 *  
 */
public class RestClient {

	/*待办项*/
	public static String restForTodoItem(String loginNames,String loginName,String userId,String deptId,String type){
         Map<String,String> map = new HashMap<String,String>();
         //System.out.println(loginNames);
         map.put("loginNames", loginNames);
         map.put("loginName", loginName);
         map.put("userId", userId);
         map.put("deptId", deptId);
         map.put("type", type);
         String url = PropertyUtil.getValueByKey("config.properties", "controllerUrl");
         return restForUrl(url,map);
	}
	
	/*流程控制器*/
	public static String restForController(String loginNames,String loginName,String userId,String deptId,String type){
         Map<String,String> map = new HashMap<String,String>();
         //System.out.println(loginNames);
         map.put("loginNames", loginNames);
         map.put("loginName", loginName);
         map.put("userId", userId);
         map.put("deptId", deptId);
         map.put("type", type);
         String url = PropertyUtil.getValueByKey("config.properties", "controllerUrl");
         return restForUrl(url,map);
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String restForUrl(String url,Map<String,String> map){
		Gson gson = new Gson();
 		Client c = Client.create();  
        WebResource r=c.resource(url);
        MultivaluedMap formData = new MultivaluedMapImpl();
        formData.add("data",gson.toJson(map));
        String response = r.type("application/x-www-form-urlencoded")
                 .post(String.class, formData);
        System.out.println(response);
        return response;
	}
	
	public static void main(String[] args){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map2 = new HashMap<String,Object>();
		map.put("process","合同后审流程");
		map.put("incident", "4");
		map.put("step", "合约部初审");
		map.put("operator", "ST/G001000001232508");
		map.put("operateTime",DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
		map.put("summary"," test1014c");
		map2.put("后审直接入库", "1");
		map2.put("test", "<22222>");
		map.put("ultimusMap", map2);
		
		System.out.println(new GsonBuilder().disableHtmlEscaping().create().toJson(map));
		//restForUrl("http://10.1.14.20:8088/workflowController/service/workflow/saveProcessStep",map);
	}
}
