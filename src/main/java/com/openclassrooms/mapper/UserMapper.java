package com.openclassrooms.mapper;

import com.openclassrooms.dto.UserDTO;
import com.openclassrooms.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    @Mapping(target = "name", source = "username")
    @Mapping(target = "id", source = "id")
    UserDTO fromUser(User user);

    @Mapping(target = "username", source = "name")
    User toUser(UserDTO userDTO);
}