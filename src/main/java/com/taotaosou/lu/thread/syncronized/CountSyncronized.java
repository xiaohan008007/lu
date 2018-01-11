/**
 * 
 */
package com.taotaosou.lu.thread.syncronized;

/**
 * @author tracy.lu 2017年9月7日
 */
public class CountSyncronized {
	byte[] lock = new byte[1];
	int num = 0;

	public void test() {
		synchronized (lock) {
			num++;
			System.out.println(Thread.currentThread().getName() + "-" + num);
		}
	}

	public static void main(String[] args) {
		CountSyncronized c = new CountSyncronized();
		for (int i = 0; i < 30; i++) {

			new Thread(new Runnable() {

				@Override
				public void run() {
					c.test();

				}
			}).start();
		}
	}
}
