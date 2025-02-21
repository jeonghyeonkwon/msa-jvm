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
    val users: Users

) : BaseTimeEntity() {

}