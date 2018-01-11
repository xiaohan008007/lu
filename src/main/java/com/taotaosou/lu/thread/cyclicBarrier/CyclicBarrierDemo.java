/**
 * 
 */
package com.taotaosou.lu.thread.cyclicBarrier;

import java.util.concurrent.CyclicBarrier;

import org.apache.commons.lang3.RandomUtils;

/**
 * @author tracy.lu 2017年9月14日 CountDownLatch与CyclicBarrier区别
 *         （1）CountDownLatch:一个线程（或者多个），等待另外n个线程完成某个事情之后才能执行
 *         （2）N个线程相互等待，任何一个线程完成前，所有的线程必须相互等待
 */
public class CyclicBarrierDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CyclicBarrier cy = new CyclicBarrier(3, new TotalTask());
		BillTask work1 = new BillTask("杭州", cy);
		BillTask work2 = new BillTask("上海", cy);
		BillTask work3 = new BillTask("北京", cy);
		work1.start();
		work2.start();
		work3.start();
		System.out.println("Main thread end");
	}

	static class TotalTask extends Thread {
		public void run() {
			System.out.println(Thread.currentThread().getName() + "触发,所有子任务都完成了，开始主任务");
		}
	}

	static class BillTask extends Thread {
		private String billName;
		private CyclicBarrier cyclicBarrier;

		public BillTask(String billName, CyclicBarrier cyclicBarrier) {
			this.billName = billName;
			this.cyclicBarrier = cyclicBarrier;
		}

		public void run() {
			try {
				long s = System.currentTimeMillis();
				System.out.println(Thread.currentThread().getName() + "市区：" + billName + "开始运算");

				Thread.sleep(RandomUtils.nextInt(1000, 5000));
				System.out.println(Thread.currentThread().getName() + "市区：" + billName + "运算完成("
						+ (System.currentTimeMillis() - s) + ")，等待中~~~");
				cyclicBarrier.await();
				System.out.println(Thread.currentThread().getName() + "全部结束，市区" + billName + "才开始后面的工作");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
