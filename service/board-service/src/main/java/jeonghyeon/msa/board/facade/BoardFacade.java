package jeonghyeon.msa.board.facade;

import jeonghyeon.msa.board.client.AuthClient;
import jeonghyeon.msa.board.domain.Users;
import jeonghyeon.msa.board.dto.request.BoardRequest;
import jeonghyeon.msa.board.dto.request.CommentRequest;
import jeonghyeon.msa.board.dto.response.BoardDetailResponse;
import jeonghyeon.msa.board.dto.response.CommentResponse;
import jeonghyeon.msa.board.dto.response.PageResponse;
import jeonghyeon.msa.board.dto.response.UsersResponse;
import jeonghyeon.msa.board.repository.redis.ViewCountRepository;
import jeonghyeon.msa.board.service.BoardService;
import jeonghyeon.msa.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class BoardFacade {
    private static final int BACK_UP_BATCH_SIZE = 100;

    private final ViewCountRepository viewCountRepository;
    private final BoardService boardService;
    private final UserService userService;
    private final AuthClient authClient;

    @Transactional
    public Long createBoard(Long usersId, BoardRequest request) {
        Optional<Users> optionalUsers = userService.findByUsersId(usersId);

        if (optionalUsers.isPresent()) {
            return boardService.createBoard(optionalUsers.get(), request);
        }

        UsersResponse userInfo = authClient.getUserInfo(usersId);
        if (isNull(userInfo)) {
            throw new IllegalArgumentException("잘못된 접근입니다");
        }

        Users savedUser = userService.createUser(userInfo);

        return boardService.createBoard(savedUser, request);
    }


    public CommentResponse createComment(Long boardId, CommentRequest request) {
        return boardService.createComment(boardId, request);
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

    public PageResponse getBoards(Pageable pageable, Long pageBlock) {
        return boardService.getBoards(pageable, pageBlock);
    }

    public PageResponse getComments(Long boardId, Pageable pageable, Long pageBlock) {
        return boardService.getComments(boardId, pageable, pageBlock);
    }
}
