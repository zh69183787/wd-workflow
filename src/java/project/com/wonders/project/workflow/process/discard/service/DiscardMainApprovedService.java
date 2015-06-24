/**
 * 
 */
package com.wonders.project.workflow.process.discard.service;

import com.wonders.project.workflow.process.discard.model.vo.DiscardMainParamVo;
import com.wonders.receive.workflow.approve.model.bo.ApprovedInfoBo;

/** 
 * @ClassName: DiscardMainApprovedService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:52:52 
 *  
 */
public interface DiscardMainApprovedService {
	public ApprovedInfoBo saveApprovedInfo(DiscardMainParamVo params);
}
