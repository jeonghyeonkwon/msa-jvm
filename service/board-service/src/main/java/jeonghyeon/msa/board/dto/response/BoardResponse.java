package jeonghyeon.msa.board.dto.response;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class BoardResponse {
    private String boardId;
    private String title;
    private String content;
    private String username;
    private Long commentCount;
    private LocalDateTime createdDate;

    @QueryProjection
    public BoardResponse(Long boardId, String title, String content, String username, LocalDateTime createdDate) {
        this.boardId = boardId.toString();
        this.title = title;
        this.content = content;
        this.username = username;
        this.createdDate = createdDate;
    }
}
