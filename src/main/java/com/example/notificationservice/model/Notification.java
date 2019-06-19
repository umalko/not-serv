package com.example.notificationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private ChatEventData chat;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
}
