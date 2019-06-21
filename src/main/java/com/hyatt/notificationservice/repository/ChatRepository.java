package com.hyatt.notificationservice.repository;

import com.hyatt.notificationservice.model.ChatEventData;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface ChatRepository extends PagingAndSortingRepository<ChatEventData, Long> {

    void deleteByChatId(String chatId);

    @Query("SELECT chatId FROM ChatEventData WHERE received_at < :receivedBefore")
    List<String> findListOfChatIdByReceivedAtBefore(@Param("receivedBefore") Instant receivedBefore);

    @Modifying
    void deleteByReceivedAtBefore(Instant due);

}
