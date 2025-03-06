package com.openclassrooms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.dto.AuthSuccessDTO;
import com.openclassrooms.dto.LoginRequestDTO;
import com.openclassrooms.dto.RegisterRequestDTO;
import com.openclassrooms.dto.UserDTO;
import com.openclassrooms.service.CustomUserDetailsService;
import com.openclassrooms.service.JWTService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private JWTService jwtService;
	private final CustomUserDetailsService userService;
	private final BCryptPasswordEncoder passwordEncoder;

	public AuthController(JWTService jwtService, CustomUserDetailsService userService,
			BCryptPasswordEncoder passwordEncoder) {
		this.jwtService = jwtService;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@Operation(summary = "Authenticate user and return token")
	@PostMapping("/login")
	public AuthSuccessDTO getToken(@RequestBody LoginRequestDTO loginRequest) {
		String token = jwtService.generateToken(loginRequest.getEmail(), loginRequest.getPassword());
		AuthSuccessDTO authSuccess = new AuthSuccessDTO();
		authSuccess.setToken(token);
		return authSuccess;
	}

	@Operation(summary = "Return authenticated user information")
	@GetMapping("/me")
	public ResponseEntity<UserDTO> getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication.getPrincipal() instanceof Jwt)) {
			return ResponseEntity.internalServerError().build();
		}
		Jwt jwt = (Jwt) authentication.getPrincipal();
		Integer id = Math.toIntExact(jwt.getClaim("id"));
		UserDTO user = userService.loadUserById(id);

		return ResponseEntity.ok(user);
	}

	@Operation(summary = "Register a new user")
	@PostMapping("/register")
	public ResponseEntity<AuthSuccessDTO> registerUser(@RequestBody RegisterRequestDTO registerRequest) {
		UserDTO newUser = createUser(registerRequest);
		AuthSuccessDTO authSuccess = createToken(newUser);

		return ResponseEntity.status(HttpStatus.CREATED).body(authSuccess);
	}

	private UserDTO createUser(RegisterRequestDTO registerRequest) {
		String encryptedPassword = this.passwordEncoder.encode(registerRequest.getPassword());
		UserDTO userDTO = new UserDTO(registerRequest.getName(), registerRequest.getEmail(), encryptedPassword);
		UserDTO savedUser = this.userService.registerUser(userDTO);
		return savedUser;
	}

	private AuthSuccessDTO createToken(UserDTO newUser) {
		String token = jwtService.generateToken(newUser.getEmail(), newUser.getPassword());
		AuthSuccessDTO authSuccess = new AuthSuccessDTO();
		authSuccess.setToken(token);
		return authSuccess;
	}

}