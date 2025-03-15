package jeonghyeon.msa.board.repository;

import jeonghyeon.msa.board.config.QueryDSLConfig;
import jeonghyeon.msa.board.domain.Board;
import jeonghyeon.msa.board.domain.BoardStatus;
import jeonghyeon.msa.board.domain.Users;
import jeonghyeon.msa.board.dto.response.BoardResponse;
import jeonghyeon.msa.board.dto.response.PageResponse;
import jeonghyeon.msa.board.util.PageLimitCalculator;
import jeonghyeon.msa.common.Snowflake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

@Import(QueryDSLConfig.class)
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

    @Test
    void list() {
        List<BoardResponse> list = boardRepository.findList(0L, 10L);
        System.out.println(list);
    }

    @Test
    void count() {
        Long count = boardRepository.count(10L);
        System.out.println(count);
    }

    @Test
    void page() {
        Long pageSize = 10L;
        Long pageNumber = 10L;

        List<BoardResponse> list = boardRepository.findList(pageNumber * pageSize, pageSize);
        Long count = boardRepository.count(
                PageLimitCalculator.calculatePageLimit(pageNumber, pageSize, 10L)
        );
        System.out.println(count);
        PageResponse<BoardResponse> page = new PageResponse<>(pageNumber, pageSize, list, count, 10L);
        System.out.println(page);
    }

    @Test
    void page2() {
        Long pageSize = 10L;
        Long pageNumber = 9L;

        List<BoardResponse> list = boardRepository.findList(pageNumber * pageSize, pageSize);
        Long count = boardRepository.count(
                PageLimitCalculator.calculatePageLimit(pageNumber, pageSize, 10L)
        );
        System.out.println(count);
        PageResponse<BoardResponse> page = new PageResponse<>(pageNumber, pageSize, list, count, 10L);
        System.out.println(page);
    }

    @Test
    void page3() {
        Long pageSize = 10L;
        Long pageNumber = 11L;

        List<BoardResponse> list = boardRepository.findList(pageNumber * pageSize, pageSize);
        Long count = boardRepository.count(
                PageLimitCalculator.calculatePageLimit(pageNumber, pageSize, 10L)
        );
        System.out.println(count);
        PageResponse<BoardResponse> page = new PageResponse<>(pageNumber, pageSize, list, count, 10L);
        System.out.println(page);
    }


}