package com.wonders.contact.todo.instance;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author XFZ
 * @version 1.0 2012-8-22
 */
public abstract class TodoInstance {
	abstract public void action(JdbcTemplate jt);
}
