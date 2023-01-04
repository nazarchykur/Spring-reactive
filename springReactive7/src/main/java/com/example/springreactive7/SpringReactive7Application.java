package com.example.springreactive7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringReactive7Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringReactive7Application.class, args);
	}

}

/*
		так як ми добавили   implementation 'org.springframework.boot:spring-boot-starter-security'
		то з коробки вже маємо налаштоване секюріті:
			при запуску ап згенерується пароль: Using generated security password: b64c0d21-6d58-4d7b-aa8a-e751e36e4297
			потім при запиті  http://localhost:8096/demo   попросить логін і пароль (img 1)
			якщо зайти через постмен, то там також покаже 401 Unauthorized  (img 2, img 3)
 */
