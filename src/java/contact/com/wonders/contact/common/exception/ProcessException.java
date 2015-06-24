package com.wonders.contact.common.exception;

import org.apache.log4j.Logger;

import com.wonders.contact.common.util.SimpleLogger;

@SuppressWarnings("serial")
public class ProcessException extends RuntimeException {
	Logger log = SimpleLogger.getLogger(this.getClass());
	public ProcessException(String error){
		super(error);
		log.warn(error);
	}
}
