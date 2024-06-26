package com.ford;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(value = "com.*")
@EnableAutoConfiguration
public class DTApplication {

	public static void main(String[] args) {
		SpringApplication.run(DTApplication.class, args);
	}

}
