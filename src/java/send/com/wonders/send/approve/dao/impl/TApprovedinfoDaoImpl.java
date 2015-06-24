/*    */ package com.wonders.send.approve.dao.impl;
/*    */ 
/*    */ import com.wonders.send.approve.model.bo.SendApprovedinfo;
import com.wonders.send.approve.dao.TApprovedinfoDao;
/*    */ import java.util.List;
/*    */ import javax.annotation.Resource;
/*    */ import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository("send-tApprovedInfoDao")
/*    */ public class TApprovedinfoDaoImpl
/*    */   implements TApprovedinfoDao
/*    */ {
/*    */   private HibernateTemplate hibernateTemplate;
/*    */ 
/*    */   public HibernateTemplate getHibernateTemplate()
/*    */   {
/* 17 */     return this.hibernateTemplate;
/*    */   }
/*    */ 
/*    */   @Resource(name="hibernateTemplate")
/*    */   public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
/* 23 */     this.hibernateTemplate = hibernateTemplate;
/*    */   }
/*    */ 
/*    */   public void save(Object obj) {
/* 27 */     getHibernateTemplate().save(obj);
/*    */   }
/*    */ 
/*    */   public List<SendApprovedinfo> findByHQL(String hql) {
/* 31 */     return getHibernateTemplate().find(hql);
/*    */   }
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.approve.dao.impl.TApprovedinfoDaoImpl
 * JD-Core Version:    0.6.0
 */