package com.openclassrooms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    private String email;
    private String name;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserDTO() {

    }

    public UserDTO(String username, String email, String encryptedPassword) {
        this.name = username;
        this.email = email;
        this.password = encryptedPassword;
    }

    public UserDTO(String username, String email) {
        this.name = username;
        this.email = email;
    }
}
