package com.wonders.contact.deptContact.service;

import java.util.List;

import com.wonders.contact.approvedInfo.model.bo.TApprovedinfo;
import com.wonders.contact.deptContact.model.bo.TDeptContactConfig;
import com.wonders.contact.deptContact.model.bo.TDeptContactMain;
import com.wonders.contact.deptContact.model.vo.DeptContactParamVo;

public interface DeptContactCommonService {
	public List<TDeptContactConfig> getDeptContactConfigInfo(String processname,String stepname);
	
	public String getReferenceIds(String mainBoId);
	
	public void saveReferences(DeptContactParamVo params);
	
	public TApprovedinfo generateApprovedInfo(DeptContactParamVo params);
	
	public void saveApprovedInfo(DeptContactParamVo params);
	
	/**获得下个编号数字
	 * @param mainBo
	 * @return
	 */
	public String getSerialNumberText(TDeptContactMain mainBo);
	
	/**生成编号显示字符串
	 * @param mainBo
	 * @return
	 */
	public Integer getNextSerialNumber(TDeptContactMain mainBo);
}
