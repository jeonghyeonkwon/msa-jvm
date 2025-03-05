package jeonghyeon.msa.memo.service


import jakarta.transaction.Transactional
import jeonghyeon.msa.common.Snowflake
import jeonghyeon.msa.memo.domain.Memo
import jeonghyeon.msa.memo.domain.Users
import jeonghyeon.msa.memo.dto.request.MemoDto
import jeonghyeon.msa.memo.dto.request.UsersDto
import jeonghyeon.msa.memo.dto.response.PageResponse
import jeonghyeon.msa.memo.repository.MemoRepository
import jeonghyeon.msa.memo.repository.UsersRepository
import jeonghyeon.msa.memo.repository.mapper.MemoMapper
import jeonghyeon.msa.memo.util.PageLimitCalculator
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class MemoService(
    private val memoRepository: MemoRepository,
    private val usersRepository: UsersRepository,
    private val memoMapper: MemoMapper
) {
    private val snowflake: Snowflake = Snowflake()

    @Transactional
    fun createUser(usersDto: UsersDto) {
        val savedUser = usersRepository.save(Users(usersDto.usersId, usersDto.username))

        memoRepository.save(
            Memo(
                snowflake.nextId(),
                "계정 생성을 축하드립니다.",
                "메모를 입력해 주세요",
                LocalDateTime.now(),
                LocalDateTime.now(),
                savedUser
            )
        )
    }

    @Transactional
    fun createMemo(usersId: Long, memoDto: MemoDto): Long {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val users: Users = usersRepository.findByIdOrNull(usersId) ?: throw IllegalArgumentException("에러발생")

        val saved = memoRepository.save(
            Memo(
                snowflake.nextId(), memoDto.title, memoDto.content,
                LocalDateTime.parse(memoDto.startDate, formatter),
                LocalDateTime.parse(memoDto.endDate, formatter),
                users
            )
        )

        return saved.id;
    }

    fun getMemos(usersId: Long, pageable: Pageable): PageResponse<MemoDto> {
        val pageNumber = pageable.pageNumber.toLong()
        val pageSize = pageable.pageSize.toLong()
        val count = memoMapper.count(
            usersId,
            PageLimitCalculator.calculatePageLimit(pageNumber, pageSize, 10L)
        )
        val memos: List<MemoDto> = memoMapper.getMemos(usersId, pageNumber * pageSize, pageSize)

        return PageResponse<MemoDto>(pageNumber, pageSize, memos, count, 10L)
    }


}