package com.example.seckill.controller;

import com.example.seckill.result.CodeMsg;
import com.example.seckill.result.Result;
import com.example.seckill.service.SeckillUserService;
import com.example.seckill.vo.LoginVo;
import com.sun.tools.javac.jvm.Code;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 描述:
 * login block
 *
 * @author ace-huang
 * @create 2019-12-22 8:52 PM
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private SeckillUserService seckillUserService;

    //跳转到登陆页
    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<CodeMsg> doLogin(@Valid LoginVo loginVo, HttpServletResponse response){
        logger.info(loginVo.toString());
        //删除了判断，参数校验,改用参数校验器了
        boolean result = seckillUserService.login(response,loginVo);
        return Result.success(CodeMsg.SUCCESS);
    }
}