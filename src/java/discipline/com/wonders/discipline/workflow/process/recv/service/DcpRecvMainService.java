/**
 * 
 */
package com.wonders.discipline.workflow.process.recv.service;

import com.wonders.discipline.workflow.process.recv.model.vo.DcpRecvMainParamVo;
import com.wonders.discipline.workflow.process.recv.model.vo.DcpRecvMainVo;

/** 
 * @ClassName: DcpRecvMainService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:36 
 *  
 */
public interface DcpRecvMainService {
	public void init(DcpRecvMainParamVo params);
	public void flowStepRegister(DcpRecvMainParamVo params);
	public void flowStepDeptLeader(DcpRecvMainParamVo params);
	public void flowStepRegisterModify(DcpRecvMainParamVo params);
	public void flowStepPersonDeal(DcpRecvMainParamVo params);	
	public void flowStepSummary(DcpRecvMainParamVo params);	
	public void flowStepDealFinish(DcpRecvMainParamVo params);	
	public DcpRecvMainVo flowStepForward(DcpRecvMainParamVo params);	
	public DcpRecvMainVo viewStepForward(DcpRecvMainParamVo params);	
	
	public DcpRecvMainVo print(DcpRecvMainParamVo params);
}
