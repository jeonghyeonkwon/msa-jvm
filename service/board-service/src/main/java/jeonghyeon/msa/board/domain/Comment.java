package jeonghyeon.msa.board.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    private Long commentId;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public Comment(Long commentId, String content, Users users, Board board) {
        isBlank(content);
        users.createComment(this);
        board.createComment(this);
        this.commentId = commentId;
        this.content = content;
        this.users = users;
        this.board = board;
    }

    private void isBlank(String content) {
        if (content == null || content.isBlank()) throw new IllegalArgumentException("빈 값을 넣을 수 없습니다.");
    }
}