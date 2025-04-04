//package com.gj.cloud.base.work.user.validator.validation.aspect;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Validator;
//import org.springframework.validation.annotation.Validated;
//
//@Aspect
//@Component
//@Order(200)
//public class ValidatorAspect {
//    @Around("@annotation(validated)")
//    public Object doValidate(ProceedingJoinPoint pjp, Validated validated) throws Throwable {
//        Validator validator = SpringContextHolder.getBeanIfPresent(validated.value());
//
//        if (validator != null) {
//            validator.validate(pjp.getArgs());
//        }
//
//        return pjp.proceed();
//    }
//}
