package com.wonders.contact.todo.timer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.wonders.contact.common.util.SimpleLogger;
import com.wonders.contact.todo.manager.TodoManager;

/**
 * 定时器
 * 
 * @author XFZ
 * @version 1.0 2012-8-22
 */
public class TodoTimer {
	static Logger log = SimpleLogger.getLogger(TodoTimer.class);

	private TodoTimer() {
	}

	/**
	 * 定时操作线程
	 */
	private final static Runnable TodoOperation = new Runnable() {
		public void run() {
			try {
				semaphore.acquire();

				TodoManager.operate();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				semaphore.release();
			}
		}
	};

	/**
	 * 同步信号量
	 */
	private static final Semaphore semaphore = new Semaphore(1);

	/**
	 * 定时执行
	 */
	private static ScheduledExecutorService scheduler = null;

	/**
	 * 启动定时器
	 */
	public static void start() {
		
		if (scheduler == null) {
			scheduler = Executors.newScheduledThreadPool(1);
			
			synchronized (scheduler) {
			
				/*
				 * TODO 设置定时器启动时间和间隔
				 */
				scheduler.scheduleAtFixedRate(TodoOperation, 20, 60*5, TimeUnit.SECONDS);
				log.debug("启动TODO定时器!");
			}
			
		} else {
			log.warn("TODO定时器正在运行!");
		}
		
	}

	/**
	 * 停止定时器
	 */
	public static void stop() {
		synchronized (scheduler) {
			if (scheduler != null && !scheduler.isShutdown()) {
				scheduler.shutdown();
				scheduler = null;
				log.debug("关闭TODO定时器!");
			}
		}
	}

	/**
	 * 重启定时器
	 */
	public static void restart() {
		stop();
		start();
	}

	/**
	 * 手动触发
	 */
	public static void trigger() {
		TodoOperation.run();
	}
}
