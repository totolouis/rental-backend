package com.openclassrooms.dto;

import java.time.LocalDateTime;

public class UserMeDTO {

    private Integer id;
        private String name;
        private String email;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    
        public UserMeDTO(Integer id, String name, String email, LocalDateTime createdAt, LocalDateTime updatedAt){
           this.id = id;
            this.name = name;
            this.email = email;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }

        public Integer getId(){
            return id;
        }
    
        public String getName(){
            return name;
        }
    
        public String getEmail(){
            return email;
        }

        public LocalDateTime getCreatedAt(){
            return createdAt;
        }

        public LocalDateTime getUpdatedAt(){
            return updatedAt;
        }
    }
    