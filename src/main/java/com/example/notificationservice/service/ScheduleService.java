package com.example.notificationservice.service;

import org.springframework.scheduling.annotation.Scheduled;

public interface ScheduleService {
    void scheduleReassignment(String chatId);

    void cancelReassignment(String chatId);

    void checkUnansweredChats();
}
