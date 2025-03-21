package jeonghyeon.msa.board.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class BoardDetailResponse {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private Long boardId;
    private String username;
    private String title;
    private String content;
    private String createdAt;
    private Long viewCount;

    @QueryProjection
    public BoardDetailResponse(Long boardId, String username, String title,
                               String content, LocalDateTime createdAt) {
        this.boardId = boardId;
        this.username = username;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt.format(formatter);
    }

    @QueryProjection
    public BoardDetailResponse(Long boardId, String username, String title,
                               String content, LocalDateTime createdAt, Long viewCount) {
        this.boardId = boardId;
        this.username = username;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.createdAt = createdAt.format(formatter);
    }

    public Long increaseViewCount() {
        return ++this.viewCount;
    }
}
