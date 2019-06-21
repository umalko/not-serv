package com.hyatt.notificationservice.dto;

import lombok.Data;

@Data
public class ChatAnsweredEvent {
    private String chatId;
    private String departmentId;
}
