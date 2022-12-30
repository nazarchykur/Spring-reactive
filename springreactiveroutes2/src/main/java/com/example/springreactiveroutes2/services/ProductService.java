package com.example.springreactiveroutes2.services;

import com.example.springreactiveroutes2.models.Product;
import com.example.springreactiveroutes2.proxy.ProductProxy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ProductService {

    private final ProductProxy productProxy;

    public ProductService(ProductProxy productProxy) {
        this.productProxy = productProxy;
    }
    
    public Flux<Product> getAll(){
        return productProxy.getAll();
    }
}
