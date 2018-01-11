/**
 * 
 */
package com.taotaosou.lu.thread.future.simple.jdk;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author tracy.lu 2017年9月15日
 */
public class Demo {
	private static ExecutorService executor = Executors.newCachedThreadPool();

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// Client c = new Client();
		// Client c2 = new Client();
		// FutureTask<String> futureTask = c.request("name");
		// // Thread.sleep(2000);
		// long t = System.currentTimeMillis();
		// String result = futureTask.get();
		// System.out.println((System.currentTimeMillis() - t) + "数据=" + result);

		FutureTask<String> futureTask = new FutureTask<String>(new RealData("name"));
		// 使用线程池
		// 执行FutureTask，相当于上例中的client.request("name")发送请求
		executor.submit(futureTask);
		FutureTask<Integer> futureTask2 = new FutureTask<Integer>(new RealData2("haha"));
		// 使用线程池
		// 执行FutureTask，相当于上例中的client.request("name")发送请求
		executor.submit(futureTask2);
		Thread.sleep(2000);
		long t = System.currentTimeMillis();
		String result = futureTask.get();
		Integer result2 = futureTask2.get();
		System.out.println((System.currentTimeMillis() - t) + "数据=" + result + result2);
	}

}
