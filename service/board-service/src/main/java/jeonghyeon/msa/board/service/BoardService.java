package jeonghyeon.msa.board.service;


import jeonghyeon.msa.board.domain.Board;
import jeonghyeon.msa.board.domain.BoardStatus;
import jeonghyeon.msa.board.domain.Comment;
import jeonghyeon.msa.board.domain.Users;
import jeonghyeon.msa.board.dto.request.BoardRequest;
import jeonghyeon.msa.board.dto.request.CommentRequest;
import jeonghyeon.msa.board.dto.request.UsersRequest;
import jeonghyeon.msa.board.dto.response.CommentResponse;
import jeonghyeon.msa.board.kafka.handle.EventHandler;
import jeonghyeon.msa.board.repository.BoardRepository;
import jeonghyeon.msa.board.repository.CommentRepository;
import jeonghyeon.msa.board.repository.UsersRepository;
import jeonghyeon.msa.common.Snowflake;
import jeonghyeon.msa.common.event.Event;
import jeonghyeon.msa.common.event.EventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final UsersRepository usersRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final Snowflake snowflake = new Snowflake();
    private final List<EventHandler> eventHandlers;


    @Transactional
    public void handleEvent(Event<EventPayload> event) {
        for (EventHandler eventHandler : eventHandlers){
            if(eventHandler.supports(event)){
                eventHandler.handle(event);
            }
        }
    }

    @Transactional
    public void createUser(UsersRequest request) {
        usersRepository.findById(request.getUsersId()).ifPresent(
                notUsed -> {
                    throw new IllegalArgumentException("이미 가입된 회원입니다");
                }
        );
        usersRepository.save(new Users(request.getUsersId(), request.getUsername()));
    }

    @Transactional
    public Long createBoard(Long usersId, BoardRequest request) {

        Users users = usersRepository.findById(usersId)
                .orElseThrow(
                        () -> new IllegalArgumentException("잘못된 접근 입니다.")
                );

        Board board = new Board(snowflake.nextId(), request.getTitle(), request.getContent(), BoardStatus.NORMAL, users);
        Board savedBoard = boardRepository.save(board);

        return savedBoard.getBoardId();
    }

    @Transactional
    public CommentResponse createComment(Long usersId, Long boardId, CommentRequest request) {
        Users users = usersRepository.findById(usersId)
                .orElseThrow(
                        () -> new IllegalArgumentException("잘못된 접근 입니다.")
                );
        Board board = boardRepository.findById(boardId)
                .orElseThrow(
                        () -> new IllegalArgumentException("잘못된 접근 입니다.")
                );

        Comment savedComment = commentRepository.save(new Comment(snowflake.nextId(), request.getContent(), users, board));

        return new CommentResponse(
                savedComment.getCommentId(), usersId, users.getUsername(), savedComment.getContent(), savedComment.getCreatedDate()
        );
    }



}
