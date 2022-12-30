package com.example.springreactiveroutes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringreactiveroutesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringreactiveroutesApplication.class, args);
    }

    /*
    
       тут у нас не має контролерів, а є роути (з контролером див. 1 урок)
       
       1) у сервіс класі, як і зазвичай додано бізнес логіка
          у цьому класі є метод   Flux<Product> getAll() 
          
       2) у класі RoutesConfig визначаємо, які у нас є роути
           так як це Configuration клас, то визначаємо біни = кожен бін - це метод-роут:
           
                @Bean
                public RouterFunction<ServerResponse> router (ProductHandler productHandler){  <= Handler клас
                    return route()
                            // .GET("/products", request -> productHandler.getAll(request))    <= lambda
                            .GET("/products", productHandler::getAll)                          <= method reference
                            .build();
                }   
                
                
          щоб цей конфігураційний клас був "чистим", то використовуємо допоміжні Handler класи, 
          які будемо використовувати, щоб посилатися на потрібні методи 
          
       3) визначаємо Handler клас
       
            @Component
            public class ProductHandler {
            
                private final ProductService productService;                   <= потрібні депенденсі
       
                public Mono<ServerResponse> getAll(ServerRequest request) {    <= метод який опрацьовує Response і Request
                    return ok()
                        .contentType(MediaType.TEXT_EVENT_STREAM)
                        .body(productService.getAll(), Product.class);
                 }
         
         
     */
}
