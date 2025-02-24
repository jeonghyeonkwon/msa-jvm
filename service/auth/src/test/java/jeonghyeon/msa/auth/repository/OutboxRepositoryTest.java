package jeonghyeon.msa.auth.repository;

import jeonghyeon.msa.auth.domain.Outbox;
import jeonghyeon.msa.auth.domain.OutboxType;
import jeonghyeon.msa.common.Snowflake;
import jeonghyeon.msa.common.event.EventType;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor
class OutboxRepositoryTest {

    @Autowired
    private OutboxRepository outboxRepository;
    private final Snowflake snowflake = new Snowflake();

    @BeforeEach
    void init() {
        outboxRepository.save(Outbox.create(snowflake.nextId(), EventType.AUTH_CREATE, "test1"));
        outboxRepository.save(Outbox.create(snowflake.nextId(), EventType.AUTH_CREATE, "test2"));

    }

    @AfterEach
    void destory(){
        outboxRepository.deleteAll();
    }

    @Test
    void ready_size(){
        List<Outbox> outboxs = outboxRepository.findByCreatedAtLessThanEqualsOrderByCreatedAtAsc(
                OutboxType.READY,
                LocalDateTime.now().minusSeconds(10),
                Pageable.ofSize(10)
        );
        assertThat(outboxs.size()).isEqualTo(2);
    }

}