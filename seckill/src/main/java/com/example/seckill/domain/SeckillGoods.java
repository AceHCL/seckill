package com.example.seckill.domain;

import lombok.Data;

import java.util.Date;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-23 1:31 PM
 */
@Data
public class SeckillGoods {

    private Long id;
    private Long goodsId;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;

}