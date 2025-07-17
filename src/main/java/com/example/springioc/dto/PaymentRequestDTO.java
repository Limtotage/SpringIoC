package com.example.springioc.dto;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private String cardNumber;
    private String cardHolderName;
    private String expiryMonth;
    private String expiryYear;
    private String cvc;
}
