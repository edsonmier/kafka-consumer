package com.coe.kafkaconsumer.entity;

import com.coe.kafkaproducer.model.GroupMember;
import com.coe.kafkaproducer.model.Message;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "message")
@Data
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private int messageId;

    @ManyToOne
    @JoinColumn(name = "from_contact")
    private ContactEntity contact;

    @Column(name = "message_text")
    private String messageText;

    @Column(name = "sent_datetime")
    private Date sentDatetime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id")
    private ConversationEntity conversation;


    public MessageEntity(){}

    public MessageEntity(int messageId, ContactEntity contact, String messageText, Date sentDatetime, ConversationEntity conversation) {
        this.messageId = messageId;
        this.contact = contact;
        this.messageText = messageText;
        this.sentDatetime = sentDatetime;
        this.conversation = conversation;
    }

    public MessageEntity(Message message) {
        this.messageId = message.getMessageId();
        this.messageText = message.getMessageText();
        this.sentDatetime = message.getSentDatetime();
    }
}
