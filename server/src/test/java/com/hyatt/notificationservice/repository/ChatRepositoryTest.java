package com.hyatt.notificationservice.repository;

import com.hyatt.notificationservice.model.ChatEventData;
import org.junit.Test;
import org.assertj.core.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;


@DataJpaTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class ChatRepositoryTest {

    private static final Duration TIMEOUT = Duration.ofSeconds(20);
    private static final String CHAT_ID = "CHAT_ID";
    private static final Instant NOW = Instant.now();
    private static final ChatEventData CHAT_EVENT_DATA = new ChatEventData(CHAT_ID, NOW);

    private static final String CHAT_ID2 = "CHAT_ID2";
    private static final Instant NOW2 = Instant.now().plus(5, ChronoUnit.MINUTES);
    private static final ChatEventData CHAT_EVENT_DATA2 = new ChatEventData(CHAT_ID2, NOW2);

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void deleteByChatId() {
        ChatEventData chatEventData = testEntityManager.persistAndFlush(CHAT_EVENT_DATA);

        Optional<ChatEventData> savedChat = chatRepository.findById(chatEventData.getId());
        Assertions.assertThat(savedChat).isPresent();

        chatRepository.deleteByChatId(chatEventData.getChatId());

        Optional<ChatEventData> chatAfterDelete = chatRepository.findById(chatEventData.getId());
        Assertions.assertThat(chatAfterDelete).isNotPresent();
    }

    @Test
    public void findListOfChatIdByReceivedAtBefore() {
        CHAT_EVENT_DATA.setReceivedAt(Instant.now().minus(30, ChronoUnit.SECONDS));
        chatRepository.save(CHAT_EVENT_DATA);

        chatRepository.save(CHAT_EVENT_DATA2);

        Iterable<ChatEventData> all = chatRepository.findAll();
        Assertions.assertThat(all).hasSize(2);

        Instant due = Clock.systemUTC().instant().minus(TIMEOUT);
        List<String> listOfChatIdByReceivedAtBefore = chatRepository.findListOfChatIdByReceivedAtBefore(due);

        Assertions.assertThat(listOfChatIdByReceivedAtBefore)
                .hasSize(1)
                .containsOnly(CHAT_ID);
    }

    @Test
    public void deleteByReceivedAtBefore() {
        CHAT_EVENT_DATA.setReceivedAt(Instant.now().minus(30, ChronoUnit.SECONDS));
        chatRepository.save(CHAT_EVENT_DATA);

        ChatEventData savedChatEventData = chatRepository.save(CHAT_EVENT_DATA2);

        Iterable<ChatEventData> all = chatRepository.findAll();
        Assertions.assertThat(all).hasSize(2);

        Instant due = Clock.systemUTC().instant().minus(TIMEOUT);
        chatRepository.deleteByReceivedAtBefore(due);

        Iterable<ChatEventData> allAfterClean = chatRepository.findAll();

        Assertions.assertThat(allAfterClean)
                .hasSize(1)
                .containsOnly(savedChatEventData);
    }
}