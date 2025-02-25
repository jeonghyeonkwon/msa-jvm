package jeonghyeon.msa.board.repository;

import jeonghyeon.msa.board.domain.Board;
import jeonghyeon.msa.board.domain.BoardStatus;
import jeonghyeon.msa.board.domain.Comment;
import jeonghyeon.msa.board.domain.Users;
import jeonghyeon.msa.common.Snowflake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentRepositoryTest {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private CommentRepository commentRepository;

    private Snowflake snowflake = new Snowflake();
    private Long usersId = snowflake.nextId();
    private Long boardId = snowflake.nextId();

    @BeforeEach
    void init() {
        Users users = usersRepository.save(new Users(usersId, "givejeong"));

        boardRepository.save(new Board(boardId, "제목", "내용", BoardStatus.NORMAL, users));

    }

    @Test
    @Rollback(value = false)
    void create() {
        Users users = usersRepository.findById(usersId).get();
        Board board = boardRepository.findById(boardId).get();
        Long commentId = snowflake.nextId();
        String content = "댓글";
        commentRepository.save(new Comment(commentId, content, users, board));

        Comment comment = commentRepository.findById(commentId).get();

        assertThat(comment.getContent()).isEqualTo(content);

    }
}