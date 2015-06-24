package com.wonders.contact.external.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XFZ
 * @version 1.0 2012-7-27
 */
public class ExternalResultMulti extends AbstractExternalResult{
	
	@SuppressWarnings("unchecked")
	public List params = new ArrayList();

	@Override
	public String getString() {
		return params.toString();
	}
}
