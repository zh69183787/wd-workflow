package com.wonders.deptDoc.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.constants.WorkflowConstants;
import com.wonders.deptDoc.constant.DeptDocConstants;
import com.wonders.deptDoc.dao.DeptDocDao;
import com.wonders.deptDoc.model.bo.ZDocsFile;
import com.wonders.deptDoc.model.bo.ZDocsRight;
import com.wonders.deptDoc.service.DeptDocService;
import com.wonders.deptDoc.util.DeptDocUtil;
import com.wonders.send.mainProcess.send.model.bo.TDocSend;
import com.wonders.util.PropertyUtil;
import com.wonders.util.StringUtil;


/**
 * @author zhou shun
 *
 */
@Service("docSendDeptDocService")
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class DocSendDeptDocServiceImpl implements DeptDocService {
	@Autowired
	private DeptDocDao<TDocSend> dao;
	
	@Override
	public void addDocs(String pname,String pincident,String taskUserLoginName,String userName,String deptId) {
		// TODO Auto-generated method stub
		TDocSend bo = this.dao.getBo("from "+TDocSend.class.getName() +""
				+ " t where t.removed='0' and t.modelid = ? and t.pinstanceid = ?", new Object[]{pname,pincident});
		
		//获取流程中部门的部门 & 人
		Map<String,Set<String>> processMap = DeptDocUtil.getDeptAndPerosn(pname, pincident);
		//获取发文主送 抄送 内发单位
		this.parse(StringUtil.getNotNullValueString(bo.getSendMainId()),processMap);
		this.parse(StringUtil.getNotNullValueString(bo.getSendInsideId()),processMap);
		this.parse(StringUtil.getNotNullValueString(bo.getSendReportId()),processMap);
				
		//增加当前处理人的部门和ID
		if(processMap.containsKey(deptId)){
			processMap.get(deptId).add(taskUserLoginName);
		}else{
			Set<String> temp = new HashSet<String>(); temp.add(taskUserLoginName);
			processMap.put(deptId, temp);
		}
		List<String> deptQuery = new ArrayList<String>();
		for(Map.Entry<String, Set<String>> entry : processMap.entrySet()){
			deptQuery.add(entry.getKey());
		}
		//获取部门对应的文件柜父目录ID
		Map<String,String> catelogMap = DeptDocUtil.getCatelogId(deptQuery);
		
		//获取链接地址及配置ID
		String listType = "0";
		if(WorkflowConstants.NEW_DOC_SEND.equals(bo.getModelid())){
			listType = "2";
		}
		String config_ip = PropertyUtil.getValueByKey("config.properties", "contextIpPath");
		String url = config_ip+"/attach/loadFileList.action?"+
				"attachMemo=fawen_att_dic&procType=view&fileGroup=contentAttMain&"+
				"fileGroupName=contentAttMainGroup&fileGroupId="+bo.getContentAttMain()+
				"&listType="+listType;
		
		//获取对应部门编号ID
		Map<String,String> codeMap = DeptDocUtil.getDeptCode();
		//save
		List<ZDocsFile> result = new ArrayList<ZDocsFile>();
		for(Map.Entry<String, String> entry : catelogMap.entrySet()){
			ZDocsFile docFile = new ZDocsFile();
			docFile.setCreateUser(taskUserLoginName);
			docFile.setCreateUserName(userName);
			docFile.setUpdateUser(taskUserLoginName);
			docFile.setUpdateUserName(userName);
			docFile.setParentSid(entry.getValue());
			docFile.setFileName(bo.getSendId() + " " + bo.getDocTitle());
			docFile.setRefId(bo.getId());
			if(WorkflowConstants.NEW_DOC_SEND.equals(bo.getModelid())){
				docFile.setFilePath(url +"&codeId="+ codeMap.get(entry.getKey()));
			}else{
				docFile.setFilePath(url);
			}		
			docFile.setFlag(DeptDocConstants.getConstants(bo.getModelid()));
			List<ZDocsRight> rights = new ArrayList<ZDocsRight>();
			Set<String> person = processMap.get(entry.getKey());
			for(String o : person){
				ZDocsRight docRight = new ZDocsRight();
				docRight.setUptuser(taskUserLoginName);
				docRight.setEmpid(o);
				docRight.setDocFile(docFile);
				rights.add(docRight);
			}
			docFile.setDocRight(rights);	
			result.add(docFile);
		}
		
		this.dao.saveAll(result);
		
	}
	
	private void parse(String deptId,Map<String,Set<String>> processMap){
		String temp[] = deptId.split(",");
		for(String o : temp){
			if(!"".equals(o)){
				if(!processMap.containsKey(o)){
					Set<String> set = new HashSet<String>();
					processMap.put(o, set);
				}
			}
		}
	}

	@Override
	public void addDocs(Object bo, String taskUserLoginName, String userName,
			String deptId) {
		TDocSend sendBo = (TDocSend)bo;
		//获取流程中部门的部门 & 人
				Map<String,Set<String>> processMap = DeptDocUtil.getDeptAndPerosn(sendBo.getModelid(),
						sendBo.getPinstanceid());
				//获取发文主送 抄送 内发单位
				this.parse(StringUtil.getNotNullValueString(sendBo.getSendMainId()),processMap);
				this.parse(StringUtil.getNotNullValueString(sendBo.getSendInsideId()),processMap);
				this.parse(StringUtil.getNotNullValueString(sendBo.getSendReportId()),processMap);
						
				//增加当前处理人的部门和ID
				if(processMap.containsKey(deptId)){
					processMap.get(deptId).add(taskUserLoginName);
				}else{
					Set<String> temp = new HashSet<String>(); temp.add(taskUserLoginName);
					processMap.put(deptId, temp);
				}
				List<String> deptQuery = new ArrayList<String>();
				for(Map.Entry<String, Set<String>> entry : processMap.entrySet()){
					deptQuery.add(entry.getKey());
				}
				//获取部门对应的文件柜父目录ID
				Map<String,String> catelogMap = DeptDocUtil.getCatelogId(deptQuery);
				
				//获取链接地址及配置ID
				String listType = "0";
				if(WorkflowConstants.NEW_DOC_SEND.equals(sendBo.getModelid())){
					listType = "2";
				}
				String config_ip = PropertyUtil.getValueByKey("config.properties", "contextIpPath");
				String url = config_ip+"/attach/loadFileList.action?"+
						"attachMemo=fawen_att_dic&procType=view&fileGroup=contentAttMain&"+
						"fileGroupName=contentAttMainGroup&fileGroupId="+sendBo.getContentAttMain()+
						"&listType="+listType;
				
				//获取对应部门编号ID
				Map<String,String> codeMap = DeptDocUtil.getDeptCode();
				//save
				List<ZDocsFile> result = new ArrayList<ZDocsFile>();
				for(Map.Entry<String, String> entry : catelogMap.entrySet()){
					ZDocsFile docFile = new ZDocsFile();
					docFile.setCreateUser(taskUserLoginName);
					docFile.setCreateUserName(userName);
					docFile.setUpdateUser(taskUserLoginName);
					docFile.setUpdateUserName(userName);
					docFile.setParentSid(entry.getValue());
					docFile.setFileName(sendBo.getSendId() + " " + sendBo.getDocTitle());
					docFile.setRefId(sendBo.getId());
					if(WorkflowConstants.NEW_DOC_SEND.equals(sendBo.getModelid())){
						docFile.setFilePath(url +"&codeId="+ codeMap.get(entry.getKey()));
					}else{
						docFile.setFilePath(url);
					}		
					docFile.setFlag(DeptDocConstants.getConstants(sendBo.getModelid()));
					List<ZDocsRight> rights = new ArrayList<ZDocsRight>();
					Set<String> person = processMap.get(entry.getKey());
					for(String o : person){
						ZDocsRight docRight = new ZDocsRight();
						docRight.setUptuser(taskUserLoginName);
						docRight.setEmpid(o);
						docRight.setDocFile(docFile);
						rights.add(docRight);
					}
					docFile.setDocRight(rights);	
					result.add(docFile);
				}
				
				this.dao.saveAll(result);
		
	}
}
