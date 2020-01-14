package com.example.seckill.vo;

import com.example.seckill.domain.SeckillUser;
import lombok.Data;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-25 8:31 PM
 */
@Data
public class GoodsDetailVo {

    private long remainSeconds;
    private int seckillStatus;
    private GoodsVo goods;
    private SeckillUser user;

}