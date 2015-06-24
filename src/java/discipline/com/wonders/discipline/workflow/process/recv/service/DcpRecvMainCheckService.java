/**
 * 
 */
package com.wonders.discipline.workflow.process.recv.service;

import com.wonders.discipline.workflow.process.recv.model.vo.DcpRecvMainParamVo;

/** 
 * @ClassName: DcpRecvMainService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:36 
 *  
 */
public interface DcpRecvMainCheckService {
	public void init(DcpRecvMainParamVo params);
	public boolean checkFlowIsInProcess();
	
	public void checkForward();
	
	public void checkRegister();

	public void checkDeptLeader();
	
	public void checkRegisterModify();
	
	public void checkPersonDeal();
	
	public void checkSummary();
	
	public void checkDealFinish();
	
	
}
