package com.zhaitiancai.framework.biz.operationlog.aspect;

import com.zhaitiancai.framework.common.util.JsonUtilS;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
@Component
@Aspect
@Slf4j
public class ApiOperationLogAspect {
    /** 以自定义 @ApiOperationLog 注解为切点，凡是添加 @ApiOperationLog 的方法，都会执行环绕中的代码 */
    @Pointcut("@annotation(com.zhaitiancai.framework.biz.operationlog.aspect.ApiOperationLog)")
    public void apiOperationLog(){}
    /**
     * 环绕
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("apiOperationLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            //请求开始时间
            long srartTime= System.currentTimeMillis();
            //获取被请求的类和方法
            String className = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = joinPoint.getSignature().getName();

            //请求入参
            Object[] args=joinPoint.getArgs();
            //入参转 JSON 字符传
            String argsJsonStr= Arrays.stream(args).map(toJsonStr()).collect(Collectors.joining());
            //功能描述信息
            String description=getApiOperationLogDesrciption(joinPoint);
            //打印请求相关参数
            log.info("====== 请求开始:[{}],入参:{},请求类: {},请求方法:{} =================================== " ,
                    description,argsJsonStr,className,methodName);

            //执行切点方法
            Object result =joinPoint.proceed();

            //执行耗时
            long executionTime=System.currentTimeMillis()-srartTime;
            //打印出参信息
            log.info("====== 请求结束:[{}] , 耗时: {}ms,出参:{}==================================="
            ,description,executionTime, JsonUtilS.toJsonString(result));

            return result;
        } finally {
        }


    }

    private String getApiOperationLogDesrciption(ProceedingJoinPoint joinPoint){
        //1. 从ProceedingJoinPoint 中获取MethodSignature
        MethodSignature signature= (MethodSignature) joinPoint.getSignature();
        //2. 使用MethodSignature 获取当前被注解的 Method
        Method method = signature.getMethod();

        //3. 从Method 中提取 LogExecution 注解
        ApiOperationLog apiOperationLog = method.getAnnotation(ApiOperationLog.class);

        //3.从 LogExecution 注解中获取 desrciption 属性
        return apiOperationLog.description();


    }
    /**
     * 转 JSON 字符串
     * @return
     */
    private Function<Object, String> toJsonStr() {
        return JsonUtilS::toJsonString;
    }

}