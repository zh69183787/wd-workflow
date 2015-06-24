package com.wonders.dept.workflow.external.model;

import java.util.ArrayList;
import java.util.List;

import com.wonders.dept.workflow.external.model.AbstractExternalResult;

/**
 * @author XFZ
 * @version 1.0 2012-7-27
 */
public class ExternalResultMulti extends AbstractExternalResult{
	
	@SuppressWarnings("rawtypes")
	public List params = new ArrayList();

	@Override
	public String getString() {
		return params.toString();
	}
}
