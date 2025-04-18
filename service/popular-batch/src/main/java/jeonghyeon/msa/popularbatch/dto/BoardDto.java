package jeonghyeon.msa.popularbatch.dto;

import lombok.Data;

@Data
public class BoardDto {
    private Long boardId;
    private Long commentCount;
    private Long viewCount;
    private Long likeCount;

    public BoardDto(Long boardId, Long commentCount, Long viewCount, Long likeCount) {
        this.boardId = boardId;
        this.commentCount = commentCount;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
    }


}
