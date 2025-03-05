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

    public String getName() {
        return name;
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

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public LocalDateTime getUpdatedAt(){
        return updatedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt){
        this.updatedAt = updatedAt;
    }
}
