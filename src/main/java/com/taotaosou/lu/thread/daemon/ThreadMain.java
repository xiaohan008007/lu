/**
 * 
 */
package com.taotaosou.lu.thread.daemon;

/**
 * @author tracy.lu 2017年9月6日
 */
public class ThreadMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ThreadA a = new ThreadA();
		ThreadB b = new ThreadB();
		Thread ta = new Thread(a);
		Thread tb = new Thread(b);
		ta.setDaemon(true);// 标为守护线程，即后台线程

		ta.start();
		tb.start();

		Thread mainThread = Thread.currentThread();

		System.out.println("ta是不是守护线程" + ta.isDaemon());
		System.out.println("tb是不是守护线程" + tb.isDaemon());
		System.out.println("main是不是守护线程" + mainThread.isDaemon());
		// 结论：随着前台线程的结束，即使守护线程还没跑完，整体就结束了
	}

}
