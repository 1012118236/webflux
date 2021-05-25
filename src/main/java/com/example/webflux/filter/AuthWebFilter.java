package com.example.webflux.filter;

import com.alibaba.fastjson.JSON;
import com.example.webflux.beans.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@Slf4j
public class AuthWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        try {
            ServerHttpRequest request = serverWebExchange.getRequest();
            HttpHeaders headers = request.getHeaders();
            List<String> keyId = headers.get("keyId");
            List<String> keySecredId = headers.get("keySecredId");
            Assert.notEmpty(keyId, "key id is null");
            Assert.notEmpty(keySecredId,"keySecredId is null");
            //keyid  keySecreId 鉴权
            if(!keyId.contains("123"))
                throw new Exception("keyId is feifa");
        } catch (Exception e) {
            return sendResponseMessage(serverWebExchange,e.getMessage());
        }
        return webFilterChain.filter(serverWebExchange);
    }

    public Mono<Void> sendResponseMessage(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();

        String text = message;
        try {
            text =  JSON.toJSONString(Result.fail(message));
        } catch (Exception e) { log.error("{}",e); }
        byte[] bits =text.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        return response.writeWith(Mono.just(buffer));
    }

}
