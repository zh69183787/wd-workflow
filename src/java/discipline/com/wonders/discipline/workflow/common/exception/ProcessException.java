package com.wonders.discipline.workflow.common.exception;

import org.apache.log4j.Logger;

import com.wonders.discipline.workflow.common.util.SimpleLogger;


@SuppressWarnings("serial")
public class ProcessException extends RuntimeException {
	Logger log = SimpleLogger.getLogger(this.getClass());
	public ProcessException(String error){
		super(error);
		log.warn(error);
	}
}
