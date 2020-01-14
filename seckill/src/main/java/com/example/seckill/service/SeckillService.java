package com.example.seckill.service;

import com.example.seckill.dao.GoodsDao;
import com.example.seckill.domain.Goods;
import com.example.seckill.domain.Order;
import com.example.seckill.domain.SeckillOrder;
import com.example.seckill.domain.SeckillUser;
import com.example.seckill.redis.RedisService;
import com.example.seckill.redis.SeckillKey;
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

    @Autowired
    private RedisService redisService;

    private static final long FAILD = -1;
    private static final long WAIT = 0;

    @Transactional
    public SeckillOrder seckill(SeckillUser user, GoodsVo goods) {

        //减少库存，下订单，写入秒杀订单
        if (!goodsService.reduceStock(goods)){
            setGoodsOver(goods.getId());
            return null;
        }
        SeckillOrder seckillOrder = orderService.createOrder(user,goods);
        return seckillOrder;
    }



    public long getSeckillResult(Long userId, long goodsId) {
        SeckillOrder seckillOrder = orderService.getSeckillOrderByUserIdAndGoodsId(userId,goodsId);
        if (seckillOrder != null){
            return seckillOrder.getId();
        }else{
            boolean isOver = getGoodsOver(goodsId);
            if (isOver){
                return SeckillService.FAILD;
            }else{
                return SeckillService.WAIT;
            }
        }
    }

    private void setGoodsOver(Long id) {
        redisService.set(SeckillKey.isGoodsOver,"id",true);
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exitsKey(SeckillKey.isGoodsOver,""+goodsId);
    }
}