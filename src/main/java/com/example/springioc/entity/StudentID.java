package com.example.springioc.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="StudentID")
public class StudentID {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;


    @OneToOne(cascade=CascadeType.ALL , orphanRemoval=true)
    @JoinColumn(name="GeneralID",referencedColumnName="id")
    @JsonManagedReference
    private GeneralID generalID;


    public Long getId() {
        return id;
    }


    public GeneralID getGeneralID() {
        return generalID;
    }


    public void setGeneralID(GeneralID generalID) {
        this.generalID = generalID;
    }

}
