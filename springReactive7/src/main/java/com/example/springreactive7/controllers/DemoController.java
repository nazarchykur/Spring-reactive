package com.example.springreactive7.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class DemoController {
    
    @GetMapping(value = "/demo", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<String> demo(){
        return Mono.just("demo");
    }
}
