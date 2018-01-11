/**
 * 
 */
package com.taotaosou.lu.thread.future.simple.my;

/**
 * @author tracy.lu
 * 
 *         2017年9月15日 无论FutureData还是RealData都要实现这个接口
 */
public interface Data {
	String getResult() throws InterruptedException;
}
