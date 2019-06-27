package com.hyatt.notificationservice.service;

import com.hyatt.notificationservice.service.impl.ScheduleServiceImpl;

/**
 * Schedule Service is used for chat events of webSocket.
 */
public interface ScheduleService {

    /**
     * Save chat info for further process (reassignment to the default department 'Omaha')
     * @param chatId chat id
     */
    void scheduleReassignment(String chatId);

    /**
     * Delete chat info due to chat was answered
     * @param chatId chat id
     */
    void cancelReassignment(String chatId);

    /**
     * Scheduled method!! See in implementation: {@link ScheduleServiceImpl#checkUnansweredChats()}
     * The following steps:
     * 1. Checks unanswered chats. If chat wasn't answered in configurable time(20s).
     * 2. Remove from db
     * 3. send to the default department(Omaha). Link of the url is configurable. Env: 'app.omahaDepartmentUrl'
     */
    void checkUnansweredChats();
}
