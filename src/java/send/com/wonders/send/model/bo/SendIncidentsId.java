/*    */ package com.wonders.send.model.bo;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.math.BigDecimal;
/*    */ import javax.persistence.Column;
/*    */ import javax.persistence.Embeddable;
/*    */ 
/*    */ @Embeddable
/*    */ public class SendIncidentsId
/*    */   implements Serializable
/*    */ {
/*    */   private String processname;
/*    */   private BigDecimal incident;
/*    */ 
/*    */   public SendIncidentsId()
/*    */   {
/*    */   }
/*    */ 
/*    */   public SendIncidentsId(String processname, BigDecimal incident)
/*    */   {
/* 32 */     this.processname = processname;
/* 33 */     this.incident = incident;
/*    */   }
/*    */ 
/*    */   @Column(name="PROCESSNAME", nullable=false, length=512)
/*    */   public String getProcessname()
/*    */   {
/* 42 */     return this.processname;
/*    */   }
/*    */ 
/*    */   public void setProcessname(String processname) {
/* 46 */     this.processname = processname;
/*    */   }
/*    */ 
/*    */   @Column(name="INCIDENT", nullable=false, precision=20, scale=0)
/*    */   public BigDecimal getIncident() {
/* 52 */     return this.incident;
/*    */   }
/*    */ 
/*    */   public void setIncident(BigDecimal incident) {
/* 56 */     this.incident = incident;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object other)
/*    */   {
/* 63 */     if (this == other) return true;
/* 64 */     if (other == null) return false;
/* 65 */     if (!(other instanceof SendIncidentsId)) return false;
/* 66 */     SendIncidentsId castOther = (SendIncidentsId)other;
/*    */ 
/* 69 */     return ((getProcessname() == castOther.getProcessname()) || ((getProcessname() != null) && (castOther.getProcessname() != null) && (getProcessname().equals(castOther.getProcessname())))) && (
/* 69 */       (getIncident() == castOther.getIncident()) || ((getIncident() != null) && (castOther.getIncident() != null) && (getIncident().equals(castOther.getIncident()))));
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 73 */     int result = 17;
/*    */ 
/* 75 */     result = 37 * result + (getProcessname() == null ? 0 : getProcessname().hashCode());
/* 76 */     result = 37 * result + (getIncident() == null ? 0 : getIncident().hashCode());
/* 77 */     return result;
/*    */   }
/*    */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.model.bo.IncidentsId
 * JD-Core Version:    0.6.0
 */