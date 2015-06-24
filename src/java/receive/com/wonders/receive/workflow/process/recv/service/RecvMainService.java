/**
 * 
 */
package com.wonders.receive.workflow.process.recv.service;

import com.wonders.receive.workflow.process.recv.model.vo.RecvMainParamVo;
import com.wonders.receive.workflow.process.recv.model.vo.RecvMainVo;

/** 
 * @ClassName: RecvMainService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:36 
 *  
 */
public interface RecvMainService {
	public void init(RecvMainParamVo params);
	public void flowStepRegister(RecvMainParamVo params);
	public void flowStepGetSerialNo(RecvMainParamVo params);
	public void flowStepRegisterModify(RecvMainParamVo params);
	public void flowStepRecord(RecvMainParamVo params);	
	public RecvMainVo flowStepForward(RecvMainParamVo params);	
	public RecvMainVo viewStepForward(RecvMainParamVo params);	
	
	public RecvMainVo print(RecvMainParamVo params);
}
