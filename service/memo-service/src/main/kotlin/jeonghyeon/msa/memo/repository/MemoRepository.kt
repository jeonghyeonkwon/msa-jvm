package jeonghyeon.msa.memo.repository

import jeonghyeon.msa.memo.domain.Memo
import jeonghyeon.msa.memo.dto.response.MemoResponse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface MemoRepository : JpaRepository<Memo, Long> {

    @Query(
        "SELECT new jeonghyeon.msa.memo.dto.response.MemoResponse(m.id,m.title,m.content,m.startDate,m.endDate) " +
                "FROM Memo m " +
                "WHERE m.users.usersId = :usersId AND m.id = :memoId"
    )
    fun findByUsersIdAndMemoId(@Param("usersId") usersId: Long, @Param("memoId") memoId: Long): MemoResponse?
}