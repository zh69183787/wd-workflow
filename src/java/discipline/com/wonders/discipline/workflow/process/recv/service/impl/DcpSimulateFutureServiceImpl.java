/**
 * 
 */
package com.wonders.discipline.workflow.process.recv.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.discipline.workflow.common.service.CommonService;
import com.wonders.discipline.workflow.process.recv.constants.DcpRecvMainStepConstants;
import com.wonders.discipline.workflow.process.recv.model.bo.DcpSimulateFutureBo;
import com.wonders.discipline.workflow.process.recv.model.vo.DcpRecvMainParamVo;
import com.wonders.discipline.workflow.process.recv.model.vo.DcpRecvMainVo;
import com.wonders.discipline.workflow.process.recv.service.DcpSimulateFutureService;

/** 
 * @ClassName: SimulateSubFutureServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-8-8 下午8:26:20 
 *  
 */

@Service("discipline-dcpSimulateFutureService")
@Scope("prototype")
public class DcpSimulateFutureServiceImpl implements DcpSimulateFutureService{
	private CommonService service;

	/** 
	* @Title: saveFutureInfo 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param params
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public DcpSimulateFutureBo saveFutureInfo(DcpRecvMainParamVo params) {
		DcpSimulateFutureBo bo = new DcpSimulateFutureBo();
		DcpRecvMainVo vo = params.vo;
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");	
		String steplabel = params.getProcessParamValue("steplabel");
		bo.setInsSignLdsId(vo.getComLeaderLoginName());
		bo.setInsSignLdsName(vo.getComLeaderName());
		bo.setInsSignLdsDepId(vo.getComLeaderDeptId());
		bo.setInsSignSecsId(vo.getDcpLoginName());
		bo.setInsSignSecsName(vo.getDcpName());
		bo.setInsSignSecsDepId(vo.getDcpDeptId());
		bo.setDepLdsId(vo.getDepLdsId());
		bo.setDepsName(vo.getDepsName());
		bo.setDepRecvsId(vo.getDepRecvsId());
		bo.setDepsId(vo.getDepsId());
		bo.setPincident(pincident);
		bo.setPname(pname);
		bo.setSuggest(vo.getSuggest());
		if(DcpRecvMainStepConstants.STEPNAME_REGISTER.equals(steplabel)
				||DcpRecvMainStepConstants.STEPNAME_RETURN_MODIFY.equals(steplabel)){
			bo.setChoice(vo.getChoice());
		}
		
		this.service.save(bo);
		
		return bo;
	}

	
	
	
	
	
	public CommonService getService() {
		return service;
	}
	
	@Autowired(required=false)
	public void setService(@Qualifier(value="discipline-commonService")CommonService service) {
		this.service = service;
	}

}
