/**
 * 
 */
package com.wonders.project.workflow.process.discard.dao;

import com.wonders.project.workflow.process.discard.model.bo.DiscardMainBo;

/** 
 * @ClassName: DiscardMainDao 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 下午7:38:14 
 *  
 */
public interface DiscardMainDao {
	public DiscardMainBo findBoByParam(String pname,String pincident);
	
}
