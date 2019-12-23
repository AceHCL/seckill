package com.example.seckill.domain;

import lombok.Data;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-23 1:29 PM
 */
@Data
public class Goods {

    private Long id;
    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private double goodsPrice;
    private Integer goodsStock;

}