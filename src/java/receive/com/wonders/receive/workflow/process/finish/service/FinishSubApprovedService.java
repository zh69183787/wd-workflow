/**
 * 
 */
package com.wonders.receive.workflow.process.finish.service;

import com.wonders.receive.workflow.approve.model.bo.ApprovedInfoBo;
import com.wonders.receive.workflow.process.finish.model.vo.FinishSubParamVo;

/** 
 * @ClassName: FinishSubApprovedService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-2-17 下午01:37:57 
 *  
 */
public interface FinishSubApprovedService {
	public ApprovedInfoBo saveApprovedInfo(FinishSubParamVo params);
}
