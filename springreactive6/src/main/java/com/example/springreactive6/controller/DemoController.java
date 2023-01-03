package com.example.springreactive6.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DemoController {

//    @GetMapping(value = "/demo", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<String> demo() {
//
////        !!! старатися не використовувати blocking методи, які повертають деякий об'єкт T
////            а використовувати інші реактів методи, щоб отримати переваги, заради яких і використовується реактів 
////
////             щоб не перетворювати reactive app => to non reactive app
////             тобто завжди використовувати підхід. коли ми повертаємо Publisher (Mono / Flux)
////        
//
//     /*
//        http://localhost:8095/demo
//    
//            data:aaa
//            data:bb
//            data:c
//            data:dddd
//            
//        у цьому випадку ми дістанемо дані один за одним
//        
//        оскільки браузер прийме цей запит, то з     produces = MediaType.TEXT_EVENT_STREAM_VALUE
//        він знає, що він має працювати як реактів
//        
//          1) - один із варіантів як створити Publisher => Flux.just
//          2) - браузер, як клієнт, підпишеться на цей запит і по одному буде тягнути елементи у буде працювати у 
//                стилі реактів => тобто, під капотом відпрацює Subscriber
//         */
//        
////        Flux<String> f1 = Flux.just("aaa", "bb", "c", "dddd");
////        return f1;
//
//        
//        /*
//        https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html
//        https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html
//        
//         так як з реактів нам пропонується функціональний стиль, то багато подібного є з Stream API, 
//         наприклад ось кілька варіантів:
//            - filter()
//            - map() / flatMap()
//            - count()
//            - distinct()
//            - generate()
//            - parallel()
//            - reduce()
//            - collect()
//            
//            ... а також ще багато з самого   projectreactor
//            
//         */
//
////        Flux<String> f1 = Flux.just("aaa", "bb", "c", "dddd");
////        return f1.filter(s -> s.length() % 2 == 0);
//        
//        /*
//            http://localhost:8095/demo
//                        data:bb
//                        data:dddd
//         */
//
////        Flux<String> f1 = Flux.just("aaa", "bb", "c", "dddd");
////        return f1
////                .log()
////                .filter(s -> s.length() % 2 == 0);
//        
//        /*
//        with logger
//        
//2023-01-03T22:46:43.017+02:00  INFO 131230 --- [or-http-epoll-2] reactor.Flux.Array.1                     : | onSubscribe([Synchronous Fuseable] FluxArray.ArrayConditionalSubscription)
//2023-01-03T22:46:43.018+02:00  INFO 131230 --- [or-http-epoll-2] reactor.Flux.Array.1                     : | request(1)
//2023-01-03T22:46:43.019+02:00  INFO 131230 --- [or-http-epoll-2] reactor.Flux.Array.1                     : | onNext(aaa)
//2023-01-03T22:46:43.019+02:00  INFO 131230 --- [or-http-epoll-2] reactor.Flux.Array.1                     : | onNext(bb)
//2023-01-03T22:46:43.034+02:00  INFO 131230 --- [or-http-epoll-2] reactor.Flux.Array.1                     : | request(31)
//2023-01-03T22:46:43.035+02:00  INFO 131230 --- [or-http-epoll-2] reactor.Flux.Array.1                     : | onNext(c)
//2023-01-03T22:46:43.035+02:00  INFO 131230 --- [or-http-epoll-2] reactor.Flux.Array.1                     : | onNext(dddd)
//2023-01-03T22:46:43.036+02:00  INFO 131230 --- [or-http-epoll-2] reactor.Flux.Array.1                     : | onComplete()
//
//            1) при першому запиті бачимо так зване backpressure = request(1)
//                у цьому прикладі, так як у нас там є функція filter щодо довжини слова, то Subscriber взяв перше слово
//                яке не пройшло у filter умову, а ось друге пройшло, тому бачимо 
//                        onNext(aaa)
//                        onNext(bb)
//                а request(1), тому що тільки одне слово пройшло умову, у Subscriber здійснив для цього запит
//                
//            2) Spring налаштоване на оптимізацію і при  request(31) бачимо 31, що означає, що Spring зрозумів, що можна 
//            спробувати опрацювати набагато більше даних, бо взяв за основу попереднє опрацювання, яке виявилося простим, 
//            і тепер для оптимізації  Spring спробує опрацювати більше даних        
//            
//            3) onComplete() - бачимо вкінці, що означає, що більше немає ніяких значень
//                ще може бути 
//                onCancel - the callback to call on Subscription.cancel()
//                onError - the callback to call on Subscriber.onError(java.lang.Throwable)
//         */
//
//
////        Flux<String> f1 = Flux.just("aaa", "bb", "c", "dddd");
////        return f1.filter(s -> s.length() % 2 == 0)
////                .log();
//        
//        /*
//        тут ми у лог побачимо тільки те що пройшло Predicate
//        
//2023-01-03T23:10:58.777+02:00  INFO 137972 --- [or-http-epoll-2] reactor.Flux.FilterFuseable.1            : | onSubscribe([Fuseable] FluxFilterFuseable.FilterFuseableSubscriber)
//2023-01-03T23:10:58.779+02:00  INFO 137972 --- [or-http-epoll-2] reactor.Flux.FilterFuseable.1            : | request(1)
//2023-01-03T23:10:58.779+02:00  INFO 137972 --- [or-http-epoll-2] reactor.Flux.FilterFuseable.1            : | onNext(bb)
//2023-01-03T23:10:58.794+02:00  INFO 137972 --- [or-http-epoll-2] reactor.Flux.FilterFuseable.1            : | request(31)
//2023-01-03T23:10:58.794+02:00  INFO 137972 --- [or-http-epoll-2] reactor.Flux.FilterFuseable.1            : | onNext(dddd)
//2023-01-03T23:10:58.794+02:00  INFO 137972 --- [or-http-epoll-2] reactor.Flux.FilterFuseable.1            : | onComplete()
//         */
//
//
////        Flux<String> f1 = Flux.just("aaa", "aaa", "bb", "c", "dddd", "aaa");
////        return f1.distinct(); // поверне без дублікатів
//
//
////        Flux<String> f1 = Flux.just("aaa", "aaa", "bb", "c", "dddd", "aaa");
////        return f1.map(s -> s + " " + s.length()); // якась дія з кожним елементом
////        
//        /*
//        http://localhost:8095/demo
//            data:aaa 3
//            data:aaa 3
//            data:bb 2
//            data:c 1
//            data:dddd 4
//            data:aaa 3
//         */
////
////        Flux<String> f1 = Flux.just("aa", "b");
////        return f1.flatMap(s -> Flux.just(s.split(""))); // розбити елемент на стрім елементів / або робота з деяким списком кожного елемента...
//
//        /*
//        http://localhost:8095/demo
//                data:a
//                data:a
//                data:b
//         */
//
//
//
//        /*
//        doOn... methods
//        
//            .doOnNext
//            .doOnEach
//            .doOnCancel
//            .doOnComplete
//            .doOnError
//            .doOnSubscribe
//        
//         */
//
////        Flux<String> f1 = Flux.just("aa", "b");
////        return f1.doOnNext(System.out::println); // виведе в консоль ці елементи
//
//        
//        
//        
//        /*
//         !!! щось подібне  НЕ потрібно робити і скоріш уникати 
//         
//                public Flux<String> demo() {
//                    List<String> f1 = Flux.just("aa", "b");
//                    List<String> strings = new ArrayList<>();
//                    
//                    return f1.doOnNext(s -> strings.add(s));
//                }
//                 
//                 
//         оскільки ми працюємо з реактів, то потрібно старатися як сам метод повертати Mono<ЯкийсьОб'єкт> / Flux<ЯкийсьОб'єкт>
//         а не List<ЯкийсьОб'єкт>  / Set / Map ...
//         щоб використовувати переваги, заради яких і використовуємо реактів у деякому нашому ап
//         
//         !!! те саме стосується blocking методів, які якраз і повертають деякий об'єкт T => тобто використовуємо як
//             не реатків ап, а як простий ап зі звичним, наприклад, рест 
//         
//         */
//
//
//        Flux<String> f1 = Flux.just("aa", "b");
//        return f1.doOnNext(System.out::println); // виведе в консоль ці елементи
//        
//    }

    /*
            якщо потрібно повернути, наприклад, деякий список, то потрібно це робити у стилі реактів підходу
            щоб не перетворювати reactive app => to non reactive app
            
            тобто завжди використовувати підхід. коли ми повертаємо Publisher (Mono / Flux)
     */
//    @GetMapping(value = "/demo", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Mono<List<String>> demo() {
//
//     /*
//        http://localhost:8095/demo
//        
//                data:["aaa","bb","c","dddd"]
//            
//         */
//
//        Flux<String> f1 = Flux.just("aaa", "bb", "c", "dddd");
//        return f1.collect(Collectors.toList());
//    }


//    @GetMapping(value = "/demo", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<String> demo() {
//
//     /*
//     наприклад, перший Flux - це дані з першого ап або БД чи іншшої таблиці
//      а другий Flux відповідно з другого
//     
//        http://localhost:8095/demo
//        
//                data:aaa
//                data:bb
//                data:c
//                data:dddd
//                data:qwer
//                data:rty
//            
//         */
//        
//        Flux<String> f1 = Flux.just("aaa", "bb", "c", "dddd"); 
//        Flux<String> f2 = Flux.just("qwer", "rty");
//        return f1.concatWith(f2); // об'єднає у один
//    }

//    @GetMapping(value = "/demo", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<String> demo() {
//
//     /*
//     наприклад, перший Flux - це дані з першого ап або БД чи іншшої таблиці
//      а другий Flux відповідно з другого
//     
//        http://localhost:8095/demo
//
//                data:qwer
//                data:rty
//            
//         */
//
//        Flux<String> f1 = Flux.just("aaa", "bb", "c", "dddd");
//        Flux<String> f2 = Flux.just("qwer", "rty");
//        return f1
//                .doOnNext(System.out::println) // означає, що тут ми ще працюємо з f1
//                .thenMany(f2); // а тут тільки з f2
//    }


    @GetMapping(value = "/demo", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> demo() {

     /*
     об'єднає два стріми у один, щоб скомбінувати наприклад два значення у якесь одне 
     
     якщо 2 стріми даних одинакового розміру, то будуть всі значення з обох
     якщо ні, то по меншому розміру
     
        http://localhost:8095/demo
        
                data:aaa = qwer
                data:bb = rty
            
         */

        Flux<String> f1 = Flux.just("aaa", "bb", "c", "dddd");
        Flux<String> f2 = Flux.just("qwer", "rty");
        return f1
                .zipWith(f2, (x, y) -> x + " = " + y);
    }
}

/*



 */