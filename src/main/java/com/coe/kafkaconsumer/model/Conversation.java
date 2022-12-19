package com.coe.kafkaconsumer.model;

import lombok.Data;

@Data
public class Conversation {

    private int conversationId;
    private String conversationName;


    public Conversation(){}
    public Conversation(int conversationId, String conversationName) {
        this.conversationId = conversationId;
        this.conversationName = conversationName;
    }

}
