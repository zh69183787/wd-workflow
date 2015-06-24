/**
 * 
 */
package com.wonders.dataExchange.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.wonders.dataExchange.model.common.vo.ParamVo;
import com.wonders.dataExchange.model.common.vo.MainParamVo;
import com.wonders.dataExchange.service.DataExchangeService;
import com.wonders.dataExchange.util.ActionWriter;
import com.wonders.dataExchange.util.LoginUtil;

/** 
 * @ClassName: DataExchangeAction 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年3月12日 下午1:33:42 
 *  
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/dataExchange")
@Controller("dataExchangeAction")
@Scope("prototype")
public class DataExchangeAction extends AbstractParamAction{
	private ActionWriter aw = new ActionWriter(response);
	public MainParamVo params;
	private DataExchangeService service;
	private String id;
	public DataExchangeAction(){
		if(params==null) params = new MainParamVo();
		params.userInfo = LoginUtil.getUserInfo(session);
	}
	
	public DataExchangeService getService() {
		return service;
	}
	@Autowired(required=false)
	public void setService(@Qualifier("dataExchangeService")DataExchangeService service) {
		this.service = service;
	}



	@Action(value="view",results={
			 @Result(name="contractReview",location="/dataExchange/contract-review.jsp"),
			 @Result(name="docReceive",location="/dataExchange/doc-receive.jsp")
	})
	public String view(){
		params.id = id;
		try {
			service.flowStepWorkflow(params);
			return params.type;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		aw.writeJsonWithAnnotation(params.resultInfo.getErrors());	
		return null;
		
	}

	@Action(value="updateStatus")
	public String updateStatus(){
		String flag = "1";
		try{
			service.updateStatus(id);
		}catch(Exception e){
			flag = "0";
		}
		aw.writeAjax(flag);
		return null;
	}
	
	/** 
	* @Title: getParams 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public ParamVo getParams() {
		// TODO Auto-generated method stub
		return params;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
