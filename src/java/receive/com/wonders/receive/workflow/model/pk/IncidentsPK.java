/**
 * 
 */
package com.wonders.receive.workflow.model.pk;

import javax.persistence.Column;

/** 
 * @ClassName: IncidentsPK 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-19 下午2:01:13 
 *  
 */
public class IncidentsPK implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1505157268675277506L;
	private String processname;
	private Integer incident;
	/**
	 * @return the processname
	 */
	@Column(name = "PROCESSNAME", nullable = true, length = 512)
	public String getProcessname() {
		return processname;
	}
	/**
	 * @param processname the processname to set
	 */
	public void setProcessname(String processname) {
		this.processname = processname;
	}
	/**
	 * @return the incident
	 */
	@Column(name = "INCIDENT")
	public Integer getIncident() {
		return incident;
	}
	/**
	 * @param incident the incident to set
	 */
	public void setIncident(Integer incident) {
		this.incident = incident;
	}
	
	@Override 
    public boolean equals(Object obj) { 
        if(obj instanceof IncidentsPK){ 
        	IncidentsPK pk=(IncidentsPK)obj; 
            if(this.processname.equals(pk.processname)&&this.incident == pk.incident){ 
                return true; 
            } 
        } 
        return false; 
    }

    @Override 
    public int hashCode() { 
    	int result = 17;
    	/*    */ 
    	/* 75 */     result = 37 * result + (getProcessname() == null ? 0 : getProcessname().hashCode());
    	/* 76 */     result = 37 * result + (getIncident() == null ? 0 : getIncident().hashCode());
    	/* 77 */     return result;
    }

}
