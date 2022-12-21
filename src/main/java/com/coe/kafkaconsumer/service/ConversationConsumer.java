package com.coe.kafkaconsumer.service;

import com.coe.kafkaconsumer.entity.ContactEntity;
import com.coe.kafkaconsumer.entity.ConversationEntity;
import com.coe.kafkaconsumer.repository.ConversationRepository;
import com.coe.kafkaproducer.model.Contact;
import com.coe.kafkaproducer.model.Conversation;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ConversationConsumer {
    @Autowired
    private ConversationRepository conversationRepository;

    @KafkaListener(topics = "conversation-save-topic", groupId = "group-json")
    public void save(ConsumerRecord<Long, Conversation> record) throws IOException {
        Conversation conversation = record.value();
        ConversationEntity entity = new ConversationEntity(conversation);
        conversationRepository.save(entity);
    }

    @KafkaListener(topics = "conversation-delete-topic", groupId = "group-json")
    public void delete(ConsumerRecord<Long, Integer> record) throws IOException {
        conversationRepository.deleteById(record.value());
    }
}
