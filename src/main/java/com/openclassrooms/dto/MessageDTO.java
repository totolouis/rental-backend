package com.openclassrooms.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MessageDTO {
    private String message;
    private Integer rentalId;
    private Integer userId;

    public MessageDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Integer getRentalId() {
        return rentalId;
    }

    public Integer getUserId() {
        return userId;
    }
}
