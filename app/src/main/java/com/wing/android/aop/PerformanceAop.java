package com.wing.android.aop;

import com.guagua.modules.utils.LogUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class PerformanceAop {

    /**
     * around代表在每个方法前后都插入代码
     * 任何方法、任何参数类型都切
     * @param joinPoint
     */
    @Around("call(* com.wning.demo.MyApplication.**(..))")
    public void getTime(ProceedingJoinPoint joinPoint) {
        LogUtils.i("PerformanceAop","getTime");

        Signature signature = joinPoint.getSignature();

        long startTime = System.currentTimeMillis();
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        LogUtils.i("PerformanceAop",signature.getName()+" cost "+ (System.currentTimeMillis() - startTime));
    }
}
