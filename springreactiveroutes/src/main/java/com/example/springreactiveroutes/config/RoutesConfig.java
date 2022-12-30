package com.example.springreactiveroutes.config;

import com.example.springreactiveroutes.models.Product;
import com.example.springreactiveroutes.services.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class RoutesConfig {
    
//    private final ProductService productService;
//
//    public RoutesConfig(ProductService productService) {
//        this.productService = productService;
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> router (){
//        /*
//            1) створюємо роут
//            2) визначаємо по якому методу цей роут буде відпрацьовувати (get / post / put / delete / patch / ...)
//                2.1) визначаємо по якому урлі цього методу = пергий параметр ("/products")
//                2.2) другий параметр = використовуючи цей запит ми повертаємо 
//                    ok()  - статус
//                    .contentType(MediaType.TEXT_EVENT_STREAM) - визначаємо що це стрім (бо по замовчуванню JSON)
//                    .body() - тіло методу (перший параметр - це метод сервіс класу
//                                          другий параметр - це що саме, тобто, який об'єкт ми повертаємо )
//         */
//        
//        /*
//        
//            http://localhost:8092/products
//            
//            data:{"name":"bread"}             <= через перші 3 секунди ми отримаємо перший продукт
//            data:{"name":"chocolate"}         <= через наступні 3 секунди ми отримаємо другий продукт
//        
//         */
//        return route()
//                .GET("/products", request -> ok()
//                        .contentType(MediaType.TEXT_EVENT_STREAM)
//                        .body(productService.getAll(), Product.class))
//                .build();
//    }
//    /*
//            так як ми можемо тут добавити багато роутів з різними методами, і відповідно різними БОДІ,
//            то цей метод стане досить великим і нечитабельним
//            
//            також так як це конфігураційний клас = тобто БІН, то тут можна додати потрібний сервіс як депенденсі
//            і потім ініціалізувати через конструктор (як у цьому прикладі)
//             - але сервісів може бути багато
//            
//     */

    /*
        можна передати потрібний сервіс одразу потрібному РОУТУ
        тобто код зробиться трохи чистішим
    
           http://localhost:8092/products
            
            data:{"name":"bread"}             <= через перші 3 секунди ми отримаємо перший продукт
            data:{"name":"chocolate"}         <= через наступні 3 секунди ми отримаємо другий продукт
     */
    @Bean
    public RouterFunction<ServerResponse> router2 (ProductService productService){
        return route()
                .GET("/products", request -> ok()
                        .contentType(MediaType.TEXT_EVENT_STREAM)
                        .body(productService.getAll(), Product.class))
                .build();
    }
    
}
