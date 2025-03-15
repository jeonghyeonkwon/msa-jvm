package jeonghyeon.msa.board.repository;

import jeonghyeon.msa.board.domain.Users;
import jeonghyeon.msa.common.Snowflake;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;
    private final Snowflake snowflake = new Snowflake();

    @Test
    void create() {
        long usersId = snowflake.nextId();
        String username = "givejeong";
        usersRepository.save(new Users(usersId, username));

        Users users = usersRepository.findById(usersId).get();

        assertThat(users.getUsername()).isEqualTo(username);
    }
}