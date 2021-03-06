package com.example.seckill.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.security.Key;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-22 6:51 PM
 */
@Service
public class RedisService {

    @Autowired
    JedisPool jedisPool;

    public <T> T get(KeyPrefix keyPrefix, String key, Class<T> clazz){
       Jedis jedis = null;
       try {
           jedis = jedisPool.getResource();
           String str = jedis.get(keyPrefix.getPrefix()+key);
           T t = stringToBean(str,clazz);
           return t;
       }finally {
           returnToPool(jedis);
       }
    }

    @SuppressWarnings("unchecked")
    public static   <T> T stringToBean(String str,Class<T> clazz) {
        if (str == null || str.length()<=0 || clazz == null ){
            return null;
        }
        if (clazz == int.class || clazz == Integer.class){
            return (T)Integer.valueOf(str);
        }else if (clazz == String.class){
            return (T)str;
        }else if (clazz == long.class || clazz == Long.class){
            return (T)Long.valueOf(str);
        }else{
            return JSON.toJavaObject(JSON.parseObject(str),clazz);
        }
    }

    public<T> boolean set(KeyPrefix keyPrefix,String key,T value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            if (str == null || str.length()<=0){
                return false;
            }
            int expireSeconds = keyPrefix.expireSeconds();
            String realKey = keyPrefix.getPrefix()+key;
            if (expireSeconds <= 0){
                jedis.set(realKey,str);
            }else{
                jedis.setex(realKey,expireSeconds,str);
            }

            return true;
        }finally {
            returnToPool(jedis);
        }
    }

    public static   <T> String beanToString(T value) {
        if (value == null){
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class){
            return value+"";
        }else if (clazz == String.class){
            return (String)value;
        }else if (clazz == long.class || clazz == Long.class){
            return ""+value;
        }else{
            return JSON.toJSONString(value);
        }

    }

    private void returnToPool(Jedis jedis) {
        if (jedis != null){
            jedis.close();
        }
    }


    public<T> boolean exitsKey(KeyPrefix keyPrefix,String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realkey = keyPrefix.getPrefix()+key;
            return jedis.exists(realkey);
        }finally {
            returnToPool(jedis);
        }
    }

    public<T> Long incr(KeyPrefix keyPrefix,String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realkey = keyPrefix.getPrefix()+key;
            return jedis.incr(realkey);
        }finally {
            returnToPool(jedis);
        }
    }

    public<T> Long decr(KeyPrefix keyPrefix,String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realkey = keyPrefix.getPrefix()+key;
            return jedis.decr(realkey);
        }finally {
            returnToPool(jedis);
        }
    }


}