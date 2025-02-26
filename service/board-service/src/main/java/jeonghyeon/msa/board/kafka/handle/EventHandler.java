package jeonghyeon.msa.board.kafka.handle;

import jeonghyeon.msa.common.event.Event;
import jeonghyeon.msa.common.event.EventPayload;

public interface EventHandler <T extends EventPayload> {
    void handle(Event<T> event);
    boolean supports(Event<T> event);
}
