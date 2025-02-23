package jeonghyeon.msa.common.event;

import jeonghyeon.msa.common.DataSerializer;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Event<T extends EventPayload> {
    private Long eventId;
    private EventType type;
    private T payload;

    public static Event<EventPayload> of(Long eventId, EventType type, EventPayload payload) {
        Event<EventPayload> event = new Event<>();
        event.eventId = eventId;
        event.type = type;
        event.payload = payload;
        return event;
    }

    public String toJson() {
        return DataSerializer.serialize(this);
    }

    public static Event<EventPayload> fromJson(String json) {
        EventRaw eventRaw = DataSerializer.deserialize(json, EventRaw.class);

        if (eventRaw == null) {
            return null;
        }

        Event<EventPayload> event = new Event<>();
        System.out.println("1");
        event.eventId = eventRaw.getEventId();
        System.out.println("2");
        event.type = EventType.from(eventRaw.getType());
        System.out.println("3");
        event.payload = DataSerializer.deserialize(eventRaw.getPayload(), event.type.getPayloadClass());
        System.out.println("4");
        return event;
    }
    @Getter
    private static class EventRaw{
        private Long eventId;
        private String type;
        private Object payload;
    }
}


