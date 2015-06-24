package com.wonders.receive.workflow.external.model;

/**
 * @author XFZ
 * @version 1.0 2012-7-27
 */
public class ExternalResultSingle extends AbstractExternalResult{

	public class ExternalParam{
		public Object param = new Object();
		
		public String getString(){
			String ret = param.toString();
			if(ret!=null&&ret.length()>0){
				if(ret.startsWith("{")&&ret.endsWith("}")){
					return "["+ret+"]";
				}
			}
			return "";
		}
	}
	
	public ExternalParam params = new ExternalParam();
	
	@Override
	public String getString() {
		return params.getString();
	}
}
