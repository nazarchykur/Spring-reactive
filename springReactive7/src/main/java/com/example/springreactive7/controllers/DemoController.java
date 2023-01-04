package com.example.springreactive7.controllers;

import org.springframework.http.MediaType;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class DemoController {
    
    @GetMapping(value = "/demo", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<String> demo(){
        /*
        щоб взяти аутентифікацію, де нам потрібно можна витягнути з  ReactiveSecurityContextHolder
         */
        
//        ReactiveSecurityContextHolder.getContext()
//                .map(SecurityContext::getAuthentication)
//                .subscribe(System.out::println);
        
        return Mono.just("demo");
    }

    @GetMapping(value = "/hello", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<String> hello(){
        return Mono.just("hello");
    }
}
