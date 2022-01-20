package com.maciejBigos.poAwarii.controller;

import com.maciejBigos.poAwarii.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/messages")
public class ChatController {

    private final MessageService messageService;

    public ChatController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public void sendMessage(@RequestParam String sender, @RequestParam String recipient,
                            @RequestBody String content, Authentication authentication) {
        messageService.sendMessage(sender, recipient, content);
    }

    @GetMapping
    public ResponseEntity<?> getMessages(@RequestParam String sender, @RequestParam String recipient,
                                         Authentication authentication) {
        return ResponseEntity.ok(messageService.getMessages(sender, recipient));
    }
}
