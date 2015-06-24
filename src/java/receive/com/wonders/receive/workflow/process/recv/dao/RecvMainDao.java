/**
 * 
 */
package com.wonders.receive.workflow.process.recv.dao;

import com.wonders.receive.workflow.process.recv.model.bo.RecvMainBo;

/** 
 * @ClassName: RecvMainDao 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 下午7:38:14 
 *  
 */
public interface RecvMainDao {
	public RecvMainBo findBoByParam(String pname,String pincident);
}
