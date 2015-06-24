package com.wonders.contact.approvedInfo.model.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.wonders.contact.common.util.CommonUtil;
import com.wonders.contact.deptContact.constant.DeptContactFlowConstants;
import com.wonders.contact.deptSubProcess.constant.DeptSubProcessFlowConstants;
import com.wonders.util.StringUtil;

/**
 * @author XFZ
 * @version 1.0 2012-6-12
 */
public class DeptContactApprovedInfoVo {
	//SimpleLogger log = new SimpleLogger(this.getClass());
	private String[] applys = {DeptContactFlowConstants.STEPNAME_APPLY,DeptContactFlowConstants.STEPNAME_SIGN};
	private String[] subs = DeptSubProcessFlowConstants.FLOW_OPERATE_STEP;
	
	
	/**部门内部意见*/
	@Expose
	public List<Map<String,Object>> applyApprovedInfo = new ArrayList<Map<String,Object>>();
	
	
	/**部门内部意见*/
	@Expose
	public List<Map<String,Object>> subApprovedInfo = new ArrayList<Map<String,Object>>();
	
	
	/**下级部门意见*/
	public List<Map<String,Object>> lowerApprovedInfo = new ArrayList<Map<String,Object>>();
	
	public Map<String,Map<String,Object>> lowerProcessInfoMap = new HashMap<String,Map<String,Object>>();
	
	
	
	
	
	/**返回发起部门内部意见*/
	@Expose
	public List<Object> backApplyApprovedInfo = new ArrayList<Object>();
	
	/**部门名称map*/
	public Map<String,String> mainDeptNameMap = new HashMap<String,String>();
	
	public void addMainInfo (Map<String,Object> datas){
		String stepname = StringUtil.getNotNullValueString(datas.get("STEPNAME"));
		String approveId = StringUtil.getNotNullValueString(datas.get("GUID"));
		
		if(approveId.length()>0&&CommonUtil.targetIsInArray(stepname, applys)){
			applyApprovedInfo.add(datas);
		}
	}
	
	
	/**添加子流程意见
	 * @param datas
	 */
	public void addSubInfo(Map<String,Object> datas){
		String approveId = StringUtil.getNotNullValueString(datas.get("GUID"));
		
		if(approveId.length()>0){
			subApprovedInfo.add(datas);
		}
	}
	
	/**添加下级流程意见
	 * @param datas
	 */
	public void addLowerInfo(Map<String,Object> datas){
		String approveId = StringUtil.getNotNullValueString(datas.get("GUID"));
				
		if(approveId.length()>0){
			lowerApprovedInfo.add(datas);
		}
	}
	
	
	
	/**添加返回发起部门意见
	 * @param datas
	 */
	public void addBackApplyInfo(Map<String,Object> datas){
		String stepname = StringUtil.getNotNullValueString(datas.get("STEPNAME"));
		String approveId = StringUtil.getNotNullValueString(datas.get("GUID"));
//log.debug("backApplyApprovedInfo "+stepname+" "+approveId);
		if(approveId.length()>0&&CommonUtil.targetIsInArray(stepname, subs)){
			backApplyApprovedInfo.add(datas);
		}
	}
	
	@Override
	public String toString(){
		return 
			" apply.size="+applyApprovedInfo.size()+
			" sub.size="+subApprovedInfo.size()+
			//" lower.size="+lowerApprovedInfo.size()+
			" backapply.size="+backApplyApprovedInfo.size() +
			" lowerProcessInfo.size="+lowerProcessInfoMap.size()
		;
	}
}
