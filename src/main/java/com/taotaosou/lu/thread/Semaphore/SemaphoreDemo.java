/**
 * 
 */
package com.taotaosou.lu.thread.Semaphore;

import java.util.concurrent.Semaphore;

/**
 * @author tracy.lu 2017年9月14日
 *         信号量场景：服务器资源只允许10个同时进行，如果一下子来了20个，那可以用信号量（红绿灯）控制释放一个进入一个
 */
public class SemaphoreDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Semaphore sp = new Semaphore(3);
		for (int i = 0; i < 10; i++) {
			final int no = i;
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						System.out.println(no + "连上了=========");
						Thread.sleep(300);
						sp.acquire();
						System.out.println(no + "开始访问");
						Thread.sleep(1000l);// 模拟干活
						sp.release();
						System.out.println(no + "结束");
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
					}
				}
			}).start();
		}
		System.out.println("main Thread end");
	}

}
