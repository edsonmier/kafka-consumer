package com.coe.kafkaconsumer.service;

import com.coe.kafkaconsumer.entity.ContactEntity;
import com.coe.kafkaconsumer.entity.ConversationEntity;
import com.coe.kafkaconsumer.entity.GroupMemberEntity;
import com.coe.kafkaconsumer.entity.MessageEntity;
import com.coe.kafkaconsumer.repository.ContactRepository;
import com.coe.kafkaconsumer.repository.ConversationRepository;
import com.coe.kafkaconsumer.repository.MessageRepository;
import com.coe.kafkaproducer.model.GroupMember;
import com.coe.kafkaproducer.model.Message;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class MessageConsumer {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @KafkaListener(topics = "message-save-topic", groupId = "group-json")
    public void save(ConsumerRecord<Long, Message> record) throws IOException {
        Message message = record.value();
        MessageEntity entity = new MessageEntity(message);
        Optional<ContactEntity> contact = contactRepository.findById(message.getFromNumber().getContactId());
        Optional<ConversationEntity> conversation = conversationRepository.findById(message.getConversation().getConversationId());
        entity.setContact(contact.get());
        entity.setConversation(conversation.get());
        messageRepository.save(entity);
    }

    @KafkaListener(topics = "message-delete-topic", groupId = "group-json")
    public void delete(ConsumerRecord<Long, Integer> record) throws IOException {
        messageRepository.deleteById(record.value());
    }
}
