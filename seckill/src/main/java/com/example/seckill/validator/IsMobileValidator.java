package com.example.seckill.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import org.thymeleaf.util.StringUtils;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-22 10:22 PM
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {


    private boolean required = false;
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (required){
            return  true;
        }else{
            if (StringUtils.isEmpty(s)){
                return true;
            }else{
                return true;
            }
        }
    }

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }
}