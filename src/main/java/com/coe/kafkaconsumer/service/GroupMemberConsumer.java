package com.coe.kafkaconsumer.service;

import aj.org.objectweb.asm.TypeReference;
import com.coe.kafkaconsumer.entity.ContactEntity;
import com.coe.kafkaconsumer.entity.ConversationEntity;
import com.coe.kafkaconsumer.entity.GroupMemberEntity;
import com.coe.kafkaconsumer.repository.ContactRepository;
import com.coe.kafkaconsumer.repository.ConversationRepository;
import com.coe.kafkaconsumer.repository.GroupMemberRepository;
import com.coe.kafkaproducer.model.Contact;
import com.coe.kafkaproducer.model.Conversation;
import com.coe.kafkaproducer.model.GroupMember;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.Map;
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
    public void save(ConsumerRecord<Long, GroupMember> record) throws IOException {
        GroupMember groupMember = record.value();
        GroupMemberEntity entity = new GroupMemberEntity(groupMember);
        Optional<ContactEntity> contact = contactRepository.findById(groupMember.getContact().getContactId());
        Optional<ConversationEntity> conversation = conversationRepository.findById(groupMember.getConversation().getConversationId());
        entity.setContact(contact.get());
        entity.setConversation(conversation.get());
        groupMemberRepository.save(entity);
    }

    @KafkaListener(topics = "groupmember-delete-topic", groupId = "group-json")
    public void delete(ConsumerRecord<Long, Integer> record) throws IOException {
        groupMemberRepository.deleteById(record.value());
    }
}
