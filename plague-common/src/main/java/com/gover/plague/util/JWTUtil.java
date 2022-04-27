package com.gover.plague.util;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

public class JWTUtil {

    //加解密时的密钥, 用来生成key
    public static final String JWT_KEY = "D7D89DASYA8DEAF9AF9AYAH9ASF89F9SAF9AF8AHF7AHF9A";

    /**
     * 生成加密后的秘钥 secretKey
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JWTUtil.JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    public static String createJWT(String id, String subject, long ttlMillis){
        //指定签名的时候使用的签名算法，也就是JWT中header那部分
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        //生成签名的时候使用的秘钥secret, 一般可以改为从本地配置文件中读取，切记这个秘钥不能泄露
        SecretKey key = generalKey();
        //设置JWT的body
        JwtBuilder builder = Jwts.builder()
                //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
//                .setClaims(claims)
                //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击
                .setId(id)
                //JWT的签发时间
                .setIssuedAt(now)
                //sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .setSubject(subject)
                //设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, key);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            //设置过期时间
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    public static Claims parseJWT(String jwt){
        //签名秘钥，和生成的签名的秘钥一模一样
        SecretKey key = generalKey();
        //得到DefaultJwtParser
        Claims claims = Jwts.parser()
                //设置签名的秘钥
                .setSigningKey(key)
                .parseClaimsJws(jwt)
                .getBody(); //设置需要解析的jwt
        return claims;
    }


    /**
     * 测试
     * @param args
     */
    public static void main(String[] args){
        Map<String, Object> user = new HashMap<>();
        user.put("username", "张三");
        user.put("password", "123");
        String jwt = createJWT(UUID.randomUUID().toString(), JSON.toJSONString(user), 3600 * 24);

        System.out.println("加密后：" + jwt);
        Claims claims = parseJWT(jwt);
        System.out.println("解密后：" + claims.getSubject());
    }

}
