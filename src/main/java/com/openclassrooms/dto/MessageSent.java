package com.openclassrooms.dto;

public class MessageSent {
    private String message;

    public MessageSent(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
