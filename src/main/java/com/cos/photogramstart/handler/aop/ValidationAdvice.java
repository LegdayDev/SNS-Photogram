package com.cos.photogramstart.handler.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ValidationAdvice {

    /**
     * Around() : 메서드가 시작부터 끝날때까지 관여
     * Before() : 메서드가 시작하기 전에 관여
     * After() : 메서드가 끝날 때 관여
     *  *Controller : Controller 로 끝나는
     *  *(..) : 어떤 파라미터가 오든 전부 실행
     *  ProceedingJoinPoint : 실행되는 메서드의 모든 정보에 접근할 수 있는 파라미터이다.
     *  proceed() : 해당 함수로 다시 돌아가는 명령어
     */
    @Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))")
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

        System.out.println("web api 컨트롤러=======================");
        return proceedingJoinPoint.proceed();
    }

    @Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        System.out.println("web 컨트롤러=======================");
        return proceedingJoinPoint.proceed();
    }

}
