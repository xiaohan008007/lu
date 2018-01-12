package com.taotaosou.lu.common.hash.method2;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * hash一致性算法
 * 存在雪崩的情况，所以我们创建多个虚拟节点对应物理机可以利用虚拟节点
 * 为了分布均匀，创建虚拟节点
 * Created by lijianzhen1 on 2017/9/6.
 */
public class VirtualHashIdentical extends HashIdentical {
    //真实结点列表,考虑到服务器上线、下线的场景，即添加、删除的场景会比较频繁，这里使用LinkedList会更好
    private static List<String> realNodes = new LinkedList<String>();
    //虚拟节点，key表示虚拟节点的hash值，value表示虚拟节点的名称
    private static SortedMap<Integer, String> virtualNodes = new TreeMap<Integer, String>();
    //虚拟节点的数目，这里写死，为了演示需要，一个真实结点对应5个虚拟节点
    private static final int VIRTUAL_NODES = 5;

    static {
        //先把原始的服务器添加到真实结点列表中
        for (int i = 0; i < servers.length; i++)
            realNodes.add(servers[i]);

        //再添加虚拟节点，遍历LinkedList使用foreach循环效率会比较高
        for (String str : realNodes) {
            virtualNodes.putAll(generateVitureNode(str));
        }
        System.out.println();
    }

    /**
     * 移除一致性hash环中的真实节点
     */
    private static void delTrueNode(String server) {
        //移除虚拟节点
        SortedMap<Integer, String> rmVitureNode = generateVitureNode(server);
        for (Iterator<Map.Entry<Integer, String>> entry = rmVitureNode.entrySet().iterator(); entry.hasNext(); ) {
            Map.Entry<Integer, String> eMap = entry.next();
            System.out.println("移除真实节点为" + server + "的虚拟地址[" + eMap.getKey() + ":" + eMap.getValue() + "]");
            virtualNodes.remove(eMap.getKey());
        }
        //移除真实的物理节点--这里其实不需要移除也可以，如果将来不再接入时候可以考虑移除，但是2^32已经足够大了
    }

    /**
     * 添加真实节点
     *
     * @param server
     */
    private static void addTrueNote(String server) {
        //添加真实节点，如果移除真实节点需要添加，没有移除的话就保留就好了
        //添加虚拟节点
        SortedMap<Integer, String> rmVitureNode = generateVitureNode(server);
        for (Iterator<Map.Entry<Integer, String>> entry = rmVitureNode.entrySet().iterator(); entry.hasNext(); ) {
            Map.Entry<Integer, String> eMap = entry.next();
            System.out.println("生成真实节点为" + server + "的虚拟地址[" + eMap.getKey() + ":" + eMap.getValue() + "]");
        }
        virtualNodes.putAll(rmVitureNode);
    }


    /**
     * 通过真实的物理机获得虚拟节点
     *
     * @param trueNode
     * @return
     */
    private static SortedMap<Integer, String> generateVitureNode(String trueNode) {
        SortedMap<Integer, String> virtualNodes = new TreeMap<Integer, String>();
        for (int i = 0; i < VIRTUAL_NODES; i++) {
            String virtualNodeName = trueNode + "&&VN" + String.valueOf(i);
            //这里可以删除
            System.out.println("虚拟节点[" + virtualNodeName + "]被添加, hash值为" + getHash(virtualNodeName));
            virtualNodes.put(getHash(virtualNodeName), virtualNodeName);
        }
        return virtualNodes;
    }


    //获得当前路由的节点
    private static String getServer(String key) {
        //得到对应的hash值
        int hash = getHash(key);
        //得到大于hash值中所有map
        SortedMap<Integer, String> subMap = virtualNodes.tailMap(hash);
        String virtualNode;
        //注意下边实现了hash一致环
        if (subMap.isEmpty()) {
            //如果当前的值没有大于该值的，从第一个节点开始
            Integer i = virtualNodes.firstKey();
            //返回对应的服务器地址
            virtualNode = virtualNodes.get(i);
        } else {
            //顺时针下个节点位置，离当前hash值最近的那个位置
            Integer i = subMap.firstKey();
            //返回对应的服务器
            virtualNode = subMap.get(i);
        }//virtualNode虚拟节点名称要截取一下
        if (!StringUtils.isEmpty(virtualNode)) {
            return virtualNode.substring(0, virtualNode.indexOf("&&"));
        }
        return null;
    }

    public static void main(String[] args) {
        String[] keys = {"美国", "俄罗斯", "中国"};
        for (String key : keys)
            System.out.println(key + "-的hash值为" + getHash(key) + ",被路由到节点" + getServer(key)); System.out.println();
        try {
            Thread.sleep(1110);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //移除某一真实的节点
        delTrueNode("192.168.0.0:111");
        System.out.println("移除物理机192.168.0.0:111后分配的hash地址\n");
        for (String key : keys)
            System.out.println(key + "-的hash值为" + getHash(key) + ",被路由到节点" + getServer(key)); System.out.println();
        try {
            Thread.sleep(1101);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addTrueNote("192.168.0.0:111");
        System.out.println("添加物理机192.168.0.0:111后分配的hash地址\n");
        for (String key : keys)
            System.out.println(key + "-的hash值为" + getHash(key) + ",被路由到节点" + getServer(key));
    }
}