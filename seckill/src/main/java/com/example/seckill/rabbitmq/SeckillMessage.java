package com.example.seckill.rabbitmq;

import com.example.seckill.domain.SeckillUser;
import lombok.Data;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2020-01-14 11:40 AM
 */
@Data
public class SeckillMessage {

    private SeckillUser user;
    private long goodsId;
}