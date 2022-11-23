package com.example.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class CacheUtils {
    //Make work with Redis cache

    public static final String PREFIX_CONNECTION_PLAYER = "player:";
    public static final String PREFIX_MOVES = "movesSendTo:";

    public final JedisPool pool;

    public CacheUtils(String host, Integer port) {
        this.pool = new JedisPool(host, port);
    }

    public Jedis getClient(){
        return pool.getResource();
    }
}
