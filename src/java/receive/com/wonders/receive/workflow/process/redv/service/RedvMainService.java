/**
 * 
 */
package com.wonders.receive.workflow.process.redv.service;

import com.wonders.receive.workflow.process.redv.model.vo.RedvMainParamVo;
import com.wonders.receive.workflow.process.redv.model.vo.RedvMainVo;

/** 
 * @ClassName: RedvMainService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:36 
 *  
 */
public interface RedvMainService {
	public void init(RedvMainParamVo params);
	public void flowStepRegister(RedvMainParamVo params);
	public void flowStepLeaderCheck(RedvMainParamVo params);
	public void flowStepRegisterModify(RedvMainParamVo params);
	public void flowStepRecord(RedvMainParamVo params);	
	public RedvMainVo flowStepForward(RedvMainParamVo params);	
	public RedvMainVo viewStepForward(RedvMainParamVo params);	
	
	public RedvMainVo print(RedvMainParamVo params);
}
