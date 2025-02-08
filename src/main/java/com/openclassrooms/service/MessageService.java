package com.openclassrooms.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.model.Message;
import com.openclassrooms.repository.MessageRepository;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public List<Message> getAllMessages() {
        return (List<Message>) messageRepository.findAll();
    }

    public List<Message> getAllMessagesFromRental(Long rentalId) {
        return (List<Message>)messageRepository.findAllById(Collections.singleton(rentalId));
    }

    public Message createMessage(Message message){
        return this.messageRepository.save(message);
    }
}
