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
import java.util.Date;
import java.util.NoSuchElementException;
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
    public void save(ConsumerRecord<Long, Message> record) throws IOException, NoSuchElementException {
        try{
            Message message = record.value();
            MessageEntity entity = new MessageEntity(message);
            Optional<ContactEntity> contact = contactRepository.findById(message.getFromNumber().getContactId());
            Optional<ConversationEntity> conversation = conversationRepository.findById(message.getConversation().getConversationId());
            entity.setContact(contact.get());
            entity.setConversation(conversation.get());
            entity.setSentDatetime(new Date());
            messageRepository.save(entity);
            System.out.println("Message saved successfully.");
        } catch (Exception e){
            if (e.getClass().equals(NoSuchElementException.class)){
                System.out.println("Specified field doesn't exist in the current context.");
            }
            System.out.println("Couldn't save message.");
        }
    }

    @KafkaListener(topics = "message-update-topic", groupId = "group-json")
    public void update(ConsumerRecord<Long, Message> record) throws IOException{
        try{
            Message message = record.value();
            // Validation to check if element with ID exists in database.
            if (!messageRepository.findById(message.getMessageId()).isEmpty()){
                MessageEntity entity = new MessageEntity(message);
                Optional<ContactEntity> contact = contactRepository.findById((int)(message.getFromNumber().getContactId()));
                Optional<ConversationEntity> conversation = conversationRepository.findById((int)(message.getConversation().getConversationId()));
                entity.setContact(contact.get());
                entity.setConversation(conversation.get());
                messageRepository.save(entity);
                System.out.println("Message updated successfully.");
            } else {
                System.out.println("There isn't a message with the given ID.");
            }
        } catch(Exception e){
            System.out.println("Couldn't update message.");
        }
    }

    @KafkaListener(topics = "message-delete-topic", groupId = "group-json")
    public void delete(ConsumerRecord<Long, Integer> record) throws IOException {
        try{
            messageRepository.deleteById(record.value());
            System.out.println("Message deleted successfully.");
        } catch (Exception e){
            System.out.println("Couldn't delete message.");
        }
    }
}
