package com.openclassrooms.mapper;

import com.openclassrooms.dto.MessageDTO;
import com.openclassrooms.dto.RentalDTO;
import com.openclassrooms.model.Message;
import com.openclassrooms.model.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MessageMapper {

    MessageDTO fromMessage(Message message);

    Message toMessage(MessageDTO messageDTO);
}




