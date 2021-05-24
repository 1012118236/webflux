package com.example.webflux.controller;

import com.example.webflux.entity.User;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class IndexController {
    @GetMapping("/index")
    public String index() {
        return "index";
    }
    @GetMapping("/user")
    public Flux<User> getUser(){
        User user = new User();
        user.setName("犬小哈");
        user.setDesc("欢迎关注我的公众号: 小哈学Java");
        Mono<User> mono = Mono.just(user);
        User user1 = new User();
        user1.setName("aaaa");
        user1.setDesc("aaaa: 小哈学Java");
        return Flux.just(user,user1).concatWith(mono);
    }
    @GetMapping("/randomNumbers")
    public Flux<ServerSentEvent<Integer>> randomNumbers() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> Tuples.of(seq, ThreadLocalRandom.current().nextInt()))
                .map(data -> ServerSentEvent.<Integer>builder()
                        .event("random")
                        .id(Long.toString(data.getT1()))
                        .data(data.getT2())
                        .build());
    }




}
