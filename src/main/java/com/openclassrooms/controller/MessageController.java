package com.openclassrooms.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.dto.MessageDTO;
import com.openclassrooms.service.MessageService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/messages")
@SecurityRequirement(name = "bearerAuth")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "Get all messages")
    @GetMapping
    public ResponseEntity<List<MessageDTO>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    // TODO: could do a check of existing user & rental in the service... will see
    // if i got time
    @Operation(summary = "Create a message")
    @PostMapping
    public ResponseEntity<MessageDTO> createMessage(@RequestBody MessageDTO messageDTO) {
        return ResponseEntity.ok(messageService.createMessage(messageDTO));
    }

}
