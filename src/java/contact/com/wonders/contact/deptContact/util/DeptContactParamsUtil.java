package com.wonders.contact.deptContact.util;

import com.wonders.contact.common.model.vo.ParamVo;
import com.wonders.contact.common.util.LoginUtil;
import com.wonders.contact.deptContact.constant.DeptContactConstants;
import com.wonders.util.StringUtil;

import javax.servlet.http.HttpServletRequest;

/**从Action中提取流程service中需要用到的值
 * @author XFZ
 *
 */
public class DeptContactParamsUtil {
	
	/**新增
	 * @param request
	 * @param params
	 */
	public static void prepareAdd(HttpServletRequest request,ParamVo params){
		prepareFlowType(request,params);
		String sign_leader = StringUtil.getNotNullValueString(request.getParameter(DeptContactConstants.PARAMS_KEY_SIGN_LEADER));
		sign_leader = LoginUtil.getUserLoginName(sign_leader);
		
		/*
		String[] refs = request.getParameterValues(DeptContactConstants.PARAMS_KEY_REF_ID);
		
		List<String> refList = new ArrayList<String>();
		
		for(int i=0;refs!=null&&i<refs.length;i++){
			String ref = StringUtil.getNotNullValueString(refs[i]);
			if(ref.length()>0&&!refList.contains(ref)){
				refList.add(ref);
			}
		}
		*/
		
		params.addParam(DeptContactConstants.IP_CONTEXT_PATH,StringUtil.getNotNullValueString(request.getContextPath()));
		
		params.addParam(DeptContactConstants.IP_PORT,StringUtil.getNotNullValueString(request.getServerPort()));
		
		String refs = StringUtil.getNotNullValueString(request.getParameter(DeptContactConstants.PARAMS_KEY_REF_ID));
		params.addParam(DeptContactConstants.PARAMS_KEY_REF_ID, refs);
		
		params.addParam(DeptContactConstants.PARAMS_KEY_SIGN_LEADER, sign_leader);
		
		//params.addProcessParam(DeptcontactConstants.PARAMS_FLOW_TYPE, DeptcontactConstants.STATUS_MAIN_STR);
	}
	
	/**跳转页面
	 * @param request
	 * @param params
	 */
	public static void prepareForward(HttpServletRequest request,ParamVo params){
		prepareFlowType(request,params);
		prepareFlowInfo(request,params);
	}

    /**跳转页面
     * @param request
     * @param params
     */
    public static void prepareKpiForward(HttpServletRequest request,ParamVo params){
        prepareFlowType(request,params);
        prepareFlowInfo(request,params);
    }
	
	/**修改
	 * @param request
	 * @param params
	 */
	public static void prepareUpdate(HttpServletRequest request,ParamVo params){
		prepareFlowType(request,params);
		
		prepareFlowInfo(request,params);
		
		String refs = StringUtil.getNotNullValueString(request.getParameter(DeptContactConstants.PARAMS_KEY_REF_ID));
		params.addParam(DeptContactConstants.PARAMS_KEY_REF_ID, refs);
	}
		
	/**申请
	 * @param request
	 * @param params
	 */
	public static void prepareApply(HttpServletRequest request,ParamVo params){
		prepareFlowType(request,params);
		
		prepareFlowInfo(request,params);
		
		String refs = StringUtil.getNotNullValueString(request.getParameter(DeptContactConstants.PARAMS_KEY_REF_ID));
		params.addParam(DeptContactConstants.PARAMS_KEY_REF_ID, refs);
		
		String sign_leader = StringUtil.getNotNullValueString(request.getParameter(DeptContactConstants.PARAMS_KEY_SIGN_LEADER));
		params.addParam(DeptContactConstants.PARAMS_KEY_SIGN_LEADER, sign_leader);
	}
	
	/**签发
	 * @param request
	 * @param params
	 */
	public static void prepareSign(HttpServletRequest request,ParamVo params){
		prepareFlowType(request,params);
		
		prepareFlowInfo(request,params);
	}
	
	
	/**
	 * @param request
	 * @param params
	 */
	public static void prepareFlowInfo(HttpServletRequest request,ParamVo params){
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
	}
	
	/**flowType
	 * @param request
	 * @param params
	 */
	public static void prepareFlowType(HttpServletRequest request,ParamVo params){
		String flowType = StringUtil.getNotNullValueString(request.getParameter("flowType"));
		if(flowType.length()==0){
			flowType = DeptContactConstants.STATUS_MAIN_STR;
//SimpleLogger.getLogger(DeptContactParamsUtil.class).debug("flowType is missing,use default!");
		}else{
//SimpleLogger.getLogger(DeptContactParamsUtil.class).debug("flowType="+flowType);
		}
		params.addProcessParam(DeptContactConstants.PARAMS_FLOW_TYPE, flowType);
	}
}

