package com.example.seckill.service;

import com.example.seckill.dao.SeckillUserDao;
import com.example.seckill.domain.SeckillUser;
import com.example.seckill.exception.GlobalException;
import com.example.seckill.redis.RedisService;
import com.example.seckill.redis.SeckillUserKey;
import com.example.seckill.result.CodeMsg;
import com.example.seckill.util.MD5Util;
import com.example.seckill.util.UUIDUtil;
import com.example.seckill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-22 9:56 PM
 */
@Service
public class SeckillUserService {
    @Autowired
    private SeckillUserDao seckillUserDao;


    @Autowired
    private RedisService redisService;
    public static final String COOKIE_NAME_TOKEN = "token";

    public SeckillUser getById(long id){
        return seckillUserDao.getById(id);
    }

    public boolean login(HttpServletResponse response,LoginVo loginVo){
        if (loginVo == null){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        SeckillUser user = getById(Long.valueOf(mobile));
        if (user == null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        String dbPass = user.getPassword();
        String slatDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass,slatDB);
        if (!calcPass.equals(dbPass)){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //做单点登录，使用token
        //生成cookie
        String token = UUIDUtil.uuid();
        addCookie(token,user,response);
        return true;

    }

    public SeckillUser getByToken(String token,HttpServletResponse response) {
        if (StringUtils.isEmpty(token)){
            return null;
        }
        SeckillUser user = redisService.get(SeckillUserKey.token,token,SeckillUser.class);
        //刷新生命周期
        if (user != null){
            addCookie(token,user,response);
        }
        return user;
    }

    private void addCookie(String token, SeckillUser user,HttpServletResponse response){
        redisService.set(SeckillUserKey.token,token,user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN,token);
        cookie.setMaxAge(SeckillUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}