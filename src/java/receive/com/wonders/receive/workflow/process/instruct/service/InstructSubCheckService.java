/**
 * 
 */
package com.wonders.receive.workflow.process.instruct.service;

import com.wonders.receive.workflow.process.instruct.model.vo.InstructSubParamVo;

/** 
 * @ClassName: RecvMainService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:36 
 *  
 */
public interface InstructSubCheckService {
	public void init(InstructSubParamVo params);
	
	public void checkSecretaryDeal();

	public void checkLeaderDeal();
	
	
	
	
}
