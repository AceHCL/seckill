package com.example.seckill.vo;

import com.example.seckill.domain.Goods;
import com.example.seckill.domain.SeckillGoods;
import lombok.Data;

import java.util.Date;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-23 1:41 PM
 */
@Data
public class GoodsVo extends Goods {

    private Double seckillPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}