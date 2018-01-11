package com.taotaosou.lu.redis;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotaosou.lu.redis.demo.DistributedLockManger;

public class ClusterJedisTester extends TestCase
{
    private Logger logger = LoggerFactory.getLogger(ClusterJedisTester.class);

    private static String[] list = new String[] { "classpath:conf/redis/spring-redis.xml" };
    
    private static ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(list, true, null);
    
    DistributedLockManger  distributedLock = (DistributedLockManger)context.getBean("distributedLock");
    
    @Test
    public void testLock() throws InterruptedException
    {
        distributedLock.test();
        while(true)
        {
            Thread.sleep(1000);
        }
    }
}