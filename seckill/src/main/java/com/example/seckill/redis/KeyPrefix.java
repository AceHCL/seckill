package com.example.seckill.redis;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-22 7:47 PM
 */
public interface KeyPrefix {

    public int expireSeconds();
    public String getPrefix();

}