/**
 * 
 */
package com.wonders.dataExchange.model.common.vo;

import java.util.Map;

import com.wonders.dataExchange.model.common.vo.ParamVo;

/** 
 * @ClassName: RecvMainParamVo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:33:57 
 *  
 */
public class MainParamVo extends ParamVo{
	public String id;
	public Map<String,Object> vo;
	public String type;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Map<String, Object> getVo() {
		return vo;
	}
	public void setVo(Map<String, Object> vo) {
		this.vo = vo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
