/**
 * 
 */
package com.wonders.receive.workflow.process.instruct.service;

import com.wonders.receive.workflow.approve.model.bo.ApprovedInfoBo;
import com.wonders.receive.workflow.process.instruct.model.vo.InstructSubParamVo;

/** 
 * @ClassName: InstructSubApprovedService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-3-18 上午09:32:33 
 *  
 */
public interface InstructSubApprovedService {
	public ApprovedInfoBo saveApprovedInfo(InstructSubParamVo params);
}
