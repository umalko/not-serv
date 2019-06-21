package com.hyatt.notificationservice.controller;

import com.hyatt.notificationservice.dto.ChatAnsweredEvent;
import com.hyatt.notificationservice.dto.NewChatEvent;
import com.hyatt.notificationservice.service.NotificationService;
import com.hyatt.notificationservice.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(path = "/api/v1/chats",
        consumes = APPLICATION_JSON_UTF8_VALUE,
        produces = APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class ChatController {

    private final NotificationService notificationService;
    private final ScheduleService scheduleService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Object newChat(@RequestBody NewChatEvent event) {
        notificationService.notifyAboutNewChat(event.getChatId(), event.getDepartmentId());
        scheduleService.scheduleReassignment(event.getChatId());
        return Boolean.TRUE;
    }

    @PostMapping("/answered")
    @ResponseStatus(HttpStatus.OK)
    public Object chatAnswered(@RequestBody ChatAnsweredEvent event) {
        notificationService.notifyAboutAnsweredChat(event.getChatId(), event.getDepartmentId());
        scheduleService.cancelReassignment(event.getChatId());
        return Boolean.TRUE;
    }
}
