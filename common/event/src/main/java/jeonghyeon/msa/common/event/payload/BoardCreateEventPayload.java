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
public class BoardCreateEventPayload implements EventPayload {
    private String boardId;
    private String title;
    private String usersId;
    private String createdAt;

    public BoardCreateEventPayload(Long boardId, String title, Long usersId, String createdAt) {
        this.boardId = boardId.toString();
        this.title = title;
        this.usersId = usersId.toString();
        this.createdAt = createdAt;
    }
}
