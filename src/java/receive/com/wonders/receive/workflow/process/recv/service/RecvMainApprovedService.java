/**
 * 
 */
package com.wonders.receive.workflow.process.recv.service;

import com.wonders.receive.workflow.approve.model.bo.ApprovedInfoBo;
import com.wonders.receive.workflow.process.recv.model.vo.RecvMainParamVo;

/** 
 * @ClassName: RecvMainApprovedService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:52:52 
 *  
 */
public interface RecvMainApprovedService {
	public ApprovedInfoBo saveApprovedInfo(RecvMainParamVo params);
}
