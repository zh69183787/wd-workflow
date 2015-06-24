package com.wonders.receive.workflow.external.model;

public abstract class AbstractExternalResult {
	public String description = "";
	public String code = "";
	
	public abstract String getString();
}
