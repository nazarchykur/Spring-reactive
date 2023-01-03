package com.example.springreactiveroutes2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Springreactiveroutes2Application {

    public static void main(String[] args) {
        SpringApplication.run(Springreactiveroutes2Application.class, args);
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


/*
        
    Представляємо парадигму реактивного програмування
        
        Реактивне програмування — це парадигма програмування, яка допомагає реалізувати неблокуючу, 
        асинхронну обробку даних, керовану подіями чи повідомленнями. Він моделює дані та події як потоки, 
        які може спостерігати та реагувати на них шляхом обробки або перетворення даних. Поговоримо про відмінності 
        між блокуючою та неблокуючою обробкою запитів.


Запит на блокування
    У звичайній програмі MVC щоразу, коли запит досягає сервера, створюється потік сервлета, який делегується робочим 
    потокам для виконання різних операцій, таких як введення/виведення, обробка бази даних тощо. Поки робочі потоки 
    зайняті виконанням своїх процесів, сервлет потоки переходять у стан очікування, через що виклики залишаються заблокованими. 
    Це блокування або синхронна обробка запиту.  (1 img)


Неблокуючий запит
    У неблокуючій системі всі вхідні запити супроводжуються обробником подій і зворотним викликом. Потік запиту делегує 
    вхідний запит пулу потоків, який керує досить невеликою кількістю потоків. Потім пул потоків делегує запит своїй 
    функції обробки та стає доступним для обробки наступних вхідних запитів із потоку запитів.

    Коли функція обробки завершує свій процес, один із потоків із пулу отримує відповідь і передає її функції зворотного 
    виклику. Таким чином, потоки в неблокуючій системі ніколи не переходять у стан очікування. 
    Це підвищує продуктивність і продуктивність програми.

    Один запит потенційно обробляється кількома потоками! (2 img)
    
    
Backpressure
    У Reactive Streams зворотний тиск визначає механізм регулювання передачі даних між потоками.
    
    Вважайте, що сервер A надсилає 1000 EPS (подій на секунду) серверу B. Але сервер B міг обробити лише 800 EPS і, 
    отже, має дефіцит 200 EPS. Сервер B тепер буде мати тенденцію відставати, оскільки він має обробляти дефіцитні дані 
    та надсилати їх униз або, можливо, зберігати в базі даних. Таким чином, сервер B має справу із зворотним тиском і 
    незабаром втратить пам’ять і вийде з ладу.

    Отже, цим протитиском можна впоратися або керувати за допомогою таких варіантів або стратегій:
        - Буфер – ми можемо легко буферизувати дефіцитні дані та обробити їх пізніше, коли на сервері буде достатньо 
            можливостей. Але при надходженні величезного навантаження даних цей буфер може збільшитися, і на сервері 
            скоро вичерпається пам’ять.
            
        - Відкинути — відкинути, тобто не обробляти події, має бути останнім варіантом. Зазвичай ми можемо використовувати 
            концепцію вибірки даних у поєднанні з буферизацією, щоб зменшити втрату даних.
            
        - Контроль. Концепція контролю над виробником, який надсилає дані, є найкращим варіантом. Reactive Streams надає
            різні параметри як у потоках push, так і на основі pull для контролю даних, які створюються та надсилаються споживачеві.
            
            
            
Реактивні бібліотеки Java

    > RxJava : це реалізовано в рамках проекту ReactorX , який містить реалізації для багатьох мов програмування та платформ. 
        ReactiveX — це поєднання найкращих ідей шаблону Observer , шаблону Iterator і функціонального програмування .          
    > Проект Reactor : Reactor — це фреймворк, створений Pivotal і на основі Spring. Він вважається однією з основ 
        реактивного стека в екосистемі Spring . Він реалізує шаблони Reactive API, які базуються на специфікації Reactive Streams .
    >  Akka Streams / Ratpack / Vert.x ....
    
    
Spring Webflux внутрішньо створено з використанням основних компонентів RxJava та RxNetty.    


Знайомство з Spring Webflux

    Spring представив модель Multi-Event Loop , щоб увімкнути реактивний стек, відомий як WebFlux. Це повністю 
    неблокуюча веб-платформа на основі анотацій, створена на Project Reactor , яка дозволяє створювати реактивні 
    веб-додатки на рівні HTTP. Він забезпечує підтримку популярних вбудованих серверів, таких як контейнери 
    Netty , Undertow і Servlet 3.1 .

    Mono: A Publisher, що випромінює 0 або 1 елемент.
            Mono<String> mono = Mono.just("John");
            Mono<Object> monoEmpty = Mono.empty();
            Mono<Object> monoError = Mono.error(new Exception());
    
    Flux: A Publisher, що випромінює від 0 до N елементів, які можуть випромінювати елементи вічно. 
        Він повертає послідовність елементів і надсилає сповіщення, коли завершить повернення всіх своїх елементів.
                Flux<Integer> flux = Flux.just(1, 2, 3, 4);
                Flux<String> fluxString = Flux.fromArray(new String[]{"A", "B", "C"});
                Flux<String> fluxIterable = Flux.fromIterable(Arrays.asList("A", "B", "C"));
                Flux<Integer> fluxRange = Flux.range(2, 5);
                Flux<Long> fluxLong = Flux.interval(Duration.ofSeconds(10));
                
                // To Stream data and call subscribe method
                List<String> dataStream = new ArrayList<>();
                Flux.just("X", "Y", "Z")
                    .log()
                    .subscribe(dataStream::add);

Коли потік даних створено, на нього потрібно підписатися, щоб він почав видавати елементи. 
Дані не надходитимуть і не оброблятимуться, доки не буде викликано метод subscribe(). 
Крім того, використовуючи наведений .log() вище метод, ми можемо відстежувати та спостерігати за всіма сигналами потоку. 
Події реєструються в консолі.


Reactor також надає операторів для роботи з Mono об’єктами Flux. Деякі з них:

Map- Використовується для перетворення одного елемента в інший.
FlatMap- Він зводить список Publishers до значень, які видають ці видавці. Перетворення асинхронне.
FlatMapMany- Це Monoоператор, який використовується для перетворення Mono об'єкта в Flux об'єкт.
DelayElements- Він затримує публікацію кожного елемента на певний час.
Concat- Використовується для об’єднання елементів, які випромінює a Publisher, зберігаючи незмінною послідовність видавців.
Merge- Використовується для об'єднання видавців без збереження послідовності.
Zip- Використовується для об’єднання двох або більше видавців шляхом очікування, коли всі джерела видадуть один елемент, і об’єднання цих елементів у вихідне значення.
 */