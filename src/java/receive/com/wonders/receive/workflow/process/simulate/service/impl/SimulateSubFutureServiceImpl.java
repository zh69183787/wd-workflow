/**
 * 
 */
package com.wonders.receive.workflow.process.simulate.service.impl;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.receive.workflow.common.service.CommonService;
import com.wonders.receive.workflow.process.simulate.model.bo.SimulateSubFutureBo;
import com.wonders.receive.workflow.process.simulate.model.vo.SimulateSubParamVo;
import com.wonders.receive.workflow.process.simulate.model.vo.SimulateSubVo;
import com.wonders.receive.workflow.process.simulate.service.SimulateSubFutureService;

/** 
 * @ClassName: SimulateSubFutureServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-8-8 下午8:26:20 
 *  
 */

@Service("receive-simulateSubFutureService")
@Scope("prototype")
public class SimulateSubFutureServiceImpl implements SimulateSubFutureService{
	private CommonService service;

	/** 
	* @Title: saveFutureInfo 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param params
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public SimulateSubFutureBo saveFutureInfo(SimulateSubParamVo params) {
		SimulateSubFutureBo bo = new SimulateSubFutureBo();
		SimulateSubVo vo = params.vo;
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");	
		try {
			BeanUtils.copyProperties(bo, vo);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bo.setPincident(pincident);
		bo.setPname(pname);
		
		this.service.save(bo);
		
		return bo;
	}

	
	
	
	
	
	public CommonService getService() {
		return service;
	}
	
	@Autowired(required=false)
	public void setService(@Qualifier(value="receive-commonService")CommonService service) {
		this.service = service;
	}

}
