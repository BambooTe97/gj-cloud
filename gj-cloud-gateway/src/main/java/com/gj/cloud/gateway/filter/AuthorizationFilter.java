//package com.gj.cloud.gateway.filter;
//
//import com.alibaba.fastjson.JSON;
//import com.gj.cloud.common.api.ResultCode;
//import com.gj.cloud.common.exception.GateWayException;
//import com.gj.cloud.gateway.component.GjRestTemplate;
//import com.gj.cloud.gateway.properties.NoAuthUrlProperties;
//import com.gj.cloud.gateway.utils.JwtUtils;
//import io.jsonwebtoken.Claims;
//import jdk.jfr.Enabled;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.cloud.gateway.config.GatewayProperties;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.util.PathMatcher;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.security.PublicKey;
//import java.util.Map;
//
//@Component
//@Slf4j
//@EnableConfigurationProperties(NoAuthUrlProperties.class)
//public class AuthorizationFilter implements GlobalFilter, Ordered, InitializingBean {
//
//    @Autowired
//    private GjRestTemplate restTemplate;
//
//    /**
//     * 请求各个微服务 不需要用户认证的URL
//     */
//    @Autowired
//    private NoAuthUrlProperties noAuthUrlProperties;
//
//    /**
//     * jwt的公钥,需要网关启动,远程调用认证中心去获取公钥
//     */
//    private PublicKey publicKey;
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        this.publicKey = JwtUtils.genPublicKey(restTemplate);
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String currentUrl = exchange.getRequest().getURI().getPath();
//        //1:不需要认证的url
//        if (shouldSkip(currentUrl)) {
//            //log.info("跳过认证的URL:{}",currentUrl);
//            return chain.filter(exchange);
//        }
//
//        //log.info("需要认证的URL:{}",currentUrl);
//
//        //第一步:解析出我们Authorization的请求头  value为: “bearer XXXXXXXXXXXXXX”
//        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
//
//        //第二步:判断Authorization的请求头是否为空
//        if (StringUtils.isEmpty(authHeader)) {
//            log.warn("需要认证的url,请求头为空");
//            throw new GateWayException(ResultCode.AUTHORIZATION_HEADER_IS_EMPTY);
//        }
//
//        //第三步 校验我们的jwt 若jwt不对或者超时都会抛出异常
//        Claims claims = JwtUtils.validateJwtToken(authHeader, publicKey);
//
//        //第四步 把从jwt中解析出来的 用户登陆信息存储到请求头中
//        ServerWebExchange webExchange = wrapHeader(exchange, claims);
//
//        return chain.filter(webExchange);
//    }
//
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//
//    /**
//     * 是否跳过授权的路径
//     *
//     * @param currentUrl 当前请求路径
//     * @return true:跳过
//     */
//    private boolean shouldSkip(String currentUrl) {
//        //路径匹配器(简介SpringMvc拦截器的匹配器)
//        //比如/oauth/** 可以匹配/oauth/token    /oauth/check_token等
//        PathMatcher pathMatcher = new AntPathMatcher();
//        for (String skipPath : noAuthUrlProperties.getSkipUrls()) {
//            if (pathMatcher.match(skipPath, currentUrl)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 把我们从jwt解析出来的用户信息存储到请求中
//     * @param serverWebExchange
//     * @param claims
//     * @return
//     */
//    private ServerWebExchange wrapHeader(ServerWebExchange serverWebExchange,Claims claims) {
//
//        String loginUserInfo = JSON.toJSONString(claims);
//
//        //log.info("jwt的用户信息:{}",loginUserInfo);
//
//        String memberId = claims.get("additionalInfo", Map.class).get("memberId").toString();
//
//        String nickName = claims.get("additionalInfo",Map.class).get("nickName").toString();
//
//        //向headers中放文件，记得build
//        ServerHttpRequest request = serverWebExchange.getRequest().mutate()
//                .header("username",claims.get("user_name",String.class))
//                .header("memberId",memberId)
//                .header("nickName",nickName)
//                .build();
//
//        //将现在的request 变成 change对象
//        return serverWebExchange.mutate().request(request).build();
//    }
//}
