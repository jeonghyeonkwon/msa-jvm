package jeonghyeon.msa.board.service;


import jeonghyeon.msa.board.domain.Board;
import jeonghyeon.msa.board.domain.BoardStatus;
import jeonghyeon.msa.board.domain.Comment;
import jeonghyeon.msa.board.domain.Users;
import jeonghyeon.msa.board.dto.request.BoardRequest;
import jeonghyeon.msa.board.dto.request.CommentRequest;
import jeonghyeon.msa.board.dto.response.BoardDetailResponse;
import jeonghyeon.msa.board.dto.response.BoardResponse;
import jeonghyeon.msa.board.dto.response.CommentResponse;
import jeonghyeon.msa.board.dto.response.PageResponse;
import jeonghyeon.msa.board.kafka.handle.EventHandler;
import jeonghyeon.msa.board.repository.BoardRepository;
import jeonghyeon.msa.board.repository.CommentRepository;
import jeonghyeon.msa.board.repository.UsersRepository;
import jeonghyeon.msa.board.util.PageLimitCalculator;
import jeonghyeon.msa.common.Snowflake;
import jeonghyeon.msa.common.event.Event;
import jeonghyeon.msa.common.event.EventPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BoardService {
    private final UsersRepository usersRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final Snowflake snowflake = new Snowflake();
    private final List<EventHandler> eventHandlers;

    @Transactional
    public void handleEvent(Event<EventPayload> event) {
        for (EventHandler eventHandler : eventHandlers) {
            if (eventHandler.supports(event)) {
                eventHandler.handle(event);
            }
        }
    }


    @Transactional
    public Long createBoard(Users users, BoardRequest request) {

        Board board = new Board(snowflake.nextId(), request.getTitle(), request.getContent(), BoardStatus.NORMAL, users);
        Board savedBoard = boardRepository.save(board);

        return savedBoard.getBoardId();
    }

    @Transactional
    public CommentResponse createComment(Long boardId, CommentRequest request) {
        Users users = usersRepository.findById(request.getUsersId())
                .orElseThrow(
                        () -> new IllegalArgumentException("잘못된 접근 입니다.")
                );
        Board board = boardRepository.findById(boardId)
                .orElseThrow(
                        () -> new IllegalArgumentException("잘못된 접근 입니다.")
                );

        if (request.getParentId() != null && request.getParentId() != 0L) {
            Comment parentComment = commentRepository.findById(request.getParentId())
                    .orElseThrow(
                            () -> new IllegalArgumentException("잘못된 접근 입니다.")
                    );
            Comment savedComment = commentRepository.save(new Comment(snowflake.nextId(), request.getContent(), users, board, parentComment));
            return new CommentResponse(savedComment.getCommentId(), parentComment.getCommentId(), users.getUsername(), savedComment.getContent(), savedComment.getCreatedDate());
        }


        Comment savedComment = commentRepository.save(new Comment(snowflake.nextId(), request.getContent(), users, board));

        return new CommentResponse(
                savedComment.getCommentId(), request.getUsersId(), users.getUsername(), savedComment.getContent(), savedComment.getCreatedDate()
        );
    }

    @Transactional
    public BoardDetailResponse getBoardDetail(Long boardId) {
        BoardDetailResponse board = boardRepository.getBoardDetail(boardId);

        return board;
    }


    public BoardDetailResponse getBoardDetailAndViewCount(Long boardId) {
        BoardDetailResponse board = boardRepository.getBoardDetailAndViewCount(boardId);
        return board;
    }

    public void updateViewCount(Long boardId, Long count) {
        boardRepository.updateViewCount(boardId, count);
    }

    public PageResponse getBoards(Pageable pageable, Long pageBlock) {
        Long pageSize = Long.valueOf(pageable.getPageSize());
        Long pageNumber = Long.valueOf(pageable.getPageNumber());

        List<BoardResponse> list = boardRepository.findList(pageNumber * pageSize, pageSize);

        List<Long> boardIds = list.stream().map(dto -> Long.valueOf(dto.getBoardId())).collect(Collectors.toList());

        Map<Long, Long> countByBoardIds = commentRepository.findCountByBoardIds(boardIds);

        list.stream().forEach(board -> board.setCommentCount(
                countByBoardIds.containsKey(board.getBoardId()) ? countByBoardIds.get(board.getBoardId()) : 0L)
        );

        Long count = boardRepository.count(
                PageLimitCalculator.calculatePageLimit(pageNumber, pageSize, pageBlock)
        );
        return new PageResponse<BoardResponse>(pageNumber, pageSize, list, count, pageBlock);
    }

    public PageResponse getComments(Long boardId, Pageable pageable, Long pageBlock) {
        Long pageSize = Long.valueOf(pageable.getPageSize());
        Long pageNumber = Long.valueOf(pageable.getPageNumber());

        log.info("boardId={}", boardId);
        log.info("pageSize={}", pageSize);
        log.info("pageNumber={}", pageNumber);
        log.info("pageBlock={}", pageBlock);
        List<CommentResponse> list = commentRepository.findList(boardId, pageNumber * pageSize, pageSize);

        Long count = commentRepository.count(
                boardId, PageLimitCalculator.calculatePageLimit(pageNumber, pageSize, pageBlock)
        );
        log.info("count={}", count);
        return new PageResponse<CommentResponse>(pageNumber, pageSize, list, count, pageBlock);
    }
}
