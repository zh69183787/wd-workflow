package com.wonders.contact.processController.exception;

import org.apache.log4j.Logger;

import com.wonders.contact.common.util.SimpleLogger;

@SuppressWarnings("serial")
public class ProcessControllerException extends RuntimeException {
	Logger log = SimpleLogger.getLogger(this.getClass());
	public ProcessControllerException(String error){
		super(error);
		log.warn(error);
	}
}
