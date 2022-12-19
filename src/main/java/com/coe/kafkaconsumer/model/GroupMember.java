package com.coe.kafkaconsumer.model;

import lombok.Data;
import java.util.Date;

@Data
public class GroupMember {

    private int groupMemberId;
    private Contact contact;
    private Conversation conversation;
    private Date joinedDatetime;
    private Date leftDatetime;


    public GroupMember(){}
    public GroupMember(Contact contactId, Conversation conversationId, Date joinedDatetime, Date leftDatetime) {
        this.contact = contactId;
        this.conversation = conversationId;
        this.joinedDatetime = joinedDatetime;
        this.leftDatetime = leftDatetime;
    }

}