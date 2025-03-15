package jeonghyeon.msa.memo.kafka.consumer

import jeonghyeon.msa.common.event.Event
import jeonghyeon.msa.common.event.EventPayload
import jeonghyeon.msa.common.event.EventType
import jeonghyeon.msa.common.event.payload.AuthCreateEventPayload
import jeonghyeon.msa.memo.dto.request.UsersDto
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
        println("[UsersCreateEventConsumer.listen] message=" + message)
        val event: Event<EventPayload> = Event.fromJson(message)
        println(event.toString())
        if (event != null) {
            if (event.type == EventType.AUTH_CREATE) {
                val payload: AuthCreateEventPayload = event.payload as AuthCreateEventPayload
                memoService.createUser(UsersDto(payload.usersId, payload.username))
                ack.acknowledge()
                return
            }
        }

    }
}