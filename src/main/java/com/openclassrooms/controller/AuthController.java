package com.openclassrooms.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.jaas.LoginExceptionResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.openclassrooms.configuration.CustomUserDetails;
import com.openclassrooms.model.User;
import com.openclassrooms.repository.UserRepository;
import com.openclassrooms.service.CustomUserDetailsService;
import com.openclassrooms.service.JWTService;

//TODO: swagger
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private JWTService jwtService;
	private final CustomUserDetailsService userService; // private final AuthenticationManager authenticationManager;
	// private final CustomUserDetailsService customUserDetailsService;
	// private final JwtUtil jwtUtil;
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	public AuthController(JWTService jwtService, CustomUserDetailsService userService, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		this.jwtService = jwtService;
		this.userService = userService;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		// this.authenticationManager = authenticationManager;
		// this.customUserDetailsService = customUserDetailsService;
	}

	// Define a DTO (Data Transfer Object) to represent the login request 
	// TODO: put it somewhere else
	public static class LoginRequest {
		private String email;
		private String password;

		// Getters and setters
		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

	public static class AuthSuccess {
		private String token;

		public String getToken() {
			return token;
		}

		public void setToken(String token){
			this.token = token;
		}
	}

	public static class RegisterRequest extends LoginRequest {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name){
			this.name = name;
		}

	}
	// TODO: better handling of the AuthSuccess dto. A Dto is not neccesary and the return is dirty
	// ! Redo
	@PostMapping("/login")
	public AuthSuccess getToken(@RequestBody LoginRequest loginRequest) {
		String token = jwtService.generateToken(loginRequest.getEmail(), loginRequest.getPassword());
		AuthSuccess authSuccess = new AuthSuccess();
		authSuccess.setToken(token);
		return authSuccess;
	}

	@GetMapping("/me")
	public ResponseEntity<?> getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication.getPrincipal() instanceof Jwt)) {
			return ResponseEntity.internalServerError().build();
		}
		Jwt jwt = (Jwt) authentication.getPrincipal();
		Integer id = Math.toIntExact(jwt.getClaim("id"));
		CustomUserDetails user = userService.loadUserById(id);
		Map<String, Object> userInfo = Map.of(
				"id", user.getId(),
				"username", user.getUsername(),
				"email", user.getEmail(),
				"createdAt", user.getCreatedDateTime(),
				"updatedAt",user.getUpdatedDateTime());
		return ResponseEntity.ok(userInfo);
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
		if (this.userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists.");
		}
		String encryptedPassword = this.passwordEncoder.encode(registerRequest.getPassword());
		User newUser = new User(null, registerRequest.getEmail(), registerRequest.getName(), encryptedPassword, null,
				null);
		userRepository.save(newUser);

		

		String token = jwtService.generateToken(newUser.getEmail(), newUser.getPassword());
		AuthSuccess authSuccess = new AuthSuccess();
		authSuccess.setToken(token);

		return ResponseEntity.status(HttpStatus.CREATED).body(authSuccess);
	}
	
}