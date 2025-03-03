package com.openclassrooms.service;

import java.util.Collections;
import java.util.List;

import com.openclassrooms.mapper.MessageMapper;
import com.openclassrooms.mapper.RentalMapper;
import org.mapstruct.factory.Mappers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.dto.MessageDTO;
import com.openclassrooms.model.Message;
import com.openclassrooms.repository.MessageRepository;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<MessageDTO> getAllMessages() {
        MessageMapper mapper = Mappers.getMapper(MessageMapper.class);
        List<Message> messages = (List<Message>) messageRepository.findAll();
        return messages.stream().map(mapper::fromMessage).toList();
    }

    public List<MessageDTO> getAllMessagesFromRental(Long rentalId) {
        MessageMapper mapper = Mappers.getMapper(MessageMapper.class);
        List<Message> messages = (List<Message>) messageRepository.findAllById(Collections.singleton(rentalId));
        return messages.stream().map(mapper::fromMessage).toList();
    }

    public MessageDTO createMessage(MessageDTO messageDTO) {
        MessageMapper mapper = Mappers.getMapper(MessageMapper.class);
        Message message = mapper.toMessage(messageDTO);
        return mapper.fromMessage(messageRepository.save(message));
    }
}
