package com.example.seckill.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2020-01-12 8:21 PM
 */
@Configuration
public class MQConfig {

    public static final String QUEUE = "queue";
    public static final String SECKILL_QUEUE="seckillQueue";


    /**
     * direct 模式
     * @return
     */
    @Bean
    public Queue queue(){
        return new Queue(MQConfig.QUEUE,true);
    }

}