package com.example.springioc.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "generalID")
public class GeneralID {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String surName;
    private String name;

    public Long getId() {
        return id;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurname(String surName) {
        this.surName = surName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    /*
    private Long generalIDNo;

    public Long getGeneralIDNo() {
        return generalIDNo;
    }

    public void setGeneralIDNo(Long generalIDNo) {
        this.generalIDNo = generalIDNo;
    }
    */
}
