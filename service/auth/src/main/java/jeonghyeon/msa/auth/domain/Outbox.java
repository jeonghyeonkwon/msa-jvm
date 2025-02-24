package jeonghyeon.msa.auth.domain;


import jakarta.persistence.*;
import jeonghyeon.msa.common.event.EventPayload;
import jeonghyeon.msa.common.event.EventType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@Table(name = "outbox")
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Outbox extends BaseTimeEntity {
    @Id
    private Long outboxId;
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private String payload;

    @Enumerated(EnumType.STRING)
    private OutboxType outboxType;

    private Outbox(Long outboxId, EventType eventType, String payload, OutboxType outboxType) {
        this.outboxId = outboxId;
        this.eventType = eventType;
        this.payload = payload;
        this.outboxType = outboxType;
    }

    public static Outbox create(Long outboxId, EventType eventType, String payload){
        return new Outbox(outboxId, eventType, payload, OutboxType.READY);
    }

    public Outbox finish() {
        this.outboxType = OutboxType.FINISH;
        return this;
    }
}
