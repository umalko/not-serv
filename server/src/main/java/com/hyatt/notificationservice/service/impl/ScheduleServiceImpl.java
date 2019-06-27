package com.hyatt.notificationservice.service.impl;

import com.hyatt.notificationservice.model.ChatEventData;
import com.hyatt.notificationservice.repository.ChatRepository;
import com.hyatt.notificationservice.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;

/**
 * Implementation of {@link ScheduleService}
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    @Value("${app.trigger.timeout:20}")
    private Integer triggerTimeout;

    @Value("${app.omahaDepartmentUrl:http://httpbin.org/post}")
    private String omahaDepartmentUrl;

    private final ChatRepository chatRepository;
    private final RestTemplate restTemplate;

    /**
     * {@inheritDoc}
     * See also: {@link ScheduleService#scheduleReassignment(String)}
     */
    @Override
    public void scheduleReassignment(String chatId) {
        chatRepository.save(new ChatEventData(chatId, Instant.now()));
    }

    /**
     * {@inheritDoc}
     * See also: {@link ScheduleService#cancelReassignment(String)}
     */
    @Override
    public void cancelReassignment(String chatId) {
        chatRepository.deleteByChatId(chatId);
    }

    /**
     * {@inheritDoc}
     * See also: {@link ScheduleService#checkUnansweredChats()}
     */
    @Override
    @Scheduled(fixedRate = 1000)
    public void checkUnansweredChats() {
        Instant due = Clock.systemUTC().instant().minus(Duration.ofSeconds(triggerTimeout));
        Collection<String> unansweredChatIds = chatRepository.findListOfChatIdByReceivedAtBefore(due);
        chatRepository.deleteByReceivedAtBefore(due);
        unansweredChatIds.parallelStream().forEach(this::assignToOmaha);
    }

    private void assignToOmaha(String chatId) {
        log.info("New chat {} was sent to Omaha department!", chatId);
        restTemplate.postForObject(omahaDepartmentUrl, chatId, Void.class);
    }
}
