/*    */ package com.wonders.send.common.dao.impl;
/*    */ 
/*    */ import com.wonders.send.common.dao.CommonDao;

/*    */ import java.io.Serializable;
import java.util.Collection;
/*    */ import java.util.List;
/*    */ import javax.annotation.Resource;
/*    */ import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository("send-commonDao")
/*    */ public class CommonDaoImpl
/*    */   implements CommonDao
/*    */ {
/*    */   private HibernateTemplate hibernateTemplate;
/*    */ 
/*    */   public List ListByHql(String hql)
/*    */   {
/* 21 */     List list = this.hibernateTemplate.find(hql);
/* 22 */     return list;
/*    */   }
/*    */ 
/*    */   public List ListByHql(String hql, Object[] params)
/*    */   {
/* 27 */     List list = this.hibernateTemplate.find(hql, params);
/* 28 */     return list;
/*    */   }
/*    */ 
/*    */   public void UpdateByHql(String hql)
/*    */   {
/* 33 */     this.hibernateTemplate.bulkUpdate(hql);
/*    */   }
/*    */ 
/*    */   public void save(Object obj) {
/* 37 */     getHibernateTemplate().save(obj);
/*    */   }
/*    */ 
/*    */   public Object load(String id, Class clazz)
/*    */   {
/* 42 */     Object instance = getHibernateTemplate().get(clazz, id);
/* 43 */     return instance;
/*    */   }
/*    */ 
/*    */   public void update(Object obj) {
/* 47 */     getHibernateTemplate().update(obj);
/*    */   }
/*    */ 
/*    */   public void remove(Object obj) {
/* 51 */     getHibernateTemplate().delete(obj);
/*    */   }
/*    */ 
/*    */   public Object load(Serializable id, Class clazz)
/*    */   {
/* 56 */     Object instance = getHibernateTemplate().get(clazz, id);
/* 57 */     return instance;
/*    */   }
/*    */ 
/*    */   public HibernateTemplate getHibernateTemplate()
/*    */   {
/* 62 */     return this.hibernateTemplate;
/*    */   }
/*    */ 
/*    */   @Resource(name="hibernateTemplate")
/*    */   public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
/* 68 */     this.hibernateTemplate = hibernateTemplate;
/*    */   }
/*    */ 
/*    */   public List<Object> findByHQL(String hql) {
/* 72 */     return this.hibernateTemplate.find(hql);
/*    */   }

public void saveOrUpdateAll(Collection cols){
	hibernateTemplate.saveOrUpdateAll(cols);
}
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.common.dao.impl.CommonDaoImpl
 * JD-Core Version:    0.6.0
 */