package com.openclassrooms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.configuration.CustomUserDetails;
import com.openclassrooms.service.CustomUserDetailsService;
import com.openclassrooms.service.JWTService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private JWTService jwtService;
    // private final AuthenticationManager authenticationManager;
	// private final CustomUserDetailsService customUserDetailsService;
    // private final JwtUtil jwtUtil;

	public AuthController(JWTService jwtService) {
		this.jwtService = jwtService;
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
        // Here you can authenticate the user using loginRequest.getEmail() and loginRequest.getPassword()
        // If authentication is successful, generate and return the token
        // For now, assume authentication is handled correctly
		String token = jwtService.generateToken(loginRequest.getEmail(), loginRequest.getPassword());
		return token;
    }

	//     @PostMapping("/login2")
    // public ResponseEntity<String> authenticateUser(@RequestBody LoginRequest loginRequest) {
    //     Authentication authentication = authenticationManager.authenticate(
    //             new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
    //     );

	// 	String token = jwtService.generateToken(loginRequest.getEmail(), loginRequest.getPassword());

    //     return ResponseEntity.ok(jwt);
    // }

}