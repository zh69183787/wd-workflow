/**
 * 
 */
package com.wonders.project.workflow.process.discard.service;

import com.wonders.project.workflow.process.discard.model.vo.DiscardMainParamVo;
import com.wonders.project.workflow.process.discard.model.vo.DiscardMainVo;

/** 
 * @ClassName: DiscardMainService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:36 
 *  
 */
public interface DiscardMainService {
	public void init(DiscardMainParamVo params);
	public void flowStepRegister(DiscardMainParamVo params);
	public void flowStepDeptLeader(DiscardMainParamVo params);
	public void flowStepRegisterModify(DiscardMainParamVo params);
	public void flowStepPersonDeal(DiscardMainParamVo params);	
	public void flowStepPreTrial(DiscardMainParamVo params);	
	public void flowStepDealFinish(DiscardMainParamVo params);	
	public DiscardMainVo flowStepForward(DiscardMainParamVo params);	
	public DiscardMainVo viewStepForward(DiscardMainParamVo params);	
	
	public DiscardMainVo print(DiscardMainParamVo params);
}
