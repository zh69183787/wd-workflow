/**
 * 
 */
package com.wonders.receive.workflow.process.sign.service;

import com.wonders.receive.workflow.process.sign.model.vo.SignSubParamVo;

/** 
 * @ClassName: RecvMainService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:36 
 *  
 */
public interface SignSubCheckService {
	public void init(SignSubParamVo params);
	
	public void checkSecretaryDeal();

	public void checkLeaderDeal();
	
	
	
	
}
