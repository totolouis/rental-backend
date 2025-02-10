package com.openclassrooms.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.configuration.CustomUserDetails;
import com.openclassrooms.service.CustomUserDetailsService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final CustomUserDetailsService customUserDetailsService;

    public UserController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        CustomUserDetails user = customUserDetailsService.loadUserById(Math.toIntExact(userId));
        Map<String, Object> userInfo = Map.of(
                "id", user.getId(),
                "name", user.getUsername(),
                "email", user.getEmail(),
                "created_at", user.getCreatedDateTime(),
                "updated_at", user.getUpdatedDateTime());
        return ResponseEntity.ok(userInfo);
    }

}
