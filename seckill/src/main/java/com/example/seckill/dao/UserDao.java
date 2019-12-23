package com.example.seckill.dao;

import com.example.seckill.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-22 4:32 PM
 */
@Mapper
public interface UserDao {

    /**asdfas
     * @param id fa
     * @returnfasd
     */
    @Select("select * from user where id = #{id}")
    public User getById(@Param("id") int id);
}