package jeonghyeon.msa.memo.dto.request

data class MemoDto(
    val title: String,
    val content: String,
    val startDate: String,
    val endDate: String
)