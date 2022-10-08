package com.example.ojbot.utils.root;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author tzih
 * @date 2022.10.02
 */
@Aspect
@Component
public class RootAop {

    @Resource
    private LarkRoot larkRoot;

    @Pointcut("execution(* *..controller.*.rank(..)) && args(id)")
    public void lark(Integer id) {

    }

//    @Pointcut("execution(* *..controller.*.*(..))")
//    public void lark() {
//
//    }

    @AfterReturning(pointcut = "lark(id)", returning = "returnObject", argNames = "joinPoint,returnObject,id")
    public void After(JoinPoint joinPoint, Object returnObject, Integer id) {
        if (returnObject != null) {
            System.out.println("aop");
            System.out.println("return"+returnObject);
            larkRoot.sendMessage(returnObject, id);
        }
    }


//
//    @AfterThrowing(pointcut = "lark()",)
//    public void

}
