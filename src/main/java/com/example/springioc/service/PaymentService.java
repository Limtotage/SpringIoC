package com.example.springioc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springioc.entity.Payment;
import com.example.springioc.repository.PaymentRepo;

@Service
public class PaymentService {
    
    @Autowired
    private PaymentRepo paymentDB;

    public Payment SavePayment(Payment entity){
        return paymentDB.save(entity);
    }
}
