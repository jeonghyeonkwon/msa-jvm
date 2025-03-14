package jeonghyeon.msa.memo.repository.mapper

import jeonghyeon.msa.memo.dto.request.MemoDto
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param


@Mapper
interface MemoMapper {

    fun count(@Param("usersId") usersId: Long, @Param("limit") limit: Long): Long
    fun getMemos(
        @Param("usersId") usersId: Long,
        @Param("offset") offset: Long,
        @Param("limit") limit: Long
    ): List<MemoDto>

}