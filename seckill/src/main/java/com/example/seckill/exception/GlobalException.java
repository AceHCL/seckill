package com.example.seckill.exception;

import com.example.seckill.result.CodeMsg;
import lombok.Data;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-22 10:47 PM
 */
@Data
public class GlobalException extends RuntimeException {

    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }
}