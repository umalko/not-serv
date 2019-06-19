package com.example.notificationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chats")
public class ChatEventData {

    @Id
    @GeneratedValue
    private Long id;

    private String chatId;
    private Instant receivedAt;

    public ChatEventData(String chatId, Instant receivedAt) {
        this.chatId = chatId;
        this.receivedAt = receivedAt;
    }
}
