package com.project.owlback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class OwlBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(OwlBackApplication.class, args);
	}

}
