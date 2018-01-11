/**
 * 
 */
package com.taotaosou.lu.thread.future.simple.my;

/**
 * @author tracy.lu 2017年9月15日
 * 
 *         Client主要完成的功能包括：1. 返回一个FutureData；2.开启一个线程用于构造RealData。
 */
public class Client {
	public Data request(final String name) {
		FutureData futureData = new FutureData();
		new Thread(new Runnable() {

			@Override
			public void run() {
				RealData realData = new RealData(name);
				futureData.setRealData(realData);
			}
		}).start();
		return futureData;// 先直接返回FutureData
	}
}
