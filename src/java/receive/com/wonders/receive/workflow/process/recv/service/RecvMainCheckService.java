/**
 * 
 */
package com.wonders.receive.workflow.process.recv.service;

import com.wonders.receive.workflow.process.recv.model.vo.RecvMainParamVo;

/** 
 * @ClassName: RecvMainService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:36 
 *  
 */
public interface RecvMainCheckService {
	public void init(RecvMainParamVo params);
	public boolean checkFlowIsInProcess();
	
	public void checkForward();
	
	public void checkRegister();

	public void checkGetSerialNo();
	
	public void checkRegisterModify();
	
	public void checkRecord();
	
	
}
