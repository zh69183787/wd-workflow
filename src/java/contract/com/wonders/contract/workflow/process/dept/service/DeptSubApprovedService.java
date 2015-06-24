/**
 * 
 */
package com.wonders.contract.workflow.process.dept.service;


import com.wonders.contract.workflow.process.dept.model.vo.DeptSubParamVo;
import com.wonders.receive.workflow.approve.model.bo.ApprovedInfoBo;

/** 
 * @ClassName: DeptSubApprovedService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 下午01:37:57 
 *  
 */
public interface DeptSubApprovedService {
	public ApprovedInfoBo saveApprovedInfo(DeptSubParamVo params);
}
