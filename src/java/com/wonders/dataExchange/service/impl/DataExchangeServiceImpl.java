/**
 * 
 */
package com.wonders.dataExchange.service.impl;

import java.io.File;
import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.dataExchange.constants.DataExchangeConstants;
import com.wonders.dataExchange.constants.DataExchangeMessage;
import com.wonders.dataExchange.dao.DataExchangeDao;
import com.wonders.dataExchange.model.common.bo.DwWorkflowInterface;
import com.wonders.dataExchange.model.common.vo.ResultInfo;
import com.wonders.dataExchange.model.common.vo.UserInfo;
import com.wonders.dataExchange.model.common.vo.MainParamVo;
import com.wonders.dataExchange.model.flow.vo.ReviewMainVo;
import com.wonders.dataExchange.service.DataExchangeService;
import com.wonders.util.DateUtil;

/** 
 * @ClassName: DataExchangeServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年3月11日 下午8:09:29 
 *  
 */

@Transactional(value = "txManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service("dataExchangeService")
@Scope("prototype")
public class DataExchangeServiceImpl implements DataExchangeService{
	
	private MainParamVo params;
	private ReviewMainVo vo;
	private UserInfo userInfo;
	private ResultInfo resultInfo;
	private DataExchangeDao dao;
	
	@Override
	public void init(MainParamVo params) {
		this.params = params;
		this.userInfo = params.userInfo;
		//this.userInfo.initTaskUser(params.getProcessParamValue("taskuser"));
		this.resultInfo = params.resultInfo;
		DwWorkflowInterface bo = (DwWorkflowInterface) this.dao.load(params.id, DwWorkflowInterface.class);
		params.type = DataExchangeConstants.getType(bo.getType());
		
	}
	
	
	private boolean flowWorkflow(){
		
		return true;
	}
	
	public void flowStepWorkflow(MainParamVo params){
		if(resultInfo.getOperateFlag()){
			if(flowWorkflow()){
				
			}
		}else{
			throw new RuntimeException(DataExchangeMessage.FAIL_ERROR.textCn);
		}	
	}
	
	

	public void updateStatus(String id){
		DwWorkflowInterface bo = (DwWorkflowInterface) this.dao.load(id, DwWorkflowInterface.class);
		bo.setStatus("1");
		bo.setOperateTime(DateUtil.getCurrDate(DateUtil.TIME_FORMAT));
		this.dao.update(bo);
	}
	
	public static void main(String[] args) throws Exception{
		 SAXReader reader = new SAXReader();   
		 Document document = reader.read(new File("D://s.xml"));
		 Element be = (Element)document.selectSingleNode("//root/basicData");
		 Element attchElement2 = (Element)be.selectSingleNode("./projectName");
		 //attchElement2.addElement("aaaaaaa");
		 System.out.println("1111"+attchElement2.getStringValue());
		 Element root = document.getRootElement();   
		 Element attchElement = (Element)document.selectSingleNode("//root/attachData");
		 root.remove(attchElement);
		 //JAXBContext context2 = JAXBContext.newInstance(ReviewMainXml.class);
	    // Unmarshaller unmarshaller = context2.createUnmarshaller();
	     StringWriter out = new StringWriter();
         OutputFormat format = OutputFormat.createPrettyPrint();    
         format.setEncoding("GBK");    // 指定XML编码            
         format.setIndent(true);      // 设置是否缩进
         format.setIndent("   ");     // 以空格方式实现缩进
         format.setNewlines(true);    // 设置是否换行
         format.setPadText(true);
        // XMLWriter writer = new XMLWriter(new FileWriter("D://zzz.xml"),format);        
         XMLWriter writer = new XMLWriter(out,format);
         writer.write(document);    
         writer.close();    
	    // ReviewMainXml xml = (ReviewMainXml) unmarshaller.unmarshal(
	    //		 new StringReader(out.toString()));
	     System.out.println(out.toString());
	}


	
	
	
	public DataExchangeDao getDao() {
		return dao;
	}
	
	@Autowired(required=false)
	public void setDao(@Qualifier("dataExchangeDao")DataExchangeDao dao) {
		this.dao = dao;
	}
	



	
	
}
