package com.openclassrooms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.dto.CreateMessageRequestDTO;
import com.openclassrooms.model.Message;
import com.openclassrooms.service.MessageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @PostMapping
    public ResponseEntity<?> createMessag(@RequestBody CreateMessageRequestDTO createMessageRequestDTO) {
        Message newMessage = Message.builder()
        .message(createMessageRequestDTO.message)
        .userId(createMessageRequestDTO.userId)
        .rentalId(createMessageRequestDTO.rentalId)
        .build();
        messageService.createMessage(newMessage);
        return ResponseEntity.ok(newMessage);        
    }
    
}
