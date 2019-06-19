package com.example.notificationservice.repository;

import com.example.notificationservice.model.ChatEventData;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;

@Repository
public interface ChatRepository extends PagingAndSortingRepository<ChatEventData, Long> {

    void addChat(String chatId, Instant receivedAt);

    void markAsAnswered(String chatId);

    Collection<String> getUnansweredChatsAndMarkAsReassigned(Instant receivedBefore);

}
