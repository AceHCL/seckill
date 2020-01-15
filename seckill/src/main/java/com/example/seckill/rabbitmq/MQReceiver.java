package com.example.seckill.rabbitmq;

import com.example.seckill.domain.SeckillOrder;
import com.example.seckill.domain.SeckillUser;
import com.example.seckill.redis.RedisService;
import com.example.seckill.result.CodeMsg;
import com.example.seckill.result.Result;
import com.example.seckill.service.GoodsService;
import com.example.seckill.service.OrderService;
import com.example.seckill.service.SeckillService;
import com.example.seckill.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2020-01-12 8:20 PM
 */
@Service
public class MQReceiver {

    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SeckillService seckillService;

    @RabbitListener(queues = MQConfig.SECKILL_QUEUE)
    public void receive(String message){
//        log.info("receive message");
        SeckillMessage seckillMessage = RedisService.stringToBean(message,SeckillMessage.class);
        SeckillUser user = seckillMessage.getUser();
        long goodsId = seckillMessage.getGoodsId();

//        判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock<= 0){
            return;
        }

        //判断是否已经秒杀
        SeckillOrder seckillOrder = orderService.getSeckillOrderByUserIdAndGoodsId(user.getId(),goodsId);
        if (seckillOrder != null){
            return;
        }
        //开始秒杀，减库存，下订单，写入秒杀订单
        SeckillOrder order = seckillService.seckill(user,goods);
        return ;
    }

//    @RabbitListener(queues = MQConfig.QUEUE)
//    public void receive(String message){
//        log.info("reveive message");
//    }

}