package jeonghyeon.msa.common.event.payload;


import jeonghyeon.msa.common.event.EventPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardLikeDeleteEventPayload implements EventPayload {
    private String boardId;
    private String boardCreatedAt;
    public BoardLikeDeleteEventPayload(Long boardId, LocalDateTime boardCreated) {
        this.boardId = boardId.toString();
        this.boardCreatedAt = boardCreated.toString();
    }
}
