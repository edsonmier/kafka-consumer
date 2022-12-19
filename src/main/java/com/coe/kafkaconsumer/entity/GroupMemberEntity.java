package com.coe.kafkaconsumer.entity;

import lombok.Data;
import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "group_member")
@Data
public class GroupMemberEntity {

    @Id
    @Column(name = "group_member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private ContactEntity contact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id")
    private ConversationEntity conversation;

    @Column(name = "joined_datetime")
    private Date joinedDatetime;

    @Column(name = "left_datetime")
    private Date leftDatetime;


    public GroupMemberEntity(){}

    public GroupMemberEntity(int groupMemberId, ContactEntity contact, ConversationEntity conversation, Date joinedDatetime, Date leftDatetime) {
        this.groupMemberId = groupMemberId;
        this.contact = contact;
        this.conversation = conversation;
        this.joinedDatetime = joinedDatetime;
        this.leftDatetime = leftDatetime;
    }
}