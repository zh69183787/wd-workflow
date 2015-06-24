package com.wonders.deptDoc.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wonders.deptDoc.constant.DeptDocConstants;
import com.wonders.deptDoc.dao.DeptDocDao;
import com.wonders.deptDoc.model.bo.ZDocsFile;
import com.wonders.deptDoc.model.bo.ZDocsRight;
import com.wonders.deptDoc.service.DeptDocService;
import com.wonders.deptDoc.util.DeptDocUtil;
import com.wonders.receive.workflow.process.recv.model.bo.RecvMainBo;
import com.wonders.receive.workflow.process.util.TextUtil;
import com.wonders.util.PropertyUtil;


/**
 * @author zhou shun
 *
 */
@Service("docRecvDeptDocService")
public class DocRecvDeptDocServiceImpl implements DeptDocService {
	@Autowired
	private DeptDocDao<RecvMainBo> dao;
	
	@Override
	public void addDocs(String pname,String pincident,String taskUserLoginName,String userName,String deptId) {
		// TODO Auto-generated method stub
		RecvMainBo bo = this.dao.getBo("from "+RecvMainBo.class.getName() +""
				+ " t where t.removed='0' and t.modleId = ? and t.instanceId = ?", new Object[]{pname,pincident});
		//获取流程中部门的部门 & 人
		Map<String,Set<String>> processMap = DeptDocUtil.getDeptAndPerosn(pname, pincident);
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
		String config_ip = PropertyUtil.getValueByKey("config.properties", "contextIpPath");
		String url = TextUtil.generateMainUrl(pname,pincident,"","","");
		
		//save
		List<ZDocsFile> result = new ArrayList<ZDocsFile>();
		for(Map.Entry<String, String> entry : catelogMap.entrySet()){
			ZDocsFile docFile = new ZDocsFile();
			docFile.setCreateUser(taskUserLoginName);
			docFile.setCreateUserName(userName);
			docFile.setUpdateUser(taskUserLoginName);
			docFile.setUpdateUserName(userName);
			docFile.setParentSid(entry.getValue());
			docFile.setFileName(bo.getModleId() + " " + bo.getTitle());
			docFile.setRefId(bo.getId());
			docFile.setFilePath(config_ip+url);
			docFile.setFlag(DeptDocConstants.FILE_DOC_RECEIVE);
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

	@Override
	public void addDocs(Object bo, String taskUserLoginName, String userName,
			String deptId) {
		// TODO Auto-generated method stub
		
	}

}
