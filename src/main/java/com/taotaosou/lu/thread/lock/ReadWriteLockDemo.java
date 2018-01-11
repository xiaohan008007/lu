/**
 * 
 */
package com.taotaosou.lu.thread.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁 ：执行结果看出 读是并发的 写是阻塞有序的
 * 
 * @author tracy.lu 2017年9月11日
 */
public class ReadWriteLockDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final Count c = new ReadWriteLockDemo().new Count();
		for (int i = 0; i < 3; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					c.get();
				}
			}).start();
		}

		for (int i = 0; i < 3; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					c.put();
				}
			}).start();
		}

	}

	class Count {
		private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

		public void get() {
			rwl.readLock().lock();
			try {
				System.out.println(Thread.currentThread().getName() + " read start");
				Thread.sleep(1000);// 模拟干活
				System.out.println(Thread.currentThread().getName() + " read end");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				rwl.readLock().unlock();
			}
		}

		public void put() {
			rwl.writeLock().lock();
			try {
				System.out.println(Thread.currentThread().getName() + " write start");
				Thread.sleep(1000);
				System.out.println(Thread.currentThread().getName() + " write end");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				rwl.writeLock().unlock();
			}
		}
	}

}
