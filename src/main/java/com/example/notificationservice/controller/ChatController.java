package com.example.notificationservice.controller;

import com.example.notificationservice.dto.ChatAnsweredEvent;
import com.example.notificationservice.dto.NewChatEvent;
import com.example.notificationservice.service.NotificationService;
import com.example.notificationservice.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
public class ChatController {

    private final NotificationService notificationService;
    private final ScheduleService scheduleService;

    @PostMapping("/chat/new")
    public Object newChat(@RequestBody NewChatEvent event) {
        notificationService.notifyAboutNewChat(event.getChatId(), event.getDepartmentId());
        scheduleService.scheduleReassignment(event.getChatId());
        return true;
    }

    @PostMapping("/chat/answered")
    public Object chatAnswered(@RequestBody ChatAnsweredEvent event) {
        notificationService.notifyAboutAnsweredChat(event.getChatId(), event.getDepartmentId());
        scheduleService.cancelReassignment(event.getChatId());
        return true;
    }
}
