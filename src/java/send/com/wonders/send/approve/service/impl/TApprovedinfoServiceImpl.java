/*     */ package com.wonders.send.approve.service.impl;
/*     */ 
/*     */ import com.wonders.send.approve.dao.TApprovedinfoDao;
import com.wonders.send.approve.model.bo.SendApprovedinfo;
import com.wonders.util.StringUtil;
/*     */ import com.wonders.send.approve.service.TApprovedinfoService;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Qualifier;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Repository;
/*     */ import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
/*     */ @Repository("send-tApprovedInfoService")
/*     */ @Scope("prototype")
/*     */ public class TApprovedinfoServiceImpl
/*     */   implements TApprovedinfoService
/*     */ {
/*     */   private TApprovedinfoDao tApprovedInfoDao;
/*     */ 
/*     */   @Autowired(required=false)
/*     */   public void settApprovedInfoDao(@Qualifier("send-tApprovedInfoDao") TApprovedinfoDao tApprovedInfoDao)
/*     */   {
/*  30 */     this.tApprovedInfoDao = tApprovedInfoDao;
/*     */   }
/*     */ 
/*     */   public void save(Object obj) {
/*  34 */     this.tApprovedInfoDao.save(obj);
/*     */   }
/*     */ 
/*     */   public List<SendApprovedinfo> findByHQL(String hql) {
/*  38 */     return this.tApprovedInfoDao.findByHQL(hql);
/*     */   }
/*     */ 
/*     */   public Map tapprovedinfoServiceByFW(String processName, String instanceId) {
/*  42 */     List pbApprovedinfoList = new ArrayList();
/*  43 */     List hqApprovedinfoList = new ArrayList();
/*  44 */     List xgApprovedinfoList = new ArrayList();
/*  45 */     List sgApprovedinfoList = new ArrayList();
/*  46 */     List qcApprovedinfoList = new ArrayList();
/*  47 */     List jgApprovedinfoList = new ArrayList();
/*  48 */     Map map = new HashMap();
/*  49 */     String hql = "from SendApprovedinfo m where m.status=1";
/*     */ 
/*  51 */     if ((processName != null) && (processName.length() > 0)) {
/*  52 */       hql = hql + " and m.process='" + processName + "'";
/*     */     }
/*  54 */     if ((instanceId != null) && (instanceId.length() > 0)) {
/*  55 */       hql = hql + " and m.incidentno=" + instanceId;
/*     */     }
/*  57 */     hql = hql + " order by m.upddate desc";
/*  58 */     List lists = null;
/*  59 */     lists = this.tApprovedInfoDao.findByHQL(hql);
/*  60 */     if ((lists != null) && (lists.size() > 0)) {
/*  61 */       Iterator iter = lists.iterator();
/*  62 */       while (iter.hasNext()) {
/*  63 */         SendApprovedinfo approvedinfo = (SendApprovedinfo)iter.next();
/*  64 */         String stepName = approvedinfo.getStepname();
/*  65 */         if ((stepName != null) && (stepName.length() > 0)) {
/*  66 */           if ("签发领导".equals(stepName))
/*  67 */             pbApprovedinfoList.add(approvedinfo);
/*  68 */           else if ("领导".equals(stepName))
/*  69 */             hqApprovedinfoList.add(approvedinfo);
/*  70 */           else if ("核稿".equals(stepName))
/*  71 */             xgApprovedinfoList.add(approvedinfo);
/*  72 */           else if ("审稿".equals(stepName))
/*  73 */             sgApprovedinfoList.add(approvedinfo);
/*  74 */           else if (("起草".equals(stepName)) || ("拟稿人修改".equals(stepName)))
/*  75 */             qcApprovedinfoList.add(approvedinfo);
/*  76 */           else if ("校稿".equals(stepName)) {
/*  77 */             jgApprovedinfoList.add(approvedinfo);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  83 */     map.put("pbApprovedinfoList", pbApprovedinfoList);
/*  84 */     map.put("hqApprovedinfoList", hqApprovedinfoList);
/*  85 */     map.put("xgApprovedinfoList", xgApprovedinfoList);
/*  86 */     map.put("sgApprovedinfoList", sgApprovedinfoList);
/*  87 */     map.put("qcApprovedinfoList", qcApprovedinfoList);
/*  88 */     map.put("jgApprovedinfoList", jgApprovedinfoList);
/*  89 */     return map;
/*     */   }
/*     */ 
/*     */   public List quereyApprovedInfoByDeptIdWithLeaderSign(String processName, String instanceId, String[] stepName, String deptId, String groupCode)
/*     */   {
/*  95 */     String hql = "select new com.wonders.send.approve.model.vo.TApprovedinfoVo(m,to_char(F_ISINGROUPBYLOGINNAME(subStr(m.username, 4,length(m.username)),'" + groupCode + "')) ) from SendApprovedinfo m where 1=1 ";
/*     */ 
/*  97 */     if ((processName != null) && (processName.length() > 0)) {
/*  98 */       hql = hql + " and m.process='" + processName + "'";
/*     */     }
/*     */ 
/* 101 */     if ((instanceId != null) && (instanceId.length() > 0)) {
/* 102 */       hql = hql + " and m.incidentno=" + instanceId;
/*     */     }
/*     */ 
/* 105 */     if ((stepName != null) && (stepName.length > 0)) {
/* 106 */       hql = hql + " and ( ";
/* 107 */       for (int i = 0; i < stepName.length; i++) {
/* 108 */         if (i < stepName.length - 1)
/* 109 */           hql = hql + " m.stepname like '" + stepName[i] + "' or ";
/*     */         else
/* 111 */           hql = hql + " m.stepname like '" + stepName[i] + "'";
/*     */       }
/* 113 */       hql = hql + " )";
/*     */     }
/* 115 */     if (!StringUtil.isNull(deptId)) {
/* 116 */       hql = hql + " and deptId = '" + deptId + "'";
/*     */     }
/* 118 */     hql = hql + " order by upddate desc";
/* 119 */     List lists = null;
/* 120 */     lists = this.tApprovedInfoDao.findByHQL(hql);
/*     */ 
/* 122 */     return lists;
/*     */   }
/*     */ 
/*     */   public List queryApprovedinfoLikeByDeptId(String processName, String instanceId, String[] stepName)
/*     */   {
/* 128 */     String hql = "select distinct deptId from SendApprovedinfo m where 1=1";
/*     */ 
/* 130 */     if ((processName != null) && (processName.length() > 0)) {
/* 131 */       hql = hql + " and m.process='" + processName + "'";
/*     */     }
/*     */ 
/* 134 */     if ((instanceId != null) && (instanceId.length() > 0)) {
/* 135 */       hql = hql + " and m.incidentno=" + instanceId;
/*     */     }
/*     */ 
/* 138 */     if ((stepName != null) && (stepName.length > 0)) {
/* 139 */       hql = hql + " and ( ";
/* 140 */       for (int i = 0; i < stepName.length; i++) {
/* 141 */         if (i < stepName.length - 1)
/* 142 */           hql = hql + " m.stepname like '" + stepName[i] + "%' or ";
/*     */         else
/* 144 */           hql = hql + " m.stepname like '" + stepName[i] + "%'";
/*     */       }
/* 146 */       hql = hql + " )";
/*     */     }
/* 148 */     List lists = null;
/* 149 */     lists = this.tApprovedInfoDao.findByHQL(hql);
/*     */ 
/* 152 */     return lists;
/*     */   }
/*     */ 
/*     */   public List queryApprovedinfoLike(String processName, String instanceId, String[] stepName, String dept)
/*     */   {
/* 157 */     String hql = "from SendApprovedinfo m where 1=1";
/*     */ 
/* 159 */     if ((processName != null) && (processName.length() > 0)) {
/* 160 */       hql = hql + " and m.process='" + processName + "'";
/*     */     }
/*     */ 
/* 163 */     if ((instanceId != null) && (instanceId.length() > 0)) {
/* 164 */       hql = hql + " and m.incidentno=" + instanceId;
/*     */     }
/*     */ 
/* 167 */     if ((stepName != null) && (stepName.length > 0)) {
/* 168 */       hql = hql + " and ( ";
/* 169 */       for (int i = 0; i < stepName.length; i++) {
/* 170 */         if (i < stepName.length - 1)
/* 171 */           hql = hql + " m.stepname like '" + stepName[i] + "%' or ";
/*     */         else
/* 173 */           hql = hql + " m.stepname like '" + stepName[i] + "%'";
/*     */       }
/* 175 */       hql = hql + " )";
/*     */     }
/*     */ 
/* 178 */     if ((dept != null) && (dept.length() > 0)) {
/* 179 */       hql = hql + " and m.dept like '%" + dept + "%'";
/*     */     }
/* 181 */     hql = hql + " order by m.upddate desc";
/*     */ 
/* 183 */     List tmpList = this.tApprovedInfoDao.findByHQL(hql);
/* 184 */     tmpList = tmpList == null ? new ArrayList() : tmpList;
/* 185 */     return tmpList;
/*     */   }

public List queryApprovedinfo(String processName, String instanceId,String[] stepName,String dept){
	String hql="from SendApprovedinfo m where m.status=1 ";
	
	if(processName!=null && processName.length()>0){
		hql+=" and m.process='"+ processName +"'";
	}
	
	if(instanceId!=null && instanceId.length()>0){
		hql+=" and m.incidentno="+instanceId;
	}
	
	if(stepName!=null && stepName.length>0){
		hql+=" and ( ";
		for(int i=0;i<stepName.length;i++){
			if(i<stepName.length-1)
				hql+=" m.stepname = '"+ stepName[i] +"' or ";
			else
				hql+=" m.stepname = '"+ stepName[i] +"'";
		}
		hql+=" )";
	}
	
	if(dept!=null && dept.length()>0){
		hql+=" and m.dept like '%" + dept +"%'";
	}
	hql+=" order by m.upddate desc";		
	
	List tmpList=this.tApprovedInfoDao.findByHQL(hql);
	tmpList=(tmpList==null)?new ArrayList():tmpList;
	return tmpList;
}	
/*     */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.approve.service.impl.TApprovedinfoServiceImpl
 * JD-Core Version:    0.6.0
 */