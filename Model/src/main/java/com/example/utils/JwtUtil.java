package com.example.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.Model.constant.JwtConst;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtUtil {
    private Algorithm algorithm= Algorithm.HMAC256(JwtConst.JWT_SECRET);
    public String  generateToken(Integer id){
         //用密钥创建算法对象，用于加密
        String token = JWT.create().withIssuer(JwtConst.JWT_ISSUER)
                .withClaim("userId",id)
                .withIssuedAt(new Date())
                .withExpiresAt(getExipireDate())
                .sign(algorithm);
        log.info("生成token:{}",token);
        return token;
    }
    public Date getExipireDate(){//用于获取结束时间
        return new Date(new Date().getTime() + JwtConst.JWT_EXIST_HOUR * 60 * 60 * 1000);
    }
    public Integer verifyToken(String token){
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(JwtConst.JWT_ISSUER)
                .build();
        DecodedJWT verify = verifier.verify(token);
        Integer userId = verify.getClaim("userId").asInt();
        log.info("检验token，找到userId:{}",userId);
        return userId;
    }
}
