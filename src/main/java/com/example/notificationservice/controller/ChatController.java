package com.example.notificationservice.controller;

import com.example.notificationservice.dto.ChatAnsweredEvent;
import com.example.notificationservice.dto.NewChatEvent;
import com.example.notificationservice.service.NotificationService;
import com.example.notificationservice.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chats")
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
