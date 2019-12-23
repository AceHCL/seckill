package com.example.seckill.util;

import java.util.UUID;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-23 11:02 AM
 */
public class UUIDUtil {

    //生成UUID
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }

}