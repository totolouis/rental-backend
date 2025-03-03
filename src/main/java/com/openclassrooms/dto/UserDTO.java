package com.openclassrooms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    private String email;
    private String username;
    private String password;

    public UserDTO() {

    }

    public UserDTO(String username, String email, String encryptedPassword) {
        this.username = username;
        this.email = email;
        this.password = encryptedPassword;
    }

    public UserDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Integer getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
