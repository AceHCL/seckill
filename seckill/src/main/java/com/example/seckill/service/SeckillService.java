package com.example.seckill.service;

import com.example.seckill.dao.GoodsDao;
import com.example.seckill.domain.Goods;
import com.example.seckill.domain.Order;
import com.example.seckill.domain.SeckillUser;
import com.example.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-23 4:33 PM
 */
@Service
public class SeckillService {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Transactional
    public Order seckill(SeckillUser user, GoodsVo goods) {
        //减少库存，下订单，写入秒杀订单
        goodsService.reduceStock(goods);

        Order order = orderService.createOrder(user,goods);
        return order;
    }
}