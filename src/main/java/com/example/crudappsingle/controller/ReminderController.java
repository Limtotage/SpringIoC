package com.example.crudappsingle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crudappsingle.entity.Reminder;
import com.example.crudappsingle.service.ReminderService;

@RestController
@RequestMapping("/reminders")
public class ReminderController {
    @Autowired
    private final ReminderService reminderService;

    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }
    @GetMapping
    public List<Reminder> GetAllReminders(){
        return reminderService.GetAllReminders();
    }
    @PostMapping
    public Reminder SaveReminder(@RequestBody Reminder reminder){
        return reminderService.SaveReminder(reminder);
    }
    @PutMapping("/{id}")
    public Reminder UpdateReminder(@PathVariable Long id, @RequestBody Reminder reminder){
        return reminderService.UpdateReminder(id, reminder);
    }
    @DeleteMapping("/{id}")
    public void DeleteReminder(@PathVariable Long id){
        reminderService.DeleteReminder(id);
    }
}
