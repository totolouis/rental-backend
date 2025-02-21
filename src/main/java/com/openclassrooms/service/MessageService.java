package com.openclassrooms.service;

import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.dto.MessageDTO;
import com.openclassrooms.model.Message;
import com.openclassrooms.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    private ModelMapper modelMapper;

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<MessageDTO> getAllMessages() {
        List<Message> messages = (List<Message>) messageRepository.findAll();
        return messages.stream().map(message -> modelMapper.map(message, MessageDTO.class)).toList();
    }

    public List<MessageDTO> getAllMessagesFromRental(Long rentalId) {
        List<Message> messages = (List<Message>) messageRepository.findAllById(Collections.singleton(rentalId));
        return messages.stream().map(message -> modelMapper.map(message, MessageDTO.class)).toList();
    }

    public MessageDTO createMessage(MessageDTO messageDTO) {
        Message message = modelMapper.map(messageDTO, Message.class);
        return modelMapper.map(this.messageRepository.save(message), MessageDTO.class);
    }
}
