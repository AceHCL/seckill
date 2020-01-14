package com.example.seckill.controller;

import com.example.seckill.domain.SeckillUser;
import com.example.seckill.redis.GoodsKey;
import com.example.seckill.redis.RedisService;
import com.example.seckill.result.Result;
import com.example.seckill.service.GoodsService;

import com.example.seckill.vo.GoodsDetailVo;
import com.example.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.thymeleaf.context.WebContext;

import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-23 11:13 AM
 */
@Controller
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private RedisService redisService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;
    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping(value = "/to_list",produces = "text/html")
    @ResponseBody
    public String toList(HttpServletRequest request,HttpServletResponse response,Model model, SeckillUser user){
        String html = redisService.get(GoodsKey.getGoodsList,"",String.class);
        if (!StringUtils.isEmpty(html)){
            return html;
        }
        model.addAttribute("user",user);
        //查询商品列表
        List<GoodsVo> goodsList = goodsService.getGoodsVoList();
        model.addAttribute("goodsList",goodsList);
        //手动渲染
        WebContext webContext = new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list",webContext);
        if (!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsList,"",html);
        }
        return html;
//        return "goods_list";
    }

    //做页面分离，
//    @GetMapping(value = "to_detail/{goodsId}")
//    public String toDetail(Model model, SeckillUser user,
//                            @PathVariable("goodsId")long goodsId){
//        model.addAttribute("user",user);
//        //snowflake
//        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
//
//        long startAt = goods.getStartDate().getTime();
//        long endAt = goods.getEndDate().getTime();
//        long now = System.currentTimeMillis();
//
//        long remainSeconds = 0;
//
//        int seckillStatus = 0;
//        if (now<startAt){
//            seckillStatus = 0;
//            remainSeconds = (startAt - now)/1000;
//        }else if (now >endAt){
//            seckillStatus = 2;
//            remainSeconds = -1;
//        }else{
//            seckillStatus = 1;
//            remainSeconds = 0;
//        }
//        model.addAttribute("seckillStatus",seckillStatus);
//        model.addAttribute("remainSeconds",remainSeconds);
//        model.addAttribute("goods",goods);
//        return "goods_detail";
//    }


    @GetMapping(value = "to_detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> toDetail(SeckillUser user,
                                          @PathVariable("goodsId")long goodsId){
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);

        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        long remainSeconds = 0;

        int seckillStatus = 0;
        if (now<startAt){
            seckillStatus = 0;
            remainSeconds = (startAt - now)/1000;
        }else if (now >endAt){
            seckillStatus = 2;
            remainSeconds = -1;
        }else{
            seckillStatus = 1;
            remainSeconds = 0;
        }
        GoodsDetailVo goodsDetailVo = new GoodsDetailVo();
        goodsDetailVo.setGoods(goods);
        goodsDetailVo.setRemainSeconds(remainSeconds);
        goodsDetailVo.setSeckillStatus(seckillStatus);
        goodsDetailVo.setUser(user);
        return Result.success(goodsDetailVo);
    }
}