package com.example.springreactive.controllers;

import com.example.springreactive.models.Product;
import com.example.springreactive.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.awt.*;

/*
    поки використаємо RestController
    пізніше будемо використовувати Route
 */
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    
    /*
        produces = MediaType.TEXT_EVENT_STREAM_VALUE
        
        вказуємо що буде EVENT_STREAM
        бо інакше це буде наш звичний JSON (тобто поверне список продуктів), і це не те, що очікуємо при reactive app
        
        
        для виконання запиту заходимо у браузер (бо він може працювати як реактів клієнт)
            http://localhost:8091/products
            
            у результаті поступово через кожні 3 секунди буде добавлятися наступні дані
                    data:{"id":1,"name":"chocolate"}

                    data:{"id":2,"name":"water"}
                    
                    data:{"id":3,"name":"bread"}
                    
                    
            
            - постмен не є реактів клієнт, тому цей запит там спрацює як звичайний:
                буде чекати всі 9 секунд, щоб мати всі дані, і тільки тоді поверне результат
                
                
          важливо!!!
                 клієнт має бути реактів, тобто сервіс (інший мікросервіс ...), який здійснює запит на реактів сервіс
                 має працювати з реактів, бо інакше це немає сенсу, оскільки запити будуть працювати як при звичному РЕСТ 
                 (тобто чекати перше на респонс і тільки тоді продовжувати працювати з даними)
     */
    @GetMapping(value = "/products", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Product> getProduct() {
        return productService.getProducts();
    }
}
