package jeonghyeon.msa.memo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class MemoApplication

fun main(args: Array<String>) {
    runApplication<MemoApplication>(*args)
}