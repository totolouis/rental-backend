package com.openclassrooms.dto;

public class MessageSentDTO {
    private String message;

    public MessageSentDTO(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
