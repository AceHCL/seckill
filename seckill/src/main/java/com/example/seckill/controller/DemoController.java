package com.example.seckill.controller;

import com.example.seckill.domain.SeckillUser;
import com.example.seckill.domain.User;
import com.example.seckill.rabbitmq.MQSender;
import com.example.seckill.redis.KeyPrefix;
import com.example.seckill.redis.RedisService;
import com.example.seckill.redis.SeckillUserKey;
import com.example.seckill.redis.UserKey;
import com.example.seckill.result.CodeMsg;
import com.example.seckill.result.Result;
import com.example.seckill.service.UserService;
import com.example.seckill.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisPool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-22 2:25 PM
 */

@Controller
public class DemoController {
    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;

    @Autowired
    MQSender mqSender;

    @RequestMapping("/")
    @ResponseBody
    String home(){
        return "Hello World";
    }

    //1.rest api json输出 2.  页面
    //1.例如  状态码，错误信心，data成功数据

    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello(){
        return Result.success("hello");
    }

    @RequestMapping("/helloError")
    @ResponseBody
    public Result<String> helloError(){
        return Result.error(CodeMsg.SERVER_ERROR);
    }
    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name","acehcl");
        return "hello";
    }


    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet(Model model){
        User v = redisService.get(UserKey.getById,""+1,User.class);
        return Result.success(v);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet(Model model){
        User user = new User();
        user.setId(1);
        user.setName("acehcl");
        boolean result = redisService.set(UserKey.getById,""+1,user);
        return Result.success(result);
    }

    @RequestMapping("/rabbit")
    @ResponseBody
    public String rabbit(){
        mqSender.send("success");
        return "successful";
    }

    //生成token
    @RequestMapping("/token")
    @ResponseBody
    public Result<String> token(){
        File dir = new File("/Users/ace-huang/temp");
        dir.mkdirs();
        String log = "/Users/ace-huang/temp/user.log";
        File userlog = new File(log);
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(userlog,true);
            for (int i = 0; i < 1000 ; i++) {
                SeckillUser user = new SeckillUser();
                user.setId((long)i);
                user.setPassword("4t45634564");
                String token = UUIDUtil.uuid();
                redisService.set(SeckillUserKey.token, token,user);
                fileWriter.write(token+"\n");
                fileWriter.flush();
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success("success");
    }

}