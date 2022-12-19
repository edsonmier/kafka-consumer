package com.coe.kafkaconsumer.entity;

import lombok.Data;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "conversation")
@Data
public class ConversationEntity {

    @Id
    @Column(name = "conversation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int conversationId;

    @Column(name = "conversation_name")
    private String conversationName;

    @OneToMany(mappedBy = "conversation")
    private Set<GroupMemberEntity> groupMemberSet;

    @OneToMany(mappedBy = "conversation")
    private Set<MessageEntity> messages;


    public ConversationEntity(){}
    public ConversationEntity(int conversationId, String conversationName) {
        this.conversationId = conversationId;
        this.conversationName = conversationName;
    }

}
