package com.example.notificationservice.dto;

import lombok.Data;

import java.time.Clock;
import java.time.Instant;

@Data
public class NewChatEvent {
    private Instant receivedAt = Clock.systemUTC().instant();
    private String chatId;
    private String departmentId;
}