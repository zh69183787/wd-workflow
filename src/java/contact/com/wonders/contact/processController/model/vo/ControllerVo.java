package com.wonders.contact.processController.model.vo;


public abstract class ControllerVo {
	protected String type = "";
	
	protected String processname = "";
	protected String incident = "";
	
	protected String key = "";
	
	protected String contextPath = "";
	protected String port = "";
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProcessname() {
		return processname;
	}

	public void setProcessname(String processname) {
		this.processname = processname;
	}

	public String getIncident() {
		return incident;
	}

	public void setIncident(String incident) {
		this.incident = incident;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	@Override
	public String toString(){
		return "controllerVo: type:"+type+" processname:"+processname+" incident:"+incident+" key:"+key;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
}
