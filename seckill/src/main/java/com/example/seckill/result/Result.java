package com.example.seckill.result;

import lombok.Data;

/**
 * 描述:
 * 对输出结果的封装
 *
 * @author ace-huang
 * @create 2019-12-22 2:36 PM
 */
@Data
public class Result<T> {

    private int code;
    private String msg;
    private T data;

    //success use this method
    private Result(T data) {
        this.data = data;
        this.code = 0 ;
        this.msg = "success";
    }

    //fail use this method
    private Result(CodeMsg codeMsg) {
        if (codeMsg == null){
            return;
        }
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }

    /**
     * 成功的调用
     */
    public static <T> Result<T> success(T data){
        return new Result<T>(data);
    }

    /**
     * 失败的调用
     */
    public static <T> Result<T> error(CodeMsg codeMsg){
        return new Result<T>(codeMsg);
    }



}