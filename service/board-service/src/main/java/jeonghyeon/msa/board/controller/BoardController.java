package jeonghyeon.msa.board.controller;


import jeonghyeon.msa.board.dto.request.BoardRequest;
import jeonghyeon.msa.board.dto.response.BoardDetailResponse;
import jeonghyeon.msa.board.dto.response.PageResponse;
import jeonghyeon.msa.board.facade.BoardFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardFacade boardFacade;

    @PostMapping("/users/{userId}/boards")
    public ResponseEntity createBoard(@PathVariable Long userId, @RequestBody BoardRequest request) {
        Long board = boardFacade.createBoard(userId, request);
        return new ResponseEntity(board, HttpStatus.CREATED);
    }

    @GetMapping("/boards")
    public ResponseEntity getBoards(Pageable pageable) {
        PageResponse boards = boardFacade.getBoards(pageable);
        return new ResponseEntity(boards, HttpStatus.OK);
    }

    @GetMapping("/boards/{boardId}")
    public ResponseEntity getBoardDetail(@PathVariable Long boardId) {
        BoardDetailResponse board = boardFacade.getBoardDetail(boardId);
        return new ResponseEntity(board, HttpStatus.OK);
    }

    @GetMapping("/boards/{boardId}/comments")
    public ResponseEntity getComments(@PathVariable Long boardId, Pageable pageable) {
        PageResponse comments = boardFacade.getComments(boardId, pageable);
        return new ResponseEntity(comments, HttpStatus.OK);
    }

}
