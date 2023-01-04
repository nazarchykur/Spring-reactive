package com.example.springreactive7.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
}