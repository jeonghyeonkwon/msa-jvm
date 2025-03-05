package jeonghyeon.msa.memo.dto.response

import java.lang.Math.min

class PageResponse<T> {
    val isFirst: Boolean
    val isLast: Boolean
    val currentPage: Long
    val startBlockPage: Long
    val endBlockPage: Long
    val totalPage: Long
    val list: List<T>

    constructor(currentPage: Long, pageSize: Long, list: List<T>, count: Long, pageBlock: Long) {
        this.currentPage = currentPage
        this.totalPage = count / pageSize
        this.startBlockPage = (currentPage / pageBlock) * pageBlock
        this.endBlockPage = min(this.startBlockPage + pageBlock - 1, this.totalPage)
        this.isFirst = if (this.startBlockPage / pageBlock != 0L) true else false
        this.isLast = if ((this.startBlockPage + pageBlock - 1) * pageBlock < count) true else false;
        this.list = list
    }
}