package jeonghyeon.msa.memo.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table


@Entity
@Table(name = "users")
class Users(
    @Id
    val usersId: Long,
    val username: String,

    ) {
    @OneToMany(mappedBy = "users")
    val memos: MutableList<Memo> = mutableListOf()
}