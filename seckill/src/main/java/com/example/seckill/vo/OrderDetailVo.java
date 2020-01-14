package com.example.seckill.vo;

import com.example.seckill.domain.Order;
import lombok.Data;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-25 9:22 PM
 */
@Data
public class OrderDetailVo {

    private GoodsVo goods;
    private Order order;

}