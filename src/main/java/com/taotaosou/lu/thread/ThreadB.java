/**
 * 
 */
package com.taotaosou.lu.thread;

/**
 * @author tracy.lu 2017年8月30日
 */
public class ThreadB implements Runnable {

	@Override
	public void run() {
		try {
			Thread.sleep(500l);// 模拟执行500毫秒
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread curThread = Thread.currentThread();
		System.out.println("线程名:" + curThread.getName());
		System.out.println(curThread.getName() + "线程组中的活动线程数目：" + Thread.activeCount());
		System.out.println(curThread.getName() + "标识符：" + curThread.getId());
		System.out.println(curThread.getName() + "优先级：" + curThread.getPriority());
		System.out.println(curThread.getName() + "状态：" + curThread.getState());
		System.out.println(curThread.getName() + "所属线程组：" + curThread.getThreadGroup());
		System.out.println(curThread.getName() + "是否处于活动状态：" + curThread.isAlive());
		System.out.println(curThread.getName() + "是否守护线程：" + curThread.isDaemon());
	}

	public static void main(String[] args) {
		ThreadB t = new ThreadB();
		for (int i = 0; i < 5; i++) {

			new Thread(t, "线程名称:(" + i + ")").start();
		}
		Thread mainThread = Thread.currentThread();
		System.out.println("主线程:");
		System.out.println("主线程名:" + mainThread.getName());
		System.out.println(mainThread.getName() + "线程组中的活动线程数目：" + Thread.activeCount());
		System.out.println(mainThread.getName() + "标识符：" + mainThread.getId());
		System.out.println(mainThread.getName() + "优先级：" + mainThread.getPriority());
		System.out.println(mainThread.getName() + "状态：" + mainThread.getState());
		System.out.println(mainThread.getName() + "所属线程组：" + mainThread.getThreadGroup());
		System.out.println(mainThread.getName() + "是否处于活动状态：" + mainThread.isAlive());
		System.out.println(mainThread.getName() + "是否守护线程：" + mainThread.isDaemon());
		try {
			Thread.sleep(10000l);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
