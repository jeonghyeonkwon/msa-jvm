package jeonghyeon.msa.memo.dto.response

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


data class MemoResponse(
    var memosId: String?,
    val title: String,
    val content: String,
    val startDate: String,
    val endDate: String
) {
    companion object {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    }

    constructor(
        _id: Long,
        _title: String,
        _content: String,
        _startDate: LocalDateTime,
        _endDate: LocalDateTime
    ) : this(_id.toString(), _title, _content, _startDate.format(formatter), _endDate.format(formatter))
}