package jeonghyeon.msa.board.repository;

import jeonghyeon.msa.board.dto.response.CommentResponse;

import java.util.List;
import java.util.Map;

public interface CommentRepositoryCustom {
    List<CommentResponse> findList(Long boardId, Long offset, Long limit);

    Map<Long, Long> findCountByBoardIds(List<Long> boardIds);

    List<CommentResponse> findRepliesByParentIds(List<Long> parentsId);
}
