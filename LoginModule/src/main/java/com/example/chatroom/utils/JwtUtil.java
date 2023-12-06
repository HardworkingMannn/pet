package com.example.chatroom.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.existHour}")
    private Integer existHour;
    private Algorithm algorithm;
    @PostConstruct
    public void init(){
        algorithm = Algorithm.HMAC256(secret);
    }
    public String generateToken(Integer id){
         //用密钥创建算法对象，用于加密
        String token = JWT.create().withIssuer(issuer)
                .withClaim("userId",id)
                .withIssuedAt(new Date())
                .withExpiresAt(getExipireDate())
                .sign(algorithm);
        log.info("生成token:{}",token);
        return token;
    }
    public Date getExipireDate(){//用于获取结束时间
        return new Date(new Date().getTime() + existHour * 60 * 60 * 1000);
    }
    public Integer verifyToken(String token){
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build();
        DecodedJWT verify = verifier.verify(token);
        Integer userId = verify.getClaim("userId").asInt();
        log.info("检验token，找到userId:{}",userId);
        return userId;
    }
}
