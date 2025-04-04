package com.gj.cloud.gjcloudai.functions;

import java.util.function.Function;

/**
 * @author houby@email.com
 * @date 2025/1/13
 */
public class LocationNameFunction implements Function<LocationNameFunction.Request, LocationNameFunction.Response> {

    /**
     * 接收GPT提取后的信息，（自动调用该方法）
     * @param request the function argument
     * @return
     */
    @Override
    public Response apply(Request request) {
        if (request.location().equals("") || request.name.equals("")) {
            return new Response("提示信息不可用");
        }

        // 这里正常调用第三方接口查询对应服务的数据库数据获取数据值
        return new Response("");
    }

    /**
     * 密封类
     * 带了@Data注解的类
     * 负责告诉GPT，需要提取那些关键信息，接收GPT提取后的信息
     */
    public record Request(String location, String name) {

    }

    /**
     * 最终响应GPT
     */
    public record Response(String msg) {

    }
}
