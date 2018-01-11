package com.taotaosou.lu.thread.future.fork_join;

import java.util.concurrent.RecursiveTask;

class CountTask extends RecursiveTask<Integer> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int splitSize = 2;// 粒度
	private int start, end;

	public CountTask(int start, int end) {
		this.start = start;
		this.end = end;
	}

	@Override
	protected Integer compute() {
		int sum = 0;
		boolean canCompute = (end - start) <= splitSize;// 按粒度拆分 是否直接结算
		if (canCompute) {
			for (int i = start; i <= end; i++) {
				sum += i;
			}
			System.out.println(Thread.currentThread().getName());
		} else {
			int middle = (start + end) / 2;
			CountTask firstTask = new CountTask(start, middle);
			CountTask secondTask = new CountTask(middle, end);
			firstTask.fork();
			secondTask.fork();
			// 得不到结果下面两个都会阻塞直到有结果出来
			int firstResult = firstTask.join();
			int secondResult = secondTask.join();
			// 合并结果
			sum = firstResult + secondResult;
		}
		return sum;
	}
}