/**
 * 
 */
package com.taotaosou.lu.thread.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author tracy.lu 2017年9月11日
 */
public class ReadWriteLockDemo2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Count c = new ReadWriteLockDemo2().new Count();
		for (int i = 0; i < 20; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName() + " 来读 " + c.read("q"));
				}
			}).start();
		}
	}

	class Count {
		private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
		private final Map<String, String> map = new HashMap<>();// 数据缓存

		public String read(String key) {
			String value = null;
			rwl.readLock().lock();// 先上读锁
			try {
				value = map.get(key);
				if (value == null) {
					rwl.readLock().unlock();// 没有结果那就释放读锁，开始写操作
					rwl.writeLock().lock();// 上写锁
					try {
						rwl.readLock().lock();
						value = map.get(key);
						rwl.readLock().unlock();
						if (value == null) {
							Thread.sleep(100);
							System.out.println(Thread.currentThread().getName() + " 写入key " + key);
							value = "aaa";
							map.put(key, value);// 模拟数据库操作
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						rwl.writeLock().unlock();// 写完毕 释放锁
					}
					rwl.readLock().lock();// 再上读锁
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				rwl.readLock().unlock();// 最终释放读锁
			}
			return value;
		}
	}

}
