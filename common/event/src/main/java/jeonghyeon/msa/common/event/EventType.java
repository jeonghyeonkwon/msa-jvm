package jeonghyeon.msa.common.event;

import jeonghyeon.msa.common.event.payload.AuthCreateEventPayload;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum EventType {
    AUTH_CREATE(AuthCreateEventPayload.class, Topic.AUTH_CREATE);

    private final Class<? extends EventPayload> payloadClass;
    private final String topic;

    public static EventType from(String type) {
        try {
            return valueOf(type);
        } catch (Exception e) {
            log.error("[EventType.from] type = {}", type, e);
            return null;
        }
    }

    public static class Topic {
        public static final String AUTH_CREATE = "auth-users";
    }
}
