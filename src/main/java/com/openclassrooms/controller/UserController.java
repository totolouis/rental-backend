package com.openclassrooms.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.configuration.CustomUserDetails;
import com.openclassrooms.model.User;
import com.openclassrooms.service.CustomUserDetailsService;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/user")
public class UserController {

    private final CustomUserDetailsService customUserDetailsService;

    public UserController(CustomUserDetailsService customUserDetailsService){
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CustomUserDetails> getUser(@PathVariable Long userId) {
        CustomUserDetails user = customUserDetailsService.loadUserById(Math.toIntExact(userId));
        return ResponseEntity.ok(user);
    }
    
    
}
