package com.coe.kafkaconsumer.entity;

import com.coe.kafkaproducer.model.Contact;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "contact")
@Data
public class ContactEntity {
    @Id
    @Column(name = "contact_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contactId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "profile_photo")
    private byte[] profilePhoto;

    @Column(name = "phone_number")
    private long phoneNumber;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "contact")
    private Set<GroupMemberEntity> groupMemberSet;

    @OneToMany(mappedBy = "contact")
    private Set<MessageEntity> messageEntitySet;


    public ContactEntity(){

    }

    public ContactEntity(int contactId, String firstName, String lastName, byte[] profilePhoto, long phoneNumber, String status) {
        this.contactId = contactId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePhoto = profilePhoto;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public ContactEntity(Contact contact) {
        this.contactId = contact.getContactId();
        this.firstName = contact.getFirstName();
        this.lastName = contact.getLastName();
        this.profilePhoto = contact.getProfilePhoto();
        this.phoneNumber = contact.getPhoneNumber();
        this.status = contact.getStatus();
    }
}
