package com.example.notificationservice.service;

public interface NotificationService {

    void notifyAboutNewChat(String chatId, String departmentId);

    void notifyAboutAnsweredChat(String chatId, String departmentId);
}
