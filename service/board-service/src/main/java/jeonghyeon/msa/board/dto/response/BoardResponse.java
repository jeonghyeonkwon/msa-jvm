package jeonghyeon.msa.board.dto.response;


import com.querydsl.core.annotations.QueryProjection;
import jeonghyeon.msa.board.util.DateFormatter;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@ToString
public class BoardResponse {

    private String boardId;
    private String title;
    private String content;
    private String username;
    private Long commentCount;
    private String  createdAt;

    @QueryProjection
    public BoardResponse(Long boardId, String title, String content, String username, LocalDateTime createdDate) {
        this.boardId = boardId.toString();
        this.title = title;
        this.content = content;
        this.username = username;
        this.createdAt = DateFormatter.toStringByLocalDateTime(createdDate);
    }
}
