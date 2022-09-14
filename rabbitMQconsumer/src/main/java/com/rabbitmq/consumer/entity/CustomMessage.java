package com.rabbitmq.consumer.entity;


public class CustomMessage {
    private String messageId;
    private String messageDate;
    private String message;

    public CustomMessage(String messageId, String messageDate, String message) {
        this.messageId = messageId;
        this.messageDate = messageDate;
        this.message = message;
    }

    public CustomMessage() {
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CustomMessage{" +
                "messageId='" + messageId + '\'' +
                ", messageDate='" + messageDate + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
