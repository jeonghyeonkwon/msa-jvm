package jeonghyeon.msa.board.repository;

import jeonghyeon.msa.board.domain.Board;
import jeonghyeon.msa.board.domain.BoardStatus;
import jeonghyeon.msa.board.domain.Users;
import jeonghyeon.msa.common.Snowflake;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BoardRepositoryTest {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private BoardRepository boardRepository;
    private Snowflake snowflake = new Snowflake();
    private Long usersId = snowflake.nextId();

    @BeforeEach
    void init() {
        usersRepository.save(new Users(usersId, "givejeong"));
    }

    @Test
    @Rollback(value = false)
    void create() {
        Users users = usersRepository.findById(usersId).get();
        Long createBoardId = snowflake.nextId();
        String title = "제목";
        String content = "내용";
        boardRepository.save(new Board(createBoardId, title, content, BoardStatus.NORMAL, users));

        Board board = boardRepository.findById(createBoardId).get();
        System.out.println(board.getBoardId());
        assertAll(
                () -> assertThat(board.getTitle()).isEqualTo(title),
                () -> assertThat(board.getContent()).isEqualTo(content)
        );

    }


}