/**
 * 
 */
package com.wonders.dept.workflow.process.pass.dao;

import com.wonders.dept.workflow.process.pass.model.bo.PassMainBo;


/** 
 * @ClassName: PassMainDao 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 下午7:38:14 
 *  
 */
public interface PassMainDao {
	public PassMainBo findBoByParam(String pname,String pincident);
}
