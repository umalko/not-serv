package com.hyatt.notificationservice.service;

import com.hyatt.notificationservice.dto.NotificationDto;
import com.hyatt.notificationservice.model.NotificationType;
import com.hyatt.notificationservice.service.impl.NotificationServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceTest {

    private static final String CHAT_ID = "CHAT_ID";
    private static final String DEPARTMENT_ID = "DEPARTMENT_ID";

    @Mock
    private SimpMessageSendingOperations messagingTemplate;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    public void notifyAboutNewChat() {
        notificationService.notifyAboutNewChat(CHAT_ID, DEPARTMENT_ID);
        sendNewEventMessage(NotificationType.NEW);
    }

    @Test
    public void notifyAboutAnsweredChat() {
        notificationService.notifyAboutAnsweredChat(CHAT_ID, DEPARTMENT_ID);
        sendNewEventMessage(NotificationType.ANSWERED);
    }

    private void sendNewEventMessage(NotificationType type) {
        int expectedNumberOfTheMethodInvocations = 1;
        NotificationDto expectedNotification = new NotificationDto(type.name(), CHAT_ID, DEPARTMENT_ID);

        BDDMockito.then(messagingTemplate)
                .should(VerificationModeFactory.times(expectedNumberOfTheMethodInvocations))
                .convertAndSend("/department/" + DEPARTMENT_ID, expectedNotification);
    }
}