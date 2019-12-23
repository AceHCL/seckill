package com.example.seckill.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-22 8:35 PM
 */
public class MD5Util {

    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }


    private static final String salt = "1a2b3c4d";
    //用户输入密码的加密
    public static String inputPassFormPass(String inputPass){
        String str = "" + salt.charAt(0) + salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }

    //passwd again
    public static String formPassToDBPass(String fromPass,String salt){
        String str = salt.charAt(0) + salt.charAt(2)+fromPass+salt.charAt(5);
        return md5(str);
    }

    public static String inputPasstoDBPass(String inputPass,String saltDB){
        String formPass = inputPassFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass,saltDB);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(MD5Util.inputPassFormPass("123456"));
        System.out.println(inputPasstoDBPass("123456","HCLhclfoaiwejfoiew"));
    }


}