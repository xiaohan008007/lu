package com.taotaosou.lu.redis;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.Transaction;

public class MyRunnable implements Runnable {

    String watchkeys = "watchkeys";// 监视keys
    Jedis jedis = new Jedis("192.168.3.175", 6379);
//    JedisCluster jedisCluster;
    public MyRunnable() {
//    	JedisClusterConfig jedisClusterConfig=new JedisClusterConfig();
//    	jedisCluster=jedisClusterConfig.getJedisCluster();
//    	List<JedisShardInfo> shards = Arrays.asList( 
//    			   new JedisShardInfo("localhost",6379), 
//    			   new JedisShardInfo("localhost",6380)); 
//    			  
//    			 ShardedJedis sharding = new ShardedJedis(shards); 
    }

    @Override
    public void run() {
        try {
        	jedis.watch(watchkeys);// watchkeys

            String val = jedis.get(watchkeys);
            int valint = Integer.valueOf(val);
            String userifo = UUID.randomUUID().toString();
            if (valint < 10) {
                Transaction tx = jedis.multi();// 开启事务

                tx.incr("watchkeys");

                List<Object> list = tx.exec();// 提交事务，如果此时watchkeys被改动了，则返回null
                if (list != null) {
                    System.out.println("用户：" + userifo + "抢购成功，当前抢购成功人数:"
                            + (valint + 1));
                    /* 抢购成功业务逻辑 */
                    jedis.sadd("setsucc", userifo);
                } else {
                    System.out.println("用户：" + userifo + "抢购失败");
                    /* 抢购失败业务逻辑 */
                    jedis.sadd("setfail", userifo);
                }

            } else {
                System.out.println("用户：" + userifo + "抢购失败==");
                jedis.sadd("setfail", userifo);
                // Thread.sleep(500);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }

    }

}