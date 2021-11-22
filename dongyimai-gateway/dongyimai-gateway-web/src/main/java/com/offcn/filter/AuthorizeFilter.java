package com.offcn.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author chenxi
 * @date 2021/11/16 16:52
 * @description
 */
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {

    //令牌头名字
    private static final String AUTHORIZE_TOKEN = "Authorization";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String path = request.getURI().getPath();

        if (path.startsWith("/api/user/login") || path.startsWith("/api/user/add") || path.startsWith("/api/user/sendCode")){
            Mono<Void> filter = chain.filter(exchange);
            return filter;
        }

        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN);

        if (StringUtils.isEmpty(token)){
            token = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
            if (StringUtils.isEmpty(token)){
                HttpCookie cookie = request.getCookies().getFirst(AUTHORIZE_TOKEN);
                if (cookie == null){
                    response.setStatusCode(HttpStatus.METHOD_NOT_ALLOWED);
                    return response.setComplete();
                }
                token = cookie.getValue();
            }
        }




        try {
            if (!token.startsWith("Bearer ") && !token.startsWith("bearer ")){
                token = "bearer "+token;
            }
            request.mutate().header(AUTHORIZE_TOKEN, token);
            return chain.filter(exchange);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
