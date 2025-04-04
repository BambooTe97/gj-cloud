//package com.gj.cloud.gateway.filter;
//
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
///**
// * 过滤器
// * 当过滤器的order值一样时，会按照defaultFilter > 路由过滤器 > GlobalFilter 的顺序执行
// * @author houby@email.com
// * @date 2023/12/24
// */
//@Order(Ordered.HIGHEST_PRECEDENCE)
//@Component
//public class AuthorizeFilter implements GlobalFilter {
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        // 获取请求参数
//        ServerHttpRequest request = exchange.getRequest();
//        MultiValueMap<String, String> queryParams = request.getQueryParams();
//        // 获取参数的 authorization
//        String auth = queryParams.getFirst("authorization");
//        // 判断参数是否等于admin
//        if ("admin".equals(auth)) {
//            return chain.filter(exchange);
//        }
//
//        // 拦截 设置状态码
//        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//        return exchange.getResponse().setComplete();
//    }
//}
