/**
 * 
 */
package com.taotaosou.lu.thread.future.simple.jdk;

import java.util.concurrent.Callable;

/**
 * @author tracy.lu 2017年9月15日
 */
public class RealData<T> implements Callable<T> {
	protected T data;

	public RealData(T data) {
		this.data = data;
	}

	@Override
	public T call() throws Exception {
		Thread.sleep(1000l);// 利用sleep方法来表示真是业务是非常缓慢的
		return data;
	}
}
