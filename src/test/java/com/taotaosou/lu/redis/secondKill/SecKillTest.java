package com.taotaosou.lu.redis.secondKill;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.concurrent.CountDownLatch;

import org.junit.Before;
import org.junit.Test;

import com.taotaosou.lu.redis.secondKill.aop.CacheLockInterceptor;
import com.taotaosou.lu.redis.secondKill.util.RedisClient;

import redis.clients.jedis.JedisPool;

public class SecKillTest {
	private static Long commidityId1 = 10000001L;
	private static Long commidityId2 = 10000002L;
	private  RedisClient client;
	private JedisPool jedisPool;
	@Before
	public synchronized void  beforeTest() throws IOException{
		
		
		jedisPool = new JedisPool("192.168.3.175",6379);
		
	}
	
	@Test
	public void testSecKill(){
		int threadCount = 400;
		int splitPoint = 200;
		CountDownLatch endCount = new CountDownLatch(threadCount);
		CountDownLatch beginCount = new CountDownLatch(1);
		SecKillImpl testClass = new SecKillImpl();
		Thread[] threads = new Thread[threadCount];
		//起500个线程，秒杀第一个商品
		for(int i= 0;i < splitPoint;i++){
			threads[i] = new Thread(new  Runnable() {
				public void run() {
					try {
						//等待在一个信号量上，挂起
						beginCount.await();
						//用动态代理的方式调用secKill方法
						SeckillInterface proxy = (SeckillInterface) Proxy.newProxyInstance(SeckillInterface.class.getClassLoader(), 
							new Class[]{SeckillInterface.class}, new CacheLockInterceptor(testClass));
						proxy.secKill("test", commidityId1);
					} catch (InterruptedException e) {
						//System.out.println(e.getMessage());
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}catch(Exception e1){
						System.out.println("commidityId1有人没抢到");
						//System.out.println(e1.getMessage());
						//e1.printStackTrace();
					}finally {
						endCount.countDown();
						
					}
				}
			});
			threads[i].start();

		}
		
		for(int i= splitPoint;i < threadCount;i++){
			threads[i] = new Thread(new  Runnable() {
				public void run() {
					try {
						//等待在一个信号量上，挂起
						beginCount.await();
						//用动态代理的方式调用secKill方法
						SeckillInterface proxy = (SeckillInterface) Proxy.newProxyInstance(SeckillInterface.class.getClassLoader(), 
							new Class[]{SeckillInterface.class}, new CacheLockInterceptor(testClass));
						proxy.secKill("test", commidityId2);
						//testClass.testFunc("test", 10000001L);
					}  catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}catch(Exception e1){System.out.println("commidityId2有人没抢到"+e1.getMessage());}finally {
						endCount.countDown();
						
					}
				}
			});
			threads[i].start();

		}
		
		
		long startTime = System.currentTimeMillis();
		//主线程释放开始信号量，并等待结束信号量
		beginCount.countDown();
		
		try {
			//主线程等待结束信号量
			endCount.await();
			//观察秒杀结果是否正确
			System.out.println(SecKillImpl.inventory.get(commidityId1));
			System.out.println(SecKillImpl.inventory.get(commidityId2));
			System.out.println("error count " + CacheLockInterceptor.ERROR_COUNT);
			System.out.println("total cost time " + (System.currentTimeMillis() - startTime));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}