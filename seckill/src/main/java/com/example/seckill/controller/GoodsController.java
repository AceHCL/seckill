package com.example.seckill.controller;

import com.example.seckill.domain.SeckillUser;
import com.example.seckill.service.GoodsService;
import com.example.seckill.service.SeckillUserService;
import com.example.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

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

    @RequestMapping("/to_list")
    public String toList(Model model, SeckillUser user){
        model.addAttribute("user",user);
        //查询商品列表
        List<GoodsVo> goodsList = goodsService.getGoodsVoList();
        model.addAttribute("goodsList",goodsList);
        return "goods_list";
    }

    @GetMapping(value = "to_detail/{goodsId}")
    public String toDetail(Model model, SeckillUser user,
                            @PathVariable("goodsId")long goodsId){
        model.addAttribute("user",user);
        //snowflake
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
        model.addAttribute("seckillStatus",seckillStatus);
        model.addAttribute("remainSeconds",remainSeconds);
        model.addAttribute("goods",goods);
        return "goods_detail";
    }
}