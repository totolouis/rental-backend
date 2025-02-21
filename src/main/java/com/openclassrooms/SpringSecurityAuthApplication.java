package com.openclassrooms;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.openclassrooms")
@EntityScan(basePackages = "com.openclassrooms.model")
@EnableJpaRepositories(basePackages = "com.openclassrooms.repository")
public class SpringSecurityAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityAuthApplication.class, args);
	}

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
}
