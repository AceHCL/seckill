package com.example.seckill.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 描述:
 * redis配置文件
 * @author ace-huang
 * @create 2019-12-22 6:43 PM
 */

@Component
@ConfigurationProperties(prefix = "redis")
@Data
public class RedisConfig {

    private String host;
    private int port;
    private int timeout;
    private String password;
    private int poolMaxTotal;
    private int poolMaxIdle;
    private int poolMaxWait;

}