package com.hyatt.notificationservice.service;

/**
 * Notification Service is used for chat events of webSocket.
 */
public interface NotificationService {

    /**
     * Notify About New Chat. Send chat id and department id to the chat bots
     * @param chatId chat id
     * @param departmentId department Id
     */
    void notifyAboutNewChat(String chatId, String departmentId);

    /**
     * Notify About Answered Chat. Send chat id and department id to the chat bots
     * @param chatId chat id
     * @param departmentId department Id
     */
    void notifyAboutAnsweredChat(String chatId, String departmentId);
}
