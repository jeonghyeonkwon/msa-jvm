package jeonghyeon.msa.board.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity {
    @Id
    private Long boardId;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private BoardStatus boardStatus;

    public Board(Long boardId, String title, String content, BoardStatus boardStatus, Users users) {
        users.createBoard(this);
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.boardStatus = boardStatus;
        this.users = users;
    }

    protected void createComment(Comment comment) {
        this.comments.add(comment);
    }
}
