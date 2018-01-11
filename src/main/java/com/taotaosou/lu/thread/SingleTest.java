/**
 * 
 */
package com.taotaosou.lu.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author tracy.lu 2017年9月15日
 * 
 *         懒汉单例模式测试（并发）
 */
public class SingleTest {
	private static Singleton instance;

	public static Singleton getInstance() {
		if (instance == null) {
			synchronized (Singleton.class) {
				if (instance == null) {
					System.out.println(Thread.currentThread().getName() + "进来初始化了");
					instance = new SingleTest().new Singleton("haha");
				}
			}
		}
		return instance;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 0; i < 10000; i++) {
			executor.submit(new Runnable() {

				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName() + SingleTest.getInstance().getName());

				}
			});
		}

	}

	class Singleton {
		private String name;

		public Singleton(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
