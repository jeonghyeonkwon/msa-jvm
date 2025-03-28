package jeonghyeon.msa.board.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "users", indexes = @Index(name = "idx_username", columnList = "username"))
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Users extends BaseTimeEntity {
    @Id
    private Long usersId;

    private String username;

    @OneToMany(mappedBy = "users")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "users")
    private List<Comment> comments = new ArrayList<>();


    @OneToMany(mappedBy = "users")
    private List<BoardLike> boardLikes = new ArrayList<>();


    public Users(Long usersId, String username) {
        this.usersId = usersId;
        this.username = username;
    }

    protected void createBoard(Board board) {
        this.boards.add(board);
    }

    protected void createComment(Comment comment) {
        this.comments.add(comment);
    }

    public void createLike(BoardLike boardLike) {
        this.boardLikes.add(boardLike);
    }
}
