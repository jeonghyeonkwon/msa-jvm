package jeonghyeon.msa.auth.kafka;


import jeonghyeon.msa.auth.domain.Outbox;
import jeonghyeon.msa.common.Snowflake;
import jeonghyeon.msa.common.event.Event;
import jeonghyeon.msa.common.event.EventPayload;
import jeonghyeon.msa.common.event.EventType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxEventPublisher {
    private final Snowflake outboxId = new Snowflake();
    private final Snowflake eventId = new Snowflake();

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(EventType eventType, EventPayload payload) {
        Outbox outbox = Outbox.create(outboxId.nextId(), eventType,
                Event.of(
                        eventId.nextId(), eventType, payload
                ).toJson()
        );

        applicationEventPublisher.publishEvent(OutboxEvent.of(outbox));
    }
}
