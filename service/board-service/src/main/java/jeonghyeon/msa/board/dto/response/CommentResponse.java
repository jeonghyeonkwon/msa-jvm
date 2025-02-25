package jeonghyeon.msa.board.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponse {
    private Long commentId;
    private Long usersId;
    private String username;
    private String content;
    private LocalDateTime createdDate;

    public CommentResponse(Long commentId, Long usersId, String username, String content, LocalDateTime createdDate) {
        this.commentId = commentId;
        this.usersId = usersId;
        this.username = username;
        this.content = content;
        this.createdDate = createdDate;
    }
}
