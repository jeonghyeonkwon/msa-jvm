package jeonghyeon.msa.board.repository;

import jeonghyeon.msa.board.dto.response.BoardDetailResponse;
import jeonghyeon.msa.board.dto.response.BoardPopularPostsResponse;
import jeonghyeon.msa.board.dto.response.BoardResponse;

import java.util.List;

public interface BoardRepositoryCustom {
    BoardDetailResponse getBoardDetail(Long boardId);

    BoardDetailResponse getBoardDetailAndViewCount(Long boardId);

    List<BoardResponse> findList(Long offset, Long limit);

    BoardPopularPostsResponse getPopularPosts(Long boardId);
}
