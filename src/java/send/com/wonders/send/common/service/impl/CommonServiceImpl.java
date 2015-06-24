/*    */ package com.wonders.send.common.service.impl;
/*    */ 
/*    */ import com.wonders.send.common.dao.CommonDao;
import com.wonders.send.common.service.CommonService;

/*    */ import java.io.Serializable;
import java.util.Collection;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.beans.factory.annotation.Qualifier;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Repository;
/*    */ import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
/*    */ @Repository("send-commonService")
/*    */ @Scope("prototype")
/*    */ public class CommonServiceImpl
/*    */   implements CommonService
/*    */ {
/*    */   private CommonDao dao;
/*    */ 
/*    */   public List ListByHql(String hql)
/*    */   {
/* 26 */     return this.dao.ListByHql(hql);
/*    */   }
/*    */ 
/*    */   public List ListByHql(String hql, Object[] params)
/*    */   {
/* 31 */     return this.dao.ListByHql(hql, params);
/*    */   }
/*    */ 
/*    */   public void UpdateByHql(String hql) {
/* 35 */     this.dao.UpdateByHql(hql);
/*    */   }
/*    */ 
/*    */   public Object load(String id, Class clazz)
/*    */   {
/* 40 */     return this.dao.load(id, clazz);
/*    */   }
/*    */ 
/*    */   public void remove(Object obj) {
/* 44 */     this.dao.remove(obj);
/*    */   }
/*    */ 
/*    */   public void save(Object obj) {
/* 48 */     this.dao.save(obj);
/*    */   }
/*    */ 
/*    */   public void update(Object obj) {
/* 52 */     this.dao.update(obj);
/*    */   }
/*    */ 
/*    */   public CommonDao getDao()
/*    */   {
/* 57 */     return this.dao;
/*    */   }
/*    */   @Autowired(required=false)
/*    */   public void setDao(@Qualifier("send-commonDao") CommonDao dao) {
/* 62 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public Object load(Serializable id, Class clazz) {
/* 66 */     return this.dao.load(id, clazz);
/*    */   }
/*    */ 
/*    */   public List<Object> findByHQL(String hql) {
/* 70 */     return this.dao.findByHQL(hql);
/*    */   }

public void saveOrUpdateAll(Collection cols){
	dao.saveOrUpdateAll(cols);
}
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.common.service.impl.CommonServiceImpl
 * JD-Core Version:    0.6.0
 */