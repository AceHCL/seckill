package com.example.seckill.redis;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-22 7:54 PM
 */
public class OrderKey extends BasePrefix {

    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}