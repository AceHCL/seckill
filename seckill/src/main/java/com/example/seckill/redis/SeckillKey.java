package com.example.seckill.redis;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-25 4:22 PM
 */
public class SeckillKey extends BasePrefix {

    public SeckillKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public SeckillKey(String prefix) {
        super(prefix);
    }

    public static SeckillKey isGoodsOver = new SeckillKey("go");

}