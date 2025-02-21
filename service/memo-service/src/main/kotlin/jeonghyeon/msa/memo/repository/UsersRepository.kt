package jeonghyeon.msa.memo.repository

import jeonghyeon.msa.memo.domain.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsersRepository : JpaRepository<Users, Long> {
}