/**
 * 
 */
package com.taotaosou.lu.thread.daemon;

/**
 * @author tracy.lu 2017年9月6日
 */
public class ThreadA implements Runnable {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		for (int i = 0; i < 9999; i++) {
			System.out.println("线程A第" + i + "次执行！");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
