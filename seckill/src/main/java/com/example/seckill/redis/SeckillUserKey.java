package com.example.seckill.redis;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-22 7:53 PM
 */
public class SeckillUserKey extends BasePrefix {
    public static final int TOKEN_EXPIRE = 3600*24*2;

    private SeckillUserKey(int expireSeconds,String prefix) {
        super(expireSeconds,prefix);
    }

    public static SeckillUserKey token = new SeckillUserKey(TOKEN_EXPIRE,"token");
}