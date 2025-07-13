package com.throttlex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThrottleXApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThrottleXApplication.class, args);
		System.out.println("🚀 Redis Host: " + System.getenv("SPRING_REDIS_HOST"));

	}

}
	