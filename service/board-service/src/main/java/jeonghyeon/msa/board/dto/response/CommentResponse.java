package jeonghyeon.msa.board.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponse {
    private Long commentId;
    private Long usersId;
    private String username;
    private String content;
    private LocalDateTime createdDate;

    @QueryProjection
    public CommentResponse(Long commentId, Long usersId, String username, String content, LocalDateTime createdDate) {
        this.commentId = commentId;
        this.usersId = usersId;
        this.username = username;
        this.content = content;
        this.createdDate = createdDate;
    }
}
