package com.openclassrooms.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.configuration.CustomUserDetails;
import com.openclassrooms.service.CustomUserDetailsService;
import com.openclassrooms.service.JWTService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private JWTService jwtService;
	private final CustomUserDetailsService userService; // private final AuthenticationManager authenticationManager;
	// private final CustomUserDetailsService customUserDetailsService;
	// private final JwtUtil jwtUtil;

	public AuthController(JWTService jwtService, CustomUserDetailsService userService) {
		this.jwtService = jwtService;
		this.userService = userService;
		// this.authenticationManager = authenticationManager;
		// this.customUserDetailsService = customUserDetailsService;
	}

	// Define a DTO (Data Transfer Object) to represent the login request
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

	@PostMapping("/login")
	public String getToken(@RequestBody LoginRequest loginRequest) {
		// Here you can authenticate the user using loginRequest.getEmail() and
		// loginRequest.getPassword()
		// If authentication is successful, generate and return the token
		// For now, assume authentication is handled correctly
		String token = jwtService.generateToken(loginRequest.getEmail(), loginRequest.getPassword());
		return token;
	}

	@GetMapping("/me")
	public ResponseEntity<?> getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Le front y accede de maniere bizarre. Il faut que je regarde pour mieux le faire car la dans letat cel ane fonctionne pas
		if (!(authentication.getPrincipal() instanceof Jwt)) {
			throw new RuntimeException("Unsupported principal type: " + authentication.getPrincipal().getClass());
		}
		Jwt jwt = (Jwt) authentication.getPrincipal();
		Integer id = Math.toIntExact(jwt.getClaim("id"));
		CustomUserDetails user = userService.loadUserById(id);
		Map<String, Object> userInfo = Map.of(
				"id", user.getId(),
				"username", user.getUsername(),
				"email", user.getEmail(),
				// TODO: remove this. its bc old user created manually. Have to do the register
				// yet.
				"createdAt", (user.getCreatedDateTime() != null) ? user.getCreatedDateTime() : LocalDateTime.now(),
				"updatedAt", (user.getUpdatedDateTime() != null) ? user.getUpdatedDateTime() : LocalDateTime.now());
		return ResponseEntity.ok(userInfo);
	}

	// @PostMapping("/login2")
	// public ResponseEntity<String> authenticateUser(@RequestBody LoginRequest
	// loginRequest) {
	// Authentication authentication = authenticationManager.authenticate(
	// new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
	// loginRequest.getPassword())
	// );

	// String token = jwtService.generateToken(loginRequest.getEmail(),
	// loginRequest.getPassword());

	// return ResponseEntity.ok(jwt);
	// }

}