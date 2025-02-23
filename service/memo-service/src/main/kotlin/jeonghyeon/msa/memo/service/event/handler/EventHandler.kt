package jeonghyeon.msa.memo.service.event.handler

import jeonghyeon.msa.common.event.Event
import jeonghyeon.msa.common.event.EventPayload

interface EventHandler<T : EventPayload> {
    fun handle(event: Event<T>)
    fun supports(event: Event<T>): Boolean
}