package com.demo.weblinkscraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AutoConfigurationPackage
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
