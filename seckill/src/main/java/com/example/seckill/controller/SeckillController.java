package com.example.seckill.controller;

import com.example.seckill.domain.Order;
import com.example.seckill.domain.SeckillOrder;
import com.example.seckill.domain.SeckillUser;
import com.example.seckill.result.CodeMsg;
import com.example.seckill.service.GoodsService;
import com.example.seckill.service.OrderService;
import com.example.seckill.service.SeckillService;
import com.example.seckill.service.SeckillUserService;
import com.example.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-23 11:13 AM
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SeckillService seckillService;

    /**
     * 秒杀
     * @param model 模型
     * @param user 用户
     * @return 返回订单页
     */
    @RequestMapping("/do_seckill")
    public String doSeckill(Model model, SeckillUser user,
                        @RequestParam("goodsId")long goodsId){
        if (user == null){
            return "login";
        }
        model.addAttribute("user",user);
        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock<= 0){
            model.addAttribute("errmsg", CodeMsg.SECKILL_OVER);
            return "seckill_fail";
        }
        //判断是否已经秒杀
        SeckillOrder seckillOrder = orderService.getSeckillOrderByUserIdAndGoodsId(user.getId(),goodsId);
        if (seckillOrder != null){
            model.addAttribute("errmsg",CodeMsg.REPEAT_SECKILL);
            return "seckill_fail";
        }
        //开始秒杀，减库存，下订单，写入秒杀订单
        Order order = seckillService.seckill(user,goods);
        model.addAttribute("orderInfo",order);
        model.addAttribute("goods",goods);
        return "order_detail";
    }

}