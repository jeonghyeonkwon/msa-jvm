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
        this.totalPage = if ((count % pageSize) == 0L) (count / pageSize) - 1 else (count / pageSize)
        this.startBlockPage = (currentPage / pageBlock) * pageBlock
        this.endBlockPage = min(this.startBlockPage + pageBlock - 1, this.totalPage)
        this.isFirst = if (this.startBlockPage / pageBlock != 0L) true else false
        this.isLast = if (this.endBlockPage < this.totalPage) true else false;
        this.list = list
    }
}