package jeonghyeon.msa.board.dto.response;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class KafkaResponseDto {
    private Long boardId;
    private LocalDateTime boardCreatedAt;

    public KafkaResponseDto(Long boardId, LocalDateTime boardCreatedAt) {
        this.boardId = boardId;
        this.boardCreatedAt = boardCreatedAt;
    }
}
