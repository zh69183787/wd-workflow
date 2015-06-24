/**   
* @Title: XmlParser.java 
* @Package com.wonders.dataExchange.factory 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年4月16日 下午8:26:18 
* @version V1.0   
*/
package com.wonders.dataExchange.factory;

import com.wonders.dataExchange.model.common.bo.DwWorkflowInterface;
import com.wonders.dataExchange.model.common.vo.MainParamVo;

/** 
 * @ClassName: XmlParser 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年4月16日 下午8:26:18 
 *  
 */
public interface XmlParser {
	public void parse(MainParamVo params, DwWorkflowInterface bo);
}
