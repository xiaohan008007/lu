package com.taotaosou.lu.thread.queue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DealyQueueDemo {
	public static void main(String[] args) throws InterruptedException {
		DelayQueue<Student> dq = new DelayQueue<Student>();
		DealyQueueDemo dd = new DealyQueueDemo();
		dq.put(dd.new Student("小王", 50l));
		System.out.println(dq.poll(5, TimeUnit.SECONDS).getName());
	}

	class Student implements Delayed {
		private String name;
		private long workTime;
		private long submitTime;

		public Student(String name, long submitTime) {
			this.name = name;
			this.workTime = submitTime;
			this.submitTime = TimeUnit.NANOSECONDS.convert(submitTime, TimeUnit.SECONDS) + System.nanoTime();
		}

		public String getName() {
			return this.name + " 交卷，用时" + workTime;
		}

		@Override
		public int compareTo(Delayed o) {
			Student that = (Student) o;
			if (submitTime > that.submitTime) {
				return 1;
			} else if (submitTime < that.submitTime) {
				return -1;
			} else {
				return 0;
			}
		}

		@Override
		public long getDelay(TimeUnit unit) {
			// 返回一个延迟时间
			return unit.convert(submitTime - System.nanoTime(), unit.NANOSECONDS);
		}

	}
}
