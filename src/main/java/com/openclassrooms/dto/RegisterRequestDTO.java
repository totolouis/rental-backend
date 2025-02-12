package com.openclassrooms.dto;

public class RegisterRequestDTO extends LoginRequestDTO {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

}