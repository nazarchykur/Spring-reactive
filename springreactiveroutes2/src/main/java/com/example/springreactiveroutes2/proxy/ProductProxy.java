package com.example.springreactiveroutes2.proxy;

import com.example.springreactiveroutes2.exceptions.ProductRetrieveException;
import com.example.springreactiveroutes2.models.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Flux;

@Component
public class ProductProxy {

    private final WebClient webClient;

    public ProductProxy(WebClient webClient) {
        this.webClient = webClient;
    }
    
    public Flux<Product> getAll(){
        Product defaultProduct = new Product();
        defaultProduct.setName("default");


        return webClient.get()
                .uri("/products")
                .exchangeToFlux(res -> res.bodyToFlux(Product.class))
//                .onErrorResume(e -> Flux.just(defaultProduct));                                              // <= 1.1 
//                .onErrorResume(WebClientRequestException.class, e -> Flux.just(defaultProduct));             // <= 1.2
//                .onErrorResume(e -> e instanceof WebClientRequestException, e -> Flux.just(defaultProduct)); // <= 1.3
//                .onErrorResume(e -> e.getMessage() == null, e -> Flux.just(defaultProduct));                 // <= 1.3

//                .onErrorReturn(defaultProduct);                                                              // <= 2.1 
//                .onErrorReturn(WebClientRequestException.class, defaultProduct);                             // <= 2.2
//                .onErrorReturn(e -> e.getMessage() == null, defaultProduct);                                 // <= 2.3

//                .onErrorMap(e -> new ProductRetrieveException());                                            // <= 3.1

//                .doOnNext(element -> {                                    // для прикладу 
//                    if (element.getName() == null) {                      // є якась валідація
//                        throw new RuntimeException();                     // має бути якийсь конкретний ексепшн !!!
//                    }
//                })                                                                                           // <= 4.1
//                
//                // дозволяє добавити логіку до проблемного елемента і продовжити роботу
//                // щоб не викидати ексепшн і не припиняти всю роботу, якщо, наприклад, є проблеми з якимось одним елементом зі всього списку
//                .onErrorContinue((exception, object) -> System.out.println(exception.getMessage())); // наприклад логування цього елемента

//                .onErrorContinue(WebClientRequestException.class, (exception, object) -> System.out.println(exception.getMessage())); // <= 4.2
//                .onErrorContinue(e -> e.getMessage() == null, (exception, object) -> System.out.println(exception.getMessage()));     // <= 4.3

//                .retry();           // <= 5.1
                .retry(3);  // <= 5.2
    }
    
    
    /*
        є багато варіантів як опрацювати ерор:
        
        ці ексепшини мають похожі overloaded 3 methods
        
        1.1) (не рекомендується)  при будь-якому Exception
           зробити кол на інший сервіс (наприклад якщо перший не відповідає) 
           і, наприклад, повернути якийсь дефолтний елемент / список елементів    і т.д.
           
           є доступ до ерор, з яким можна ще щось зробити:
             - потрібна логіка щодо цього Exception
             - хоча б залогувати
             ...
           
           
        1.2) краще використовувати цей спосіб, коли зазначаємо конкретний Exception
        
           різниця між 1 і 2 у тому, що 2 відпрацює тільки, якщо буде WebClientRequestException, 
           у той час як 1 - при будь-якому Exception
           
        1.3) перший параметр - це Predicate 
            тільки якщо він поверне TRUE, тоді другий параметр зробить свою логіку   
            тобто перший параметр перевіряє якийсь кондішн у залежності від потрібної логіки, 
            наприклад, якщо  e.getMessage() == null
            
        2.1) схожий на 1.1
           різниця у тому, що тут ми не маємо доступу до ерора 
         
        2.2) схожий на 1.2
        
        2.3) схожий на 1.3
            
            
            
         так як зазвичай під другим параметром буде набагато більше коду чим тільки пів рядка (так як тут для прикладу)   
          
        3.1) мапить один ексепшн до іншого нашого   ProductRetrieveException
             тобто коли хочемо конвертнути отриманий ексепшн до потрбіного нашого ексепшина  
            
            
         4.1)  (не рекомендується до використання, краще або 4.2 або 4.3)
         
               в реальних умовах може бути, що серед тисячі елементів буде, наприклад якийсь частково незаповнений об'єкт 
                 або пустий ... і т.д. який приведе до помилки, і щоб не викидати ексепшн і припиняти роботу з усіма 
                 елементами можна продовжити роботу, а з проблемним елементом щось додатково зробити:
                    - залогувати 
                    - зробити додатковий кол
                    - зробити інший кол
                    ...
         
         4.2) схоже на 1.2  = працюємо з конкретним Exception
         
         4.3) схоже до 1.3  = перший параметр - це Predicate 
        
        
        спробувати кілька разів здійснити запит
         5.1)
         5.2) спробувати х (кілька) разів   
         5.3)    
     */
}





























