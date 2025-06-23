package com.example;

public class EmailService implements IMessageService {
    @Override
    public String getMessage() {
        return "E-mail Message Sended";
    }
    
}
