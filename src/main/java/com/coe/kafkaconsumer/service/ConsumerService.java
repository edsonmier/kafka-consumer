package com.coe.kafkaconsumer.service;

import com.coe.kafkaconsumer.entity.ContactEntity;
import com.coe.kafkaconsumer.model.Contact;
import com.coe.kafkaconsumer.repository.ContactRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ConsumerService {

    @Autowired
    private ContactRepository contactRepository;

    @KafkaListener(topics = "contact-save-topic", groupId = "group-json")
    public void save(ConsumerRecord<Long, Contact> record) throws IOException{
        Contact contact = record.value();
        ContactEntity entity = new ContactEntity(contact);
        contactRepository.save(entity);
    }
}
