/**
 * 
 */
package com.wonders.dept.workflow.process.pass.service;

import com.wonders.dept.workflow.process.pass.model.vo.PassMainParamVo;


/** 
 * @ClassName: PassMainService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:36 
 *  
 */
public interface PassMainCheckService {
	public void init(PassMainParamVo params);
	public boolean checkFlowIsInProcess();
	
	public void checkForward();
	
	public void checkRegister();

	public void checkDealPerson();
	
	public void checkDeptLeader();
	
	public void checkAddSign();
	
}
