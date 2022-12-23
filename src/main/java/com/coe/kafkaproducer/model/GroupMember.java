package com.coe.kafkaproducer.model;

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

    public GroupMember(Contact contact, Conversation conversation, Date joinedDatetime, Date leftDatetime) {
        this.contact = contact;
        this.conversation = conversation;
        this.joinedDatetime = joinedDatetime;
        this.leftDatetime = leftDatetime;
    }
}