package com.example;


public class MyApp 
{
    private IMessageService messageService;

    public MyApp(IMessageService messageService) {
        this.messageService = messageService;
    }
    public void ProcessMessage() {
        System.out.println(messageService.getMessage());
    }
}
