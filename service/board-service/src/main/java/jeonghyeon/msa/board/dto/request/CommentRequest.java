package jeonghyeon.msa.board.dto.request;

import lombok.Data;

@Data
public class CommentRequest {
    private Long usersId;
    private Long parentId;
    private String content;
}
