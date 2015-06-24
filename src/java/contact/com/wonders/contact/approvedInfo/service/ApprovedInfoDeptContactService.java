package com.wonders.contact.approvedInfo.service;

import com.wonders.contact.approvedInfo.model.vo.DeptContactApprovedInfoVo;

/**多级工作联系单意见service
 * @author XFZ
 * @version 1.0 2012-6-12
 */
public interface ApprovedInfoDeptContactService {
	
	/**获得意见vo
	 * @param mainBoId
	 * @param types
	 * @return
	 */
	public DeptContactApprovedInfoVo getApprovedInfoVo(String mainBoId,String filterStr);
}
