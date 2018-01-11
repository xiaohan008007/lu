package com.taotaosou.lu.redis;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.taotaosou.lu.redis.constant.Constant;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
 
import java.util.HashSet;
import java.util.Set;
 
public class JedisClusterConfig {
 
    public JedisCluster getJedisCluster() {
        String[] serverArray = Constant.REDIS_CACHE_CLUSTER_NODES.split(",");
        Set<HostAndPort> nodes = new HashSet();
 
        for (String ipPort : serverArray) {
            String[] ipPortPair = ipPort.split(":");
            nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
        }
 
        JedisCluster jedisCluster = new JedisCluster(nodes, Constant.REDIS_CACHE_COMMANDTIMEOUT, Constant.REDIS_CACHE_SOTIMEOUT, Constant.REDIS_CACHE_MAXATTEMPTS, Constant.REDIS_CACHE_CLUSTER_PASSWORD, new GenericObjectPoolConfig());
        return jedisCluster;
    }
}