package com.example.springreactive7.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
public class SecurityConfig {

    /*
        щоб працювати з Reactive Security потрібно добарити бін ReactiveUserDetailsService
        => похоже до UserDetailsService для звичайного не реактів налаштування Spring Security, 
        різниця тільки у тому, що ReactiveUserDetailsService
                public interface ReactiveUserDetailsService {
                    Mono<UserDetails> findByUsername(String username);
                }
                
               
                public interface UserDetailsService {
                    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
                }
               
                
        є реалізація інтерфейсу in memory, щоб показати, що все працює       
     */
    @Bean
    public ReactiveUserDetailsService userDetailsService() {
        /*
        для демонстрації
        в усіх інших випадках у тому числі реальних додатках, це дані з БД
         */
        UserDetails user = User.withUsername("nazar")
                .password(passwordEncoder().encode("pass"))
                .authorities("read")
                .build();

        return new MapReactiveUserDetailsService(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /*
    додаємо авторизацію
    
    раніше потрібно було extends WebSecurityConfigurerAdapter але від вже депрекейтед
    зараз потрібно додати бін SecurityWebFilterChain, де як параметр використовуємо ServerHttpSecurity,
    який візьметься з контексту спрінг
    
    отже у цьому біні, у функціональному стилі додаємо потрібні методи один за одним, де визначаємо у цьому фільтрі
    що, як і які доступа 
    
    у спрінг реактів використовується .authorizeExchange(), де
        .pathMatchers("/demo/**").authenticated() - визначаємо що такі-то ендпоінти мають бути аутентифіковані
        .anyExchange().permitAll() - для всіх інших доступ вільний
    
     */

    // httpBasic
    @Bean
    public SecurityWebFilterChain webFilterChain(ServerHttpSecurity http) {
        return http
                    .httpBasic()
                .and()
                    .authorizeExchange()
                        .pathMatchers("/demo/**").authenticated()
                        .anyExchange().permitAll()
                .and()
                    .build();
    }

    /*
    User Management and Authentication
    
        є багато сторонніх сервісів які можуть надавати різного роду доступів з використанням різних ключів / токенів ...
        
        нариклад KeyCloak / Auth0 / Okta / ...
     */
//    @Bean
//    public SecurityWebFilterChain webFilterChain(ServerHttpSecurity http) {
//        return http
//                    .oauth2ResourceServer(oauth2 -> oauth2.jwt().jwkSetUri("http//some resource that we use as a ACCESS MANAGEMENT"))
//                    .authorizeExchange()
//                        .pathMatchers("/demo/**").authenticated()
//                        .anyExchange().permitAll()
//                .and()
//                    .build();
//    }
}