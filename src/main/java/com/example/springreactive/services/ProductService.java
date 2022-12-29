package com.example.springreactive.services;

import com.example.springreactive.models.Product;
import com.example.springreactive.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
    
    /*
        так як добавлено деяку затримку для кожного елемента, то виконання методу займе відповідно
         3 елементи * 3секунди = 9 секунд
         
         але клієнт зможе отримувати елементи по черзі і через кілька секунд отримає 1 елемент, потім 2 іт.д
     */
    public Flux<Product> getProducts(){
//        return productRepository.findAll();
        
        /*
           for simulation purpose lets add delay for each element
         */
        return productRepository.findAll().delayElements(Duration.ofSeconds(3));
    }
    
}
