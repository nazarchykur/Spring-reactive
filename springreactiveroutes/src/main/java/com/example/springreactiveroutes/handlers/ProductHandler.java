package com.example.springreactiveroutes.handlers;

import com.example.springreactiveroutes.models.Product;
import com.example.springreactiveroutes.services.ProductService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class ProductHandler {

    private final ProductService productService;

    public ProductHandler(ProductService productService) {
        this.productService = productService;
    }
    /*
        отже у цьому відділеному ProductHandler класі ми визначаємо як ми опрацьовуємо Response і Request
     */
    public Mono<ServerResponse> getAll(ServerRequest request) {
        // у цьому прикладі ми не використовуємо request параметр, проте зазвичай нам потрібно буде щось брати з request
        // або queryParams(), або pathVariable() / pathVariables()
        return ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(productService.getAll(), Product.class);
    }
}
