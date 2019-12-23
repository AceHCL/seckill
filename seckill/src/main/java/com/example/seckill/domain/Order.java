package com.example.seckill.domain;

import lombok.Data;

import java.util.Date;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-23 1:33 PM
 */
@Data
public class Order {
    private Long id;
    private Long userId;
    private Long goodsId;
    private Long deliveryAddrId;
    private String goodsName;
    private Integer goodsCount;
    private Double goodsPrice;
    private Integer orderChannel;
    private Integer status;
    private Date createDate;
    private Date payDate;

}