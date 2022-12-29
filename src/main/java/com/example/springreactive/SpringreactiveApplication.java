package com.example.springreactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringreactiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringreactiveApplication.class, args);
    }

}
/*
    https://spring.io/reactive
    
    https://www.cognizantsoftvision.com/blog/getting-started-with-reactive-spring-spring-webflux/
    
    https://medium.com/@cheron.antoine/reactor-java-1-how-to-create-mono-and-flux-471c505fa158
    
    Reactive streams and Project Reactor
    
    Spring WebFlux supports the Reactive Stream API, which is a standardized tool for processing asynchronous streams 
    with non-blocking backpressure. Backpressure is a way of dealing with a data stream that may be too large to be 
    reliably processed. In other words, backpressure refers to the ability to request data when the consumer is ready 
    to process them.
    
    Reactive streams have a publisher (producer) — subscriber (consumer) model. 
    The publisher emits an event and a subscriber will read it. 
    In the Reactive Streams API there are four main interfaces:
    
    > Publisher — Emits events to subscribers based on the demands received from its subscribers. 
        A publisher can serve multiple subscribers and it has only one method: subscribe
    > Subscriber — Receives events emitted by the Publisher. 
        The subscribe has four methods to deal with the events received: onSubscribe, onNext, onError and onComplete
    > Subscription — Represents the relationship between the subscriber and the publisher. 
        It has methods that allow requesting for data request(long n) and to cancel the demand of events cancel()
    > Processor — Publisher and subscriber at the same time; rarely used.
    
    
    Spring WebFlux internally uses Project Reactor and its publisher implementations, Flux and Mono.
            Mono — A publisher that can emit 0 or 1 element.
            Flux — A publisher that can emit 0..N elements.
    
    
    Mono and Flux offer simple ways of creating streams of data:
    
            Mono<Integer> mono = Mono.just(1);
            Mono<Object> monoEmpty = Mono.empty();
            Mono<Object> monoError = Mono.error(new Exception());
            
            Flux<Integer> flux = Flux.just(1, 2, 3, 4);
            Flux<Integer> fluxFromIterable = Flux.fromIterable(Arrays.asList(1, 2, 3, 4));
            Flux<Integer> fluxRange = Flux.range(10, 5);
            Flux<Long fluxInterval = Flux.interval(Duration.ofSeconds(2));
            
            
     After creating a stream, in order for it to emit elements we need to subscribe to it. 
     Nothing will happen until you subscribe to the publisher; the data won’t flow until the subscribe method is called.
            
            List<String> streamData = new ArrayList<>();
            
            Flux.just("A", "B", "C", "D")
                    .log()
                    .subscribe(streamData::add);
            
            Assertions.assertThat(streamData).containsExactly("A", "B", "C", "D");
            
            
            
      By using the .log() method we can observe all stream signals and trace them. 
      If no logging framework is configured, the events are logged to the console.     
      
      
      
      Reactor offers several operators for working with Flux and Mono objects. Most commonly used are:

        Map — Used to transform the publisher elements to another elements
        FlatMap — Similar to map, but transformation is asynchronous
        FlatMapMany — Mono operator used to transform a Mono into a Flux
        DelayElements — Delays the publishing of each element by a given duration
        Concat — Used to combine publishers’ elements by keeping the sequence of the publishers
        Merge — Used to combine publishers without keeping the publishers’ sequence, instead it interleaves the values
        Zip — Used to combine two or more publishers by waiting on all the sources to emit one element and combining these elements into an output value 
        
        
        Building a RestApi with WebFlux

            Spring WebFlux supports two programming models:
                    Annotation-based reactive components
                    Functional routing and handling
        
        
        RestAPI with annotated controller
        
            The annotation model is practically the same as in Spring MVC, so we can use the existing annotations:
                    @Controller
                    @RestController
                    @GetMapping
                    @PostMapping
                    @PutMapping
                    @DeleteMapping
                    @PatchMapping

 */