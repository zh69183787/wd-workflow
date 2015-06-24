/**
 * 
 */
package com.wonders.dataExchange.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.attach.model.bo.AttachFile;
import com.wonders.attach.service.FjshService;
import com.wonders.common.model.vo.TaskUserVo;
import com.wonders.dataExchange.constants.DataExchangeMessage;
import com.wonders.dataExchange.dao.DataExchangeDao;
import com.wonders.dataExchange.factory.XmlParser;
import com.wonders.dataExchange.model.common.bo.DwWorkflowInterface;
import com.wonders.dataExchange.model.common.vo.ResultInfo;
import com.wonders.dataExchange.model.common.vo.UserInfo;
import com.wonders.dataExchange.model.common.xml.RegisterDataVo;
import com.wonders.dataExchange.model.common.vo.MainParamVo;
import com.wonders.dataExchange.model.flow.vo.ReviewMainVo;
import com.wonders.dataExchange.model.flow.xml.ReviewMainXml;
import com.wonders.dataExchange.service.DataExchangeCheckService;
import com.wonders.dataExchange.util.DataExchangeUtil;
import com.wonders.dataExchange.util.LoginUtil;
import com.wonders.util.SpringBeanUtil;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: DataExchangeCheckServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年3月13日 上午9:41:15 
 *  
 */

@Service("dataExchangeCheckService")
@Scope("prototype")
public class DataExchangeCheckServiceImpl implements DataExchangeCheckService{
	private DataExchangeDao dao;
	private ResultInfo resultInfo = new ResultInfo();
	private MainParamVo params;
	private DwWorkflowInterface bo;
	
	@Override
	public void init(MainParamVo params) {
		this.params = params;
		if(params!=null){
			this.resultInfo = params.resultInfo;
		}
		
	}
	
	@Override
	public void checkWorkflow(){
		if(checkStatus()){
			checkXml();
			checkRegisterInfo();
		}
	}
	
	private void checkRegisterInfo(){
		UserInfo userInfo = params.userInfo;
		String taskuser = params.registerVo.getLoginName();
		String loginName = LoginUtil.getUserLoginName(userInfo);
		Map<String,TaskUserVo> map = userInfo.getMap();
		if (map != null && map.containsKey(taskuser)&&!"".equals(loginName)){		
		}else{
			resultInfo.addErrors(DataExchangeMessage.CHECK_WRONG_REGISTER);
			return;
		}
	}
	
	private boolean checkStatus(){
		String id = StringUtil.getNotNullValueString(params.id);
		Map<String,Object> filter = new HashMap<String,Object>();
		filter.put("id", id);
		filter.put("removed", "0");
		filter.put("status", "0");
		filter.put("autoLaunch", "0");
		bo = (DwWorkflowInterface) this.dao.
				findBoCount("from DwWorkflowInterface t where 1=1",filter);
		if(bo == null ){
			resultInfo.addErrors(DataExchangeMessage.FAIL_TO_FIND_FLOW);
			return false;
		}
		return true;
	}
	
	
	
	private void checkXml() {		
		try {
			XmlParser parse = (XmlParser) SpringBeanUtil.getBean(params.type+"XmlParser");
			parse.parse(params, bo);
		}catch(Exception e){
			e.printStackTrace();
			resultInfo.addErrors(DataExchangeMessage.FAIL_TO_REVERT_BO);
			return;
		}
		
	}

	
	
	
	public DataExchangeDao getDao() {
		return dao;
	}
	@Autowired(required=false)
	public void setDao(@Qualifier("dataExchangeDao")DataExchangeDao dao) {
		this.dao = dao;
	}



}
