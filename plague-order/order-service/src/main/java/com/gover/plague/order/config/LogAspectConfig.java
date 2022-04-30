package com.gover.plague.order.config;

import com.alibaba.fastjson.JSONObject;
import com.gover.plague.log.annotation.ApiAccessLog;
import com.gover.plague.log.req.ApiAccessLogs;
import com.gover.plague.log.service.LogSendService;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

@Component
@Aspect
public class LogAspectConfig {

    @Reference
    private LogSendService logSendService;

    @Pointcut("@annotation(com.gover.plague.log.annotation.ApiAccessLog)")
    public void api_annotation() {
    }

    /**
     * execution(方法修饰符 返回类型 方法所属的包.类名.方法名称(方法参数)
     */
    @Pointcut("execution(public * com.gover.plague.order.service.impl.*.*(..))")
    public void api_method() {
    }

    /**
     * 环绕通知
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("api_annotation()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Date startDate = new Date();

        //获取当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();

        // 执行方法
        Object result = joinPoint.proceed();

        // 获取签名和方法
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        // 记录请求信息
        ApiAccessLogs webLog = new ApiAccessLogs();
        ApiAccessLog annotation = method.getAnnotation(ApiAccessLog.class);
        // 操作描述
        webLog.setDescription(annotation.desc());
        // 请求IP
        webLog.setIp(request.getRemoteAddr());
        // 请求方法
        webLog.setMethod(request.getMethod());
        // 请求结果
        webLog.setResult(result);
        // 请求消耗时间
        webLog.setSpendTime((int) (System.currentTimeMillis() - startTime));
        // 请求开始时间
        webLog.setStartTime(startDate);
        // 请求方法名
        webLog.setMethodName(signature.getDeclaringTypeName());
        // 请求URI
        webLog.setUri(request.getRequestURI());
        // 请求URL
        webLog.setUrl(url);
        // 请求参数
        String[] paramNames = ((MethodSignature) signature).getParameterNames();
        Object[] paramValues = joinPoint.getArgs();
        int paramLength = null == paramNames ? 0 : paramNames.length;
        if (paramLength != 0) {
            StringBuilder param = new StringBuilder();
            for (int i = 0; i < paramLength - 1; i++) {
                param.append(paramNames[i]).append("=").append(JSONObject.toJSONString(paramValues[i])).append(",");
            }
            param.append(paramNames[paramLength - 1]).append("=").append(JSONObject.toJSONString(paramValues[paramLength - 1]));
            webLog.setParameter(param);
        }

        // 异步发送日志
        System.out.println(webLog);
        logSendService.sendApiAccessLog(webLog);
        return result;
    }

    /**
     * 方法执行前
     *
     * @param joinPoint
     * @throws Exception
     */
//    @Before("api_method()")
    public void requestInfo(JoinPoint joinPoint) throws Exception {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        StringBuilder requestLog = new StringBuilder();
        Signature signature = joinPoint.getSignature();

        requestLog.append(((MethodSignature) signature).getMethod().getAnnotation(ApiAccessLog.class).desc()).append("\t")
                .append("请求URL：").append("URL = {").append(request.getRequestURI()).append("},\t")
                .append("请求方式 = {").append(request.getMethod()).append("},\t")
                .append("请求IP = {").append(request.getRemoteAddr()).append("},\t")
                .append("类方法 = {").append(signature.getDeclaringTypeName()).append(".")
                .append(signature.getName()).append("},\t");

        // 处理请求参数
        String[] paramNames = ((MethodSignature) signature).getParameterNames();
        Object[] paramValues = joinPoint.getArgs();
        int paramLength = null == paramNames ? 0 : paramNames.length;
        if (paramLength == 0) {
            requestLog.append("请求参数 = {} ");
        } else {
            requestLog.append("请求参数 = [");
            for (int i = 0; i < paramLength - 1; i++) {
                requestLog.append(paramNames[i]).append("=").append(JSONObject.toJSONString(paramValues[i])).append(",");
            }
            requestLog.append(paramNames[paramLength - 1]).append("=").append(JSONObject.toJSONString(paramValues[paramLength - 1])).append("]");
        }

        System.out.println(requestLog);

    }


}
