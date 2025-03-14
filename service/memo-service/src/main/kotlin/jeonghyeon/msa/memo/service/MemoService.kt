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
import java.time.DateTimeException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class MemoService(
    private val memoRepository: MemoRepository,
    private val usersRepository: UsersRepository,
    private val memoMapper: MemoMapper
) {
    private val snowflake: Snowflake = Snowflake()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")

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

        val users: Users = usersRepository.findByIdOrNull(usersId) ?: throw IllegalArgumentException("에러발생")

        val startDate = toLocalDateTime(memoDto.startDate)
        val endDate = LocalDateTime.parse(memoDto.endDate)

        val saved = memoRepository.save(
            Memo(
                snowflake.nextId(), memoDto.title, memoDto.content,
                startDate,
                endDate,
                users
            )
        )

        return saved.id;
    }

    private fun toLocalDateTime(
        text: String,
        ):LocalDateTime{
      return try{
          LocalDateTime.parse(text, formatter)
      }catch (e:DateTimeException){
          throw IllegalArgumentException("잘못된 날짜 형식입니다.")
      }
    }

    fun getMemos(usersId: Long, pageable: Pageable, pageBlock: Long): PageResponse<MemoDto> {
        val pageNumber = pageable.pageNumber.toLong()
        val pageSize = pageable.pageSize.toLong()
        val count = memoMapper.count(
            usersId,
            PageLimitCalculator.calculatePageLimit(pageNumber, pageSize, pageBlock)
        )
        val memos: List<MemoDto> = memoMapper.getMemos(usersId, pageNumber * pageSize, pageSize)
        return PageResponse<MemoDto>(pageNumber, pageSize, memos, count, pageBlock)
    }


}