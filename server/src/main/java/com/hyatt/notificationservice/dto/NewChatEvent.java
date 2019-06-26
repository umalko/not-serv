package com.hyatt.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Clock;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewChatEvent {
    private Instant receivedAt = Clock.systemUTC().instant();
    private String chatId;
    private String departmentId;

    public NewChatEvent(String chatId, String departmentId) {
        this.chatId = chatId;
        this.departmentId = departmentId;
    }
}
