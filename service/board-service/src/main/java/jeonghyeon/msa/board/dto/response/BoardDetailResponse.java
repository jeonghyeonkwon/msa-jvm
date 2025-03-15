package jeonghyeon.msa.board.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.util.List;

@Data
public class BoardDetailResponse {
    private Long boardId;
    private String username;
    private String title;
    private String content;
    private Long viewCount;
    private List<CommentResponse> comments;

    @QueryProjection
    public BoardDetailResponse(Long boardId, String username, String title,
                               String content) {
        this.boardId = boardId;
        this.username = username;
        this.title = title;
        this.content = content;
    }

    @QueryProjection
    public BoardDetailResponse(Long boardId, String username, String title,
                               String content, Long viewCount) {
        this.boardId = boardId;
        this.username = username;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
    }

    public Long increaseViewCount() {
        return ++this.viewCount;
    }
}
