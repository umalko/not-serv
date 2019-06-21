package com.hyatt.notificationservice.service;

public interface ScheduleService {

    void scheduleReassignment(String chatId);

    void cancelReassignment(String chatId);

    void checkUnansweredChats();
}
