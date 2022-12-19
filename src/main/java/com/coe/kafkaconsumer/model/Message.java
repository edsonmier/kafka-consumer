package com.coe.kafkaconsumer.model;

import lombok.Data;
import java.util.Date;

@Data
public class Message {

    private int messageId;
    private long fromNumber;
    private String messageText;
    private Date sentDatetime;
    private Conversation conversation;


    public Message(){}
    public Message(int messageId, long fromNumber, String messageText, Date sentDatetime, Conversation conversation) {
        this.messageId = messageId;
        this.fromNumber = fromNumber;
        this.messageText = messageText;
        this.sentDatetime = sentDatetime;
        this.conversation = conversation;
    }

}
