package jeonghyeon.msa.board.dto.response;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardResponse {
    private Long boardId;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createdDate;

    @QueryProjection
    public BoardResponse(Long boardId, String title, String content, String username, LocalDateTime createdDate) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.username = username;
        this.createdDate = createdDate;
    }
}
