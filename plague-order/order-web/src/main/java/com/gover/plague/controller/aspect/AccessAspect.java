package com.gover.plague.controller.aspect;

import cn.hutool.crypto.digest.MD5;
import com.gover.plague.auth.annotation.AccessVerify;
import com.gover.plague.common.ResultCode;
import com.gover.plague.common.ResultVO;
import com.gover.plague.constant.AuthConstant;
import com.gover.plague.util.AESUtil;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 访问认证切面
 */
@Component
@Aspect
public class AccessAspect {
    
    @Pointcut("@annotation(com.gover.plague.auth.annotation.AccessVerify)")
    public void accessMethod() {}

    @Around("accessMethod()")
    public Object processVerify(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        AccessVerify accessVerify = ((MethodSignature) signature).getMethod().getAnnotation(AccessVerify.class);
        // 获取Request 类
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 如果需要登录，则进行校验
        if(accessVerify.needLogin()){
            String gateKey = AuthConstant.GATEWAY_KEY;
            String tokenKey = AuthConstant.TOKEN_INFO_KEY;
            // 取出
            String flag = request.getHeader(gateKey);
            String jwToken = request.getHeader(tokenKey);
            if(StringUtils.isBlank(flag) || StringUtils.isBlank(jwToken)){
                return ResultVO.failed(ResultCode.ILLEGAL_ACCESS, ResultCode.ILLEGAL_ACCESS.getMsg());
            }
            try {
                // 比较
                byte[] jwtToken2 = Base64.decode(jwToken);
                String flag2 = MD5.create().digestHex(AESUtil.encrypt(new String(jwtToken2), AuthConstant.AES_SALT_VALUE));
                if (!StringUtils.equals(flag, flag2)) {
                    return ResultVO.failed(ResultCode.ILLEGAL_ACCESS, ResultCode.ILLEGAL_ACCESS.getMsg());
                }
            } catch (Exception e){
                return ResultVO.failed(ResultCode.ILLEGAL_ACCESS, ResultCode.ILLEGAL_ACCESS.getMsg());
            }
        }
        // 无需登录，但仍然需要经过网关
        else {
            String noLoginKey = request.getHeader(AuthConstant.NO_LOGIN_GATEWAY_KEY);
            String rId = request.getHeader(AuthConstant.REQUEST_ID);
            if(StringUtils.isBlank(rId)){
                return ResultVO.failed(ResultCode.ILLEGAL_ACCESS, ResultCode.ILLEGAL_ACCESS.getMsg());
            }
            String enKey = MD5.create().digestHex(AESUtil.encrypt(String.valueOf(rId), AuthConstant.AES_SALT_VALUE));
            if(!enKey.equals(noLoginKey)){
                return ResultVO.failed(ResultCode.ILLEGAL_ACCESS, ResultCode.ILLEGAL_ACCESS.getMsg());
            }
        }
        return joinPoint.proceed();
    }
}
