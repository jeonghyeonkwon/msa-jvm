package jeonghyeon.msa.board.repository;

import jeonghyeon.msa.board.dto.response.CommentResponse;

import java.util.List;

public interface CommentRepositoryCustom {
    List<CommentResponse> findList(Long boardId, Long offset, Long limit);
}
