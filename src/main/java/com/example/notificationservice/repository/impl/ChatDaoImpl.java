package com.example.notificationservice.repository.impl;

import com.example.notificationservice.repository.ChatDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class ChatDaoImpl implements ChatDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addChat(String chatId, Instant receivedAt) {
        jdbcTemplate.update("INSERT INTO unanswered_chats(chat_id, received_at) VALUES (?,?)", chatId, receivedAt.getEpochSecond());
    }

    @Override
    public void markAsAnswered(String chatId) {
        jdbcTemplate.update("DELETE FROM unanswered_chats WHERE chat_id = ?", chatId);
    }

    @Override
    public Collection<String> getUnansweredChatsAndMarkAsReassigned(Instant receivedBefore) {
        List<Object[]> chatIds = jdbcTemplate.query(
                "SELECT chat_id FROM unanswered_chats WHERE received_at < ?", new Object[]{receivedBefore.getEpochSecond()},
                (rs, rowNum) -> new Object[]{rs.getString("chat_id")});

        jdbcTemplate.batchUpdate("DELETE FROM unanswered_chats WHERE chat_id = ?", chatIds);
        return chatIds.stream().map(objects -> objects[0].toString()).collect(Collectors.toList());
    }
}
