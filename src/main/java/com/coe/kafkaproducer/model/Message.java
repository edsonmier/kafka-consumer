package com.coe.kafkaproducer.model;

import lombok.Data;
import java.util.Date;

@Data
public class Message {

    private int messageId;
    private Contact fromNumber;
    private String messageText;
    private Date sentDatetime;
    private Conversation conversation;

    public Message(){}

    public Message(int messageId, Contact fromNumber, String messageText, Date sentDatetime, Conversation conversation) {
        this.messageId = messageId;
        this.fromNumber = fromNumber;
        this.messageText = messageText;
        this.sentDatetime = sentDatetime;
        this.conversation = conversation;
    }
}
