package jeonghyeon.msa.memo.controller

import jeonghyeon.msa.memo.dto.request.MemoDto
import jeonghyeon.msa.memo.dto.response.MemoResponse
import jeonghyeon.msa.memo.dto.response.PageResponse
import jeonghyeon.msa.memo.service.MemoService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api")
class MemoController(private val memoService: MemoService) {

    @GetMapping("/user/{usersId}/memo")
    fun getMemos(
        @PathVariable usersId: Long,
        pageable: Pageable,
        @RequestParam(value = "pageBlock", defaultValue = "10") pageBlock: Long
    ): ResponseEntity<PageResponse<MemoResponse>> {
        return ResponseEntity(memoService.getMemos(usersId, pageable, pageBlock), HttpStatus.OK)
    }

    @PostMapping("/user/{usersId}/memo")
    fun createMemo(@PathVariable usersId: Long, @RequestBody memoDto: MemoDto): ResponseEntity<String> {
        val memoId: Long = memoService.createMemo(usersId, memoDto)
        return ResponseEntity(memoId.toString(), HttpStatus.CREATED)
    }


    @GetMapping("/user/{usersId}/memo/{memoId}")
    fun getMemo(@PathVariable usersId: Long, @PathVariable memoId: Long): ResponseEntity<MemoResponse> {
        return ResponseEntity(memoService.getMemo(usersId, memoId), HttpStatus.OK)
    }

}