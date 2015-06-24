/**   
* @Title: ContractReviewXmlParser.java 
* @Package com.wonders.dataExchange.factory 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年4月16日 下午8:28:06 
* @version V1.0   
*/
package com.wonders.dataExchange.factory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.attach.model.bo.AttachFile;
import com.wonders.attach.service.FjshService;
import com.wonders.dataExchange.dao.DataExchangeDao;
import com.wonders.dataExchange.model.common.bo.DwWorkflowInterface;
import com.wonders.dataExchange.model.common.vo.MainParamVo;
import com.wonders.dataExchange.model.common.xml.RegisterDataVo;
import com.wonders.dataExchange.model.flow.vo.ReviewMainVo;
import com.wonders.dataExchange.model.flow.xml.ReviewMainXml;
import com.wonders.dataExchange.util.DataExchangeUtil;

/** 
 * @ClassName: ContractReviewXmlParser 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年4月16日 下午8:28:06 
 *  
 */
@Transactional(value = "txManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service("contractReviewXmlParser")
public class ContractReviewXmlParser implements XmlParser{
	private DataExchangeDao dao;
	private FjshService fjshService;
	/** 
	* @Title: parse 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param params
	* @param @param content    设定文件 
	* @throws 
	*/
	@Override
	public void parse(MainParamVo params, DwWorkflowInterface bo) {
		String content = bo.getContent();
		ReviewMainXml xml = (ReviewMainXml) DataExchangeUtil.parseXml(content,ReviewMainXml.class);
		ReviewMainVo mainVo = xml.getMainVo();
		RegisterDataVo registerVo = xml.getRegisterVo();
	    List<AttachFile> attachList = xml.getAttachVo().getAttachList();
	    String groupId = mainVo.getAttach();
	    
	    if(groupId == null || groupId.length() == 0){
	    	groupId = this.fjshService.uploadHttpFiles(attachList);
	    	mainVo.setAttach(groupId);
	    	bo.setContent(DataExchangeUtil.rebuildXml(content,groupId));
	    	//System.out.println(bo.getContent());
			this.dao.update(bo);
	    }
	    
	    params.vo = DataExchangeUtil.generateMap(mainVo);
	    params.registerVo = registerVo;
	}
	
	
	public FjshService getFjshService() {
		return fjshService;
	}
	@Autowired(required=false)
	public void setFjshService(@Qualifier("fjshService")FjshService fjshService) {
		this.fjshService = fjshService;
	}
	public DataExchangeDao getDao() {
		return dao;
	}
	@Autowired(required=false)
	public void setDao(@Qualifier("dataExchangeDao")DataExchangeDao dao) {
		this.dao = dao;
	}


}
