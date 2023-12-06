package com.example.gateway;

import com.example.Model.constant.JwtConst;
import com.example.Model.pojo.Result;
import com.example.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;

@Component
public class TokenFilter implements GlobalFilter {
    @Autowired
    private JwtUtil jwtUtils;
    @Autowired
    private Jedis jedis;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        if (request.getURI().toString().contains("login")) {//把登录接口排除
            System.out.println("是登录相关的接口");
            return chain.filter(exchange);
        }
        List<String> list = request.getHeaders().get("token");
        if(list==null){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        String token = list.get(0);
        Integer userId = jwtUtils.verifyToken(token);
        if (userId != null&&jedis.get(token)==null) {
            jedis.setex(token, JwtConst.JWT_EXIST_HOUR *60*60,""+userId);   //设置在redis中，让其他共享
            return chain.filter(exchange);
        }
            System.out.println("没有token");
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
    }
}
