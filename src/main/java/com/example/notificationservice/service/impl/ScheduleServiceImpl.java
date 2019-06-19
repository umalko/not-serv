package com.example.notificationservice.service.impl;

import com.example.notificationservice.repository.ChatDao;
import com.example.notificationservice.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private static final Duration TIMEOUT = Duration.ofSeconds(20);
    private static final String HTTPBIN_URL = "http://httpbin.org/post";

    private final ChatDao chatRepository;
    private final RestTemplate restTemplate;

    @Override
    public void scheduleReassignment(String chatId) {
        chatRepository.addChat(chatId, Instant.now());
    }

    @Override
    public void cancelReassignment(String chatId) {
        chatRepository.markAsAnswered(chatId);
    }

    @Override
    @Scheduled(fixedRate = 1000)
    public void checkUnansweredChats() {
        Instant due = Clock.systemUTC().instant().minus(TIMEOUT);
        Collection<String> unansweredChatIds = chatRepository.getUnansweredChatsAndMarkAsReassigned(due);
        unansweredChatIds.forEach(this::assignToOmaha);
    }

    private void assignToOmaha(String chatId) {
        log.info("New chat {} was sent to Omaha department!", chatId);
        restTemplate.postForObject(HTTPBIN_URL, chatId, Void.class);
    }
}
