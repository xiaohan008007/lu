package com.taotaosou.lu.thread.AbstractQueuedSynchronizer;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 利用AbstractQueuedSynchronizer自己实现锁
 * 
 * @author tracy.lu 2017年9月13日
 */
public class MyLockDemo implements Lock {
	public static void main(String[] args) {
		int a = 1;
		assert a == 0;
		System.out.println(a);
	}

	private static class Sync extends AbstractQueuedSynchronizer {
		// 是否占用状态 1就是占用了
		protected boolean isHeldExclusively() {
			return getState() == 1;
		}

		// 当状态为0时获取锁
		public boolean tryAcquire(int acquires) {
			assert acquires == 1;
			if (compareAndSetState(0, 1)) {// 为0时设为1 并返回true
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
			return false;
		}

		// 释放锁，将状态置为0
		protected boolean tryRelease(int releases) {
			assert releases == 1;
			if (getState() == 0) {
				throw new IllegalMonitorStateException();
			}
			setExclusiveOwnerThread(null);
			setState(0);
			return true;

		}

	}

	private final Sync sync = new Sync();

	@Override
	public void lock() {
		sync.acquire(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean tryLock() {
		return sync.tryAcquire(1);
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unlock() {
		sync.release(1);

	}

	public boolean isLocked() {
		return sync.isHeldExclusively();
	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

}
