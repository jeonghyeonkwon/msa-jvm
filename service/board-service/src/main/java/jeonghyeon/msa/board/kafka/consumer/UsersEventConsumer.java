package jeonghyeon.msa.board.kafka.consumer;


import jeonghyeon.msa.board.service.BoardService;
import jeonghyeon.msa.common.event.Event;
import jeonghyeon.msa.common.event.EventPayload;
import jeonghyeon.msa.common.event.EventType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsersEventConsumer {
    private final BoardService boardService;

    @KafkaListener(topics = {
            EventType.Topic.AUTH_CREATE
    })
    public void listen(String message, Acknowledgment ack) {
        log.info("[UsersCreateEventConsumer.listen] message = {}", message);
        Event<EventPayload> event = Event.fromJson(message);
        if(event!=null){
            boardService.handleEvent(event);
        }
        ack.acknowledge();
    }
}
