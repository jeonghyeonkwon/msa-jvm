package jeonghyeon.msa.board.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardPopularPostsResponse {
    private String boardId;
    private String title;
    private Long commentCount;
    private Long viewCount;
    private Long likeCount;
    private String usersId;
    private String createdAt;

    @QueryProjection
    public BoardPopularPostsResponse(Long boardId, String title, Long commentCount,
                                     Long viewCount, Long likeCount, Long usersId, LocalDateTime createdAt) {
        this.boardId = boardId.toString();
        this.title = title;
        this.commentCount = commentCount;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.usersId = usersId.toString();
        this.createdAt = createdAt.toString();
    }

}
