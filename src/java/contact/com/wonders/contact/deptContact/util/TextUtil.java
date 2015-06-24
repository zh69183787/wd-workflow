package com.wonders.contact.deptContact.util;

import com.wonders.contact.common.model.vo.ParamVo;
import com.wonders.contact.constant.CommonConstants;
import com.wonders.contact.deptContact.model.bo.TDeptContactMain;
import com.wonders.util.StringUtil;

/**多级工作联系单字符串操作类
 * @author XFZ
 * @version 1.0 2012-6-2
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
	public static String generateDeptContactUrl(String pname,String pincident,String cname,String cincident,String Steplabel){
		
		String url = 
			"/contact-deptContact/forward.action?"
			+"pname="+StringUtil.getEncodeStr(pname)+"&pincident="+pincident
			+"&cname="+StringUtil.getEncodeStr(cname)+"&cincident="+cincident
			+"&steplabel="+StringUtil.getEncodeStr(Steplabel);
		
		return url;
	}
	
	/**生成表单url
	 */
	public static String generateDeptContactUrl(TDeptContactMain mainBo,String Steplabel){
		
		String url = 
			"/contact-deptContact/forward.action?"
			+"pname="+StringUtil.getEncodeStr(mainBo.getProcessname())+"&pincident="+mainBo.getIncidentno()
			+"&cname="+StringUtil.getEncodeStr(mainBo.getProcessname())+"&cincident="+mainBo.getIncidentno()
			+"&steplabel="+StringUtil.getEncodeStr(Steplabel);
		
		return url;
	}
	
	public static String generateDeptContactUrl(ParamVo params){
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String cname = params.getProcessParamValue("cname");
		String cincident = params.getProcessParamValue("cincident");
		
		String steplabel = params.getProcessParamValue("steplabel");
		
		String url = 
			"/contact-deptContact/forward.action?"
			+"pname="+StringUtil.getEncodeStr(pname)+"&pincident="+pincident
			+"&cname="+StringUtil.getEncodeStr(cname)+"&cincident="+cincident
			+"&steplabel="+StringUtil.getEncodeStr(steplabel);
		
		return url;
		
	}
}
