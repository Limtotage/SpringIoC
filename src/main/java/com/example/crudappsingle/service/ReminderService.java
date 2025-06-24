package com.example.crudappsingle.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.crudappsingle.entity.Reminder;
import com.example.crudappsingle.repository.ReminderDB;

@Service
public class ReminderService {
    private final ReminderDB reminderDB;

    public ReminderService(ReminderDB reminderDB){
        this.reminderDB=reminderDB;
    }
    public List<Reminder> GetAllReminders(){
        return reminderDB.findAll();
    }
    public Reminder SaveReminder(Reminder reminder){
        return reminderDB.save(reminder);
    }
    public Reminder UpdateReminder(Long id,Reminder reminder){
        Reminder newreminder= reminderDB.findById(id).orElseThrow();
        newreminder.setTime(reminder.getTime());
        newreminder.setInfo(reminder.getInfo());
        return reminderDB.save(newreminder);
    }
    public void DeleteReminder(Long id){
        reminderDB.deleteById(id);
    }
}
