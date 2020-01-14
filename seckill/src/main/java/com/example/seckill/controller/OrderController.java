package com.example.seckill.controller;

import com.example.seckill.domain.Goods;
import com.example.seckill.domain.Order;
import com.example.seckill.domain.SeckillOrder;
import com.example.seckill.domain.SeckillUser;
import com.example.seckill.result.CodeMsg;
import com.example.seckill.result.Result;
import com.example.seckill.service.GoodsService;
import com.example.seckill.service.OrderService;
import com.example.seckill.service.SeckillService;
import com.example.seckill.vo.GoodsVo;
import com.example.seckill.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-23 11:13 AM
 */
@Controller
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> orderinfo(SeckillUser user,
                                           @RequestParam("orderId") long orderId){
        if (user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        Order order = orderService.getOrderById(user.getId(),orderId);
        if (order == null){
            return Result.error(CodeMsg.ORDER_ERROR);
        }
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(order.getGoodsId());
        if (goods == null){
            return Result.error(CodeMsg.PROCUDT_ERROR);
        }
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setOrder(order);
        orderDetailVo.setGoods(goods);
        return Result.success(orderDetailVo);
    }






}