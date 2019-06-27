package com.hyatt.notificationservice.controller;

import com.hyatt.notificationservice.dto.ChatAnsweredEvent;
import com.hyatt.notificationservice.dto.NewChatEvent;
import com.hyatt.notificationservice.service.NotificationService;
import com.hyatt.notificationservice.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * Chat Controller is responsible for processing NEW and ANSWERED chat events.
 */
@RestController
@RequestMapping(path = "/api/v1/chats",
        consumes = APPLICATION_JSON_UTF8_VALUE,
        produces = APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class ChatController {

    private final NotificationService notificationService;
    private final ScheduleService scheduleService;

    /**
     * Process NEW chat event data. Saves to the db and reassign to the default department
     * if was not processed within 20s(configured)
     *
     * @param event NEW chat event. Contains event id; department id;
     *              receivedAt -- created automatically(don't need to provide!)
     * @return TRUE if the process was success
     */
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Boolean newChat(@RequestBody NewChatEvent event) {
        notificationService.notifyAboutNewChat(event.getChatId(), event.getDepartmentId());
        scheduleService.scheduleReassignment(event.getChatId());
        return Boolean.TRUE;
    }

    /**
     * Process ANSWERED chat event data. Removes from db. Stop processing.
     *
     * @param event ANSWERED chat event. Contains event id; department id;
     * @return TRUE if the process was success
     */
    @PostMapping("/answered")
    @ResponseStatus(HttpStatus.OK)
    public Boolean chatAnswered(@RequestBody ChatAnsweredEvent event) {
        notificationService.notifyAboutAnsweredChat(event.getChatId(), event.getDepartmentId());
        scheduleService.cancelReassignment(event.getChatId());
        return Boolean.TRUE;
    }
}
