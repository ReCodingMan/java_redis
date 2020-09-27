package com.kuang;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ListPosition;
import redis.clients.jedis.SortingParams;

public class TestList {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        jedis.flushDB();
        System.out.println("===========添加一个list===========");
        jedis.lpush("collections", "ArrayList", "Vector", "Stack", "HashMap", "WeakHashMap", "LinkedHashMap");
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
        //[start,stop]闭区间 -1代表倒数第一个元素，-2代表倒数第二个元素,end为-1表示查询全部
        System.out.println("collections区间0-3的元素："+jedis.lrange("collections",0,3));
        System.out.println("===============================");
        /**
         * lrem命令会从列表中找到等于value的元素进行删除， 根据count的不同
         * 分为三种情况：
         * ·count>0， 从左到右， 删除最多count个元素。
         * ·count<0， 从右到左， 删除最多count绝对值个元素。
         * ·count=0， 删除所有
         */
        jedis.lpush("collections_lrem", "ArrayList", "HashMap", "Vector", "Stack", "HashMap", "WeakHashMap", "HashMap", "LinkedHashMap", "HashMap");
        System.out.println("删除指定元素个数："+jedis.lrem("collections_lrem", 2, "HashMap"));
        System.out.println("删除指定元素个数后的内容："+jedis.lrange("collections_lrem", 0, -1));
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
        System.out.println("保留下表0-2区间之外的元素："+jedis.ltrim("collections", 0, 2));
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
        System.out.println("collections列表出栈（左端）："+jedis.lpop("collections"));
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
        System.out.println("collections添加元素，从列表右端，与lpush相对应："+jedis.rpush("collections", "EnumMap"));
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
        System.out.println("collections列表出栈（右端）："+jedis.rpop("collections"));
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
        System.out.println("修改collections指定下标1的内容："+jedis.lset("collections", 1, "LinkedArrayList"));
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));

        //指定插入位置
        jedis.linsert("collections", ListPosition.BEFORE,"WeakHashMap","WeakHashMap0");
        jedis.linsert("collections", ListPosition.AFTER,"LinkedArrayList","LinkedArrayList1");
        System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
        System.out.println("===============================");
        System.out.println("collections的长度："+jedis.llen("collections"));
        System.out.println("获取collections下标为2的元素："+jedis.lindex("collections", 2));
        System.out.println("===============================");
        jedis.lpush("sortedList", "3","6","2","0","7","4");
        System.out.println("sortedList排序前："+jedis.lrange("sortedList", 0, -1));
        System.out.println("sortedList排序："+jedis.sort("sortedList"));
        System.out.println("sortedList排序后："+jedis.lrange("sortedList", 0, -1));

        //阻塞 当给定列表内没有任何元素可供弹出的时候，连接将被 BLPOP 命令阻塞，直到等待超时或发现可弹出元素为止。
        System.out.println(jedis.blpop(1,"list_nil"));
        System.out.println(jedis.brpop(1,"list_nil"));

        System.out.println("===============================");
        SortingParams sortingParameters = new SortingParams();
        jedis.lpush("sortedList", "3","6","2","0","7","4");
        System.out.println("sortedList排序前："+jedis.lrange("sortedList", 0, -1));
        System.out.println("升序："+jedis.sort("sortedList", sortingParameters.asc()));
        System.out.println("降序："+jedis.sort("sortedList", sortingParameters.desc()));
    }
}
