package com.taotaosou.lu.thread.exception;

public class TestThreadException {

	public static void main(String[] args) {
		Thread s = new Thread(new Runnable() {

			@Override
			public void run() {
				int a = Integer.parseInt("ttt");
			}
		});
		s.setUncaughtExceptionHandler(new ExceptionHandler());
		s.start();

	}

}
