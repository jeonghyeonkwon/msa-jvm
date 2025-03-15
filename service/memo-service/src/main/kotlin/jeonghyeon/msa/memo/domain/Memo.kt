package jeonghyeon.msa.memo.domain

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "memo")
class Memo(
    @Id
    var id: Long,
    var title: String,
    var content: String,
    var startDate: LocalDateTime,
    var endDate: LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    val users: Users

) : BaseTimeEntity() {
    init {
        if (title.isBlank() || content.isBlank()) {
            throw IllegalArgumentException("빈 값을 넣을 수 없습니다")
        }
    }
}