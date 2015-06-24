package com.wonders.contact.todo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.wonders.contact.common.util.SimpleLogger;
import com.wonders.contact.todo.timer.TodoTimer;

@SuppressWarnings("serial")
public class TodoServlet extends HttpServlet {
	
	Logger log = SimpleLogger.getLogger(this.getClass());
	
	public TodoServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); 
		TodoTimer.stop();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	public void init() throws ServletException {
		TodoTimer.start();
	}
}
