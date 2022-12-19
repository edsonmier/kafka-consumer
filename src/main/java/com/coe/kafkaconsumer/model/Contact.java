package com.coe.kafkaconsumer.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Contact implements Serializable {

    private int contactId;
    private String firstName;
    private String lastName;
    private byte[] profilePhoto;
    private long phoneNumber;


    public Contact(){}
    public Contact(int contactId, String firstName, String lastName, byte[] profilePhoto, long phoneNumber) {
        this.contactId = contactId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePhoto = profilePhoto;
        this.phoneNumber = phoneNumber;
    }

}
