/*     */ package com.wonders.send.future.dao.impl;
/*     */ 
/*     */ import com.wonders.util.StringUtil;
import com.wonders.send.future.dao.FutureDao;
/*     */ import com.wonders.send.future.model.bo.Future;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import org.hibernate.Hibernate;
/*     */ import org.hibernate.Query;
/*     */ import org.hibernate.SQLQuery;
/*     */ import org.hibernate.Session;
/*     */ import org.hibernate.SessionFactory;
/*     */ import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
/*     */ 
/*     */ @Repository("send-futureDao")
/*     */ public class FutureDaoImpl
/*     */   implements FutureDao
/*     */ {
/*     */   private HibernateTemplate hibernateTemplate;
/*     */ 
/*     */   public HibernateTemplate getHibernateTemplate()
/*     */   {
/*  23 */     return this.hibernateTemplate;
/*     */   }
/*     */ 
/*     */   @Resource(name="hibernateTemplate")
/*     */   public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
/*  29 */     this.hibernateTemplate = hibernateTemplate;
/*     */   }
/*     */ 
/*     */   public Object load(String id, Class clazz)
/*     */   {
/*  34 */     Object instance = getHibernateTemplate().get(clazz, id);
/*  35 */     return instance;
/*     */   }
/*     */ 
/*     */   public void save(Object obj) {
/*  39 */     getHibernateTemplate().save(obj);
/*     */   }
/*     */ 
/*     */   public void update(Object obj) {
/*  43 */     getHibernateTemplate().update(obj);
/*     */   }
/*     */ 
/*     */   public Future findFuturesByPName(String processName, String incidentNo)
/*     */   {
/*  48 */     String hql = "from Future f where f.processName = '" + processName + "' and f.incidentNo = '" + incidentNo + "'";
/*  49 */     Session session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
/*  50 */     Query query = session.createQuery(hql);
/*  51 */     Future future = (Future)query.uniqueResult();
/*  52 */     return future;
/*     */   }
/*     */ 
/*     */   private String connactFurue(String str, String temp) {
/*  56 */     String[] temps = (String[])null;
/*  57 */     if (!"".equals(temp.trim())) {
/*  58 */       temps = temp.split(",");
/*  59 */       if ((str != null) && (!"".equals(str.trim()))) {
/*  60 */         for (int i = 0; i < temps.length; i++) {
/*  61 */           if (str.indexOf(temps[i]) > -1)
/*     */           {
/*     */             continue;
/*     */           }
/*  65 */           if (str.endsWith(","))
/*  66 */             str = str + temps[i] + ",";
/*     */           else {
/*  68 */             str = str + "," + temps[i] + ",";
/*     */           }
/*     */         }
/*     */       }
/*     */       else {
/*  73 */         return temp;
/*     */       }
/*     */     }
/*  76 */     return str;
/*     */   }
/*     */ 
/*     */   public void updateFutures(Future future, String id) {
/*  80 */     if (future == null)
/*  81 */       return;
/*  82 */     Future oldFuture = (Future)load(id, Future.class);
/*  83 */     oldFuture.setUserId(connactFurue(oldFuture.getUserId(), StringUtil.getNotNullValueString(future.getUserId())));
/*  84 */     oldFuture.setUsername(connactFurue(oldFuture.getUsername(), StringUtil.getNotNullValueString(future.getUsername())));
/*  85 */     oldFuture.setDeptIdList(connactFurue(oldFuture.getDeptIdList(), StringUtil.getNotNullValueString(future.getDeptIdList())));
/*  86 */     oldFuture.setDeptList(connactFurue(oldFuture.getDeptList(), StringUtil.getNotNullValueString(future.getDeptList())));
/*  87 */     oldFuture.setLeaderList(connactFurue(oldFuture.getLeaderList(), StringUtil.getNotNullValueString(future.getLeaderList())));
/*  88 */     oldFuture.setFinishList(connactFurue(oldFuture.getFinishList(), StringUtil.getNotNullValueString(future.getFinishList())));
/*  89 */     oldFuture.setFinishlistdeptid(future.getFinishlistdeptid());
/*     */ 
/*  93 */     oldFuture.setHqldid(future.getHqldid());
/*  94 */     oldFuture.setHqldmsid(future.getHqldmsid());
/*  95 */     oldFuture.setHqldmsname(future.getHqldmsname());
/*  96 */     oldFuture.setHqldname(future.getHqldname());
/*  97 */     oldFuture.setHqlddeptid(future.getHqlddeptid());
/*  98 */     oldFuture.setHqldmsdeptid(future.getHqldmsdeptid());
/*     */ 
/* 100 */     oldFuture.setNbsugdept(future.getNbsugdept());
/* 101 */     oldFuture.setNbsugdeptldid(future.getNbsugdeptldid());
/*     */ 
/* 103 */     oldFuture.setPshqldid(future.getPshqldid());
/* 104 */     oldFuture.setPshqldmsid(future.getPshqldmsid());
/* 105 */     oldFuture.setPshqldmsname(future.getPshqldmsname());
/* 106 */     oldFuture.setPshqldname(future.getPshqldname());
/* 107 */     oldFuture.setPshqlddeptid(future.getPshqlddeptid());
/* 108 */     oldFuture.setPshqldmsdeptid(future.getPshqldmsdeptid());
/*     */ 
/* 110 */     oldFuture.setPsshldid(future.getPsshldid());
/* 111 */     oldFuture.setPsshldmsid(future.getPsshldmsid());
/* 112 */     oldFuture.setPsshldmsname(future.getPsshldmsname());
/* 113 */     oldFuture.setPsshldname(future.getPsshldname());
/* 114 */     oldFuture.setPsshlddeptid(future.getPsshlddeptid());
/* 115 */     oldFuture.setPsshldmsdeptid(future.getPsshldmsdeptid());
/*     */ 
/* 117 */     oldFuture.setQfldid(future.getQfldid());
/* 118 */     oldFuture.setQfldmsid(future.getQfldmsid());
/* 119 */     oldFuture.setQfldmsname(future.getQfldmsname());
/* 120 */     oldFuture.setQfldname(future.getQfldname());
/* 121 */     oldFuture.setQflddeptid(future.getQflddeptid());
/* 122 */     oldFuture.setQfldmsdeptid(future.getQfldmsdeptid());
/*     */ 
/* 124 */     oldFuture.setShldid(future.getShldid());
/* 125 */     oldFuture.setShldmsid(future.getShldmsid());
/* 126 */     oldFuture.setShldmsname(future.getShldmsname());
/* 127 */     oldFuture.setShldname(future.getShldname());
/* 128 */     oldFuture.setShlddeptid(future.getShlddeptid());
/* 129 */     oldFuture.setShldmsdeptid(future.getShldmsdeptid());
/*     */ 
/* 131 */     oldFuture.setXbdeptid(future.getXbdeptid());
/* 132 */     oldFuture.setXbdeptname(future.getXbdeptname());
/* 133 */     oldFuture.setXbdeptreid(future.getXbdeptreid());
/*     */ 
/* 135 */     oldFuture.setZbdeptid(future.getZbdeptid());
/* 136 */     oldFuture.setZbdeptname(future.getZbdeptname());
/* 137 */     oldFuture.setZbdeptreid(future.getZbdeptreid());
/* 138 */     update(oldFuture);
/*     */   }
/*     */ 
/*     */   public void justSaveOrUpdateFuture(Future future) {
/* 142 */     Future oldFuture = findFuturesByPName(future.getProcessName(), future.getIncidentNo());
/* 143 */     if (oldFuture == null) {
/* 144 */       save(future);
/*     */     } else {
/* 146 */       oldFuture.setLeaderList(future.getLeaderList());
/*     */ 
/* 148 */       oldFuture.setDeptIdList(future.getDeptIdList());
/* 149 */       oldFuture.setDeptList(future.getDeptList());
/*     */ 
/* 151 */       oldFuture.setFinishList(future.getFinishList());
/* 152 */       oldFuture.setFinishlistname(future.getFinishlistname());
/*     */ 
/* 154 */       oldFuture.setHqldid(future.getHqldid());
/* 155 */       oldFuture.setHqldmsid(future.getHqldmsid());
/* 156 */       oldFuture.setHqldmsname(future.getHqldmsname());
/* 157 */       oldFuture.setHqldname(future.getHqldname());
/* 158 */       oldFuture.setHqlddeptid(future.getHqlddeptid());
/* 159 */       oldFuture.setHqldmsdeptid(future.getHqldmsdeptid());
/*     */ 
/* 161 */       oldFuture.setFinishlistdeptid(future.getFinishlistdeptid());
/*     */ 
/* 163 */       oldFuture.setNbsugdept(future.getNbsugdept());
/* 164 */       oldFuture.setNbsugdeptldid(future.getNbsugdeptldid());
/*     */ 
/* 166 */       oldFuture.setPshqldid(future.getPshqldid());
/* 167 */       oldFuture.setPshqldmsid(future.getPshqldmsid());
/* 168 */       oldFuture.setPshqldmsname(future.getPshqldmsname());
/* 169 */       oldFuture.setPshqldname(future.getPshqldname());
/* 170 */       oldFuture.setPshqlddeptid(future.getPshqlddeptid());
/* 171 */       oldFuture.setPshqldmsdeptid(future.getPshqldmsdeptid());
/*     */ 
/* 173 */       oldFuture.setPsshldid(future.getPsshldid());
/* 174 */       oldFuture.setPsshldmsid(future.getPsshldmsid());
/* 175 */       oldFuture.setPsshldmsname(future.getPsshldmsname());
/* 176 */       oldFuture.setPsshldname(future.getPsshldname());
/* 177 */       oldFuture.setPsshlddeptid(future.getPsshlddeptid());
/* 178 */       oldFuture.setPsshldmsdeptid(future.getPsshldmsdeptid());
/*     */ 
/* 180 */       oldFuture.setQfldid(future.getQfldid());
/* 181 */       oldFuture.setQfldmsid(future.getQfldmsid());
/* 182 */       oldFuture.setQfldmsname(future.getQfldmsname());
/* 183 */       oldFuture.setQfldname(future.getQfldname());
/* 184 */       oldFuture.setQflddeptid(future.getQflddeptid());
/* 185 */       oldFuture.setQfldmsdeptid(future.getQfldmsdeptid());
/*     */ 
/* 187 */       oldFuture.setShldid(future.getShldid());
/* 188 */       oldFuture.setShldmsid(future.getShldmsid());
/* 189 */       oldFuture.setShldmsname(future.getShldmsname());
/* 190 */       oldFuture.setShldname(future.getShldname());
/* 191 */       oldFuture.setShlddeptid(future.getShlddeptid());
/* 192 */       oldFuture.setShldmsdeptid(future.getShldmsdeptid());
/*     */ 
/* 194 */       oldFuture.setUserId(future.getUserId());
/* 195 */       oldFuture.setUsername(future.getUsername());
/*     */ 
/* 197 */       oldFuture.setXbdeptid(future.getXbdeptid());
/* 198 */       oldFuture.setXbdeptname(future.getXbdeptname());
/* 199 */       oldFuture.setXbdeptreid(future.getXbdeptreid());
/*     */ 
/* 201 */       oldFuture.setZbdeptid(future.getZbdeptid());
/* 202 */       oldFuture.setZbdeptname(future.getZbdeptname());
/* 203 */       oldFuture.setZbdeptreid(future.getZbdeptreid());
/*     */ 
/* 207 */       update(oldFuture);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toOrdersByQFLeader(String noOrderString)
/*     */   {
/* 213 */     String orderString = "";
/* 214 */     String inString = "''";
/* 215 */     if ((noOrderString != null) && (noOrderString.length() > 0)) {
/* 216 */       String[] temps = noOrderString.split(",");
/* 217 */       for (int i = 0; i < temps.length; i++) {
/* 218 */         if ((temps[i] != null) && (temps[i].length() > 0)) {
/* 219 */           inString = inString + ",'" + temps[i] + "'";
/*     */         }
/*     */       }
/*     */     }
/* 223 */     String sql = "select SUP_LOGIN_NAME from v_superinfo v where v.sup_login_name in (" + inString + ") order by orders";
/* 224 */     Session session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
/* 225 */     SQLQuery query = session.createSQLQuery(sql);
/* 226 */     query.addScalar("SUP_LOGIN_NAME", Hibernate.STRING);
/* 227 */     List list = query.list();
/* 228 */     if ((list != null) && (list.size() > 0)) {
/* 229 */       for (int i = 0; i < list.size(); i++) {
/* 230 */         orderString = orderString + String.valueOf(list.get(i)) + ",";
/*     */       }
/*     */     }
/* 233 */     return orderString;
/*     */   }
/*     */ 
/*     */   public String toOrdersByDept(String noOrderString)
/*     */   {
/* 238 */     String orderString = "";
/* 239 */     String inString = "''";
/* 240 */     if ((noOrderString != null) && (noOrderString.length() > 0)) {
/* 241 */       String[] temps = noOrderString.split(",");
/* 242 */       for (int i = 0; i < temps.length; i++) {
/* 243 */         if ((temps[i] != null) && (temps[i].length() > 0)) {
/* 244 */           inString = inString + ",'" + temps[i] + "'";
/*     */         }
/*     */       }
/* 247 */       String sql = "select n.name from cs_organ_model t,cs_organ_node n where t.id in (" + inString + ")  and t.org_node_id=n.id order by t.orders";
/* 248 */       Session session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
/* 249 */       SQLQuery query = session.createSQLQuery(sql);
/* 250 */       query.addScalar("name", Hibernate.STRING);
/* 251 */       List list = query.list();
/* 252 */       if ((list != null) && (list.size() > 0)) {
/* 253 */         for (int i = 0; i < list.size(); i++) {
/* 254 */           orderString = orderString + String.valueOf(list.get(i)) + ",";
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 259 */     return orderString;
/*     */   }
/*     */ 
/*     */   public String getDeptsOrders(String noOrderString)
/*     */   {
/* 264 */     String orderString = "";
/* 265 */     String inString = "''";
/* 266 */     if ((noOrderString != null) && (noOrderString.length() > 0)) {
/* 267 */       String[] temps = noOrderString.split(",");
/* 268 */       for (int i = 0; i < temps.length; i++) {
/* 269 */         if ((temps[i] != null) && (temps[i].length() > 0)) {
/* 270 */           inString = inString + ",'" + temps[i] + "'";
/*     */         }
/*     */       }
/* 273 */       String sql = "select t.orders,'#'||n.id||'#'||n.name as name from cs_organ_model t,cs_organ_node n where t.id in (" + inString + ")  and t.org_node_id=n.id order by t.orders";
/* 274 */       Session session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
/* 275 */       SQLQuery query = session.createSQLQuery(sql);
/* 276 */       query.addScalar("orders", Hibernate.INTEGER).addScalar("name", Hibernate.STRING);
/* 277 */       List list = query.list();
/* 278 */       int orders = 0;
/* 279 */       if ((list != null) && (list.size() > 0)) {
/* 280 */         for (int i = 0; i < list.size(); i++) {
/* 281 */           orders = Integer.valueOf(String.valueOf(((Object[])list.get(i))[0])).intValue();
/* 282 */           orderString = orderString + getOrdersNum(orders) + String.valueOf(((Object[])list.get(i))[1]) + ",";
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 288 */     return orderString;
/*     */   }
/*     */ 
/*     */   public String getOrdersNum(int i) {
/* 292 */     String nextNum = "00";
/* 293 */     if (i < 10)
/* 294 */       nextNum = nextNum + i;
/* 295 */     else if ((i >= 10) && (i < 100))
/* 296 */       nextNum = "0" + i;
/*     */     else {
/* 298 */       nextNum = i+"";
/*     */     }
/* 300 */     return nextNum;
/*     */   }
/*     */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.future.dao.impl.FutureDaoImpl
 * JD-Core Version:    0.6.0
 */