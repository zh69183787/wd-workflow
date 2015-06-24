/**
 * 
 */
package com.wonders.todo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.wonders.todo.dao.TodoItemDao;
import com.wonders.todo.service.TodoItemService;

/** 
 * @ClassName: TodoItemServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年1月8日 下午8:25:04 
 *  
 */
@Service("todoItemService")
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class TodoItemServiceImpl implements TodoItemService{
	private TodoItemDao dao;

	public TodoItemDao getDao() {
		return dao;
	}
	@Autowired(required=false)
	public void setDao(@Qualifier("todoItemDao")TodoItemDao dao) {
		this.dao = dao;
	}
	
	public void updateById(String id){
		this.dao.updateById(id);;
	}
	
}
