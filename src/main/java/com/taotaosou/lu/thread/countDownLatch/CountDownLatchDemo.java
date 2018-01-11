/**
 * 
 */
package com.taotaosou.lu.thread.countDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * @author tracy.lu 2017年9月13日
 */
public class CountDownLatchDemo {

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		CountDownLatch cd = new CountDownLatch(3);

		CountDownLatchDemo cdemo = new CountDownLatchDemo();
		Worker w1 = cdemo.new Worker(cd);
		Worker w2 = cdemo.new Worker(cd);
		Worker w3 = cdemo.new Worker(cd);
		w1.run();
		w2.run();
		w3.run();
		cd.await();
		System.out.println("全部结束");
	}

	class Worker implements Runnable {
		private CountDownLatch cd;

		public Worker(CountDownLatch cd) {
			this.cd = cd;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cd.countDown();
			System.out.println(Thread.currentThread().getName() + "干完活");

		}

	}

}
