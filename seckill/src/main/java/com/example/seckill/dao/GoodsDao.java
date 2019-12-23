package com.example.seckill.dao;

import com.example.seckill.domain.User;
import com.example.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-22 4:32 PM
 */
@Mapper
public interface GoodsDao {
    @Select("select sg.*, sgs.seckill_price, sgs.stock_count,sgs.start_date,sgs.end_date from sk_goods_seckill as sgs left join sk_goods sg on sgs.goods_id = sg.id")
    List<GoodsVo> getGoodsVoList();

    @Select("select sg.*, sgs.seckill_price, sgs.stock_count,sgs.start_date,sgs.end_date from sk_goods_seckill as sgs left join sk_goods sg on sgs.goods_id = sg.id where sg.id = #{goodsId}")
    GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);
}