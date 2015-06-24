package com.wonders.contact.deptSubProcess.util;

import javax.servlet.http.HttpServletRequest;

import com.wonders.contact.common.model.vo.ParamVo;
import com.wonders.util.StringUtil;

/**从Action中提取部门内部子流程中需要用到的值
 * @author XFZ
 * @version 1.0 2012-6-12
 */
public class DeptSubProcessParamsUtil {
	/**流程基本信息
	 * @param request
	 * @param params
	 */
	private static void prepareProcessInfo(HttpServletRequest request,ParamVo params){
		String pname = StringUtil.getNotNullValueString(request.getParameter("pname"));
		String pincident = StringUtil.getNotNullValueString(request.getParameter("pincident"));
		String cname = StringUtil.getNotNullValueString(request.getParameter("cname"));
		String cincident = StringUtil.getNotNullValueString(request.getParameter("cincident"));
		
		String steplabel = StringUtil.getNotNullValueString(request.getParameter("steplabel"));
		String taskuser = StringUtil.getNotNullValueString(request.getParameter("taskuser"));
		params.addProcessParam("pname", pname);
		params.addProcessParam("pincident", pincident);
		params.addProcessParam("cname", cname);
		params.addProcessParam("cincident", cincident);
		params.addProcessParam("steplabel", steplabel);
params.addProcessParam("taskuser", taskuser);
		
		params.userInfo.initTaskUser(params.getProcessParamValue("taskuser"));
//System.out.println("processParams.size="+params.processParam.size());
	}
	
	/**部门接收员分发
	 * @param request
	 * @param params
	 */
	public static void prepareDispatcher(HttpServletRequest request,ParamVo params){
		prepareProcessInfo(request,params);
	}
	
	/**部门处理人员处理
	 * @param request
	 * @param params
	 */
	public static void prepareDeal(HttpServletRequest request,ParamVo params){
		prepareProcessInfo(request,params);
	}
	
	/**部门领导审核
	 * @param request
	 * @param params
	 */
	public static void prepareLeaderDeal(HttpServletRequest request,ParamVo params){
		prepareProcessInfo(request,params);
	}
}

