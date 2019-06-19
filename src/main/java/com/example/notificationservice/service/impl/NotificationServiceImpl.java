package com.example.notificationservice.service.impl;

import com.example.notificationservice.model.Notification;
import com.example.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    public void notifyAboutNewChat(String chatId, String departmentId) {
        sendNotification("new.chat", departmentId, chatId);
    }

    @Override
    public void notifyAboutAnsweredChat(String chatId, String departmentId) {
        sendNotification("chat.is.answered", departmentId, chatId);
    }

    private void sendNotification(String notificationType, String departmentId, String chatId) {
        Notification notification = new Notification(notificationType, chatId, departmentId);
        messagingTemplate.convertAndSend("/department/" + departmentId, notification);
    }
}
