package com.wonders.deptDoc.service.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.deptDoc.service.DeptDocService;

/**访问外部系统数据类
 * @author XFZ
 * @version 1.0 2012-6-3
 */
@Service("deptDocService")
@Scope("prototype")
public class DeptDocServiceImpl implements DeptDocService {

	@Override
	public void addDocs(String pname, String pincident,
			String taskUserLoginName, String userName, String deptId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addDocs(Object bo, String taskUserLoginName, String userName,
			String deptId) {
		// TODO Auto-generated method stub
		
	}

}
