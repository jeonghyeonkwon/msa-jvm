package jeonghyeon.msa.memo.repository.mapper

import jeonghyeon.msa.memo.dto.request.MemoDto
import org.apache.ibatis.annotations.Mapper

@Mapper
interface MemoMapper {

    fun count(usersId: Long, limit: Long): Long
    fun getMemos(usersId: Long, offset: Long, limit: Long): List<MemoDto>

}