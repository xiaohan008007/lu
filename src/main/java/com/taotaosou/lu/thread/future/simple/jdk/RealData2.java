/**
 * 
 */
package com.taotaosou.lu.thread.future.simple.jdk;

import java.util.concurrent.Callable;

/**
 * @author tracy.lu 2017年9月15日
 */
public class RealData2 implements Callable<Integer> {
	protected String data;

	public RealData2(String data) {
		this.data = data;
	}

	@Override
	public Integer call() throws Exception {
		Thread.sleep(1000l);// 利用sleep方法来表示真是业务是非常缓慢的
		return 99;
	}
}
