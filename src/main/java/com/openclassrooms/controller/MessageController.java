package com.openclassrooms.controller;

import com.openclassrooms.dto.CreateMessageRequestDTO;
import com.openclassrooms.dto.MessageSentDTO;
import com.openclassrooms.model.Message;
import com.openclassrooms.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




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

    //TODO: could do a check of existing user & rental in the service... will see if i got time
    @Operation(summary = "Create a message")
    @PostMapping
    public ResponseEntity<MessageSentDTO> createMessage(@RequestBody CreateMessageRequestDTO createMessageRequestDTO) {
        Message newMessage = new Message(createMessageRequestDTO.rental_id, createMessageRequestDTO.user_id,
                createMessageRequestDTO.message);
        messageService.createMessage(newMessage);
        return ResponseEntity.ok(new MessageSentDTO(createMessageRequestDTO.message));
    }

}
