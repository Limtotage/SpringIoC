package com.example.springioc.dto;

import lombok.Data;

@Data
public class PaymentDTO {
    private String cardNumber;
    private String expirationDate;
    private String cvc;
    private String cardHolderName;

    private String message;

}
