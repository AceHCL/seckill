package com.example.seckill.redis;

import lombok.Data;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-22 7:49 PM
 */
public abstract class BasePrefix implements KeyPrefix {


    private int expireSeconds;
    private String prefix;

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }
    public BasePrefix(String prefix) {
        this(0,prefix);
    }

    @Override
    public int expireSeconds() {//0 代表永不过期
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className+":"+prefix;
    }
}