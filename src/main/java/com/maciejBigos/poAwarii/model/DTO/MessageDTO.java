package com.maciejBigos.poAwarii.model.DTO;

public class MessageDTO {

    public MessageDTO() {
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageDTO(String content) {
        this.message = content;
    }
}
