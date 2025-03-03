package com.openclassrooms.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MessageDTO {
    private String message;
    private Integer rentalId;
    private Integer userId;

    public MessageDTO(){}

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

    public void setMessage(String message){
        this.message = message;
    }
    public void setRentalId(Integer rentalId){
        this.rentalId = rentalId;
    }
    public void setUserId(Integer userId){
        this.userId = userId;
    }
}
