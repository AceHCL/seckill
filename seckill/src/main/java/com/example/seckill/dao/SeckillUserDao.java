package com.example.seckill.dao;

import com.example.seckill.domain.SeckillUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-22 9:54 PM
 */

@Mapper
public interface SeckillUserDao {
    @Select("select * from sk_user where id = #{id} ")
    public SeckillUser getById(@Param("id") long id);
}