package com.wonders.contact.approvedInfo.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.contact.approvedInfo.model.vo.DeptContactApprovedInfoVo;
import com.wonders.contact.approvedInfo.service.ApprovedInfoDeptContactService;
import com.wonders.contact.approvedInfo.service.DeptTimeComparator;
import com.wonders.contact.common.util.SimpleLogger;
import com.wonders.contact.deptContact.constant.DeptContactConstants;
import com.wonders.contact.external.service.ExternalService;
import com.wonders.contact.organTree.model.bo.OrganDeptBo;
import com.wonders.util.DbUtil;
import com.wonders.util.StringUtil;

/**
 * @author XFZ
 * @version 1.0 2012-6-12
 */
@Service("contact-approvedInfoDeptContactService")
@Scope("prototype")
public class ApprovedInfoDeptContactServiceImpl implements ApprovedInfoDeptContactService {
	private ExternalService externalService;
	
	SimpleLogger log = new SimpleLogger(this.getClass());
	private DeptContactApprovedInfoVo vo = new DeptContactApprovedInfoVo();
	
	private DbUtil dbUtil;
	
	/**获得对应mainBo以及其下一级意见（主流程、下级流程（主流程）、子流程、返回发起部门流程）
	 * @param mainBoId
	 * @param filterStr
	 * @return
	 */

	public List<Map<String,Object>> getApprovedInfo(String mainBoId, String filterStr){
		String sql = "select to_char(tree.type) as type,tree.pname,tree.pincident,tree.cname,tree.cincident,tree.main_unit_id as mainDeptId," +
		" t.stepname,t.option_code,t.dept_id,t.dept,t.username,t.userfullname" +
		" ,t.remark,to_char(t.upddate,'yyyy-mm-dd hh24:mi:ss') as upddate " +
		" ,to_char(t.upddate,'yyyy-mm-dd') as day,to_char(t.upddate,'hh24:mi:ss') as time " +
		" ,t.file_group_id" +
		" ,t.guid,tree.p_id,tree.c_id" +
		" from (select * from t_dept_contact_tree tree" +
		" where tree.removed!=1 and " +
		" (tree.p_id = ? or tree.c_id = ?)" +
		" ) tree " +
		" left join " +
		" (select t.* from t_approvedinfo t where t.status=1) t" +
		" on (t.process = tree.cname and t.incidentno = tree.cincident)" +
		" where 1=1" +
		filterStr +
		" order by tree.type,tree.main_unit_id,t.upddate desc";
		
		List<Map<String,Object>> list = dbUtil.getJdbcTemplate().queryForList(sql, new Object[]{mainBoId,mainBoId});
		
//log.debug("list.size()="+list.size());
		return list;
	}
	
	
	@Override
	public DeptContactApprovedInfoVo getApprovedInfoVo(String mainBoId, String filterStr) {
		List<Map<String,Object>> list = getApprovedInfo(mainBoId,"");
		for(int i=0;i<list.size();i++){
			Map<String,Object> datas = list.get(i);
			String deptId = StringUtil.getNotNullValueString(datas.get("dept_id"));
			vo.mainDeptNameMap.put(deptId, "");
		}
//log.debug("list.size()="+list.size());
		if(list.size()==0){
			return new DeptContactApprovedInfoVo();
		}
		
		for(int i=0;i<list.size();i++){
			Map<String,Object> map = list.get(i);
			
			String type = StringUtil.getNotNullValueString(map.get("type"));
			addToVo(mainBoId,type,map);
		}
//log.debug("list.size()="+list.size());
		/**查找下级流程对应结果意见*/
		initLowerApprovedInfo(mainBoId,vo);
		
		/**获得部门ID对应中文名称*/
		StringBuffer mainDeptIds = new StringBuffer("-9999");
		for(String key:vo.mainDeptNameMap.keySet()){
			if(!"".equals(key)){
				mainDeptIds.append(","+key);
			}
		}

		String mainDeptIds_str = StringUtil.getNotNullValueString(mainDeptIds.toString());
//log.debug("mainDeptIds_str="+mainDeptIds_str);
		
		if(!"-9999".equals(mainDeptIds_str)){
			List<OrganDeptBo> deptNameList = externalService.getNodesInfo(mainDeptIds_str);
			
			for(int i=0;deptNameList!=null&&i<deptNameList.size();i++){
				OrganDeptBo bo = deptNameList.get(i);
				vo.mainDeptNameMap.put(StringUtil.getNotNullValueString(bo.id), bo.name);
			}
		}
		
		//子流程意见重新排序
		Collections.sort(vo.subApprovedInfo,new DeptTimeComparator());
		
		return vo;
	}
	
	/**vo中添加对应意见
	 * @param stepname
	 * @param type
	 * @param datas
	 */
	private void addToVo(String mainBoId,String type,Map<String,Object> datas){
		String mainDeptId = StringUtil.getNotNullValueString(datas.get("MAINDEPTID"));
		String c_id = StringUtil.getNotNullValueString(datas.get("C_ID"));
		String p_id = StringUtil.getNotNullValueString(datas.get("P_ID"));
		
		if(mainDeptId.length()>0) vo.mainDeptNameMap.put(mainDeptId, "");
		
		/**主流程步骤*/
		if(DeptContactConstants.STATUS_MAIN_STR.equals(type)){
			vo.addMainInfo(datas);
		}
		
		/**下级流程信息*/
		if(DeptContactConstants.STATUS_LOWER_STR.equals(type)){
			if(c_id.equals(mainBoId)){
				vo.addMainInfo(datas);
			}
			if(p_id.equals(mainBoId)){
//log.debug("lowerProcessInfo add:"+c_id);
				vo.lowerProcessInfoMap.put(c_id, datas);
			}
		}
		
		/**子流程步骤*/
		if(DeptContactConstants.STATUS_SUB_STR.equals(type)){
			vo.addSubInfo(datas);
		}
		
		/**返回发起部门步骤*/
		if(DeptContactConstants.STATUS_MAIN_BACKSUB_STR.equals(type)){
			vo.addBackApplyInfo(datas);
		}
	}
	
	
	/**
	 * 获取下级意见
	 */
	private void initLowerApprovedInfo(String parentMainBoId,DeptContactApprovedInfoVo vo){
		if(vo.lowerProcessInfoMap.size()==0) return;
		/*
		String filterWhere = "and t.option_code in " +
				"('LEADER_PASS:"+DeptContactConstants.STATUS_MAIN_BACKSUB_STR+"','SIGN_FINISH:"+DeptContactConstants.STATUS_LOWER_STR+"')" +
				" and t.agree = 1";
		*/
		//子流程中的LEADER_PASS以及主流程中的SIGN_FINISH
		
//log.debug("parentMainBoId:"+parentMainBoId);
		for(String mainBoId:vo.lowerProcessInfoMap.keySet()){
			String filterWhere = 
				" and (" +
				"(t.option_code in ('LEADER_PASS:"+DeptContactConstants.STATUS_MAIN_BACKSUB_STR+"'," +
				" 'LEADER_FAILED:"+DeptContactConstants.STATUS_MAIN_BACKSUB_STR+"')" +
				" and tree.p_id='"+mainBoId+"')" +
				" or(t.option_code ='SIGN_FINISH:"+DeptContactConstants.STATUS_LOWER_STR+"' and tree.c_id='"+mainBoId+"')" +
				") "
				//" and t.agree = 1"
				;
			
//log.debug("mainBoId:"+mainBoId);
			List<Map<String,Object>> list = getApprovedInfo(mainBoId,filterWhere);
//log.debug("Lower size()"+list.size());
			for(int i=0;i<list.size();i++){
//log.debug("mainBoId="+mainBoId);
				Map<String,Object> datas = list.get(i);
				//String type = StringUtil.getNotNullValueString(datas.get("type"));
				//String c_id = StringUtil.getNotNullValueString(datas.get("C_ID"));
				//String p_id = StringUtil.getNotNullValueString(datas.get("P_ID"));
				
				//if(!parentMainBoId.equals(p_id)&&!DeptContactConstants.STATUS_LOWER_STR.equals(type)){
				String mainDeptId = StringUtil.getNotNullValueString(datas.get("MAINDEPTID"));
				
				//vo.addLowerInfo(datas);
				datas.put("lowerText", "（下级流程）");
				datas.put("lowerFlag", "1");
				datas.put("dept_id", mainDeptId);
				vo.addSubInfo(datas);
//log.debug("add lower:"+mainDeptId);
				
				vo.mainDeptNameMap.put(mainDeptId, "");
				
				//vo.addLowerInfo(mainDeptId,datas);
				//}
			}
			//}
		}
	}
	
	
	public DbUtil getDbUtil() {
		return dbUtil;
	}
	
	@Autowired(required=false)
	public void setDbUtil(@Qualifier("dbUtil")DbUtil dbUtil) {
		this.dbUtil = dbUtil;
	}
	
	public ExternalService getExternalService() {
		return externalService;
	}
	
	@Autowired
	public void setExternalService(@Qualifier("contact-externalService")ExternalService externalService) {
		this.externalService = externalService;
	}
}
