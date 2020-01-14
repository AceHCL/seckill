package com.example.seckill.result;

import lombok.Data;

/**
 * 描述:
 * fail information 封装
 *
 * @author ace-huang
 * @create 2019-12-22 2:45 PM
 */
@Data
public class CodeMsg {

    private int code;
    private String msg;

    //usually error
    public static CodeMsg SUCCESS = new CodeMsg(0,"success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100,"server error");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500100,"password not is empty");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500100,"mobile not is empty");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500100,"user not exist");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500100, "password error");
    public static CodeMsg BIND_ERROR = new CodeMsg(500100, "参数校验错误:%s");
    public static CodeMsg SESSION_ERROR = new CodeMsg(500100, "用户未登陆");
    //login error 500200

    //seckill error 500300

    //order error

    public static CodeMsg ORDER_ERROR = new CodeMsg(500300, "订单为空");
    public static CodeMsg PROCUDT_ERROR = new CodeMsg(500300, "商品信息为空");


    public static CodeMsg SECKILL_OVER = new CodeMsg(500500, "库存不足");

    public static CodeMsg REPEAT_SECKILL = new CodeMsg(500501, "不能重复秒杀");

    //........

    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public CodeMsg fillArgs(Object...args){
        int code = this.code;
        String message = String.format(this.msg,args);
        return new CodeMsg(code,message);
    }
}