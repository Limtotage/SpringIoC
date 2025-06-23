package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public IMessageService messageService() {
        return new ChatService();
    }
    @Bean
    public MyApp myApp(IMessageService messageService) {
        return new MyApp(messageService);
    }
}
