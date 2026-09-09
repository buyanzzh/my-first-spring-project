package com.zzh.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    // 拦截 com.zzh.controller 包（含子包）下所有类的所有方法
    // 注意：必须写 controller..* 两个点，写 controller* 匹配不到子包下的类，切面会静默失效
    @Around("execution(* com.zzh.controller..*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long cost = System.currentTimeMillis() - start;
            log.info("{} 耗时 {} ms", joinPoint.getSignature(), cost);
        }
    }
}