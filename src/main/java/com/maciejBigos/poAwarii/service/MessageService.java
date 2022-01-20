package com.maciejBigos.poAwarii.service;

import com.maciejBigos.poAwarii.model.Message;
import com.maciejBigos.poAwarii.model.messeges.ResponseMessage;
import com.maciejBigos.poAwarii.repository.MessageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void sendMessage(String sender, String recipient, String content){
        Message message = new Message();
        message.setContent(content);
        message.setRecipient(recipient);
        message.setSender(sender);
        messageRepository.save(message);
    }

    public List<Message> getMessages(String sender,String recipient) {
            return messageRepository.findAllBySenderAndByRecipient(sender,recipient).collect(Collectors.toList());
    }

}
