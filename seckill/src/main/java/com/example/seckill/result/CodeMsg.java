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
    //login error 500200

    //product error 500300

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