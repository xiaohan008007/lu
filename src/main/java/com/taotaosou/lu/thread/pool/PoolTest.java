/**
 * 
 */
package com.taotaosou.lu.thread.pool;

import java.util.concurrent.Executors;

/**
 * @author tracy.lu 2017年9月14日
 */
public class PoolTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Executors.newCachedThreadPool();
		Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	}

}
