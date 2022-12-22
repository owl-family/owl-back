package com.project.owlback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OwlBackApplication {
	public static void main(String[] args) {
		SpringApplication.run(OwlBackApplication.class, args);
	}
}
