/**
 * 
 */
package com.wonders.project.workflow.process.discard.service;

import com.wonders.project.workflow.common.model.vo.UserInfo;
import com.wonders.project.workflow.process.discard.model.bo.DiscardMainBo;

/** 
 * @ClassName: DiscardMainMessageService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:52:52 
 *  
 */
public interface DiscardMainMessageService {
	public void sendMessageOnFinish(DiscardMainBo bo, UserInfo userInfo);
}
