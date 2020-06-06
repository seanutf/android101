package com.seanutf.android.base.aop;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class TraceAspect {

    private static final String TAG = "xxxxxxxxxxxxxxxxxx";

    @Pointcut("execution(@com.seanutf.android.base.aop.MyAnnotation * *(..))")
    public void logMyAspect() {
        System.out.println("xxxxxxxxxxxxxxxxxx TraceAspect logMyAspect");
    }

    @Before("execution(@com.seanutf.android.base.aop.MyAnnotation * *(..))")
    public void onMethodBefore(JoinPoint joinPoint) throws Throwable {
        String key = joinPoint.getSignature().toString();
        System.out.println("xxxxxxxxxxxxxxxxxx TraceAspect onMethodBefore");
    }
}
