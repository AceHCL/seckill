package com.example.seckill.controller;

import com.example.seckill.domain.Order;
import com.example.seckill.domain.SeckillOrder;
import com.example.seckill.domain.SeckillUser;
import com.example.seckill.rabbitmq.MQSender;
import com.example.seckill.rabbitmq.SeckillMessage;
import com.example.seckill.redis.GoodsKey;
import com.example.seckill.redis.RedisService;
import com.example.seckill.result.CodeMsg;
import com.example.seckill.result.Result;
import com.example.seckill.service.GoodsService;
import com.example.seckill.service.OrderService;
import com.example.seckill.service.SeckillService;
import com.example.seckill.service.SeckillUserService;
import com.example.seckill.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-23 11:13 AM
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MQSender mqSender;

    /**
     * 初始化的操作
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //查询商品数量，放入redis
        List<GoodsVo> goodsVoList = goodsService.getGoodsVoList();
        if (goodsVoList == null){
            return;
        }
        for (GoodsVo goods:goodsVoList
             ) {
            redisService.set(GoodsKey.getGoodsStock,""+goods.getId(),goods.getStockCount());
        }

    }

    /**
     * 秒杀
     * @param
     * @param user 用户
     * @return 返回订单页
     */
    //改造成前后端分离的
//    @RequestMapping("/do_seckill")
//    public String doSeckill(Model model, SeckillUser user,
//                        @RequestParam("goodsId")long goodsId){
//        if (user == null){
//            return "login";
//        }
//        model.addAttribute("user",user);
//        //判断库存
//        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
//        int stock = goods.getStockCount();
//        if (stock<= 0){
//            model.addAttribute("errmsg", CodeMsg.SECKILL_OVER);
//            return "seckill_fail";
//        }
//        //判断是否已经秒杀
//        SeckillOrder seckillOrder = orderService.getSeckillOrderByUserIdAndGoodsId(user.getId(),goodsId);
//        if (seckillOrder != null){
//            model.addAttribute("errmsg",CodeMsg.REPEAT_SECKILL);
//            return "seckill_fail";
//        }
//        //开始秒杀，减库存，下订单，写入秒杀订单
//        Order order = seckillService.seckill(user,goods);
//        model.addAttribute("orderInfo",order);
//        model.addAttribute("goods",goods);
//        return "order_detail";
//    }

    /**
     * 1 系统初始化，商品数量加入rdis
     * 2 收到请求，直接判断redis，
     * 3 请求入队，返回排队中
     * 4 请求出队 生成订单 减少库存
     * 5 客户轮询
     * @param user
     * @param goodsId
     * @return
     */

    @RequestMapping(value = "/do_seckill",method = RequestMethod.POST)
    @ResponseBody
    public Result doSeckill(SeckillUser user,
                                   @RequestParam("goodsId")long goodsId){
        if (user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long stock = redisService.decr(GoodsKey.getGoodsStock,""+goodsId);
        if (stock < 0){
            return Result.error(CodeMsg.SECKILL_OVER);
        }
//        判断是否已经秒杀
        SeckillOrder seckillOrder = orderService.getSeckillOrderByUserIdAndGoodsId(user.getId(),goodsId);
        if (seckillOrder != null){

            return Result.error(CodeMsg.REPEAT_SECKILL);
        }
        //入队
        SeckillMessage seckillMessage = new SeckillMessage();
        seckillMessage.setUser(user);
        seckillMessage.setGoodsId(goodsId);
        mqSender.sendSeckillMessage(seckillMessage);
        return Result.success(0);//排队中


//        //判断库存
//        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
//        int stock = goods.getStockCount();
//        if (stock<= 0){
//
//            return Result.error(CodeMsg.SECKILL_OVER);
//        }
//        //判断是否已经秒杀
//        SeckillOrder seckillOrder = orderService.getSeckillOrderByUserIdAndGoodsId(user.getId(),goodsId);
//        if (seckillOrder != null){
//
//            return Result.error(CodeMsg.REPEAT_SECKILL);
//        }
//        //开始秒杀，减库存，下订单，写入秒杀订单
//        SeckillOrder order = seckillService.seckill(user,goods);
//        return Result.success(order);
    }

    //查询秒杀结果,成功id，失败，1，0，排队中
    @RequestMapping(value = "/result",method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> result(SeckillUser user,
                                   @RequestParam("goodsId")long goodsId){
        if (user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long orderId = seckillService.getSeckillResult(user.getId(),goodsId);
        return Result.success(orderId);
    }
}