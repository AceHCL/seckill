package com.example.seckill.redis;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-25 4:22 PM
 */
public class GoodsKey extends BasePrefix {

    public GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public GoodsKey(String prefix) {
        super(prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(60,"goodslist");
    public static GoodsKey getGoodsStock = new GoodsKey(0,"gs");
}