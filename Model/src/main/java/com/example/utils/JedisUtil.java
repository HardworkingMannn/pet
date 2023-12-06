package com.example.utils;

import com.example.Model.constant.JedisConst;
import redis.clients.jedis.Jedis;

public class JedisUtil {
    public static final Jedis jedis=new Jedis(JedisConst.JEDIS_HOST,JedisConst.JEDIS_PORT);
    public static Jedis  getJedis(){
        return jedis;
    }
}
