/**
 * 
 */
package com.taotaosou.lu.thread.future.fork_join;

import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

/**
 * @author tracy.lu 2017年9月15日
 */
public class ForkJoinTaskDemo {

	/**
	 * @param args
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ForkJoinPool forkJoinPool = new ForkJoinPool();

		// ===========demo1================
		// CountTask task = new CountTask(1, 5);
		// Future<Integer> result = forkJoinPool.submit(task);
		// System.out.println("1-5相加结果:" + result.get());
		// long t = System.currentTimeMillis();
		// CountTask task2 = new CountTask(1, 10);
		// Future<Integer> result2 = forkJoinPool.submit(task2);
		// System.out.println("1-100相加结果:" + (System.currentTimeMillis() - t) + "\t" +
		// result2.get());

		// CountTask task2 = new CountTask(1, 10);
		// Future<Integer> result2 = forkJoinPool.submit(task2);
		// System.out.println("1-100相加结果:" + (System.currentTimeMillis() - t) + "\t" +
		// result2.get());

		// ==============demo2==================
		FileCountingTask fileCountingTask = new FileCountingTask(Paths.get("E:\\bbs"));
		Integer count = forkJoinPool.invoke(fileCountingTask);
		System.out.println("文件数:" + count);
		System.out.println("mainThread end");

	}

}
