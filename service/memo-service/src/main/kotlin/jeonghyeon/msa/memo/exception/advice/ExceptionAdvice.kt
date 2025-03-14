package jeonghyeon.msa.memo.exception.advice

import jeonghyeon.msa.memo.exception.ErrorResult
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.IllegalArgumentException

@Slf4j
@RestControllerAdvice
class ExceptionAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
        IllegalArgumentException::class
    )
    fun illegalState(e: IllegalArgumentException): ErrorResult {
        
        return ErrorResult(e.message)
    }
}