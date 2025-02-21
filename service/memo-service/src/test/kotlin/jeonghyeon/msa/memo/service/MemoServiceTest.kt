package jeonghyeon.msa.memo.service

import jeonghyeon.msa.common.Snowflake
import jeonghyeon.msa.memo.dto.request.UsersDto
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
internal class MemoServiceTest @Autowired constructor(
    private val memoService: MemoService


){
    private val snowflake = Snowflake()
    @Test
    @DisplayName("kafka를 이용한 유저 생성")
    fun createUser(){
        memoService.createUser(UsersDto(snowflake.nextId(),"givejeong2"))
    }


}