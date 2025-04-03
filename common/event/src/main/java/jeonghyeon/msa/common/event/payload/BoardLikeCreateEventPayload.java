package jeonghyeon.msa.common.event.payload;


import jeonghyeon.msa.common.event.EventPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardLikeCreateEventPayload implements EventPayload {
    private String boardId;
    private static final String TYPE = "create";

    public BoardLikeCreateEventPayload(Long boardId) {
        this.boardId = boardId.toString();
    }
}
