/**
 * 
 */
package com.wonders.receive.workflow.process.redv.dao;

import com.wonders.receive.workflow.process.redv.model.bo.RedvMainBo;

/** 
 * @ClassName: RedvMainDao 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 下午7:38:14 
 *  
 */
public interface RedvMainDao {
	public RedvMainBo findBoByParam(String pname,String pincident);
}
