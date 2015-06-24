package com.wonders.discipline.workflow.process.util;

import com.wonders.discipline.workflow.constants.CommonConstants;
import com.wonders.discipline.workflow.constants.UltimusConstants;
import com.wonders.discipline.workflow.model.vo.ParamVo;
import com.wonders.util.StringUtil;


/**多级工作联系单字符串操作类
 * @author Z
 * @version 1.0 2013
 */
public class TextUtil {
	/**
	 * 根据字符串生产where内条件
	 * 
	 * @return
	 */
	public static String generateWhereInClauseByStr(String strs, String splitExp) {
		String ret = "";
		String str = StringUtil.getNotNullValueString(strs);

		if (str.length() > 0) {
			if (str.indexOf(splitExp) > 0) {
				str = str.replaceAll(splitExp, "'" + splitExp + "'");
			}
		}
		ret = "'" + str + "'";
		return ret;
	}

	/**
	 * 根据字符串生产where内条件
	 * 
	 * @return
	 */
	public static String generateWhereInClauseByStr(String strs) {
		String ret = "";
		String str = StringUtil.getNotNullValueString(strs);
		String splitExp = CommonConstants.SPLIT_EXP;
		
		if (str.length() > 0) {
			if (str.indexOf(splitExp) > 0) {
				str = str.replaceAll(splitExp, "'" + splitExp + "'");
			}
		}
		ret = "'" + str + "'";
		return ret;
	}
	
	/*
	public static String getUuid(){
		 UUID uuid = java.util.UUID.randomUUID();
		 return uuid.toString();
	}
	*/
	
	/**生成表单url
	 */
	public static String generateMainUrl(String pname,String pincident,String cname,String cincident,String Steplabel){
		String type = "";
		if(UltimusConstants.DISCIPLINE_RECV_DICTIONARY_NAME.equals(pname)){
			type = "discipline-dcpRecvMain";
		}
		String url = 
				"/"+type+"/forward.action?"
			+"pname="+StringUtil.getEncodeStr(pname)+"&pincident="+pincident
			+"&cname="+StringUtil.getEncodeStr(cname)+"&cincident="+cincident
			+"&steplabel="+StringUtil.getEncodeStr(Steplabel);
		
		return url;
	}
	
	
	
	public static String generateMainUrl(ParamVo params){
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String cname = params.getProcessParamValue("cname");
		String cincident = params.getProcessParamValue("cincident");
		String taskid = params.getProcessParamValue("taskid");
		String steplabel = params.getProcessParamValue("steplabel");
		String type = "";
		if(UltimusConstants.DISCIPLINE_RECV_DICTIONARY_NAME.equals(pname)){
			type = "discipline-dcpRecvMain";
		}
		String url = 
			"/"+type+"/forward.action?"
			+"pname="+StringUtil.getEncodeStr(pname)+"&pincident="+pincident
			+"&cname="+StringUtil.getEncodeStr(cname)+"&cincident="+cincident
			+"&steplabel="+StringUtil.getEncodeStr(steplabel)
			+"&taskid="+taskid+"&viewType=1";
		
		return url;
		
	}
	
	
}
