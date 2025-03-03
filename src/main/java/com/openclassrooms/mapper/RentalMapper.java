package com.openclassrooms.mapper;

import com.openclassrooms.dto.RentalDTO;
import com.openclassrooms.dto.UserDTO;
import com.openclassrooms.model.Rental;
import com.openclassrooms.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RentalMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "ownerId", source = "ownerId")
    RentalDTO fromRental(Rental rental);

    @Mapping(target = "name", source = "name")
    Rental toRental(RentalDTO rentalDTO);
}




