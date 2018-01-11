/**
 * 
 */
package com.taotaosou.lu.thread.future.simple.my;

/**
 * @author tracy.lu 2017年9月15日
 * 
 *         FutureData是Future模式的关键，它实际上是真实数据RealData的代理，封装了获取RealData的等待过程。
 */
public class FutureData implements Data {
	RealData realData = null;// FutureData是RealData的封装
	private boolean isReady;// 是否已经准备好

	@Override
	public synchronized String getResult() throws InterruptedException {
		if (!isReady) {
			wait();// 一直等到RealData注入到FutureData中
		}
		return realData.getResult();
	}

	public synchronized void setRealData(RealData realData) {
		if (isReady)
			return;
		this.realData = realData;
		isReady = true;
		notifyAll();// RealData已经被注入到FutureData中了，通知getResult()方法
	}

}
