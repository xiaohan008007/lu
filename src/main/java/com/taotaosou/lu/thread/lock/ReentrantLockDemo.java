/**
 * 
 */
package com.taotaosou.lu.thread.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author tracy.lu 2017年9月7日
 */
public class ReentrantLockDemo {
	public static void main(String[] args) {
		final Count count = new ReentrantLockDemo().new Count();
		for (int i = 0; i < 2; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					count.get();

				}
			}).start();
		}
		for (int i = 0; i < 2; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					count.put();

				}
			}).start();
		}
	}

	class Count {
		final ReentrantLock lock = new ReentrantLock();// 全局的 和放在方法内部不一样

		public void get() {

			// final ReentrantLock lock = new ReentrantLock();
			try {
				lock.lock();
				System.out.println(Thread.currentThread().getName() + " get start");
				Thread.sleep(1000);
				System.out.println(Thread.currentThread().getName() + " get end");
				lock.unlock();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void put() {
			// final ReentrantLock lock = new ReentrantLock();
			try {
				lock.lock();
				System.out.println(Thread.currentThread().getName() + " put start");
				Thread.sleep(1000);
				System.out.println(Thread.currentThread().getName() + " put end");
				lock.unlock();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
