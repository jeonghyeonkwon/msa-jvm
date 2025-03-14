package jeonghyeon.msa.memo.util

object PageLimitCalculator {
    fun calculatePageLimit(page: Long, pageSize: Long, movablePageCount: Long): Long {
        return (((page) / movablePageCount) + 1) * pageSize * movablePageCount + 1

        //(((0) / 5) + 1) * 1 * 5 + 1
    }
}