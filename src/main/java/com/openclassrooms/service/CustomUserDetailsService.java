package com.openclassrooms.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.configuration.CustomUserDetails;
import com.openclassrooms.model.User;
import com.openclassrooms.repository.UserRepository;
import com.openclassrooms.configuration.exceptions.*;
import com.openclassrooms.dto.UserDTO;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found for username:" + username));

        return new CustomUserDetails(user);
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found for email:" + email));

        return new CustomUserDetails(user);
    }

    public UserDTO loadUserById(Integer id) throws UsernameNotFoundException {
        User user = userRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new UsernameNotFoundException("User not found for specified id"));
        CustomUserDetails user2 = new CustomUserDetails(user);
        return modelMapper.map(user2, UserDTO.class);
    }

    public UserDTO registerUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        checkIfEmailIsTaken(user.getEmail());
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    private void checkIfEmailIsTaken(String email) {
        if (this.userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("Email already taken");
        }
    }

}
