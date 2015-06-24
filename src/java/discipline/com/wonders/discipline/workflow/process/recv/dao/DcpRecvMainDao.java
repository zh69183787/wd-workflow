/**
 * 
 */
package com.wonders.discipline.workflow.process.recv.dao;

import com.wonders.discipline.workflow.process.recv.model.bo.DcpRecvMainBo;

/** 
 * @ClassName: DcpRecvMainDao 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 下午7:38:14 
 *  
 */
public interface DcpRecvMainDao {
	public DcpRecvMainBo findBoByParam(String pname,String pincident);
}
