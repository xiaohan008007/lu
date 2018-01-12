package com.taotaosou.lu.common.hash.method2;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * hash一致性算法
 * 存在雪崩的情况，所以我们创建多个虚拟节点对应物理机可以利用虚拟节点
 * Created by lijianzhen1 on 2017/9/6.
 */
public class HashIdentical {
    //待添加入Hash环的服务器列表
    protected static String[] servers = {"192.168.0.0:111", "192.168.0.1:111",
            "192.168.0.2:111", "192.168.0.3:111", "192.168.0.4:111"};
    //key表示服务器的hash值，value表示服务器
    private static SortedMap<Integer, String> sortedMap = new TreeMap<Integer, String>();

    //程序初始化，将所有的服务器放入sortedMap中
    static {
        for (int i = 0; i < servers.length; i++) {
            int hash = getHash(servers[i]);
            System.out.println("[" + servers[i] + "]加入集合中, 其Hash值为" + hash);
            sortedMap.put(hash, servers[i]);
        }
        System.out.println();
    }

    //使用FNV1_32_HASH算法计算服务器的Hash值,这里不使用重写hashCode的方法，最终效果没区别
    protected static int getHash(String server) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < server.length(); i++)
            hash = (hash ^ server.charAt(i)) * p;
        //以下操作注意尽量使用质数
        hash += hash << 5;
        hash ^= hash >> 3;
        hash += hash << 11;
        hash ^= hash >> 17;
        hash += hash << 19;
        // 如果算出来的值为负数则取其绝对值
        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }


    //获得当前路由的节点
    private static String getServer(String key) {
        //得到对应的hash值
        int hash = getHash(key);
        //得到大于hash值中所有map
        SortedMap<Integer, String> subMap = sortedMap.tailMap(hash);
        //注意下边实现了hash一致环
        if (subMap.isEmpty()) {
            //如果当前的值没有大于该值的，从第一个节点开始
            Integer i = sortedMap.firstKey();
            //返回对应的服务器地址
            return sortedMap.get(i);
        } else {
            //顺时针下个节点位置，离当前hash值最近的那个位置
            Integer i = subMap.firstKey();
            return subMap.get(i);
        }
    }

    public static void main(String[] args) {
        String[] keys = {"美国", "俄罗斯", "中国"};
        for (String key : keys)
            System.out.println(key + "-的hash值为" + getHash(key) + ",被路由到节点" + getServer(key));

    }
}