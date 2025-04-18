package jeonghyeon.msa.popularbatch.entity.popularposts;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Posts {
    private static final int VIEW_SCORE = 1;
    private static final int LIKE_SCORE = 2;
    private static final int COMMENT_SCORE = 3;

    @Id
    private Long id;

    @Column(name = "rankScore")
    private Long rankScore;

    @Column(name = "commentCount")
    private Long commentCount;

    public Posts(Long id, Long commentCount, Long viewCount, Long likeCount) {
        this.id = id;
        this.commentCount = commentCount;
        this.rankScore = calculateRankScore(commentCount, viewCount, likeCount);
    }

    private Long calculateRankScore(Long commentCount, Long viewCount, Long likeCount) {
        return commentCount * COMMENT_SCORE + viewCount * VIEW_SCORE + likeCount * LIKE_SCORE;
    }



}
