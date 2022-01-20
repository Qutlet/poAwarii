package com.maciejBigos.poAwarii.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "messages")
@Table(indexes = {
        @Index(name = "senderIndex" , columnList = "sender"),
        @Index(name = "recipientIndex", columnList = "recipient")
})
public class Message {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String content;
    private String sender;
    private String recipient;
    private final Date creationTime = new Date();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Message(String content, String sender, String recipient) {
        this.content = content;
        this.sender = sender;
        this.recipient = recipient;
    }

    public Message() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    @Override
    public String toString(){
        return "{\"Message\":{"
                + "                        \"content\":\"" + content + "\""
                + ",                         \"sender\":\"" + sender + "\""
                + ",                         \"recipient\":\"" + recipient+ "\""
                + ",                         \"creattionTime\":" + creationTime
                + "}}";
    }

}