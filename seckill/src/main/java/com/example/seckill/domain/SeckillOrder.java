package com.example.seckill.domain;

import lombok.Data;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-23 1:36 PM
 */
@Data
public class SeckillOrder {

    private Long id;
    private Long userId;
    private Long orderId;
    private Long goodsId;
}