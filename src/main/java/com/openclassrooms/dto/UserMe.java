package com.openclassrooms.dto;

import java.time.LocalDateTime;

public class UserMe {

        private String name;
        private String email;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    
        public UserMe(String name, String email, LocalDateTime createdAt, LocalDateTime updatedAt){
            this.name = name;
            this.email = email;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
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
    