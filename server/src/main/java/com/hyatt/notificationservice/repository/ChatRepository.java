package com.hyatt.notificationservice.repository;

import com.hyatt.notificationservice.model.ChatEventData;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Chat Repository contains CRUD operations with DB layer
 */
@Repository
public interface ChatRepository extends PagingAndSortingRepository<ChatEventData, Long> {

    void deleteByChatId(String chatId);

    /**
     * Find list of chat Ids which were received before value of param: receivedBefore.
     *
     * @param receivedBefore time to get chat ids. Ex: receivedBefore - 20s. =>
     *                       Get all chat ids which were created before now - 20s
     * @return list of chat Ids
     */
    @Query("SELECT chatId FROM ChatEventData WHERE received_at < :receivedBefore")
    List<String> findListOfChatIdByReceivedAtBefore(@Param("receivedBefore") Instant receivedBefore);

    /**
     * Delete all chat ids which were received before value of param: receivedBefore.
     *
     * @param receivedBefore time to get chat ids. Ex: receivedBefore - 20s. =>
     *                       Get all chat ids which were created before now - 20s
     */
    @Modifying
    void deleteByReceivedAtBefore(Instant receivedBefore);

}
