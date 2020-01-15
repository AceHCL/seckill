package com.example.seckill.rabbitmq;

import com.example.seckill.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2020-01-12 8:20 PM
 */
@Service
public class MQSender {


    @Autowired
    AmqpTemplate amqpTemplate;

    private static Logger log = LoggerFactory.getLogger(MQSender.class);

    public void send(Object message){
        String msg = RedisService.beanToString(message);
        amqpTemplate.convertAndSend(MQConfig.QUEUE,msg);
    }

    //入队
    public void sendSeckillMessage(SeckillMessage seckillMessage) {
        String msg = RedisService.beanToString(seckillMessage);
//        log.info("send message" + msg);
        amqpTemplate.convertAndSend(MQConfig.SECKILL_QUEUE,msg);

    }
}