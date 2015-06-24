/**
 * 
 */
package com.wonders.project.workflow.approve.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.receive.workflow.approve.model.bo.ApprovedInfoBo;
import com.wonders.project.workflow.approve.model.comparator.ApprovedInfoComparator;
import com.wonders.project.workflow.approve.service.ApprovedInfoService;
import com.wonders.project.workflow.common.service.CommonService;
import com.wonders.project.workflow.util.FlowUtil;

/** 
 * @ClassName: ApprovedInfoServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-8-1 上午9:25:40 
 *  
 */
@Scope("prototype")
@Service("project-approvedInfoService")
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class ApprovedInfoServiceImpl implements ApprovedInfoService{
	private CommonService commonService;
	private static ApprovedInfoComparator com = new ApprovedInfoComparator();
	private Map<Integer,List<ApprovedInfoBo>> appRounds = new TreeMap<Integer,List<ApprovedInfoBo>>(com);
	private Map<Integer,Map<String,List<ApprovedInfoBo>>> appDeptRounds = new HashMap<Integer,Map<String,List<ApprovedInfoBo>>>();

	private Map<Integer,List<String>> deptRounds = new TreeMap<Integer,List<String>>(com);
	private Map<String,String> deptMap = new HashMap<String,String>();

	@SuppressWarnings("unchecked")
	public void queryByRounds(String pname,String pincident,String[] stepname){
		
		String hql = "from ApprovedInfoBo a where " +
				" a.status=1 and a.process=? and a.incidentno=? ";
		if(stepname!=null && stepname.length>0){
			hql+=" and ( ";
			for(int i=0;i<stepname.length;i++){
				if(i<stepname.length-1)
					hql+=" a.stepname = '"+ stepname[i] +"' or ";
				else
					hql+=" a.stepname = '"+ stepname[i] +"'";
			}
			hql+=" )";
		}
		hql += " order by a.upddate desc";
		Object[] params = new Object[]{pname,Long.valueOf(pincident)};		
		List<ApprovedInfoBo> list = (List<ApprovedInfoBo>)commonService.ListByHql(hql, params);
		if(list != null && list.size() > 0){
			for(ApprovedInfoBo bo : list){
				if(appRounds.containsKey(bo.getRounds())){
					appRounds.get(bo.getRounds()).add(bo);
				}else{
					List<ApprovedInfoBo> bolist = new ArrayList<ApprovedInfoBo>();
					bolist.add(bo);
					appRounds.put(bo.getRounds(), bolist);
				}
				
			}
		}
		
		List<Integer> rounds = FlowUtil.getMaxRound(pname, pincident);
		if(rounds != null && rounds.size() > 0){
			for(Integer r : rounds){
				if(appRounds.size() > 0 && !(appRounds.containsKey(r))){
					appRounds.put(r, null);
				}
			}
			
		}
		
		if(appRounds.size() > 0 && !(appRounds.containsKey(0))){
			appRounds.put(0, null);
		}
	
	}
	
	
	@SuppressWarnings("unchecked")
	public void queryByDeptRounds(String pname,String pincident,String[] stepname){
		
		String hql = "from ApprovedInfoBo a where " +
				" a.status=1 and a.process=? and a.incidentno=? ";
		if(stepname!=null && stepname.length>0){
			hql+=" and ( ";
			for(int i=0;i<stepname.length;i++){
				if(i<stepname.length-1)
					hql+=" a.stepname = '"+ stepname[i] +"' or ";
				else
					hql+=" a.stepname = '"+ stepname[i] +"'";
			}
			hql+=" )";
		}
		hql += " order by a.upddate desc";
		Object[] params = new Object[]{pname,Long.valueOf(pincident)};		
		List<ApprovedInfoBo> list = (List<ApprovedInfoBo>)commonService.ListByHql(hql, params);
		if(list != null && list.size() > 0){
			for(ApprovedInfoBo bo : list){
				if(deptMap.containsKey(bo.getRounds())){
				}else{
					deptMap.put(bo.getDeptId(), bo.getDept());
				}
				
				if(deptRounds.containsKey(bo.getRounds())){
					if(!(deptRounds.get(bo.getRounds()).contains(bo.getDeptId()))){
						deptRounds.get(bo.getRounds()).add(bo.getDeptId());
					}
				}else{
					List<String> deptlist = new ArrayList<String>();
					deptlist.add(bo.getDeptId());
					deptRounds.put(bo.getRounds(), deptlist);
				}
				if(appDeptRounds.containsKey(bo.getRounds())
						&& appDeptRounds.get(bo.getRounds()).containsKey(bo.getDeptId())
						){
					appDeptRounds.get(bo.getRounds()).get(bo.getDeptId()).add(bo);
				}else if(appDeptRounds.containsKey(bo.getRounds())
						&& !(appDeptRounds.get(bo.getRounds()).containsKey(bo.getDeptId()))
						){
					Map<String,List<ApprovedInfoBo>> boMap = appDeptRounds.get(bo.getRounds());
					List<ApprovedInfoBo> bolist = new ArrayList<ApprovedInfoBo>();
					bolist.add(bo);
					boMap.put(bo.getDeptId(),bolist);
				}else {
					Map<String,List<ApprovedInfoBo>> boMap = new HashMap<String,List<ApprovedInfoBo>>();
					List<ApprovedInfoBo> bolist = new ArrayList<ApprovedInfoBo>();
					bolist.add(bo);
					boMap.put(bo.getDeptId(),bolist);
					appDeptRounds.put(bo.getRounds(), boMap);
				}
				
			}
		}
	
		
		List<Integer> rounds = FlowUtil.getMaxRound(pname, pincident);
		if(rounds != null && rounds.size() > 0){
			for(Integer r : rounds){
				if(deptRounds.size() > 0 && !(deptRounds.containsKey(r))){
					deptRounds.put(r, null);
				}
			}
			
		}
		if(deptRounds.size() > 0 && !(deptRounds.containsKey(0))){
			deptRounds.put(0, null);
		}
		
	}

	/**
	 * @return the appRounds
	 */
	public Map<Integer, List<ApprovedInfoBo>> getAppRounds() {
		return appRounds;
	}


	/**
	 * @return the appDeptRounds
	 */
	public Map<Integer, Map<String, List<ApprovedInfoBo>>> getAppDeptRounds() {
		return appDeptRounds;
	}

	/**
	 * @return the deptRounds
	 */
	public Map<Integer, List<String>> getDeptRounds() {
		return deptRounds;
	}


	/**
	 * @return the deptMap
	 */
	public Map<String, String> getDeptMap() {
		return deptMap;
	}



	public CommonService getCommonService() {
		return commonService;
	}
	
	@Autowired(required=false)
	public void setCommonService(@Qualifier(value="project-commonService")CommonService commonService) {
		this.commonService = commonService;
	}

}
