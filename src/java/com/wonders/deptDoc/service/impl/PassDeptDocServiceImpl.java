package com.wonders.deptDoc.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wonders.dept.workflow.process.pass.model.bo.PassMainBo;
import com.wonders.deptDoc.dao.DeptDocDao;
import com.wonders.deptDoc.model.bo.PZDocsRight;
import com.wonders.deptDoc.service.DeptDocService;
import com.wonders.deptDoc.util.DeptDocUtil;

/**
 * @author zhou shun
 *
 */
@Service("passDeptDocService")
public class PassDeptDocServiceImpl implements DeptDocService {
	@Autowired
	private DeptDocDao<PassMainBo> dao;
	
	@Override
	public void addDocs(String pname,String pincident,String taskUserLoginName,String userName,String deptId) {
		// TODO Auto-generated method stub
		PassMainBo bo = this.dao.getBo("from "+PassMainBo.class.getName() +""
				+ " t where t.removed='0' and t.process = ? and t.incident = ?", new Object[]{pname,pincident});
		
		//save
		if(bo.getMainId() != null){
			//获取部门文件柜中该文件的 文件ID
			String fileId = DeptDocUtil.getFileId(new Object[]{deptId,bo.getMainId()});
			
			if(fileId != null){
				boolean isExist = DeptDocUtil.isExist(taskUserLoginName, fileId);
				if(!isExist){
					PZDocsRight docRight = new PZDocsRight();
					docRight.setUptuser(taskUserLoginName);
					docRight.setEmpid(taskUserLoginName);
					docRight.setRightid(fileId);
					this.dao.save(docRight);
				}
			}
		}
		
		
		
		
	}

	@Override
	public void addDocs(Object bo, String taskUserLoginName, String userName,
			String deptId) {
		// TODO Auto-generated method stub
		
	}

}
