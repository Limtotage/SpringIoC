package com.example.springioc.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PaymentResponseDTO {
    public class PaymentResponse {
    private String message;
    private LocalDateTime paymentDate;
    private Double totalPaid;
}
}
