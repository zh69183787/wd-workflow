/**
 * 
 */
package com.wonders.dataExchange.service;

import com.wonders.dataExchange.model.common.vo.MainParamVo;


/** 
 * @ClassName: DataExchangeCheckService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年3月13日 上午9:41:02 
 *  
 */
public interface DataExchangeCheckService {
	public void init(MainParamVo params);
	public void checkWorkflow();
}
