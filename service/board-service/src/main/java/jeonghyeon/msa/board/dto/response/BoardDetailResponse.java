package jeonghyeon.msa.board.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import jeonghyeon.msa.board.util.DateFormatter;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class BoardDetailResponse {

    private String boardId;
    private String username;
    private String title;
    private String content;
    private String createdAt;
    private Long viewCount;
    private boolean isLiked;

    @QueryProjection
    public BoardDetailResponse(Long boardId, String username, String title,
                               String content, LocalDateTime createdAt) {
        this.boardId = boardId.toString();
        this.username = username;
        this.title = title;
        this.content = content;
        this.createdAt = DateFormatter.toStringByLocalDateTime(createdAt);
    }

    @QueryProjection
    public BoardDetailResponse(Long boardId, String username, String title,
                               String content, LocalDateTime createdAt, boolean isLiked) {
        this.boardId = boardId.toString();
        this.username = username;
        this.title = title;
        this.content = content;
        this.createdAt = DateFormatter.toStringByLocalDateTime(createdAt);
        this.isLiked = isLiked;
    }

    @QueryProjection
    public BoardDetailResponse(Long boardId, String username, String title,
                               String content, LocalDateTime createdAt, Long viewCount) {
        this.boardId = boardId.toString();
        this.username = username;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.createdAt = DateFormatter.toStringByLocalDateTime(createdAt);
    }

    @QueryProjection
    public BoardDetailResponse(Long boardId, String username, String title,
                               String content, LocalDateTime createdAt, Long viewCount, boolean isLiked) {
        this.boardId = boardId.toString();
        this.username = username;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.createdAt = DateFormatter.toStringByLocalDateTime(createdAt);
        this.isLiked = isLiked;
    }

    public Long increaseViewCount() {
        return ++this.viewCount;
    }
}
