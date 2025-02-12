package com.openclassrooms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.dto.CreateMessageRequestDTO;
import com.openclassrooms.dto.MessageSentDTO;
import com.openclassrooms.model.Message;
import com.openclassrooms.service.MessageService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "Get all messages")
    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @Operation(summary = "Create a message")
    @PostMapping
    public ResponseEntity<MessageSentDTO> createMessage(@RequestBody CreateMessageRequestDTO createMessageRequestDTO) {
        Message newMessage = Message.builder()
                .message(createMessageRequestDTO.message)
                .userId(createMessageRequestDTO.user_id)
                .rentalId(createMessageRequestDTO.rental_id)
                .build();
        messageService.createMessage(newMessage);
        return ResponseEntity.ok(new MessageSentDTO(createMessageRequestDTO.message));
    }

}
