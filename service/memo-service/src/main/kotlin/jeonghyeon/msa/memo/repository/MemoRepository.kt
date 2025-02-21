package jeonghyeon.msa.memo.repository

import jeonghyeon.msa.memo.domain.Memo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemoRepository : JpaRepository<Memo, Long> {
}