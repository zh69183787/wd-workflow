/**
 * 
 */
package com.wonders.receive.oa.service;

import com.wonders.receive.workflow.model.vo.ParamVo;

/** 
 * @ClassName: OaService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-8-6 上午10:42:31 
 *  
 */
public interface OaService {
	public void addProcessMsg(ParamVo params);
	public void addDocsFile(ParamVo params);
	public String queryProcessMsg(ParamVo params);
	public void init(ParamVo params);
}
