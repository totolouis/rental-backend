package com.openclassrooms.service;

import com.openclassrooms.mapper.UserMapper;
import org.mapstruct.factory.Mappers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        UserMapper mapper = Mappers.getMapper(UserMapper.class);
        User user = userRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new UsernameNotFoundException("User not found for specified id"));
        return mapper.fromUser(user);
    }

    public UserDTO registerUser(UserDTO userDTO) {
        UserMapper mapper = Mappers.getMapper(UserMapper.class);
        User user = mapper.toUser(userDTO);
        checkIfEmailIsTaken(user.getEmail());
        User savedUser = userRepository.save(user);
        return mapper.fromUser(savedUser);
    }

    private void checkIfEmailIsTaken(String email) {
        if (this.userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("Email already taken");
        }
    }

}
