package jeonghyeon.msa.auth.service;

import jeonghyeon.msa.auth.domain.Users;
import jeonghyeon.msa.auth.dto.request.RegisterDto;
import jeonghyeon.msa.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootApplication
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    @Test
    void 중복_검증() {
        when(userRepository.findByUsername("givejeong"))
                .thenReturn(Optional.of(Users.createBasic(1L, "givejeong", "1234", "test")));

        assertThrows(IllegalArgumentException.class,()->{
            userService.register(new RegisterDto("givejeong","1234","닉네임"));
        });

    }
}