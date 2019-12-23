package com.example.seckill.domain;

import lombok.Data;

import java.util.Date;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-22 9:51 PM
 */

@Data
public class SeckillUser {
    private Long id;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private Date registerDate;
    private Date lastLoginDate;
    private Integer loginCount;

}