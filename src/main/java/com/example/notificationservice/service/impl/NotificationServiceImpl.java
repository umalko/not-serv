package com.example.notificationservice.service.impl;

import com.example.notificationservice.dto.NotificationDto;
import com.example.notificationservice.model.NotificationType;
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
        sendNotification(NotificationType.NEW, departmentId, chatId);
    }

    @Override
    public void notifyAboutAnsweredChat(String chatId, String departmentId) {
        sendNotification(NotificationType.ANSWERED, departmentId, chatId);
    }

    private void sendNotification(NotificationType notificationType, String departmentId, String chatId) {
        NotificationDto notification = new NotificationDto(notificationType.name(), chatId, departmentId);
        messagingTemplate.convertAndSend("/department/" + departmentId, notification);
    }
}
