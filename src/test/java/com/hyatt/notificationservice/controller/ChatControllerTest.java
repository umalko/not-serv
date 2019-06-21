package com.hyatt.notificationservice.controller;

import com.hyatt.notificationservice.service.NotificationService;
import com.hyatt.notificationservice.service.ScheduleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.then;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChatController.class)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class ChatControllerTest {

    private static final String CHAT_ID = "123";
    private static final String DEPARTMENT_ID = "321";

    private static final String BODY = "{\n" +
            "\t\"chatId\": \"" + CHAT_ID + "\",\n" +
            "\t\"departmentId\": \"" + DEPARTMENT_ID + "\"\n" +
            "}";

    private static final String CREATE_NEW_CHAT_EVENT = "/api/v1/chats/new";
    private static final String CREATE_ANSWERED_CHAT_EVENT = "/api/v1/chats/answered";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

    @MockBean
    private ScheduleService scheduleService;

    @Test
    public void newChat() throws Exception {
        callControllerAndCheckInvocations(CREATE_NEW_CHAT_EVENT, status().isCreated());
        int expectedNumberOfTheMethodInvocations = 1;

        then(notificationService)
                .should(times(expectedNumberOfTheMethodInvocations))
                .notifyAboutNewChat(CHAT_ID, DEPARTMENT_ID);

        then(scheduleService)
                .should(times(expectedNumberOfTheMethodInvocations))
                .scheduleReassignment(CHAT_ID);
    }

    @Test
    public void chatAnswered() throws Exception {
        callControllerAndCheckInvocations(CREATE_ANSWERED_CHAT_EVENT, status().isOk());
        int expectedNumberOfTheMethodInvocations = 1;

        then(notificationService)
                .should(times(expectedNumberOfTheMethodInvocations))
                .notifyAboutAnsweredChat(CHAT_ID, DEPARTMENT_ID);

        then(scheduleService)
                .should(times(expectedNumberOfTheMethodInvocations))
                .cancelReassignment(CHAT_ID);
    }

    private void callControllerAndCheckInvocations(String url, ResultMatcher status) throws Exception {
        this.mockMvc.perform(
                post(url)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(BODY))
                .andDo(print())
                .andExpect(status)
                .andExpect(content().string(containsString(Boolean.TRUE.toString())));
    }

}