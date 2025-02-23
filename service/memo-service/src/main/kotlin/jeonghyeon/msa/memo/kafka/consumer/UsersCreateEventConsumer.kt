package jeonghyeon.msa.memo.kafka.consumer

import jeonghyeon.msa.common.event.EventType
import jeonghyeon.msa.memo.service.MemoService
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Slf4j
@Component
@RequiredArgsConstructor
class UsersCreateEventConsumer(
    private val memoService: MemoService
) {

    @KafkaListener(
        topics = [EventType.Topic.AUTH_CREATE]
    )
    fun listen(message: String, ack: Acknowledgment) {
        println("[UsersCreateEventConsumer.listen] message="+ message);
    }
}