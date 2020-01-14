package com.example.seckill.service;

import com.example.seckill.dao.OrderDao;
import com.example.seckill.domain.Order;
import com.example.seckill.domain.SeckillOrder;
import com.example.seckill.domain.SeckillUser;
import com.example.seckill.redis.RedisService;
import com.example.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-23 4:24 PM
 */
@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private RedisService redisService;

    public SeckillOrder getSeckillOrderByUserIdAndGoodsId(Long userId, long goodsId) {
        return orderDao.getSeckillOrderByUserIdAndGoodsId(userId,goodsId);
    }

    @Transactional
    public SeckillOrder createOrder(SeckillUser user, GoodsVo goods) {
        Order order = new Order();
        order.setCreateDate(new Date());
        order.setDeliveryAddrId(0L);
        order.setGoodsCount(1);
        order.setUserId(user.getId());
        order.setGoodsId(goods.getId());
        order.setGoodsName(goods.getGoodsName());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setGoodsPrice(goods.getSeckillPrice());
        orderDao.insertOrder(order);

        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setGoodsId(goods.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setUserId(user.getId());
        orderDao.insertSeckillOrder(seckillOrder);
        //将订单写入redis
        return seckillOrder;
    }

    public Order getOrderById(Long userId, long orderId) {
        return orderDao.getOrderById(userId,orderId);

    }
}