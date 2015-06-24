/**
 * 
 */
package com.wonders.dept.workflow.process.pass.service;

import com.wonders.dept.workflow.process.pass.model.vo.PassMainParamVo;
import com.wonders.dept.workflow.process.pass.model.vo.PassMainVo;


/** 
 * @ClassName: PassMainService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:36 
 *  
 */
public interface PassMainService {
	public void init(PassMainParamVo params);
	public void flowStepRegister(PassMainParamVo params);
	public void flowStepDeptLeader(PassMainParamVo params);
	public void flowStepDealPerson(PassMainParamVo params);
	
	
	public PassMainVo flowStepForward(PassMainParamVo params);	
	public PassMainVo viewStepForward(PassMainParamVo params);	
	
	public PassMainVo print(PassMainParamVo params);
}
