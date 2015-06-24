package com.wonders.contact.todo.manager;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.wonders.contact.common.util.SimpleLogger;
import com.wonders.contact.todo.instance.TodoInstance;
import com.wonders.contact.todo.instance.impl.DeptContactTodoInstance;
import com.wonders.util.DbUtil;

/**
 * @author XFZ
 * @version 1.0 2012-8-22
 */
@Service("todoManager")
public class TodoManager {
	private static TodoInstance[] services = {new DeptContactTodoInstance()};
	
	static Logger log = SimpleLogger.getLogger(TodoManager.class);
	
	private static DbUtil dbUtil;
	private static PlatformTransactionManager transactionManager;
	
	public static void operate(){
		for(int i=0;i<services.length;i++){
			try{
				final TodoInstance instance = services[i];
				
				TransactionTemplate tt = new TransactionTemplate(transactionManager);
				tt.execute(new TransactionCallback() {
					public Object doInTransaction(TransactionStatus status) {
						try{
						JdbcTemplate jt = dbUtil.getJdbcTemplate();
						instance.action(jt);
						}catch(Exception e){
							e.printStackTrace();
							status.setRollbackOnly();
						}
						return null;
					}
				});  
			}catch(Exception e){
				log.error("service error:"+e.getMessage());
			}
		}
	}

	public static DbUtil getDbUtil() {
		return dbUtil;
	}
	
	@Autowired(required=false)
	public void setDbUtil(@Qualifier("dbUtil")DbUtil dbUtil) {
		TodoManager.dbUtil = dbUtil;
	}

	public static PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}

	@Resource(name="dsTransactionManager")
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		TodoManager.transactionManager = transactionManager;
	}
}
