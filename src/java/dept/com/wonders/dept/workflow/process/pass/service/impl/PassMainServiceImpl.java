/**
 * 
 */
package com.wonders.dept.workflow.process.pass.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.dept.workflow.common.model.vo.ResultInfo;
import com.wonders.dept.workflow.common.model.vo.UserInfo;
import com.wonders.dept.workflow.common.service.CommonService;
import com.wonders.dept.workflow.common.util.LoginUtil;
import com.wonders.dept.workflow.constants.UltimusConstants;
import com.wonders.dept.workflow.process.pass.constants.PassMainConstants;
import com.wonders.dept.workflow.process.pass.constants.PassMainMessage;
import com.wonders.dept.workflow.process.pass.dao.PassMainDao;
import com.wonders.dept.workflow.process.pass.model.bo.PassMainBo;
import com.wonders.dept.workflow.process.pass.model.vo.PassMainParamVo;
import com.wonders.dept.workflow.process.pass.model.vo.PassMainVo;
import com.wonders.dept.workflow.process.pass.service.PassMainApprovedService;
import com.wonders.dept.workflow.process.pass.service.PassMainService;
import com.wonders.dept.workflow.process.util.ProcessUtil;
import com.wonders.dept.workflow.process.util.TextUtil;
import com.wonders.dept.workflow.util.FlowUtil;
import com.wonders.deptDoc.service.DeptDocService;
import com.wonders.todo.dao.TodoItemDao;
import com.wonders.util.DateUtil;
import com.wonders.util.StringUtil;

/** 
 * @ClassName: PassMainServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-21 上午9:38:17 
 *  
 */
@Service("dept-passMainService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class PassMainServiceImpl implements PassMainService{
	
	//2014-10-18 zhoushun
	@Autowired
	@Qualifier("passDeptDocService")
	private DeptDocService passDeptDocService;
	private CommonService commonService;
	private PassMainApprovedService PassMainApprovedService;
	private TodoItemDao todoItemDao;
	private PassMainParamVo params;
	private PassMainVo vo;
	private UserInfo userInfo;
	private PassMainDao dao;
	private ResultInfo resultInfo;
	
	@Override
	public void init(PassMainParamVo params) {
		this.params = params;
		this.vo = params.vo;
		this.userInfo = params.userInfo;
		//this.userInfo.initTaskUser(params.getProcessParamValue("taskuser"));
		this.resultInfo = params.resultInfo;
	}
	
	
	private PassMainVo forward(PassMainParamVo params){
		//init(params);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
//		String cname = params.getProcessParamValue("cname");
//		String cincident = params.getProcessParamValue("cincident");
//		String steplabel = params.getProcessParamValue("steplabel");
		PassMainVo vo = new PassMainVo();
		PassMainBo bo = this.dao.findBoByParam(pname, pincident);
		try {
			BeanUtils.copyProperties(vo, bo);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		
		return vo;
	}
	
	@Override
	public PassMainVo flowStepForward(PassMainParamVo params){
		return this.forward(params);
	}
	
	@Override
	public PassMainVo viewStepForward(PassMainParamVo params){
		return this.forward(params);
	}
	
	@Override
	public PassMainVo print(PassMainParamVo params){
		return this.forward(params);
	}
	
	@Override
	public void flowStepRegister(PassMainParamVo params){
		if(resultInfo.getOperateFlag()){
			String pname = UltimusConstants.PASS_DICTIONARY_NAME;
			//init(params);
			PassMainBo bo = new PassMainBo();
			try {
				BeanUtils.copyProperties(bo, vo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			this.commonService.save(bo);
			int incidentNo = flowRegister(bo.getId());
			if(incidentNo > 0){	
				bo.setProcess(pname);
				bo.setIncident(incidentNo+"");
				bo.setRegTime(DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
				bo.setRegDeptName(userInfo.getDeptName());
				bo.setRegLoginName(userInfo.getLoginName());
				bo.setRegName(userInfo.getUserName());
				bo.setRegDeptId(userInfo.getDeptId());
				this.commonService.update(bo);
				params.addProcessParam("pname", pname);
				params.addProcessParam("pincident", incidentNo+"");
				PassMainApprovedService.saveApprovedInfo(params);
				updateTodoItem();
			}else{
				resultInfo.addErrors(PassMainMessage.FAIL_TO_LAUNCH_PROCESS);
				throw new RuntimeException(PassMainMessage.FAIL_TO_LAUNCH_PROCESS.textCn);
			}
		}
	}
	
	@Override
	public void flowStepDealPerson(PassMainParamVo params){
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowDealPerson()){
				PassMainApprovedService.saveApprovedInfo(params);
				resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(PassMainMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(PassMainMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}
	
	@Override
	public void flowStepDeptLeader(PassMainParamVo params){
		if(resultInfo.getOperateFlag()){
			//init(params);
			if(flowDeptLeader()){
				PassMainApprovedService.saveApprovedInfo(params);
				resultInfo.url = TextUtil.generateMainUrl(params);
			}else{
				resultInfo.addErrors(PassMainMessage.FAIL_TO_COMPLETE_STEP);
				throw new RuntimeException(PassMainMessage.FAIL_TO_COMPLETE_STEP.textCn);
			}
		}
	}
	
	
	
	
	

	/** 
	* @Title: flowRegister 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws 
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private int flowRegister(String tableId) {
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		PassMainBo bo = (PassMainBo) this.commonService.load(tableId, PassMainBo.class);
		String pname = UltimusConstants.PASS_DICTIONARY_NAME;
		String summary = pname + "-" +bo.getTitle();
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		this.vo.initList();
		Map map = new HashMap();
		
		if(PassMainConstants.CHOICE_FINISH_FLOW.equals(choice)){
			map.put("是否结束传阅", "1");
		}else if(PassMainConstants.CHOICE_SELECT_DEPT_PERSON_FINISH.equals(choice)){
			map.put("是否结束传阅", "0");
			map.put("是否回到领导", "0");
			map.put("部门领导账号", userInfo.getLoginName());
			map.put("部门领导账号拼接", FlowUtil.getLoginNameLink(userInfo.getLoginName(),userInfo.getDeptId()));
			FlowUtil.putUltimusMap("部门业务人员账号",vo.getDealPersonList(),map);
			map.put("部门业务人员账号拼接", 
					FlowUtil.getLoginNameLink(vo.getDealPersonList(),userInfo.getDeptId())
					);
		}else{
			map.put("是否结束传阅", "0");
			map.put("是否回到领导", "1");
			map.put("部门领导账号", userInfo.getLoginName());
			map.put("部门领导账号拼接", FlowUtil.getLoginNameLink(userInfo.getLoginName(),userInfo.getDeptId()));
			FlowUtil.putUltimusMap("部门业务人员账号",vo.getDealPersonList(),map);
			map.put("部门业务人员账号拼接", 
					FlowUtil.getLoginNameLink(vo.getDealPersonList(),userInfo.getDeptId())
					);
		}
		
		int incidentNo = ProcessUtil.launchProcess(pname, taskUserLoginName, summary, map);
		
		return incidentNo;
	}
	
	
	/** 
	* @Title: flowDeptLeader 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws 
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean flowDeptLeader() {
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String summary = FlowUtil.getSummaryByProcessInfo(pname, pincident);
		String choice = StringUtil.getNotNullValueString(vo.getChoice());
		Map map = new HashMap();
		this.vo.initList();
		if(PassMainConstants.CHOICE_FINISH_FLOW.equals(choice)){
			map.put("是否结束传阅", "1");
		}else if(PassMainConstants.CHOICE_SELECT_DEPT_PERSON_FINISH.equals(choice)){
			map.put("是否结束传阅", "0");
			map.put("是否回到领导", "0");
			map.put("部门领导账号", userInfo.getLoginName());
			map.put("部门领导账号拼接", FlowUtil.getLoginNameLink(userInfo.getLoginName(),userInfo.getDeptId()));
			FlowUtil.putUltimusMap("部门业务人员账号",vo.getDealPersonList(),map);
			map.put("部门业务人员账号拼接", 
					FlowUtil.getLoginNameLink(vo.getDealPersonList(),userInfo.getDeptId())
					);
		}else{
			map.put("是否结束传阅", "0");
			map.put("是否回到领导", "1");
			map.put("部门领导账号", userInfo.getLoginName());
			map.put("部门领导账号拼接", FlowUtil.getLoginNameLink(userInfo.getLoginName(),userInfo.getDeptId()));
			FlowUtil.putUltimusMap("部门业务人员账号",vo.getDealPersonList(),map);
			map.put("部门业务人员账号拼接", 
					FlowUtil.getLoginNameLink(vo.getDealPersonList(),userInfo.getDeptId())
					);
		}
		
		boolean flag=ProcessUtil.launchProcessStep(pname, taskUserLoginName, Integer.parseInt(pincident), steplabel, summary, "", map);
		
		return flag;
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean flowDealPerson() {
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		String pname = params.getProcessParamValue("pname");
		String pincident = params.getProcessParamValue("pincident");
		String steplabel = params.getProcessParamValue("steplabel");
		String summary = FlowUtil.getSummaryByProcessInfo(pname, pincident);
		Map map = new HashMap();
		
		this.passDeptDocService.addDocs(pname, pincident, userInfo.getLoginName(),
					userInfo.getUserName(),userInfo.getDeptId());
		
		boolean flag=ProcessUtil.launchProcessStep(pname, taskUserLoginName, Integer.parseInt(pincident), steplabel, summary, "", map);
		
		return flag;
	}
	
	
	private void updateTodoItem(){
		String todoId = params.getProcessParamValue("todoId");
		if(todoId != null && todoId.length() > 0){
			this.todoItemDao.updateById(todoId);
		}
	}
	
	
	public CommonService getCommonService() {
		return commonService;
	}
	
	@Autowired(required=false)
	public void setCommonService(@Qualifier(value="dept-commonService")CommonService commonService) {
		this.commonService = commonService;
	}

	public PassMainApprovedService getPassMainApprovedService() {
		return PassMainApprovedService;
	}

	@Autowired(required=false)
	public void setPassMainApprovedService(
			@Qualifier(value="dept-passMainApprovedService")PassMainApprovedService PassMainApprovedService) {
		this.PassMainApprovedService = PassMainApprovedService;
	}

	
	public PassMainDao getDao() {
		return dao;
	}

	@Autowired(required=false)
	public void setDao(@Qualifier("dept-passMainDao")PassMainDao dao) {
		this.dao = dao;
	}


	public TodoItemDao getTodoItemDao() {
		return todoItemDao;
	}

	@Autowired(required=false)
	public void setTodoItemDao(@Qualifier("todoItemDao")TodoItemDao todoItemDao) {
		this.todoItemDao = todoItemDao;
	}
	
	
}
