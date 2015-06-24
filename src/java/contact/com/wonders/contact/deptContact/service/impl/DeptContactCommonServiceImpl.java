package com.wonders.contact.deptContact.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.contact.approvedInfo.model.bo.TApprovedinfo;
import com.wonders.contact.common.model.vo.UserInfo;
import com.wonders.contact.common.service.CommonService;
import com.wonders.contact.common.util.CommonUtil;
import com.wonders.contact.common.util.LoginUtil;
import com.wonders.contact.common.util.SimpleLogger;
import com.wonders.contact.deptContact.constant.DeptContactConstants;
import com.wonders.contact.deptContact.constant.DeptContactFlowConstants;
import com.wonders.contact.deptContact.model.bo.TDeptContactConfig;
import com.wonders.contact.deptContact.model.bo.TDeptContactMain;
import com.wonders.contact.deptContact.model.bo.TDeptContactReference;
import com.wonders.contact.deptContact.model.vo.DeptContactOperateVo;
import com.wonders.contact.deptContact.model.vo.DeptContactParamVo;
import com.wonders.contact.deptContact.service.DeptContactCommonService;
import com.wonders.util.StringUtil;

@Service("contact-deptContactCommonService")
@Scope("prototype")
public class DeptContactCommonServiceImpl implements DeptContactCommonService {
	SimpleLogger log = new SimpleLogger(this.getClass());
	
	private CommonService service;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TDeptContactConfig> getDeptContactConfigInfo(String processname,String stepname){
		List<TDeptContactConfig> list = new ArrayList<TDeptContactConfig>();
		/*
SimpleLogger.getLogger(this.getClass()).debug("ByJson");
		list = getDeptContactConfigInfoFromJson();
		if(list!=null&&list.size()>0) return list;
		*/
//SimpleLogger.getLogger(this.getClass()).debug("ByDatabase");
		
		String hql = "from TDeptContactConfig t where t.processname=? and t.stepname = ? and t.removed != 1";
		Object[] params = {processname,stepname};
		
		list = service.ListByHql(hql, params);
		
		return list;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public String getReferenceIds(String mainBoId) {
		if(mainBoId==null||mainBoId.length()==0) return "";
		
		String hql = "from TDeptContactReference t where t.mainId='"+mainBoId+"' and t.removed!=1";
		List<TDeptContactReference> list = service.ListByHql(hql);
		List<String> result = new ArrayList<String>();
		for(int i=0;i<list.size();i++){
			result.add(list.get(i).getRefId());
		}
		
		return CommonUtil.listToStringBySplit(result);
	}
	
	
	@Override
	public void saveReferences(DeptContactParamVo params) {
		
		String refs = StringUtil.getNotNullValueString(params.getParamObject(DeptContactConstants.PARAMS_KEY_REF_ID));
		
		if(params.mainBo!=null&&params.mainBo.getId()!=null&&params.mainBo.getId().length()>0){
			String hql = "update TDeptContactReference t set t.removed=1 where t.mainId='"+params.mainBo.getId()+"'";
			service.UpdateByHql(hql);
		}
		
		List<String> refList = new ArrayList<String>();
		refList = CommonUtil.stringsToList(refs);
		
		for(int i=0;i<refList.size();i++){
			
			String refId = StringUtil.getNotNullValueString(refList.get(i));
			
			if(refId.length()>0){
				TDeptContactReference refBo = new TDeptContactReference(); 
				
				refBo.setMainId(params.mainBo.getId());
				refBo.setRefId(refId);
				refBo.setOrders(Long.valueOf(i));
				
				refBo.setOperateDate(params.mainBo.getOperateDate());
				refBo.setOperateUser(params.mainBo.getOperateUser());
				refBo.setOperateName(params.mainBo.getOperateName());
				
				service.save(refBo);
			}
		}
	}
	
	@Override
	public TApprovedinfo generateApprovedInfo(DeptContactParamVo params) {
		
		UserInfo userInfo = params.userInfo;
		TApprovedinfo tai = new TApprovedinfo();
		
		String taskUserLoginName = LoginUtil.getUserLoginName(userInfo);
		
		DeptContactOperateVo operateVo = params.operateVo;
		
		/**默认为主流程*/
		if(operateVo==null){
			operateVo = new DeptContactOperateVo();
			operateVo.setSteplabel(DeptContactFlowConstants.STEPNAME_BEGIN);
			operateVo.setFlowType(DeptContactConstants.STATUS_MAIN_STR);
		}
		
		String processName = params.getProcessParamValue("cname");
		String incidentNo = params.getProcessParamValue("cincident");
		
		String flowType = operateVo.getFlowType();
		
		String steplabel = operateVo.getSteplabel();
		
		String suggest = StringUtil.getNotNullValueString(operateVo.getSuggest());
		String fileGroupId = StringUtil.getNotNullValueString(operateVo.getAttachId());
		
		String optionCode = "";
		
		/**添加*/
		if(DeptContactFlowConstants.STEPNAME_BEGIN.equals(steplabel)){
			optionCode = "BEGIN";
		}
		
		/**申请*/
		if(DeptContactFlowConstants.STEPNAME_APPLY.equals(steplabel)){
			/**签发*/
			if(DeptContactConstants.CHOICE_APPLY_TO_SIGN.equals(operateVo.getChoice())){
				optionCode = "APPLY_TO_SIGN";
				tai.setAgree(1);
			}
			/**取消*/
			if(DeptContactConstants.CHOICE_APPLY_TO_CANCEL.equals(operateVo.getChoice())){
				optionCode = "APPLY_TO_CANCEL";
				tai.setDisagree(1);
			}
		}
		
		/**签发*/
		if(DeptContactFlowConstants.STEPNAME_SIGN.equals(steplabel)){
			/**同意签发*/
			if(DeptContactConstants.CHOICE_SIGN_AGREE.equals(operateVo.getChoice())){
				optionCode = "SIGN_AGREE";
				tai.setAgree(1);
			}
			/**返回修改*/
			if(DeptContactConstants.CHOICE_SIGN_TO_APPLY.equals(operateVo.getChoice())){
				optionCode = "SIGN_TO_APPLY";
				tai.setReturned(1);
			}
			
			/**处理完成，结束下级流程*/
			if(DeptContactConstants.CHOICE_SIGN_FINISH.equals(operateVo.getChoice())){
				optionCode = "SIGN_FINISH";
				tai.setAgree(1);
			}
		}
		
		tai.setDeptId(userInfo.getDeptId());
		tai.setDept(userInfo.getDeptName()); 
		tai.setUsername(taskUserLoginName);
		tai.setUserfullname(userInfo.getUserName());
		
		tai.setUpddate(new Date());
		
		tai.setProcess(processName);
		tai.setIncidentno(Long.parseLong(incidentNo));
		tai.setStepname(steplabel);
		
		tai.setFileGroupId(fileGroupId);
		
		tai.setRemark(suggest);
		tai.setStatus(1);
		
		tai.setOptionCode(optionCode+":"+flowType);
		//service.save(tai);
		
//log.debug("添加意见:id="+tai.getGuid());
		
		return tai;
	}
	
	@Override
	public void saveApprovedInfo(DeptContactParamVo params){
		TApprovedinfo tai = generateApprovedInfo(params);
		service.save(tai);
	}
	
	
	public void addCopyUnit(){}
	
	@Override
	public String getSerialNumberText(TDeptContactMain mainBo){
		/*生成显示编号*/
		/*
		 * TODO 替换为部门描述
		 */
		if(mainBo.getSerial()!=null&&mainBo.getSerial()>0){
			return mainBo.getCreateDeptname()+"-"+mainBo.getContactDate().substring(0,4)+"-"+mainBo.getSerial();
		}else{
			return "";
		}
	}
	
	/**获得编号
	 * @param deptId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer getNextSerialNumber(TDeptContactMain mainBo){
		String hql = "select nvl(max(nvl(t.serial,0)),0)+1 from TDeptContactMain t " +
					" where t.createDeptid = '"+mainBo.getCreateDeptid()+"'" +
					" and substr(t.contactDate,0,4) = substr('"+mainBo.getContactDate()+"',0,4)";
		
		Integer ret = null;
		try{
			List<Integer> list = service.ListByHql(hql);
			if(list.size()>0){
				ret = list.get(0);
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
//log.debug("serial="+ret);
		return ret;
	}
	
	
	/*
	private List<TDeptContactConfig> getDeptContactConfigInfoFromJson(){
		List<Map<String,String>> source = new ArrayList<Map<String,String>>();
		List<TDeptContactConfig> list = new ArrayList<TDeptContactConfig>();
		
		String json = CommonUtil.getJsonByPath("deptContactConfig.json");
		source = CommonUtil.getInstanceByJson(json, source.getClass());
		
		for(int i=0;source!=null&&i<source.size();i++){
			Map<String,String> map = source.get(i);
			TDeptContactConfig config  = new TDeptContactConfig();
			
			try {
				BeanUtils.populate(config, map);
				list.add(config);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
//System.out.println("list.size()="+list.size());
		return list;
	}
	*/
	
	
	
	
	public CommonService getService() {
		return service;
	}
	
	@Autowired(required=false)
	public void setService(@Qualifier(value="contact-commonService")CommonService service) {
		this.service = service;
	}
	
}
