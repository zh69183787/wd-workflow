/**
 * 
 */
package com.wonders.discipline.workflow.process.recv.service;

import com.wonders.receive.workflow.approve.model.bo.ApprovedInfoBo;
import com.wonders.discipline.workflow.process.recv.model.vo.DcpRecvMainParamVo;

/** 
 * @ClassName: DcpRecvMainApprovedService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:52:52 
 *  
 */
public interface DcpRecvMainApprovedService {
	public ApprovedInfoBo saveApprovedInfo(DcpRecvMainParamVo params);
}
