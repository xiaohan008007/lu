package com.taotaosou.lu.thread.ThreadLoal;

public class TestThreadLocal {
	ThreadLocal<String> threadLocal = new ThreadLocal<String>() {
		protected String initialValue() {
			return "hehe";
		}
	};

	public static void main(String[] args) {
		TestThreadLocal testThreadLocal = new TestThreadLocal();
		testThreadLocal.threadLocal.set("haha");
		System.out.println(testThreadLocal.threadLocal.get());
		TestClient test1 = new TestClient(testThreadLocal);
		for (int i = 0; i < 3; i++) {
			new Thread(test1).start();
		}
		// ThreadLocalSimple<Integer> t = new ThreadLocalSimple<Integer>() {
		// protected Integer initialValue() {
		// return 0;
		// }
		// };
		// ThreadLocal<Integer> tl = new ThreadLocal<Integer>() {
		// protected Integer initialValue() {
		// return 0;
		// }
		// };
		// TestClient test1 = new TestClient(t, tl);
		// for (int i = 0; i < 3; i++) {
		// new Thread(test1).start();
		// }
	}

	private static class TestClient implements Runnable {

		private ThreadLocalSimple<Integer> t;
		private ThreadLocal<Integer> tl;
		private TestThreadLocal tString;

		public TestClient(ThreadLocalSimple<Integer> t, ThreadLocal<Integer> tl) {
			this.t = t;
			this.tl = tl;
		}

		public TestClient(TestThreadLocal tl) {
			this.tString = tl;
		}

		@Override
		public void run() {
			for (int i = 0; i < 3; i++) {
				// System.out.println(Thread.currentThread().getName() + "\t simple " +
				// t.get());
				// t.set(t.get() + 1);
				// System.out.println(Thread.currentThread().getName() + "\t system " +
				// tl.get());
				// tl.set(tl.get() + 1);
				System.out.println(Thread.currentThread().getName() + "\t system " + tString.threadLocal.get());
				// tl.set(tl.get() + 1);
			}
		}

	}
}
