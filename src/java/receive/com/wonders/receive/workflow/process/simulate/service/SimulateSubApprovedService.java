/**
 * 
 */
package com.wonders.receive.workflow.process.simulate.service;

import com.wonders.receive.workflow.approve.model.bo.ApprovedInfoBo;
import com.wonders.receive.workflow.process.simulate.model.vo.SimulateSubParamVo;

/** 
 * @ClassName: SimulateSubApprovedService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 下午01:37:57 
 *  
 */
public interface SimulateSubApprovedService {
	public ApprovedInfoBo saveApprovedInfo(SimulateSubParamVo params);
}
