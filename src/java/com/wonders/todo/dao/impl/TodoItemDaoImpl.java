/**
 * 
 */
package com.wonders.todo.dao.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.todo.dao.TodoItemDao;
import com.wonders.todo.model.bo.TodoItemBo;

/** 
 * @ClassName: TodoDaoImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年1月8日 下午8:15:35 
 *  
 */
@Repository("todoItemDao")
public class TodoItemDaoImpl implements TodoItemDao{

	private HibernateTemplate hibernateTemplate;
	
	/** 
	* @Title: updateById 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param     设定文件 
	* @throws 
	*/
	@Override
	public void updateById(String id) {
		// TODO Auto-generated method stub
		TodoItemBo bo = this.getHibernateTemplate().get(TodoItemBo.class, id);
		if(bo != null){
			bo.setStatus(1);
			this.getHibernateTemplate().update(bo);
		}
		
	}

	
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	//注入hibernateTemplate
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
}
