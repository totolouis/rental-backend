package com.openclassrooms;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.controller.AuthController;

@SpringBootTest
class SpringSecurityAuthApplicationTests {
	
	@Autowired
	private AuthController loginController;

	@Test
	void contextLoads() {
		
	}

}