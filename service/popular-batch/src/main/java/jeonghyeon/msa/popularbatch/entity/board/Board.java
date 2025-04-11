package jeonghyeon.msa.popularbatch.entity.board;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name="board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    private Long boardId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    private Users users;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    private LocalDateTime createdDate;

    private Long viewCount;

    private Long likeCount;


}
