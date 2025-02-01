package com.openclassrooms.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.openclassrooms.configuration.CustomUserDetails;

@Service
public class JWTService {

        private JwtEncoder jwtEncoder;
        private CustomUserDetailsService userDetailsService;

        public JWTService(JwtEncoder jwtEncoder, CustomUserDetailsService userDetailsService) {
                this.jwtEncoder = jwtEncoder;
                this.userDetailsService = userDetailsService;
        }

        public String generateToken(Authentication authentication) {
                Instant now = Instant.now();
                // Map<String, Object> claims = new HashMap<>();
                // claims.put("id", userDe)
                // Extract CustomUserDetails from Authentication
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

                // Add custom claims
                JwtClaimsSet claims = JwtClaimsSet.builder()
                                .issuer("self")
                                .issuedAt(now)
                                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                                .subject(userDetails.getEmail()) // typically the email
                                .claim("id", userDetails.getId()) // custom claim for user ID
                                .claim("role", "ROLE_USER") // custom claim for role
                                .build();
                JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters
                                .from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
                return this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
        }

        public String generateToken(String email, String password) {
                UserDetails user = this.userDetailsService.loadUserByEmail(email);
                Authentication authentication = new UsernamePasswordAuthenticationToken(user, password);
                // Generate the token based on the authentication object
                return generateToken(authentication);

        }

}