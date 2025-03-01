package jeonghyeon.msa.board.repository;

import jeonghyeon.msa.board.dto.response.BoardResponse;
import jeonghyeon.msa.board.dto.response.CommentResponse;

import java.util.List;

public interface CommentRepositoryCustom {
    public List<CommentResponse> findList(Long boardId, Long offset, Long limit);
}
