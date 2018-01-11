/**
 * 
 */
package com.taotaosou.lu.thread.exception;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * @author tracy.lu 2017年9月7日
 */
public class ExceptionHandler implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.out.println("An exception has been captured");
		System.out.printf("Thread:%s\n", t.getId());
		System.out.printf("Exception: %s: %s\n", e.getClass().getName(), e.getMessage());
		e.printStackTrace(System.out);
		System.out.printf("Thread status: %s\n", t.getState());
	}

}
