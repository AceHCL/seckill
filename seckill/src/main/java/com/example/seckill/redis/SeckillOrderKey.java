package com.example.seckill.redis;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-25 4:22 PM
 */
public class SeckillOrderKey extends BasePrefix {

    public SeckillOrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public SeckillOrderKey(String prefix) {
        super(prefix);
    }

    public static SeckillOrderKey seckillOrderKey = new SeckillOrderKey(30,"sok");

}