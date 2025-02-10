package com.openclassrooms.dto;

public class RegisterRequest extends LoginRequest {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

}