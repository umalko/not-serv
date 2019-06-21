package com.hyatt.notificationservice.service;

import com.hyatt.notificationservice.repository.ChatRepository;
import com.hyatt.notificationservice.service.impl.ScheduleServiceImpl;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Matchers.any;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
public class ScheduleServiceTest {

    private static final String CHAT_ID = "CHAT_ID";
    private static final String HTTPBIN_URL = "http://httpbin.org/post";

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    @Test
    public void scheduleReassignment() {
        scheduleService.scheduleReassignment(CHAT_ID);
        int expectedNumberOfTheMethodInvocations = 1;

        BDDMockito.then(chatRepository)
                .should(VerificationModeFactory.times(expectedNumberOfTheMethodInvocations))
                .save(any());
    }

    @Test
    public void cancelReassignment() {
        scheduleService.cancelReassignment(CHAT_ID);
        int expectedNumberOfTheMethodInvocations = 1;

        BDDMockito.then(chatRepository)
                .should(VerificationModeFactory.times(expectedNumberOfTheMethodInvocations))
                .deleteByChatId(CHAT_ID);
    }

    @Test
    public void checkUnansweredChats() {
        Mockito.when(chatRepository.findListOfChatIdByReceivedAtBefore(any())).thenReturn(Lists.newArrayList(CHAT_ID));

        scheduleService.checkUnansweredChats();
        int expectedNumberOfTheMethodInvocations = 1;

        BDDMockito.then(restTemplate)
                .should(VerificationModeFactory.times(expectedNumberOfTheMethodInvocations))
                .postForObject(HTTPBIN_URL, CHAT_ID, Void.class);
    }

    @Test
    public void checkUnansweredChats_withEmptyList() {
        Mockito.when(chatRepository.findListOfChatIdByReceivedAtBefore(any())).thenReturn(Lists.newArrayList());

        scheduleService.checkUnansweredChats();
        int expectedNumberOfTheMethodInvocations = 0;

        BDDMockito.then(restTemplate)
                .should(VerificationModeFactory.times(expectedNumberOfTheMethodInvocations))
                .postForObject(HTTPBIN_URL, CHAT_ID, Void.class);
    }
}