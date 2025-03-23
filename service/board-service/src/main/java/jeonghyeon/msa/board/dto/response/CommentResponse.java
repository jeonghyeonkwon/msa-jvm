package jeonghyeon.msa.board.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import jeonghyeon.msa.board.util.DateFormatter;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponse {
    private String commentId;
    private String parentId;
    private String usersId;
    private String username;
    private String content;
    private String createdDate;

    @QueryProjection
    public CommentResponse(Long commentId, Long usersId, String username, String content, LocalDateTime createdDate) {
        this.commentId = commentId.toString();
        this.usersId = usersId.toString();
        this.username = username;
        this.content = content;
        this.createdDate = DateFormatter.toStringByLocalDateTime(createdDate);
    }

    @QueryProjection
    public CommentResponse(Long commentId, Long parentId, Long usersId, String username, String content, LocalDateTime createdDate) {
        this.commentId = commentId.toString();
        this.parentId = parentId.toString();
        this.usersId = usersId.toString();
        this.username = username;
        this.content = content;
        this.createdDate = DateFormatter.toStringByLocalDateTime(createdDate);
    }
}
