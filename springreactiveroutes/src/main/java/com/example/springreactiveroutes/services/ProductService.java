package com.example.springreactiveroutes.services;

import com.example.springreactiveroutes.models.Product;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@Service
public class ProductService {
    
    /*
        Flux / Mono - it is Publisher
        
        дженерік тип - це те, що публікує (надсилає) це паблішер
        
        отже, можна собі це уявити ніби, що Flux / Mono створює стрім чогось
        тобто, наприклад трубу, у яку по черзі закидає визначений як дженірк тип об'єкт
        тобто, по мірі того, як готовий цей об'єкт, він надсилається 
     */
    public Flux<Product> getAll() {
        /*
            імітуємо що дістаємо дані з БД ...
         */
        Product p1 = new Product();
        p1.setName("bread");

        Product p2 = new Product();
        p2.setName("chocolate");

        Flux<Product> products = Flux.fromStream(Stream.of(p1, p2))
                .delayElements(Duration.ofSeconds(3)); // simulate something happen with products

        return products;
    }
}
