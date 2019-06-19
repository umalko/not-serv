package com.example.notificationservice.repository;

import java.time.Instant;
import java.util.Collection;

public interface ChatDao {

    void addChat(String chatId, Instant receivedAt);

    void markAsAnswered(String chatId);

    Collection<String> getUnansweredChatsAndMarkAsReassigned(Instant receivedBefore);

}
