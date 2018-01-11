/**
 * 
 */
package com.taotaosou.lu.thread.future.simple.my;

/**
 * @author tracy.lu 2017年9月15日
 * 
 *         RealData是最终需要使用的数据，它的构造函数很慢。
 */
public class RealData implements Data {
	protected String data;

	public RealData(String data) {
		try {
			Thread.sleep(1000);// 用来表示构造的过程是很慢的
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.data = data;
	}

	@Override
	public String getResult() throws InterruptedException {
		return data;
	}

}
