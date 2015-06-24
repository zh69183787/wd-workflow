/**
 * 
 */
package com.wonders.receive.workflow.process.redv.service;

import com.wonders.receive.workflow.approve.model.bo.ApprovedInfoBo;
import com.wonders.receive.workflow.process.redv.model.vo.RedvMainParamVo;

/** 
 * @ClassName: RedvMainApprovedService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:52:52 
 *  
 */
public interface RedvMainApprovedService {
	public ApprovedInfoBo saveApprovedInfo(RedvMainParamVo params);
}
