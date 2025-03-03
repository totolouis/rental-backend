package com.openclassrooms.mapper;

import com.openclassrooms.dto.UserDTO;
import com.openclassrooms.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    @Mapping(target = "username", source = "username")
    UserDTO fromUser(User user);

    @Mapping(target = "username", source = "username")
    User toUser(UserDTO userDTO);
}