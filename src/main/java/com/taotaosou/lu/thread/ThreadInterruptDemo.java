/**
 * 
 */
package com.taotaosou.lu.thread;

/**
 * @author tracy.lu 2017年8月30日
 */
public class ThreadInterruptDemo implements Runnable {

	@Override
	public void run() {
		boolean stop = false;
		while (!stop) {
			System.out.println("my thread is running~~~");
			long time = System.currentTimeMillis();
			while (System.currentTimeMillis() - time < 1000) {

			}
			if (Thread.currentThread().isInterrupted()) {
				break;
			}
		}
		System.out.println("my thread exiting under request~~~~~~~");

	}

	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(new ThreadInterruptDemo(), "InterruptDemo Thread");
		System.out.println("Starting thread~~~~");
		thread.start();
		Thread.sleep(3000);
		System.out.println("Interrupting thread ~~~");
		thread.interrupt();
		System.out.println("线程是否中断:" + thread.isInterrupted());
		Thread.sleep(3000);
		System.out.println("stopping application~~~");
	}

}
