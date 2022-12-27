package com.coe.kafkaconsumer.service;

import com.coe.kafkaconsumer.entity.ContactEntity;
import com.coe.kafkaconsumer.entity.ConversationEntity;
import com.coe.kafkaconsumer.entity.GroupMemberEntity;
import com.coe.kafkaconsumer.repository.ContactRepository;
import com.coe.kafkaconsumer.repository.ConversationRepository;
import com.coe.kafkaconsumer.repository.GroupMemberRepository;
import com.coe.kafkaproducer.model.Conversation;
import com.coe.kafkaproducer.model.GroupMember;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class GroupMemberConsumer {
    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @KafkaListener(topics = "groupmember-save-topic", groupId = "group-json")
    public void save(ConsumerRecord<Long, GroupMember> record) throws IOException, NoSuchElementException {
        try{
            GroupMember groupMember = record.value();
            GroupMemberEntity entity = new GroupMemberEntity(groupMember);
            Optional<ContactEntity> contact = contactRepository.findById(groupMember.getContact().getContactId());
            Optional<ConversationEntity> conversation = conversationRepository.findById(groupMember.getConversation().getConversationId());
            entity.setContact(contact.get());
            entity.setConversation(conversation.get());
            groupMemberRepository.save(entity);
            System.out.println("Group member saved correctly.");
        } catch (Exception e){
            if (e.getClass().equals(NoSuchElementException.class)){
                System.out.println("Specified field doesn't exist in the current context.");
            }
            System.out.println("Couldn't save group member.");
        }
    }

    @KafkaListener(topics = "groupmember-update-topic", groupId = "group-json")
    public void update(ConsumerRecord<Long, GroupMember> record) throws IOException{
        try{
            GroupMember groupMember = record.value();
            // Validation to check if element with ID exists in database.
            if (!groupMemberRepository.findById(groupMember.getGroupMemberId()).isEmpty()){
                GroupMemberEntity entity = new GroupMemberEntity(groupMember);
                Optional<ContactEntity> contact = contactRepository.findById((int)(groupMember.getContact().getContactId()));
                Optional<ConversationEntity> conversation = conversationRepository.findById((int)(groupMember.getConversation().getConversationId()));
                entity.setContact(contact.get());
                entity.setConversation(conversation.get());
                groupMemberRepository.save(entity);
                System.out.println("Group member updated successfully.");
            } else {
                System.out.println("There isn't a group member with the given ID.");
            }
        } catch(Exception e){
            System.out.println("Couldn't update group member.");
        }
    }

    @KafkaListener(topics = "groupmember-delete-topic", groupId = "group-json")
    public void delete(ConsumerRecord<Long, Integer> record) throws IOException {
        try{
            groupMemberRepository.deleteById(record.value());
            System.out.println("Group member deleted successfully.");
        } catch (Exception e){
            System.out.println("Couldn't delete group member.");
        }
    }
}
