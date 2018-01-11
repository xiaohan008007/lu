/**
 * 
 */
package com.taotaosou.lu.thread.future.simple.jdk;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author tracy.lu 2017年9月15日
 */
public class Client {
	private static ExecutorService executor = Executors.newSingleThreadExecutor();

	public FutureTask<String> request(String data) {
		FutureTask<String> futureTask = new FutureTask<>(new RealData(data));
		// 使用线程池
		// 执行FutureTask，相当于上例中的client.request("name")发送请求
		executor.submit(futureTask);
		return futureTask;
	}
}
