package com.wonders.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.Holder;

import com.wonders.receive.ultimus.ArrayOfAnyType;
import com.wonders.receive.ultimus.ArrayOfVariable;
import com.wonders.receive.ultimus.Variable;
import com.wonders.receive.ultimus.pws.ProcessWebServiceClient;
import com.wonders.receive.ultimus.pws.ProcessWebServiceSoap;


public class PWSUtil {
	private static int submitTimes = Integer.parseInt(PWSProperties.getValueByKey("submit_times"));
	private static long sleepTimes = Long.parseLong(PWSProperties.getValueByKey("sleep_times"));
	
	
	
	/**
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str){
		if(str==null||str.trim().equals("")||str.trim().equalsIgnoreCase("null")){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 发起�??个工作流
	 * @param processName	流程名称（发布在工作流平台上的流程名�??	 * @param loginName	登录�??	 * @param summary	标题
	 * @param paramMap	参数Map
	 * @return	返回 true：成功，false：失�??
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int launchIncident(String processName,String loginName,String summary,Map paramMap){
        int oIncidentNo=-1;
		try{
			if(!PWSUtil.isNull(processName)&&!PWSUtil.isNull(loginName)){
				String pLoginName = loginName;
				if(pLoginName.indexOf('/')>=0){
					pLoginName = pLoginName.substring(pLoginName.lastIndexOf('/')+1,pLoginName.length());
				}
				pLoginName = PWSProperties.getValueByKey("domain_name")+"/"+pLoginName;
				
				ProcessWebServiceClient client = new ProcessWebServiceClient();
		        ProcessWebServiceSoap service = client.getProcessWebServiceSoap();
		        
		        ArrayOfVariable varList = new ArrayOfVariable();
		        if(paramMap!=null&&paramMap.size()>0){
		        	Object[] keys = paramMap.keySet().toArray();
		        	for(int i=0;i<keys.length;i++){
		        		String key = (String)keys[i];
		        		Object value = paramMap.get(key);
		        		Variable val = new Variable();
		        		val.setStrVariableName(key);
		        		if(value instanceof java.util.List){
		        			List list = (List)value;
			        		ArrayOfAnyType aoat = new ArrayOfAnyType();
			        		aoat.getAnyType().addAll(list);
		        			val.setObjVariableValue(aoat);
					        varList.getVariable().add(val);
		        		}else{
		        			ArrayOfAnyType aoat = new ArrayOfAnyType();
			        		aoat.getAnyType().add(value);
			        		val.setObjVariableValue(aoat);
					        varList.getVariable().add(val);
		        		}
		        	}
		        } 
		        int nIncidentNo=0;
		        Holder nIncidentNo2 = new Holder();
		        Holder strError = new Holder();
		        
		        boolean r = service.launchIncident(processName, pLoginName, summary, varList, nIncidentNo, nIncidentNo2, strError);
		        //System.out.println("nIncidentNo2=:"+nIncidentNo2.value+"");
		        //System.out.println("nIncidentNo=:"+nIncidentNo);
		        if(r){
		        	oIncidentNo = Integer.parseInt(nIncidentNo2.value+"");
		        }else{
		        	System.out.println("processName:"+processName+",loginName:"+loginName
		        			+",summary:"+summary+",strError:"+strError.value);
		        }
		       
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return oIncidentNo;
	}	
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean abortIncident(String processName,String incident, String userName, String summary){
		ProcessWebServiceClient client = new ProcessWebServiceClient();
        ProcessWebServiceSoap service = client.getProcessWebServiceSoap();
		Holder strError = new Holder();
		return service.abortIncident(processName, PWSProperties.getValueByKey("domain_name")+"/"+userName, Integer.parseInt(incident), summary, strError);
	}
	
	public static String changeUltimusUser(String loginName){
		String r = loginName;
		if(!PWSUtil.isNull(loginName)&&loginName.endsWith("USER")){
			r = r+"ADMIN";
		}
		return r;
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean completeStepTest(String processName, String taskUser, int incident,String stepName, String summary,String memo, Map paramMap){
		boolean r = false ;
		try{	
			if(!PWSUtil.isNull(processName)&&!PWSUtil.isNull(taskUser)){
				String pLoginName = taskUser;
				if(pLoginName.indexOf('/')>=0){
					pLoginName = pLoginName.substring(pLoginName.lastIndexOf('/')+1,pLoginName.length());
				}
				pLoginName = PWSProperties.getValueByKey("domain_name")+"/"+pLoginName;
				
				ProcessWebServiceClient client = new ProcessWebServiceClient();
		        ProcessWebServiceSoap service = client.getProcessWebServiceSoap();
		        
		        ArrayOfVariable varList = new ArrayOfVariable();
		        if(paramMap!=null&&paramMap.size()>0){
		        	Object[] keys = paramMap.keySet().toArray();
		        	for(int i=0;i<keys.length;i++){
		        		String key = (String)keys[i];
		        		Object value = paramMap.get(key);
		        		Variable val = new Variable();
		        		val.setStrVariableName(key);
		        		if(value instanceof java.util.List){
		        			List list = (List)value;
			        		ArrayOfAnyType aoat = new ArrayOfAnyType();
			        		aoat.getAnyType().addAll(list);
		        			val.setObjVariableValue(aoat);
					        varList.getVariable().add(val);
		        		}else{
		        			ArrayOfAnyType aoat = new ArrayOfAnyType();
			        		aoat.getAnyType().add(value);
			        		val.setObjVariableValue(aoat);
					        varList.getVariable().add(val);
		        		}
		        	}
		        }
		       // int nIncidentNo=0;
		        //Holder nIncidentNo2 = new Holder();
		        Holder strError = new Holder();
		        
		        int times = 0;
		        while(!r && times < submitTimes){
		        	System.out.println("times:" + times);
		        	try {
							Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		        	r =  service.completeStep(processName, pLoginName, incident, stepName, summary, memo, varList, strError);
		        	System.out.println(strError.value);
		        	times++;
		        	try {
						Thread.sleep(sleepTimes);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		        }
		        
		       if(r == false ){//&& (strError.value != null || "".equals((strError.value).toString().trim()))
		    	  System.out.println("processName:"+processName+",loginName:"+pLoginName
		   				+",summary:"+summary+",strError:"+strError.value);
		    	   throw new RuntimeException((strError.value).toString() + "times:" +times);
		       }
			   // System.out.println("nIncidentNo2=:"+nIncidentNo2.value+"");
			   // System.out.println("nIncidentNo=:"+nIncidentNo);
				//if(!r){
				//System.out.println("processName:"+processName+",loginName:"+pLoginName
				//+",summary:"+summary+",strError:"+strError.value);
				//}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return r;
		
	}
	
	
	
	
	
	
	public static void main(String[] s) throws Exception{
		Map map = new HashMap();
		List<String> list = new ArrayList<String>();
		list.add("b");list.add("c");list.add("d");list.add("e");
		//map.put("入口序列", "a");
		////map.put("入口序列2", list);
		//map.put("重复次数", 5);
		String pname = "测试主流程";
		String cname = "测试子流程";
		
		//map.put("父办理人", "ST/G001000001612549");
		//map.put("子办理人", "ST/G001000001362510");
		//map.put("状态", "3");
		//map.put("code", "4");
		//int i = launchIncident(pname, "ST/G001000001612549", "摘要：ZZZZZZZZZ", map);
		//System.out.println(i);
		try {
			//System.out.println(completeStepTest(cname,"ST/G001000001362510",5,"子流程办理人","摘要：ZZZZZZZZZ",
			//		"",map));
			//System.out.println(completeStepTest(pname,"ST/G001000001612549",27,"Step 2","摘要：ZZZZZZZZZ",
		//				"",map));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(i);
		//String pLoginName = "ST/G00000";
		//pLoginName = pLoginName.replaceFirst("/", "@");
		//System.out.println("pLoginName====00==="+pLoginName+","+pLoginName.indexOf('/'));
		 InputStream input = new FileInputStream(new File("D://编辑1.TXT"));  
		 OutputStream output = new FileOutputStream(new File("D://编辑23.TXT"));  
		int temp =0;
		int i = 0;
		byte[] b = new byte[1024];
		System.out.println(input.available());
//		while((temp=input.read())!=-1){
//			output.write(temp);
//			System.out.println(temp);
//			i++;
//		}
		int count = 0;
		while((count = input.read(b))>0){
			output.write(b);
		}
		System.out.println(i);
		System.out.println(System.getProperty("file.encoding"));
	}
	

}