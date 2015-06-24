/*    */ package com.wonders.send.mainProcess.send.dao.impl;
/*    */ 
/*    */ import java.util.Collection;
import java.util.List;

import com.wonders.send.mainProcess.send.dao.TDocSendDao;
import com.wonders.send.mainProcess.send.model.bo.TNormativeDoc;

/*    */ import javax.annotation.Resource;

/*    */ import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository("send-tDocSendDao")
/*    */ public class TDocSendDaoImpl
/*    */   implements TDocSendDao
/*    */ {
/*    */   private HibernateTemplate hibernateTemplate;
/*    */ 
/*    */   public HibernateTemplate getHibernateTemplate()
/*    */   {
/* 14 */     return this.hibernateTemplate;
/*    */   }
/*    */ 
/*    */   @Resource(name="hibernateTemplate")
/*    */   public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
/* 20 */     this.hibernateTemplate = hibernateTemplate;
/*    */   }
/*    */ 
/*    */   public void save(Object obj) {
/* 24 */     getHibernateTemplate().save(obj);
/*    */   }

		public List<TNormativeDoc> getNormativeInfo(String mainId){
			String hql = "from TNormativeDoc t where t.mainId = ? and t.removed = '0'";
			return (List<TNormativeDoc>)this.hibernateTemplate.find(hql, new Object[]{mainId});
		}
		
		public void saveNormative(Collection<TNormativeDoc> obj){
			this.hibernateTemplate.saveOrUpdateAll(obj);
		}
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.mainProcess.send.dao.impl.TDocSendDaoImpl
 * JD-Core Version:    0.6.0
 */