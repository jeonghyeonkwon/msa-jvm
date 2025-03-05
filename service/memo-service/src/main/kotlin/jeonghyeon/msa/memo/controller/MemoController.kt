package jeonghyeon.msa.memo.controller

import jeonghyeon.msa.memo.dto.request.MemoDto
import jeonghyeon.msa.memo.service.MemoService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/")
class MemoController(private val memoService: MemoService) {


    @GetMapping("{usersId}/memo")
    fun getMemos(@PathVariable usersId: Long, pageable: Pageable) {
        memoService.getMemos(usersId, pageable)
    }

    @PostMapping("{usersId}/memo")
    fun createMemo(@PathVariable usersId: Long, @RequestBody memoDto: MemoDto): ResponseEntity<Long> {
        val memoId: Long = memoService.createMemo(usersId, memoDto)
        return ResponseEntity(memoId, HttpStatus.CREATED)
    }

}