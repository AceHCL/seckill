package com.example.seckill.dao;

import com.example.seckill.domain.Order;
import com.example.seckill.domain.SeckillOrder;
import com.example.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.*;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-23 4:39 PM
 */
@Mapper
public interface OrderDao {


    /**
     * 获取SeckillOrder
     * @param userId 用户id
     * @param goodsId 商品id
     * @return 返回订单
     */
    @Select("select * from sk_order_seckill where user_id = #{userId} and goods_id = #{goodsId}")
    SeckillOrder getSeckillOrderByUserIdAndGoodsId(@Param("userId") long userId,@Param("goodsId") long goodsId);

    @Insert("insert into sk_order(user_id,goods_id,goods_name,goods_count,goods_price,order_channel,status,create_date)" +
            "values(#{userId},#{goodsId},#{goodsName},#{goodsCount},#{goodsPrice},#{orderChannel},#{status},#{createDate})")
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = long.class,before = false,statement = "select last_insert_id()")
    long insertOrder(Order order);

    @Insert("insert into sk_order_seckill(user_id,order_id,goods_id)" +
            "values(#{userId},#{orderId},#{goodsId})")
//    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = long.class,before = false,statement = "select last_insert_id()")
    long insertSeckillOrder(SeckillOrder seckillOrder);
}