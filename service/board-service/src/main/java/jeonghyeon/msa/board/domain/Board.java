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

    @OneToMany(mappedBy = "board")
    private List<BoardLike> boardLikes = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private BoardStatus boardStatus;

    private Long viewCount;

    @Version
    private Long likeCount;


    public Board(Long boardId, String title, String content, BoardStatus boardStatus, Users users) {
        validate(title, content);
        users.createBoard(this);
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.boardStatus = boardStatus;
        this.users = users;
        this.viewCount = 0L;
        this.likeCount = 0L;
    }

    private void validate(String title, String content) {
        isBlank(title);
        isBlank(content);
    }

    private void isBlank(String text) {
        if (text == null || text.isBlank()) throw new IllegalArgumentException("빈 값을 넣을 수 없습니다.");
    }

    protected void createComment(Comment comment) {
        this.comments.add(comment);
    }


    public void createLike(BoardLike boardLike) {
        this.boardLikes.add(boardLike);
    }

    public void addLikeCount() {
        this.likeCount++;
    }

    public void minusLikeCount() {
        this.likeCount--;
    }
}
