package jeonghyeon.msa.board.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jeonghyeon.msa.board.domain.Board;
import jeonghyeon.msa.board.domain.BoardStatus;
import jeonghyeon.msa.board.domain.Users;
import jeonghyeon.msa.common.Snowflake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DataInitializer {

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    TransactionTemplate transactionTemplate;
    Snowflake snowflake = new Snowflake();
    CountDownLatch latch = new CountDownLatch(EXECUTE_COUNT);

    Users users;
    static final int BULK_INSERT_SIZE = 2000;
    static final int EXECUTE_COUNT = 6000;

    @BeforeEach
    @Disabled
    void init() {
        users = new Users(snowflake.nextId(), "givejeong");
        transactionTemplate.executeWithoutResult(
                transactionStatus -> {
                    entityManager.persist(users);
                }
        );
    }

    @Test
    @Disabled
    void initialize() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);


        for (int i = 0; i < EXECUTE_COUNT; i++) {
            executorService.submit(() -> {
                insert();
                latch.countDown();
                System.out.println("latch.getCount() = " + latch.getCount());
            });
        }
        latch.await();
        executorService.shutdown();
    }

    void insert() {
        transactionTemplate.executeWithoutResult(status -> {
            for (int i = 0; i < BULK_INSERT_SIZE; i++) {
                Board board = new Board(snowflake.nextId(), "제목" + i, "내용" + i, BoardStatus.NORMAL, users);
                entityManager.persist(board);
            }
        });
    }
}
