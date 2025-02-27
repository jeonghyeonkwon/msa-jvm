package jeonghyeon.msa.board.facade;

import jeonghyeon.msa.board.dto.request.BoardRequest;
import jeonghyeon.msa.board.dto.request.CommentRequest;
import jeonghyeon.msa.board.dto.response.BoardDetailResponse;
import jeonghyeon.msa.board.dto.response.BoardResponse;
import jeonghyeon.msa.board.dto.response.CommentResponse;
import jeonghyeon.msa.board.repository.redis.ViewCountRepository;
import jeonghyeon.msa.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardFacade {
    private final ViewCountRepository viewCountRepository;
    private final BoardService boardService;

    private static final int BACK_UP_BATCH_SIZE = 100;


    public Long createBoard(Long usersId, BoardRequest request) {
        return boardService.createBoard(usersId, request);
    }


    public CommentResponse createComment(Long usersId, Long boardId, CommentRequest request) {
        return boardService.createComment(usersId, boardId, request);
    }


    // 조회시 데이터가 없다면 board에서 가져와서 +1 후 redis 저장
    // 있다면 레디스 +1해서 반환
    // 뒷자리 00 이면 백업
    @Transactional
    public BoardDetailResponse getBoardDetail(Long boardId) {
        Long viewCount = viewCountRepository.read(boardId);

        //cache miss
        if (viewCount == 0L) {
            BoardDetailResponse board = boardService.getBoardDetailAndViewCount(boardId);
            Long updateViewCount = board.increaseViewCount();
            viewCountRepository.setIncreaseViewCount(boardId, updateViewCount);
            return board;
        }

        viewCount = viewCountRepository.increase(boardId);

        if (viewCount % BACK_UP_BATCH_SIZE == 0) {
            boardService.updateViewCount(boardId, viewCount);
        }

        BoardDetailResponse board = boardService.getBoardDetail(boardId);
        board.setViewCount(viewCount);
        return board;

    }

    public List<BoardResponse> getBoards(Pageable pageable) {
        return null;
    }
}
