/**
 * 
 */
package com.taotaosou.lu.thread.queue;

import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;

/**
 * @author tracy.lu 2017年9月13日
 * 
 *         同步队列，本身不存储元素 ，一个put对应一个take 非常适合传递性场景
 * 
 *         SynchronousQueue最大的特点就是put/take是成对调用的：
 *         先调put，线程会阻塞在那；直到另外一个线程调用了take，2个线程才同时解锁。反之亦然。
 *         对于多个线程，比如3个，调用3次put，3个都会阻塞在那；直到等另外的线程，调用3次take，大家才同时解锁。反之亦然。
 * 
 *         这里就会有1个问题：先调用了3次put，那调用take的时候，是首先唤醒哪一个put线程呢？第1个，还是最后一个呢？
 * 
 *         这就涉及到2种不同的模式：公平模式（队列模式） 和 非公平模式（栈模式）。
 * 
 *         队列模式：最先调用put的线程，最先被take唤醒
 * 
 *         栈模式：最后调用put的线程，最先被take唤醒。
 */
public class SynchronousQueueDemo {

	public static void main(String[] args) {
		final SynchronousQueue<String> sq = new SynchronousQueue<>();
		TestDo td = new SynchronousQueueDemo().new TestDo();
		final Semaphore sem = new Semaphore(1);
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					String input;
					try {
						sem.acquire();
						input = sq.take();
						String output = td.doSome(input);
						System.out.println(Thread.currentThread().getName() + ":" + output);
						sem.release();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
		}

		for (int i = 0; i < 10; i++) {
			try {
				sq.put(i + "");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	class TestDo {
		public String doSome(String input) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String output = input + ":" + (System.currentTimeMillis() / 1000);
			return output;
		}
	}
}
