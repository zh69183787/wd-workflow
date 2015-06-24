/*    */ package com.wonders.send.mainProcess.send.service.impl;
/*    */ 
/*    */ import java.util.Collection;
import java.util.List;

import com.wonders.send.mainProcess.send.dao.TDocSendDao;
import com.wonders.send.mainProcess.send.model.bo.TNormativeDoc;
import com.wonders.send.mainProcess.send.service.TDocSendService;

/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.beans.factory.annotation.Qualifier;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Repository;
/*    */ import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
/*    */ @Repository("send-tDocSendService")
/*    */ @Scope("prototype")
/*    */ public class TDocSendServiceImpl
/*    */   implements TDocSendService
/*    */ {
/*    */   private TDocSendDao tDocSendDao;
/*    */ 
/*    */   @Autowired(required=false)
/*    */   public void settDocSendDao(@Qualifier("send-tDocSendDao") TDocSendDao tDocSendDao)
/*    */   {
/* 21 */     this.tDocSendDao = tDocSendDao;
/*    */   }
/*    */ 
/*    */   public void save(Object obj) {
/* 25 */     this.tDocSendDao.save(obj);
/*    */   }
			public List<TNormativeDoc> getNormativeInfo(String mainId){
				return this.tDocSendDao.getNormativeInfo(mainId);
			}
			
			public void saveNormative(Collection<TNormativeDoc> obj){
				this.tDocSendDao.saveNormative(obj);
			}
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.mainProcess.send.service.impl.TDocSendServiceImpl
 * JD-Core Version:    0.6.0
 */