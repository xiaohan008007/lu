package com.taotaosou.lu.redis.queue;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Kinglf on 2016/10/17.
 */
public class TestRedisQueue {
	public static byte[] redisKey = "key10".getBytes();
	static {
		// try {
		// init();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	private static void init() throws IOException {
		for (int i = 0; i < 1000000; i++) {
			Message message = new Message(i, "这是第" + i + "个内容");
			long size = JedisUtil.llen(redisKey);
			if (size > 9) {
				System.out.println("full");
				return;
			}
			JedisUtil.lpush(redisKey, ObjectUtil.object2Bytes(message));
			System.out.println(i);
		}

	}

	public static void push(Message message) {
		try {

			if (JedisUtil.lpush(redisKey, ObjectUtil.object2Bytes(message)) > 3) {
				System.out.println(message.getId());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		JedisUtil.del(redisKey);
		// try {
		// pop();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		CountDownLatch countDownLatch = new CountDownLatch(5);
		TestRedisQueue t = new TestRedisQueue();
		for (int i = 0; i < 5; i++) {
			Message message = new Message(i, "这是第" + i + "个内容");
			new Thread(t.new Threadb(message, countDownLatch)).start();
		}
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("最终大小：" + JedisUtil.llen(redisKey));
	}

	private static void pop() throws Exception {

		byte[] bytes = JedisUtil.rpop(redisKey);
		Message msg = (Message) ObjectUtil.bytes2Object(bytes);
		if (msg != null) {
			System.out.println(msg.getId() + "----" + msg.getContent());
		}
	}

	class Threadb implements Runnable {
		private Message message;
		private CountDownLatch countDownLatch;

		public Threadb(Message message, CountDownLatch countDownLatch) {
			this.message = message;
			this.countDownLatch = countDownLatch;
		}

		@Override
		public void run() {
			try {

				TestRedisQueue.push(message);
				countDownLatch.countDown();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}