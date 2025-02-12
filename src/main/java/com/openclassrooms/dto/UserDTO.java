package com.openclassrooms.dto;

public class UserDTO {
    private String name;
    private String email;
    private Integer id;

    public UserDTO(Integer id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public Integer getId(){
        return id;
    }
}
