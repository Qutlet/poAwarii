package com.maciejBigos.poAwarii.controller;

import com.maciejBigos.poAwarii.model.Message;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Component
@Controller
@CrossOrigin
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        this.messagingTemplate.setMessageConverter(new SimpleMessageConverter());
    }

    @MessageMapping("/message/{room}")
    @SendTo("/topic/messages/{room}")
    public Message sendMessage(final Message message) {
        messagingTemplate.convertAndSend("/topic/messages/" + message.getRecipient(), message.toString().getBytes());
        return message;
    }
}
