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
        try{
            Conversation conversation = record.value();
            ConversationEntity entity = new ConversationEntity(conversation);
            conversationRepository.save(entity);
            System.out.println("Conversation saved successfully.");
        } catch(Exception e){
            System.out.println("Couldn't save conversation");
        }
    }

    @KafkaListener(topics = "conversation-update-topic", groupId = "group-json")
    public void update(ConsumerRecord<Long, Conversation> record) throws IOException{
        try{
            Conversation conversation = record.value();
            // Validation to check if element with ID exists in database.
            if (!conversationRepository.findById(conversation.getConversationId()).isEmpty()){
                ConversationEntity entity = new ConversationEntity(conversation);
                conversationRepository.save(entity);
                System.out.println("Conversation updated successfully.");
            } else {
                System.out.println("There isn't a conversation with the given ID.");
            }
        } catch(Exception e){
            System.out.println("Couldn't update conversation.");
        }
    }

    @KafkaListener(topics = "conversation-delete-topic", groupId = "group-json")
    public void delete(ConsumerRecord<Long, Integer> record) throws IOException {
        try{
            conversationRepository.deleteById(record.value());
            System.out.println("Conversation deleted successfully.");
        } catch (Exception e){
            System.out.println("Couldn't delete conversation.");
        }
    }
}
