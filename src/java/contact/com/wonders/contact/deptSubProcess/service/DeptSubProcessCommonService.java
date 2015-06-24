/**
 * 
 */
package com.wonders.contact.deptSubProcess.service;

import com.wonders.contact.approvedInfo.model.bo.TApprovedinfo;
import com.wonders.contact.deptSubProcess.model.vo.DeptSubProcessParamVo;

/**
 * @author XFZ
 * @version 1.0 2012-6-11
 */
public interface DeptSubProcessCommonService {
	public TApprovedinfo saveApprovedInfo(DeptSubProcessParamVo params);
}
