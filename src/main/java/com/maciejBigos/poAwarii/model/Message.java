package com.maciejBigos.poAwarii.model;

import javax.persistence.Id;
import java.util.Date;

public class Message {
    private String content;
    private String sender;
    private String recipient;
    private final Date creationTime = new Date();

    public Message(String content, String sender, String recipient) {
        this.content = content;
        this.sender = sender;
        this.recipient = recipient;
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

    /**
     * returns string representation of a message
     * @return
     */
    @Override
    public String toString(){
        return "{\"Message\":{"
                + "                        \"content\":\"" + content + "\""
                + ",                         \"sender\":\"" + sender + "\""
                + ",                         \"recipient\":\"" + recipient+ "\""
                + ",                         \"creattionTime\":" + creationTime
                + "}}";
    }

    public enum MessageType{
        CHAT, LEAVE, JOIN
    }
}