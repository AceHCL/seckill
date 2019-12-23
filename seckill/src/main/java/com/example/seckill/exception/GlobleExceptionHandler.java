package com.example.seckill.exception;

import com.example.seckill.result.CodeMsg;
import com.example.seckill.result.Result;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.BindException;
import java.util.List;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-22 10:32 PM
 */
@ControllerAdvice
@ResponseBody
public class GlobleExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request,Exception e){
        e.printStackTrace();
        if (e instanceof BindException){
            BindException ex = (BindException) e;
            String errors = ex.getMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(errors));
        }else  if (e instanceof GlobalException){
            GlobalException ex = (GlobalException) e;
            return Result.error(ex.getCodeMsg());
        }
        else{
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}